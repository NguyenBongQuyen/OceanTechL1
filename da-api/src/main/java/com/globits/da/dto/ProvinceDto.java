package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceDto extends BaseObjectDto {
    private String name;
    private Set<DistrictDto> districtDtoSet;

    public ProvinceDto(Province province) {
        if (province == null) {
            return;
        }
        this.id = province.getId();
        this.name = province.getName();
        this.createDate = province.getCreateDate();
        this.createdBy = province.getCreatedBy();
        this.modifyDate = province.getModifyDate();
        this.modifiedBy = province.getModifiedBy();
        this.districtDtoSet = new LinkedHashSet<>();
        if (!ObjectUtils.isEmpty(province.getDistricts())) {
            for (District district : province.getDistricts()) {
                DistrictDto districtDto = new DistrictDto(district);
                this.districtDtoSet.add(districtDto);
            }
        }
    }

}
