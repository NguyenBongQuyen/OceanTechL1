package com.globits.da.validation;

import com.globits.da.constant.Constant;
import com.globits.da.dto.CertificateDetailDto;
import com.globits.da.exception.NotFoundException;
import com.globits.da.exception.NotNullException;
import com.globits.da.exception.OctException;
import com.globits.da.repository.CertificateDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class CertificateDetailValidation {
    private final CertificateDetailRepository certificateDetailRepository;
    private final CertificateValidation certificateValidation;
    private final EmployeeValidation employeeValidation;
    private final ProvinceValidation provinceValidation;

    @Autowired
    public CertificateDetailValidation(CertificateDetailRepository certificateDetailRepository,
                                       CertificateValidation certificateValidation,
                                       EmployeeValidation employeeValidation,
                                       ProvinceValidation provinceValidation) {
        this.certificateDetailRepository = certificateDetailRepository;
        this.certificateValidation = certificateValidation;
        this.employeeValidation = employeeValidation;
        this.provinceValidation = provinceValidation;
    }

    public void checkExist(UUID id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new NotNullException(ErrorValidation.CERTIFICATE_DETAIL_ID_NOTNULL);
        }
        if (!certificateDetailRepository.existsById(id)) {
            throw new NotFoundException(ErrorValidation.CERTIFICATE_DETAIL_ID_NOT_FOUND);
        }
    }

    public void checkStartDay(LocalDate startDay) {
        if (StringUtils.isEmpty(startDay)) {
            throw new NotNullException(ErrorValidation.CERTIFICATE_START_DAY_NOTNULL);
        }
    }

    public void checkEndDay(LocalDate endDay) {
        if (StringUtils.isEmpty(endDay)) {
            throw new NotNullException(ErrorValidation.CERTIFICATE_END_DAY_NOTNULL);
        }
    }

    public void checkCertificateInUseEmployee(UUID certificateId, UUID employeeId, UUID provinceId) {
        if (certificateDetailRepository.existCertificateInUseEmployee(certificateId, employeeId, provinceId)) {
            throw new OctException(ErrorValidation.CERTIFICATE_DETAIL_IS_BEING_USED);
        }
    }

    public void checkQuantity(UUID certificateId, UUID employeeId) {
        if (certificateDetailRepository.countCertificateInUseEmployee(certificateId, employeeId) >= Constant.CERTIFICATE_QUANTITY_MAX) {
            throw new OctException(ErrorValidation.CERTIFICATE_DETAIL_IS_LIMITED);
        }
    }

    public void checkCertificateDetail(CertificateDetailDto certificateDetailDto) {
        certificateValidation.checkExist(certificateDetailDto.getCertificateId());
        employeeValidation.checkExist(certificateDetailDto.getEmployeeId());
        provinceValidation.checkExist(certificateDetailDto.getProvinceId());
        checkStartDay(certificateDetailDto.getStartDay());
        checkEndDay(certificateDetailDto.getEndDay());
        checkCertificateInUseEmployee(certificateDetailDto.getCertificateId(),
                                      certificateDetailDto.getEmployeeId(),
                                      certificateDetailDto.getProvinceId());
        checkQuantity(certificateDetailDto.getCertificateId(), certificateDetailDto.getEmployeeId());
    }
}
