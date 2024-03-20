package com.globits.da.rest;

import com.globits.da.constant.Constant;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/provinces")
public class RestProvinceController {
    private final ProvinceService provinceService;

    @Autowired
    public RestProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @PostMapping
    public ResponseEntity<ProvinceDto> createProvince(@RequestBody ProvinceDto provinceDto) {
        ProvinceDto savedProvince = provinceService.create(provinceDto);
        return ResponseEntity.ok(savedProvince);
    }

    @GetMapping("/{pageIndex}/{pageSize}")
    public ResponseEntity<Page<ProvinceDto>> getListPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
        Page<ProvinceDto> provinces = provinceService.getListPage(pageIndex, pageSize);
        return ResponseEntity.ok(provinces);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProvinceDto> getProvinceById(@PathVariable("id") UUID id) {
        ProvinceDto provinceDto = provinceService.getById(id);
        return ResponseEntity.ok(provinceDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProvinceDto> updateProvince(@PathVariable("id") UUID id,
                                                      @RequestBody ProvinceDto provinceDto) {
        ProvinceDto currentProvince = provinceService.update(id, provinceDto);
        return ResponseEntity.ok(currentProvince);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProvince(@PathVariable("id") UUID id) {
        provinceService.deleteById(id);
        return ResponseEntity.ok(Constant.DELETE_SUCCESSFUL);
    }

}
