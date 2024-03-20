package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.search.SearchCommuneDto;
import com.globits.da.exception.NotFoundException;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.service.CommuneService;
import com.globits.da.validation.CommuneValidation;
import com.globits.da.validation.ErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Service
public class CommuneServiceImpl extends GenericServiceImpl<Commune, UUID> implements CommuneService {
    private final DistrictRepository districtRepository;
    private final CommuneRepository communeRepository;
    private final CommuneValidation communeValidation;

    @Autowired
    public CommuneServiceImpl(DistrictRepository districtRepository,
                              CommuneRepository communeRepository,
                              CommuneValidation communeValidation) {
        this.districtRepository = districtRepository;
        this.communeRepository = communeRepository;
        this.communeValidation = communeValidation;
    }

    public void setCommuneValue(Commune commune, CommuneDto communeDto) {
        commune.setName(communeDto.getName());
        District district = districtRepository.findById(communeDto.getDistrictId())
                .orElseThrow(() -> new NotFoundException(ErrorValidation.DISTRICT_ID_NOT_FOUND));
        commune.setDistrict(district);
    }

    @Override
    public CommuneDto create(CommuneDto communeDto) {
        communeValidation.checkCommune(communeDto);
        Commune commune = new Commune();
        setCommuneValue(commune, communeDto);
        return new CommuneDto(communeRepository.save(commune));
    }

    @Override
    public Page<CommuneDto> getListPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex-1, pageSize);
        return communeRepository.getListPage(pageable);
    }

public Page<CommuneDto> searchByPage(SearchCommuneDto searchCommuneDto) {
    if (searchCommuneDto == null) {
        return null;
    }

    int pageIndex = Math.max(0, searchCommuneDto.getPageIndex());
    int pageSize = searchCommuneDto.getPageSize() <= 0 ? 10 : searchCommuneDto.getPageSize();

    String whereClause = "";
    String orderBy = "ORDER BY entity.createDate DESC";

    String sql = buildSqlQuery(searchCommuneDto, whereClause, orderBy);
    String sqlCount = buildSqlCountQuery(searchCommuneDto, whereClause);

    Query q = manager.createQuery(sql, CommuneDto.class);
    Query qCount = manager.createQuery(sqlCount);

    setParameters(q, qCount, searchCommuneDto);

    int startPosition = pageIndex * pageSize;
    q.setFirstResult(startPosition);
    q.setMaxResults(pageSize);

    List<CommuneDto> entities = q.getResultList();

    long count = (long) qCount.getSingleResult();

    Pageable pageable = PageRequest.of(pageIndex, pageSize);
    return new PageImpl<>(entities, pageable, count);
}

    private String buildSqlQuery(SearchCommuneDto searchCommuneDto, String whereClause, String orderBy) {
        String sql = "SELECT NEW com.globits.da.dto.CommuneDto(entity) FROM Commune AS entity WHERE (1=1) ";
        if (searchCommuneDto.getKeyword() != null && StringUtils.hasText(searchCommuneDto.getKeyword())) {
            whereClause += " AND (entity.name LIKE :text)";
        }
        return sql + whereClause + orderBy;
    }

    private String buildSqlCountQuery(SearchCommuneDto searchCommuneDto, String whereClause) {
        return "SELECT COUNT(entity.id) FROM Commune AS entity WHERE (1=1) " + whereClause;
    }

    private void setParameters(Query q, Query qCount, SearchCommuneDto searchCommuneDto) {
        if (searchCommuneDto.getKeyword() != null && StringUtils.hasText(searchCommuneDto.getKeyword())) {
            q.setParameter("text", '%' + searchCommuneDto.getKeyword() + '%');
            qCount.setParameter("text", '%' + searchCommuneDto.getKeyword() + '%');
        }
    }


    @Override
    public CommuneDto getById(UUID id) {
        communeValidation.checkExist(id);
        return communeRepository.getById(id);
    }

    @Override
    public List<CommuneDto> getByDistrictId(UUID districtId) {
        return communeRepository.getByDistrictId(districtId);
    }

    @Override
    public CommuneDto update(UUID id, CommuneDto communeDto) {
        Commune currentCommune = communeRepository.findById(communeDto.getId())
                .orElseThrow(() -> new NotFoundException(ErrorValidation.COMMUNE_ID_NOT_FOUND));
        if (!currentCommune.getName().equals(communeDto.getName())) {
            communeValidation.checkCommune(communeDto);
        }
        setCommuneValue(currentCommune, communeDto);
        communeRepository.save(currentCommune);
        return new CommuneDto(currentCommune);
    }

    @Override
    public void deleteById(UUID id) {
        communeValidation.checkExist(id);
        communeRepository.deleteById(id);
    }
}

