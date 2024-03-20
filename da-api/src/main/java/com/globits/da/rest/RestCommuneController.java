package com.globits.da.rest;

import com.globits.da.constant.Constant;
import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.search.SearchCommuneDto;
import com.globits.da.service.CommuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/communes")
public class RestCommuneController {
    private final CommuneService communeService;

    @Autowired
    public RestCommuneController(CommuneService communeService) {
        this.communeService = communeService;
    }

    @PostMapping
    public ResponseEntity<CommuneDto> createCommune(@RequestBody CommuneDto communeDto) {
        CommuneDto savedCommune = communeService.create(communeDto);
        return ResponseEntity.ok(savedCommune);
    }

    @GetMapping("/{pageIndex}/{pageSize}")
    public ResponseEntity<Page<CommuneDto>> getListPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
        Page<CommuneDto> communes = communeService.getListPage(pageIndex, pageSize);
        return ResponseEntity.ok(communes);
    }

    @GetMapping("/searchByPage")
    public ResponseEntity<Page<CommuneDto>> searchByPage(@RequestBody SearchCommuneDto searchCommuneDto) {
        Page<CommuneDto> communes = communeService.searchByPage(searchCommuneDto);
        return ResponseEntity.ok(communes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommuneDto> getCommuneById(@PathVariable("id") UUID id) {
        CommuneDto communeDto = communeService.getById(id);
        return ResponseEntity.ok(communeDto);
    }

    @GetMapping("/district-id/{districtId}")
    public ResponseEntity<List<CommuneDto>> getCommuneByDistrictId(@PathVariable("districtId") UUID districtId) {
        List<CommuneDto> results = communeService.getByDistrictId(districtId);
        return ResponseEntity.ok(results);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommuneDto> updateCommune(@PathVariable("id") UUID id,
                                                    @RequestBody CommuneDto communeDto) {
        CommuneDto currentCommune = communeService.update(id, communeDto);
        return ResponseEntity.ok(currentCommune);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommune(@PathVariable("id") UUID id) {
        communeService.deleteById(id);
        return ResponseEntity.ok(Constant.DELETE_SUCCESSFUL);
    }
}
