package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.CertificateDetail;
import com.globits.da.dto.CertificateDetailDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CertificateDetailService extends GenericService<CertificateDetail, UUID> {
    CertificateDetailDto create(CertificateDetailDto certificateDetailDto);

    Page<CertificateDetailDto> getListPage(int pageIndex, int pageSize);

    CertificateDetailDto getById(UUID id);

    CertificateDetailDto update(UUID id, CertificateDetailDto certificateDetailDto);

    void deleteById(UUID id);
}
