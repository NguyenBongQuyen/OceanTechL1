package com.globits.da.validation;

import com.globits.da.dto.CertificateDto;
import com.globits.da.exception.DuplicateException;
import com.globits.da.exception.NotFoundException;
import com.globits.da.exception.NotNullException;
import com.globits.da.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Component
public class CertificateValidation {
    private final CertificateRepository certificateRepository;

    @Autowired
    public CertificateValidation(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    public void checkExist(UUID id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new NotNullException(ErrorValidation.CERTIFICATE_ID_NOTNULL);
        }
        if (!certificateRepository.existsById(id)) {
            throw new NotFoundException(ErrorValidation.CERTIFICATE_ID_NOT_FOUND);
        }
    }

    public void checkName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new NotNullException(ErrorValidation.CERTIFICATE_NAME_NOTNULL);
        }
        if (certificateRepository.existByName(name)) {
            throw new DuplicateException(ErrorValidation.CERTIFICATE_NAME_DUPLICATE);
        }
    }

    public void checkCertificate(CertificateDto certificateDto) {
        checkName(certificateDto.getName());
    }
}
