package com.globits.da.validation;

import com.globits.da.domain.District;
import com.globits.da.dto.DistrictDto;
import com.globits.da.exception.DuplicateException;
import com.globits.da.exception.NotFoundException;
import com.globits.da.exception.NotNullException;
import com.globits.da.exception.OctException;
import com.globits.da.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Component
public class DistrictValidation {
    private final DistrictRepository districtRepository;
    private final ProvinceValidation provinceValidation;

    @Autowired
    public DistrictValidation(DistrictRepository districtRepository,
                              ProvinceValidation provinceValidation) {
        this.districtRepository = districtRepository;
        this.provinceValidation = provinceValidation;
    }

    public void checkExist(UUID id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new NotNullException(ErrorValidation.DISTRICT_ID_NOTNULL);
        }
        if (!districtRepository.existsById(id)) {
            throw new NotFoundException(ErrorValidation.DISTRICT_ID_NOT_FOUND);
        }
    }

    public void checkName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new NotNullException(ErrorValidation.DISTRICT_NAME_NOTNULL);
        }
        if (districtRepository.existByName(name)) {
            throw new DuplicateException(ErrorValidation.DISTRICT_NAME_DUPLICATE);
        }
    }

    public void checkDistrictInProvince(UUID id, UUID provinceId) {
        District district = districtRepository.getOne(id);
        if (!district.getProvince().getId().equals(provinceId)) {
            throw new OctException(ErrorValidation.DISTRICT_NOT_IN_PROVINCE);
        }
    }

    public void checkDistrict(DistrictDto districtDto) {
        checkName(districtDto.getName());
        provinceValidation.checkExist(districtDto.getProvinceId());
    }
}
