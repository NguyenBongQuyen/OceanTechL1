package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Certificate;
import com.globits.da.dto.CertificateDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CertificateService extends GenericService<Certificate, UUID> {
    CertificateDto create(CertificateDto certificateDto);

    Page<CertificateDto> getListPage(int pageIndex, int pageSize);

    CertificateDto getById(UUID id);

    CertificateDto update(UUID id, CertificateDto certificateDto);

    void deleteById(UUID id);
}
