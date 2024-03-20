package com.globits.da.repository;

import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    @Query(value = "SELECT NEW com.globits.da.dto.EmployeeDTO(em) FROM Employee em",
            countQuery = "SELECT COUNT(em) FROM Employee em")
    Page<EmployeeDTO> getListPage(Pageable pageable);

    @Query("SELECT NEW com.globits.da.dto.EmployeeDTO(em) FROM Employee em")
    Set<EmployeeDTO> getAll();

    @Query("SELECT NEW com.globits.da.dto.EmployeeDTO(em) FROM Employee em WHERE em.id = :id")
    EmployeeDTO getById(UUID id);

    @Query("SELECT (COUNT(em) > 0) FROM Employee em WHERE em.code = :code")
    boolean existByCode(String code);
}
