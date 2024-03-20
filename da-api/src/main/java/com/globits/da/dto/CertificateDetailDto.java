package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.CertificateDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDetailDto extends BaseObjectDto {
    private LocalDate startDay;
    private LocalDate endDay;
    private UUID certificateId;
    private UUID employeeId;
    private UUID provinceId;

    public CertificateDetailDto(CertificateDetail certificateDetail) {
        if (certificateDetail == null) {
            return;
        }
        this.id = certificateDetail.getId();
        this.startDay = certificateDetail.getStartDay();
        this.endDay = certificateDetail.getEndDay();
        this.createDate = certificateDetail.getCreateDate();
        this.createdBy = certificateDetail.getCreatedBy();
        this.modifyDate = certificateDetail.getModifyDate();
        this.modifiedBy = certificateDetail.getModifiedBy();
        if (Objects.nonNull(certificateDetail.getCertificate())) {
            this.certificateId = certificateDetail.getCertificate().getId();
        }
        if (Objects.nonNull(certificateDetail.getEmployee())) {
            this.employeeId = certificateDetail.getEmployee().getId();
        }
        if (!ObjectUtils.isEmpty(certificateDetail.getProvince())) {
            this.provinceId = certificateDetail.getProvince().getId();
        }
    }
}
