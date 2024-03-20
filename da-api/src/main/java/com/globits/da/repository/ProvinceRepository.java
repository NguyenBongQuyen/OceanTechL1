package com.globits.da.repository;

import com.globits.da.domain.Province;
import com.globits.da.dto.ProvinceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, UUID> {
    @Query(value = "SELECT NEW com.globits.da.dto.ProvinceDto(p) FROM Province p",
            countQuery = "SELECT COUNT(p) FROM Province p")
    Page<ProvinceDto> getListPage(Pageable pageable);

    @Query("SELECT NEW com.globits.da.dto.ProvinceDto(p) FROM Province p WHERE p.id = :id")
    ProvinceDto getById(UUID id);

    @Query("SELECT (COUNT(p) > 0) FROM Province p WHERE p.name = :name")
    boolean existByName(String name);
}
