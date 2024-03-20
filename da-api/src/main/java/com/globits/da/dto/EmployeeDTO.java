package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO extends BaseObjectDto {
    private String code;
    private String name;
    private String email;
    private String phone;
    private Integer age;
    private UUID provinceId;
    private UUID districtId;
    private UUID communeId;
    private String provinceName;
    private String districtName;
    private String communeName;

    public EmployeeDTO(Employee employee) {
        if (employee == null) {
            return;
        }
        this.id = employee.getId();
        this.code = employee.getCode();
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.phone = employee. getPhone();
        this.age = employee.getAge();
        this.createDate = employee.getCreateDate();
        this.createdBy = employee.getCreatedBy();
        this.modifyDate = employee.getModifyDate();
        this.modifiedBy = employee.getModifiedBy();
        this.provinceId = employee.getProvince().getId();
        this.districtId = employee.getDistrict().getId();
        this.communeId = employee.getCommune().getId();
        this.provinceName = employee.getProvince().getName();
        this.districtName = employee.getDistrict().getName();
        this.communeName = employee.getCommune().getName();
    }

}
