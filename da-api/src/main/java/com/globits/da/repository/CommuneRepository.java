package com.globits.da.repository;

import com.globits.da.domain.Commune;
import com.globits.da.dto.CommuneDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommuneRepository extends JpaRepository<Commune, UUID> {
    @Query(value = "SELECT NEW com.globits.da.dto.CommuneDto(c) FROM Commune c",
            countQuery = "SELECT COUNT(c) FROM Commune c")
    Page<CommuneDto> getListPage(Pageable pageable);

    @Query("SELECT NEW com.globits.da.dto.CommuneDto(c) FROM Commune c WHERE c.id = :id")
    CommuneDto getById(UUID id);

    @Query("SELECT NEW com.globits.da.dto.CommuneDto(c) FROM Commune c WHERE c.district.id = :districtId")
    List<CommuneDto> getByDistrictId(UUID districtId);

    @Query("SELECT (COUNT(c) > 0) FROM Commune c WHERE c.name = :name")
    boolean existByName(String name);

    @Query("SELECT (COUNT(c) > 0) FROM Commune c WHERE c.id = :id AND c.district.id = :districtId")
    boolean existCommuneInDistrict(UUID id, UUID districtId);
}
