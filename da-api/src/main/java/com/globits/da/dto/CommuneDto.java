package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Commune;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommuneDto extends BaseObjectDto {
    private String name;
    private UUID districtId;

    public CommuneDto(Commune commune) {
        if (commune == null) {
            return;
        }
        this.id = commune.getId();
        this.name = commune.getName();
        this.createDate = commune.getCreateDate();
        this.createdBy = commune.getCreatedBy();
        this.modifyDate = commune.getModifyDate();
        this.modifiedBy = commune.getModifiedBy();
        this.districtId = commune.getDistrict().getId();
    }
}
