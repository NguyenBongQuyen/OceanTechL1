package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.District;
import com.globits.da.dto.DistrictDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface DistrictService extends GenericService<District, UUID> {
    DistrictDto create(DistrictDto districtDto);

    Page<DistrictDto> getListPage(int pageIndex, int pageSize);

    DistrictDto getById(UUID id);

    List<DistrictDto> getByProvinceId(UUID provinceId);

    DistrictDto update(UUID id, DistrictDto districtDto);

    void deleteById(UUID id);
}
