package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.constant.Constant;
import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.domain.Employee;
import com.globits.da.domain.Province;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.exception.FileExcelException;
import com.globits.da.exception.NotFoundException;
import com.globits.da.exception.OctException;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.repository.ProvinceRepository;
import com.globits.da.service.EmployeeService;
import com.globits.da.utils.SetFileExcelValue;
import com.globits.da.validation.EmployeeValidation;
import com.globits.da.validation.ErrorValidation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class EmployeeServiceImpl extends GenericServiceImpl<Employee, UUID> implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final CommuneRepository communeRepository;
    private final EmployeeValidation employeeValidation;
    private final SetFileExcelValue setFileExcelValue;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               ProvinceRepository provinceRepository,
                               DistrictRepository districtRepository,
                               CommuneRepository communeRepository,
                               EmployeeValidation employeeValidation,
                               SetFileExcelValue setFileExcelValue) {
        this.employeeRepository = employeeRepository;
        this.provinceRepository = provinceRepository;
        this.districtRepository = districtRepository;
        this.communeRepository = communeRepository;
        this.employeeValidation = employeeValidation;
        this.setFileExcelValue = setFileExcelValue;
    }

    public void setEmployeeValue(Employee employee, EmployeeDTO employeeDTO) {
        employee.setCode(employeeDTO.getCode());
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhone(employeeDTO.getPhone());
        employee.setAge(employeeDTO.getAge());
        Province province = provinceRepository.findById(employeeDTO.getProvinceId())
                .orElseThrow(() -> new NotFoundException(ErrorValidation.PROVINCE_ID_NOT_FOUND));
        employee.setProvince(province);
        District district = districtRepository.findById(employeeDTO.getDistrictId())
                .orElseThrow(() -> new NotFoundException(ErrorValidation.DISTRICT_ID_NOT_FOUND));
        employee.setDistrict(district);
        Commune commune = communeRepository.findById(employeeDTO.getCommuneId())
                .orElseThrow(() -> new NotFoundException(ErrorValidation.COMMUNE_ID_NOT_FOUND));
        employee.setCommune(commune);
    }

    @Override
    public EmployeeDTO create(EmployeeDTO employeeDTO) {
        employeeValidation.checkEmployee(employeeDTO);
        Employee employee = new Employee();
        setEmployeeValue(employee, employeeDTO);
        return new EmployeeDTO(employeeRepository.save(employee));
    }

    @Override
    public Page<EmployeeDTO> getListPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex-1, pageSize);
        return employeeRepository.getListPage(pageable);
    }

    @Override
    public EmployeeDTO getById(UUID id) {
        employeeValidation.checkExist(id);
        return employeeRepository.getById(id);
    }

    @Override
    public EmployeeDTO update(UUID id, EmployeeDTO employeeDTO) {
        Employee currentEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorValidation.EMPLOYEE_ID_NOT_FOUND));
        if (!currentEmployee.getCode().equals(employeeDTO.getCode())) {
            employeeValidation.checkEmployee(employeeDTO);
        }
        setEmployeeValue(currentEmployee, employeeDTO);
        employeeRepository.save(currentEmployee);
        return new EmployeeDTO(currentEmployee);
    }

    @Override
    public void deleteById(UUID id) {
        employeeValidation.checkExist(id);
        employeeRepository.deleteById(id);
        }

    @Override
    public void exportFileExcel() {
        try {
            Workbook workbook = setFileExcelValue.createFileExcel(Constant.EXCEL_FILE_PATH);
            Set<EmployeeDTO> employeeDTOSet = employeeRepository.getAll();
            Sheet sheet = workbook.createSheet("Employee");
            int rowIndex = 0;
            setFileExcelValue.writeHeaderRow(sheet, rowIndex);
            rowIndex++;
            for (EmployeeDTO employeeDTO : employeeDTOSet) {
                Row row = sheet.createRow(rowIndex);
                setFileExcelValue.writeDataRows(employeeDTO, row);
                rowIndex++;
            }
            setFileExcelValue.writeFileExcel(workbook, Constant.EXCEL_FILE_PATH);
        } catch (IOException e) {
            throw new FileExcelException(ErrorValidation.CAN_NOT_EXPORT_EXCEL_FILE);
        }
    }

    @Override
    public void importFileExcel(MultipartFile fileExcel) {
        if (fileExcel == null) {
            throw new OctException(ErrorValidation.EXCEL_FILE_NULL);
        }
        Sheet sheet;
        try (Workbook workbook = WorkbookFactory.create(fileExcel.getInputStream())) {
            sheet = workbook.getSheetAt(0);
        } catch (IOException e) {
            throw new OctException(ErrorValidation.EXCEL_FILE_WRONG_FORMAT);
        }

        List<String> errorList = new ArrayList<>();
        List<Employee> employeeList = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            EmployeeDTO employeeDTO = new EmployeeDTO();
            setFileExcelValue.setEmployeeValueFromExcel(employeeDTO, row);
            try {
                employeeValidation.checkEmployee(employeeDTO);
                Employee employee = new Employee();
                setEmployeeValue(employee, employeeDTO);
                employeeList.add(employee);
            } catch (Exception e) {
                errorList.add("Error in line: " + (i + 1));
            }
        }
        if (!CollectionUtils.isEmpty(employeeList)) {
            employeeRepository.saveAll(employeeList);
        }
    }

}

