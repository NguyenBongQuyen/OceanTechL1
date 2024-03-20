package com.globits.da.rest;

import com.globits.da.dto.MyFirstApiDTO;
import com.globits.da.service.MyFirstApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/myFirstApi")
public class RestMyFirstApiController {
    private final MyFirstApiService myFirstApiService;

    @Autowired
    public RestMyFirstApiController(MyFirstApiService myFirstApiService) {
        this.myFirstApiService = myFirstApiService;
    }

//    4.2
    @GetMapping
    public String getMyFirstApi() {
        return myFirstApiService.getMyFirstApi();
    }

//    6.3, 6.4, 7, 8
    @PostMapping("/formdata")
    public MyFirstApiDTO formDataMyFirstApi(@RequestBody MyFirstApiDTO myFirstApiDTO) {
        return myFirstApiDTO;
    }

//    9
    @PostMapping("/formdata2")
    public MyFirstApiDTO formDataMyFirstApi(@RequestParam String code, @RequestParam String name, @RequestParam Integer age) {
        MyFirstApiDTO myFirstApiDto = new MyFirstApiDTO(code,name,age);
        myFirstApiDto.setCode(code);
        myFirstApiDto.setName(name);
        myFirstApiDto.setAge(age);
        return myFirstApiDto;
    }

//    10
    @PostMapping("/pathvariable/{code}")
    public MyFirstApiDTO pathVariableMyFirstApi(@PathVariable("code") String code) {
        MyFirstApiDTO myFirstApiDTO = new MyFirstApiDTO();
        myFirstApiDTO.setCode(code);
        return myFirstApiDTO;
    }

//    11
    @PostMapping("/json")
    public MyFirstApiDTO jsonMyFirstApi(MyFirstApiDTO myFirstApiDTO) {
        return myFirstApiDTO;
    }

//    12
    @PostMapping("/uploadFileText")
    public ResponseEntity<String> readTextFile(@RequestParam("fileText") MultipartFile fileText) {
        try {
            myFirstApiService.readFileText(fileText);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading file");
        }
    }

//    12'
    @PostMapping("/uploadFileExcel")
    public ResponseEntity<String> readFileExcel(@RequestParam("fileExcel") MultipartFile fileExcel) {
        try {
            myFirstApiService.readFileExcel(fileExcel);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading file");
        }
    }

//    13
    @PostMapping("/json-not-requestBody")
    public MyFirstApiDTO jsonNotRequestBody(MyFirstApiDTO myFirstApiDTO) {
        return myFirstApiDTO;
    }
}
