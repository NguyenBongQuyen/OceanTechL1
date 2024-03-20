package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Certificate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDto extends BaseObjectDto {
    private String name;

    public CertificateDto(Certificate certificate) {
        if (certificate == null) {
            return;
        }
        this.id = certificate.getId();
        this.name = certificate.getName();
        this.createDate = certificate.getCreateDate();
        this.createdBy = certificate.getCreatedBy();
        this.modifyDate = certificate.getModifyDate();
        this.modifiedBy = certificate.getModifiedBy();
    }
}
