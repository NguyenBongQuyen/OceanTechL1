package com.globits.da.repository;

import com.globits.da.domain.Certificate;
import com.globits.da.dto.CertificateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, UUID> {
    @Query(value = "SELECT NEW com.globits.da.dto.CertificateDto(c) FROM Certificate c",
            countQuery = "SELECT COUNT(c) FROM Certificate c")
    Page<CertificateDto> getListPage(Pageable pageable);

    @Query("SELECT NEW com.globits.da.dto.CertificateDto(c) FROM Certificate c WHERE c.id = :id")
    CertificateDto getById(UUID id);

    @Query("SELECT (COUNT(c) > 0) FROM Certificate c WHERE c.name = :name")
    boolean existByName(String name);

}
