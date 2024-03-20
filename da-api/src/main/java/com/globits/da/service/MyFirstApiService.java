package com.globits.da.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MyFirstApiService {
    String getMyFirstApi();

    void readFileText(MultipartFile fileText) throws IOException;

    void readFileExcel(MultipartFile fileExcel) throws IOException;

}
