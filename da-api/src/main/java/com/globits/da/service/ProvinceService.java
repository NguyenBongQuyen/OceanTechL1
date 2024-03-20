package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Province;
import com.globits.da.dto.ProvinceDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProvinceService extends GenericService<Province, UUID> {
    ProvinceDto create(ProvinceDto provinceDto);

    Page<ProvinceDto> getListPage(int pageIndex, int pageSize);

    ProvinceDto getById(UUID id);

    ProvinceDto update(UUID id, ProvinceDto provinceDto);

    void deleteById(UUID id);
}
