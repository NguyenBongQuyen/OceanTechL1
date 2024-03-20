package com.globits.da.rest;

import com.globits.da.constant.Constant;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class RestEmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public RestEmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO savedEmployee = employeeService.create(employeeDTO);
        return ResponseEntity.ok(savedEmployee);
    }

    @GetMapping("/{pageIndex}/{pageSize}")
    public ResponseEntity<Page<EmployeeDTO>> getListPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
        Page<EmployeeDTO> employees = employeeService.getListPage(pageIndex, pageSize);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") UUID id) {
        EmployeeDTO result = employeeService.getById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@PathVariable("id") UUID id,
                                                          @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO currentEmployee = employeeService.update(id, employeeDTO);
        return ResponseEntity.ok(currentEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") UUID id) {
        employeeService.deleteById(id);
        return ResponseEntity.ok(Constant.DELETE_SUCCESSFUL);
    }

    @GetMapping("/export")
    public ResponseEntity<String> exportEmployee() {
        employeeService.exportFileExcel();
        return ResponseEntity.ok(Constant.EXPORT_EXCEL_SUCCESSFUL);
    }

    @PostMapping("/import")
    public ResponseEntity<String> importEmployee(MultipartFile fileExcel) {
        employeeService.importFileExcel(fileExcel);
        return ResponseEntity.ok(Constant.IMPORT_EXCEL_SUCCESSFUL);
    }
}
