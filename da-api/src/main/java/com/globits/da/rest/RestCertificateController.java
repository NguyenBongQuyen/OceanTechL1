package com.globits.da.rest;

import com.globits.da.constant.Constant;
import com.globits.da.dto.CertificateDto;
import com.globits.da.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/certificates")
public class RestCertificateController {
    private final CertificateService certificateService;

    @Autowired
    public RestCertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping
    public ResponseEntity<CertificateDto> createCertificate(@RequestBody CertificateDto certificateDto) {
        CertificateDto savedCertificate = certificateService.create(certificateDto);
        return ResponseEntity.ok(savedCertificate);
    }

    @GetMapping("/{pageIndex}/{pageSize}")
    public ResponseEntity<Page<CertificateDto>> getListPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
        Page<CertificateDto> certificates = certificateService.getListPage(pageIndex, pageSize);
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> getCertificateById(@PathVariable("id") UUID id) {
        CertificateDto certificateDto = certificateService.getById(id);
        return ResponseEntity.ok(certificateDto);
    }

    @PutMapping("/{id}")

    public ResponseEntity<CertificateDto> updateCertificate(@PathVariable("id") UUID id,
                                                            @Valid @RequestBody CertificateDto certificateDto) {
        CertificateDto currentCertificate = certificateService.update(id, certificateDto);
        return ResponseEntity.ok(currentCertificate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCertificate(@PathVariable("id") UUID id) {
        certificateService.deleteById(id);
        return ResponseEntity.ok(Constant.DELETE_SUCCESSFUL);
    }

}
