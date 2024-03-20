package com.globits.da.service;

import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.search.SearchCommuneDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface CommuneService {
    CommuneDto create(CommuneDto communeDto);

    Page<CommuneDto> getListPage(int pageIndex, int pageSize);

    Page<CommuneDto> searchByPage(SearchCommuneDto searchCommuneDto);

    CommuneDto getById(UUID id);

    List<CommuneDto> getByDistrictId(UUID districtId);

    CommuneDto update(UUID id, CommuneDto communeDto);

    void deleteById(UUID id);
}
