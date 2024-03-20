package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface EmployeeService extends GenericService<Employee, UUID> {
    EmployeeDTO create(EmployeeDTO employeeDTO);

    Page<EmployeeDTO> getListPage(int pageIndex, int pageSize);

    EmployeeDTO getById(UUID id);

    EmployeeDTO update(UUID id, EmployeeDTO employeeDTO);

    void deleteById(UUID id);

    void exportFileExcel();

    void importFileExcel(MultipartFile fileExcel);
}
