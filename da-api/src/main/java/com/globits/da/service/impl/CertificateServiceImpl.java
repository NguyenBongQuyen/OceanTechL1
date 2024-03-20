package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Certificate;
import com.globits.da.dto.CertificateDto;
import com.globits.da.exception.NotFoundException;
import com.globits.da.repository.CertificateRepository;
import com.globits.da.service.CertificateService;
import com.globits.da.validation.CertificateValidation;
import com.globits.da.validation.ErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CertificateServiceImpl extends GenericServiceImpl<Certificate, UUID> implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final CertificateValidation certificateValidation;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository,
                                  CertificateValidation certificateValidation) {
        this.certificateRepository = certificateRepository;
        this.certificateValidation = certificateValidation;
    }

    public void setCertificateValue(Certificate certificate, CertificateDto certificateDto) {
        certificate.setName(certificateDto.getName());
        certificate.setCreateDate(certificateDto.getCreateDate());
        certificate.setCreatedBy(certificateDto.getCreatedBy());
        certificate.setModifyDate(certificateDto.getModifyDate());
        certificate.setModifiedBy(certificateDto.getModifiedBy());
    }

    @Override
    public CertificateDto create(CertificateDto certificateDto) {
        certificateValidation.checkCertificate(certificateDto);
        Certificate certificate = new Certificate();
        setCertificateValue(certificate, certificateDto);
        return new CertificateDto(certificateRepository.save(certificate));
    }

    @Override
    public Page<CertificateDto> getListPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex-1, pageSize);
        return certificateRepository.getListPage(pageable);
    }

    @Override
    public CertificateDto getById(UUID id) {
        certificateValidation.checkExist(id);
        return certificateRepository.getById(id);
    }

    @Override
    public CertificateDto update(UUID id, CertificateDto certificateDto) {
        Certificate currentCertificate = certificateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorValidation.CERTIFICATE_ID_NOT_FOUND));
        if (!currentCertificate.getName().equals(certificateDto.getName())) {
            certificateValidation.checkCertificate(certificateDto);
        }
        setCertificateValue(currentCertificate, certificateDto);
        certificateRepository.save(currentCertificate);
        return new CertificateDto(currentCertificate);
    }

    @Override
    public void deleteById(UUID id) {
        certificateValidation.checkExist(id);
        certificateRepository.deleteById(id);
    }
}
