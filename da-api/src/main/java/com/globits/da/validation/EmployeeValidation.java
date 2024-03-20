package com.globits.da.validation;

import com.globits.da.constant.Constant;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.exception.DuplicateException;
import com.globits.da.exception.NotFoundException;
import com.globits.da.exception.NotNullException;
import com.globits.da.exception.OctException;
import com.globits.da.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmployeeValidation {
    private final EmployeeRepository employeeRepository;
    private final ProvinceValidation provinceValidation;
    private final DistrictValidation districtValidation;
    private final CommuneValidation communeValidation;

    @Autowired
    public EmployeeValidation(EmployeeRepository employeeRepository,
                              ProvinceValidation provinceValidation,
                              DistrictValidation districtValidation,
                              CommuneValidation communeValidation) {
        this.employeeRepository = employeeRepository;
        this.provinceValidation = provinceValidation;
        this.districtValidation = districtValidation;
        this.communeValidation = communeValidation;
    }

    public void checkExist(UUID id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new NotNullException(ErrorValidation.EMPLOYEE_ID_NOTNULL);
        }
        if (!employeeRepository.existsById(id)) {
            throw new NotFoundException(ErrorValidation.EMPLOYEE_ID_NOT_FOUND);
        }
    }

    public void checkCode(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new NotNullException(ErrorValidation.EMPLOYEE_CODE_NOTNULL);
        }
        if (employeeRepository.existByCode(code)) {
            throw new DuplicateException(ErrorValidation.EMPLOYEE_CODE_DUPLICATE);
        }
        if (code.contains(" ")) { // can use StringUtils.hasText(code) -> check blank
            throw new OctException(ErrorValidation.EMPLOYEE_CODE_NOT_BLANK);
        }
        if (code.length() < Constant.CODE_LENGTH_MIN || code.length() > Constant.CODE_LENGTH_MAX) {
            throw new OctException(ErrorValidation.EMPLOYEE_CODE_LENGTH);
        }
    }

    public void checkName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new NotNullException(ErrorValidation.EMPLOYEE_NAME_NOTNULL);
        }
    }

    public void checkEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            throw new NotNullException(ErrorValidation.EMPLOYEE_EMAIL_NOTNULL);
        }
        Pattern pattern = Pattern.compile(Constant.EMAIL_REGEXP);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new OctException(ErrorValidation.EMPLOYEE_EMAIL_WRONG_FORMAT);
        }
    }

    public void checkPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            throw new NotNullException(ErrorValidation.EMPLOYEE_PHONE_NOTNULL);
        }
        Pattern pattern = Pattern.compile(Constant.PHONE_REGEXP);
        Matcher matcher = pattern.matcher(phone);
        if (!matcher.matches()) {
            throw new OctException(ErrorValidation.EMPLOYEE_PHONE_WRONG_FORMAT);
        }
    }

    public void checkAge(Integer age) {
        if (age == null) {
            throw new OctException(ErrorValidation.EMPLOYEE_AGE_NOTNULL);
        }
        if (age < Constant.AGE_MIN) {
            throw new OctException(ErrorValidation.EMPLOYEE_AGE_NEGATIVE);
        }
    }

    public void checkEmployee(EmployeeDTO employeeDTO) {
        checkCode(employeeDTO.getCode());
        checkName(employeeDTO.getName());
        checkEmail(employeeDTO.getEmail());
        checkPhone(employeeDTO.getPhone());
        checkAge(employeeDTO.getAge());
        provinceValidation.checkExist(employeeDTO.getProvinceId());
        districtValidation.checkExist(employeeDTO.getDistrictId());
        districtValidation.checkDistrictInProvince(employeeDTO.getDistrictId(), employeeDTO.getProvinceId());
        communeValidation.checkExist(employeeDTO.getCommuneId());
        communeValidation.checkCommuneInDistrict(employeeDTO.getCommuneId(), employeeDTO.getDistrictId());

    }
}
