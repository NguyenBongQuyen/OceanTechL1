package com.globits.da.rest;

import com.globits.da.constant.Constant;
import com.globits.da.dto.CertificateDetailDto;
import com.globits.da.service.CertificateDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/certificate-details")
public class RestCertificateDetailController {
    private final CertificateDetailService certificateDetailService;

    @Autowired
    public RestCertificateDetailController(CertificateDetailService certificateDetailService) {
        this.certificateDetailService = certificateDetailService;
    }

    @PostMapping
    public ResponseEntity<CertificateDetailDto> createCertificateDetail(@RequestBody CertificateDetailDto certificateDetailDto) {
        CertificateDetailDto savedCertificateDetail = certificateDetailService.create(certificateDetailDto);
        return ResponseEntity.ok(savedCertificateDetail);
    }

    @GetMapping("/{pageIndex}/{pageSize}")
    public ResponseEntity<Page<CertificateDetailDto>> getListPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
        Page<CertificateDetailDto> certificateDetails = certificateDetailService.getListPage(pageIndex, pageSize);
        return ResponseEntity.ok(certificateDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateDetailDto> getCertificateDetailById(@PathVariable("id") UUID id) {
        CertificateDetailDto certificateDto = certificateDetailService.getById(id);
        return ResponseEntity.ok(certificateDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificateDetailDto> updateCertificateDetail(@PathVariable("id") UUID id,
                                                                        @RequestBody CertificateDetailDto certificateDetailDto) {
        CertificateDetailDto currentCertificateDetail = certificateDetailService.update(id, certificateDetailDto);
        return ResponseEntity.ok(currentCertificateDetail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCertificateDetail(@PathVariable("id") UUID id) {
        certificateDetailService.deleteById(id);
        return ResponseEntity.ok(Constant.DELETE_SUCCESSFUL);
    }
}
