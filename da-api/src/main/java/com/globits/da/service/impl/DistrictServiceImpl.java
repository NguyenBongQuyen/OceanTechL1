package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.DistrictDto;
import com.globits.da.exception.NotFoundException;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.repository.ProvinceRepository;
import com.globits.da.service.DistrictService;
import com.globits.da.validation.DistrictValidation;
import com.globits.da.validation.ErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class DistrictServiceImpl extends GenericServiceImpl<District, UUID> implements DistrictService {
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final DistrictValidation districtValidation;

    @Autowired
    public DistrictServiceImpl(ProvinceRepository provinceRepository,
                               DistrictRepository districtRepository,
                               DistrictValidation districtValidation) {
        this.provinceRepository = provinceRepository;
        this.districtRepository = districtRepository;
        this.districtValidation = districtValidation;
    }

    public void setDistrictValue(District district, DistrictDto districtDto) {
        district.setName(districtDto.getName());
        Province province = provinceRepository.findById(districtDto.getProvinceId())
                .orElseThrow(() -> new NotFoundException(ErrorValidation.PROVINCE_ID_NOT_FOUND));
        district.setProvince(province);
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

    @Override
    public DistrictDto create(DistrictDto districtDto) {
        districtValidation.checkDistrict(districtDto);
        District district = new District();
        setDistrictValue(district, districtDto);
        return new DistrictDto(districtRepository.save(district));
    }

    @Override
    public Page<DistrictDto> getListPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex-1, pageSize);
        return districtRepository.getListPage(pageable);
    }

    @Override
    public DistrictDto getById(UUID id) {
        districtValidation.checkExist(id);
        return districtRepository.getById(id);
    }

    @Override
    public List<DistrictDto> getByProvinceId(UUID provinceId) {
        return districtRepository.getByProvinceId(provinceId);
    }

    @Override
    public DistrictDto update(UUID id, DistrictDto districtDto) {
        District currentDistrict = districtRepository.findById(districtDto.getId())
                .orElseThrow(() -> new NotFoundException(ErrorValidation.DISTRICT_ID_NOT_FOUND));
        if (!currentDistrict.getName().equals(districtDto.getName())) {
            districtValidation.checkDistrict(districtDto);
        }
        setDistrictValue(currentDistrict, districtDto);
        districtRepository.save(currentDistrict);
        return new DistrictDto(currentDistrict);
    }

    @Override
    public void deleteById(UUID id) {
        districtValidation.checkExist(id);
        districtRepository.deleteById(id);
    }
}
