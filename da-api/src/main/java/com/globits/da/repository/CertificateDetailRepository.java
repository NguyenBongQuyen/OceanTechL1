package com.globits.da.repository;

import com.globits.da.domain.CertificateDetail;
import com.globits.da.dto.CertificateDetailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CertificateDetailRepository extends JpaRepository<CertificateDetail, UUID> {
    @Query(value = "SELECT NEW com.globits.da.dto.CertificateDetailDto(cd) FROM CertificateDetail cd",
            countQuery = "SELECT COUNT(cd) FROM CertificateDetail cd")
    Page<CertificateDetailDto> getListPage(Pageable pageable);

    @Query("SELECT NEW com.globits.da.dto.CertificateDetailDto(cd) FROM CertificateDetail cd WHERE cd.id = :id")
    CertificateDetailDto getById(UUID id);

    @Query("SELECT (COUNT(cd) > 0) FROM CertificateDetail cd " +
            "WHERE cd.certificate.id = ?1 AND cd.employee.id = ?2 AND cd.province.id = ?3 AND cd.active = true")
    boolean existCertificateInUseEmployee(UUID certificateId, UUID employeeId, UUID provinceId);

    @Query("SELECT COUNT(cd) FROM CertificateDetail cd " +
            "WHERE cd.certificate.id = ?1 AND cd.employee.id = ?2 AND cd.active = true")
    int countCertificateInUseEmployee(UUID certificateId, UUID employeeId);
}
