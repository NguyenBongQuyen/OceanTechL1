package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DistrictDto extends BaseObjectDto {
    private String name;
    private UUID provinceId;
    private Set<CommuneDto> communeDtoSet;

    public DistrictDto(District district) {
        if (district == null) {
            return;
        }
        this.id = district.getId();
        this.name = district.getName();
        this.createDate = district.getCreateDate();
        this.createdBy = district.getCreatedBy();
        this.modifyDate = district.getModifyDate();
        this.modifiedBy = district.getModifiedBy();
        this.provinceId = district.getProvince().getId();
        this.communeDtoSet = new LinkedHashSet<>();
        if (!ObjectUtils.isEmpty(district.getCommunes())) {
            for (Commune commune : district.getCommunes()) {
                CommuneDto communeDto = new CommuneDto(commune);
                this.communeDtoSet.add(communeDto);
            }
        }
    }

}
