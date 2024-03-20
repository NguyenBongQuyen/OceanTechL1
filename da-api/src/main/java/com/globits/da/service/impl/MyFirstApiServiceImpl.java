package com.globits.da.service.impl;

import com.globits.da.service.MyFirstApiService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class MyFirstApiServiceImpl implements MyFirstApiService {
    @Override
    public String getMyFirstApi() {
        return "MyFirstApi";
    }

    @Override
    public void readFileText(MultipartFile fileText) throws IOException{
        String context = StreamUtils.copyToString(fileText.getInputStream(), UTF_8);
        System.out.println(context);
    }

    @Override
    public void readFileExcel(MultipartFile fileExcel) throws IOException {
        try (Workbook workbook = WorkbookFactory.create(fileExcel.getInputStream())) {
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                Iterator<Row> rowIterator = sheet.rowIterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        System.out.print(cell.toString() + "\t");
                    }
                    System.out.println();
                }
            }
        }
    }
}
