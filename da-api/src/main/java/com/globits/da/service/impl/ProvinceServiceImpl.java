package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.exception.NotFoundException;
import com.globits.da.repository.ProvinceRepository;
import com.globits.da.service.ProvinceService;
import com.globits.da.validation.ErrorValidation;
import com.globits.da.validation.ProvinceValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class ProvinceServiceImpl extends GenericServiceImpl<Province, UUID> implements ProvinceService {
    private final ProvinceRepository provinceRepository;
    private final ProvinceValidation provinceValidation;

    @Autowired
    public ProvinceServiceImpl(ProvinceRepository provinceRepository,
                               ProvinceValidation provinceValidation) {
        this.provinceRepository = provinceRepository;
        this.provinceValidation = provinceValidation;
    }

    public void setProvinceValue(Province province, ProvinceDto provinceDto) {
        province.setName(provinceDto.getName());
        Set<DistrictDto> districtDtoSet = provinceDto.getDistrictDtoSet();
        Set<District> districtSet = new LinkedHashSet<>();
        province.setDistricts(districtSet);
        if (!ObjectUtils.isEmpty(districtDtoSet)) {
            for (DistrictDto districtDto: districtDtoSet) {
                District district = new District();
                district.setName(districtDto.getName());
                district.setProvince(province);
                districtSet.add(district);
                Set<CommuneDto> communeDtoSet = districtDto.getCommuneDtoSet();
                Set<Commune> communeSet = new LinkedHashSet<>();
                district.setCommunes(communeSet);
                if (!ObjectUtils.isEmpty(communeDtoSet)) {
                    for (CommuneDto communeDto: communeDtoSet) {
                        Commune commune = new Commune();
                        commune.setName(communeDto.getName());
                        commune.setDistrict(district);
                        communeSet.add(commune);
                    }
                }
            }
        }
    }

    @Override
    public ProvinceDto create(ProvinceDto provinceDto) {
        provinceValidation.checkProvince(provinceDto);
        Province province = new Province();
        setProvinceValue(province, provinceDto);
        return new ProvinceDto(provinceRepository.save(province));
    }

    @Override
    public Page<ProvinceDto> getListPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex-1, pageSize);
        return provinceRepository.getListPage(pageable);
    }

    @Override
    public ProvinceDto getById(UUID id) {
        provinceValidation.checkExist(id);
        return provinceRepository.getById(id);
    }

    @Override
    public ProvinceDto update(UUID id, ProvinceDto provinceDto) {
        Province currentProvince = provinceRepository.findById(provinceDto.getId())
                .orElseThrow(() -> new NotFoundException(ErrorValidation.PROVINCE_ID_NOT_FOUND));
        if (!currentProvince.getName().equals(provinceDto.getName())) {
            provinceValidation.checkProvince(provinceDto);
        }
        setProvinceValue(currentProvince, provinceDto);
        provinceRepository.save(currentProvince);
        return new ProvinceDto(currentProvince);
    }

    @Override
    public void deleteById(UUID id) {
        provinceValidation.checkExist(id);
        provinceRepository.deleteById(id);
    }
}
