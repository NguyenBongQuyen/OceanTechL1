package com.globits.da.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.globits.core.domain.BaseObject;
import com.globits.da.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_certificate_detail")
public class CertificateDetail extends BaseObject {
    @Column(name = "start_day")
    @JsonFormat(pattern = Constant.DATE_FORMAT)
    private LocalDate startDay;

    @Column(name = "end_day")
    @JsonFormat(pattern = Constant.DATE_FORMAT)
    private LocalDate endDay;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    @Column(name = "active")
    private Boolean active;

    public void setActive() {
        active = endDay.isAfter(LocalDate.now());
    }

}
