package com.globits.da.repository;

import com.globits.da.domain.District;
import com.globits.da.dto.DistrictDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DistrictRepository extends JpaRepository<District, UUID> {
    @Query(value = "SELECT NEW com.globits.da.dto.DistrictDto(d) FROM District d",
            countQuery = "SELECT COUNT(d) FROM District d")
    Page<DistrictDto> getListPage(Pageable pageable);

    @Query("SELECT NEW com.globits.da.dto.DistrictDto(d) FROM District d WHERE d.id = :id")
    DistrictDto getById(UUID id);

    @Query("SELECT NEW com.globits.da.dto.DistrictDto(d) FROM District d WHERE d.province.id = :provinceId")
    List<DistrictDto> getByProvinceId(UUID provinceId);

    @Query("SELECT (COUNT(d) > 0) FROM District d WHERE d.name = :name")
    boolean existByName(String name);
}
