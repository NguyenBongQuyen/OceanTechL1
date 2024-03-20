package com.globits.da.rest;

import com.globits.da.constant.Constant;
import com.globits.da.dto.DistrictDto;
import com.globits.da.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/districts")
public class RestDistrictController {
    private final DistrictService districtService;

    @Autowired
    public RestDistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    @PostMapping
    public ResponseEntity<DistrictDto> createDistrict(@RequestBody DistrictDto districtDto) {
        DistrictDto savedDistrict = districtService.create(districtDto);
        return ResponseEntity.ok(savedDistrict);
    }

    @GetMapping("/{pageIndex}/{pageSize}")
    public ResponseEntity<Page<DistrictDto>> getListPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
        Page<DistrictDto> districts = districtService.getListPage(pageIndex, pageSize);
        return ResponseEntity.ok(districts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DistrictDto> getDistrictById(@PathVariable("id") UUID id) {
        DistrictDto districtDto = districtService.getById(id);
        return ResponseEntity.ok(districtDto);
    }

    @GetMapping("/province-id/{provinceId}")
    public ResponseEntity<List<DistrictDto>> getDistrictByProvinceId(@PathVariable("provinceId") UUID provinceId) {
        List<DistrictDto> results = districtService.getByProvinceId(provinceId);
        return  ResponseEntity.ok(results);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DistrictDto> updateDistrict(@PathVariable("id") UUID id,
                                                      @RequestBody DistrictDto districtDto) {
        DistrictDto currentDistrict = districtService.update(id, districtDto);
        return ResponseEntity.ok(currentDistrict);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDistrict(@PathVariable("id") UUID id) {
        districtService.deleteById(id);
        return ResponseEntity.ok(Constant.DELETE_SUCCESSFUL);
    }
}
