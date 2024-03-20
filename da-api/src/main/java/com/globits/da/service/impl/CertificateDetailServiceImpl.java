package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Certificate;
import com.globits.da.domain.CertificateDetail;
import com.globits.da.domain.Employee;
import com.globits.da.domain.Province;
import com.globits.da.dto.CertificateDetailDto;
import com.globits.da.exception.NotFoundException;
import com.globits.da.repository.CertificateDetailRepository;
import com.globits.da.repository.CertificateRepository;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.repository.ProvinceRepository;
import com.globits.da.service.CertificateDetailService;
import com.globits.da.validation.CertificateDetailValidation;
import com.globits.da.validation.ErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CertificateDetailServiceImpl extends GenericServiceImpl<CertificateDetail, UUID> implements CertificateDetailService {
    private final CertificateDetailRepository certificateDetailRepository;
    private final CertificateDetailValidation certificateDetailValidation;
    private final CertificateRepository certificateRepository;
    private final EmployeeRepository employeeRepository;
    private final ProvinceRepository provinceRepository;

    @Autowired
    public CertificateDetailServiceImpl(CertificateDetailRepository certificateDetailRepository,
                                        CertificateDetailValidation certificateDetailValidation,
                                        CertificateRepository certificateRepository,
                                        EmployeeRepository employeeRepository,
                                        ProvinceRepository provinceRepository) {
        this.certificateDetailRepository = certificateDetailRepository;
        this.certificateDetailValidation = certificateDetailValidation;
        this.certificateRepository = certificateRepository;
        this.employeeRepository = employeeRepository;
        this.provinceRepository = provinceRepository;
    }

    public void setCertificateDetailValue(CertificateDetail certificateDetail, CertificateDetailDto certificateDetailDto) {
        certificateDetail.setStartDay(certificateDetailDto.getStartDay());
        certificateDetail.setEndDay(certificateDetailDto.getEndDay());
        Certificate certificate = certificateRepository.findById(certificateDetailDto.getCertificateId())
                .orElseThrow(() -> new NotFoundException(ErrorValidation.CERTIFICATE_ID_NOT_FOUND));
        certificateDetail.setCertificate(certificate);
        Employee employee = employeeRepository.findById(certificateDetailDto.getEmployeeId())
                .orElseThrow(() -> new NotFoundException(ErrorValidation.EMPLOYEE_ID_NOT_FOUND));
        certificateDetail.setEmployee(employee);
        Province province = provinceRepository.findById(certificateDetailDto.getProvinceId())
                .orElseThrow(() -> new NotFoundException(ErrorValidation.PROVINCE_ID_NOT_FOUND));
        certificateDetail.setProvince(province);
        certificateDetail.setActive();
    }

    @Override
    public CertificateDetailDto create(CertificateDetailDto certificateDetailDto) {
        certificateDetailValidation.checkCertificateDetail(certificateDetailDto);
        CertificateDetail certificateDetail = new CertificateDetail();
        setCertificateDetailValue(certificateDetail, certificateDetailDto);
        return new CertificateDetailDto(certificateDetailRepository.save(certificateDetail));
    }

    @Override
    public Page<CertificateDetailDto> getListPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex-1, pageSize);
        return certificateDetailRepository.getListPage(pageable);
    }

    @Override
    public CertificateDetailDto getById(UUID id) {
        certificateDetailValidation.checkExist(id);
        return certificateDetailRepository.getById(id);
    }

    @Override
    public CertificateDetailDto update(UUID id, CertificateDetailDto certificateDetailDto) {
        CertificateDetail currentCertificateDetail = certificateDetailRepository.findById(certificateDetailDto.getId())
                .orElseThrow(() -> new NotFoundException(ErrorValidation.CERTIFICATE_DETAIL_ID_NOT_FOUND));
        certificateDetailValidation.checkCertificateDetail(certificateDetailDto);
        setCertificateDetailValue(currentCertificateDetail, certificateDetailDto);
        certificateDetailRepository.save(currentCertificateDetail);
        return new CertificateDetailDto(currentCertificateDetail);
    }

    @Override
    public void deleteById(UUID id) {
        certificateDetailValidation.checkExist(id);
        certificateDetailRepository.deleteById(id);
    }
}
