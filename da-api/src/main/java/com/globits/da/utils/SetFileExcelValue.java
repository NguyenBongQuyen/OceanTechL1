package com.globits.da.utils;

import com.globits.da.dto.EmployeeDTO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component
public class SetFileExcelValue {
    public static final int COLUMN_INDEX_CODE = 0;
    public static final int COLUMN_INDEX_NAME = 1;
    public static final int COLUMN_INDEX_EMAIL = 2;
    public static final int COLUMN_INDEX_PHONE = 3;
    public static final int COLUMN_INDEX_AGE = 4;
    public static final int COLUMN_INDEX_PROVINCE_ID = 5;
    public static final int COLUMN_INDEX_DISTRICT_ID = 6;
    public static final int COLUMN_INDEX_COMMUNE_ID = 7;
    public static final int COLUMN_INDEX_PROVINCE_NAME = 8;
    public static final int COLUMN_INDEX_DISTRICT_NAME = 9;
    public static final int COLUMN_INDEX_COMMUNE_NAME = 10;
    public static final int COLUMN_INDEX_CREATE_DATE = 11;
    public static final int COLUMN_INDEX_CREATE_BY = 12;
    public static final int COLUMN_INDEX_MODIFY_DATE = 13;
    public static final int COLUMN_INDEX_MODIFY_BY = 14;

    // Get Workbook
    public Workbook createFileExcel(String excelFilePath) {
        Workbook workbook;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
        return workbook;
    }

    public void writeHeaderRow(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_CODE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("CODE");

        cell = row.createCell(COLUMN_INDEX_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("NAME");

        cell = row.createCell(COLUMN_INDEX_EMAIL);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("EMAIL");

        cell = row.createCell(COLUMN_INDEX_PHONE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("PHONE");

        cell = row.createCell(COLUMN_INDEX_AGE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("AGE");

        cell = row.createCell(COLUMN_INDEX_PROVINCE_ID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("PROVINCE_ID");

        cell = row.createCell(COLUMN_INDEX_DISTRICT_ID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("DISTRICT_ID");

        cell = row.createCell(COLUMN_INDEX_COMMUNE_ID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("COMMUNE_ID");

        cell = row.createCell(COLUMN_INDEX_PROVINCE_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("PROVINCE_NAME");

        cell = row.createCell(COLUMN_INDEX_DISTRICT_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("DISTRICT_NAME");

        cell = row.createCell(COLUMN_INDEX_COMMUNE_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("COMMUNE_NAME");

        cell = row.createCell(COLUMN_INDEX_CREATE_DATE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("CREATE_DATE");

        cell = row.createCell(COLUMN_INDEX_CREATE_BY);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("CREATE_BY");

        cell = row.createCell(COLUMN_INDEX_MODIFY_DATE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("MODIFY_DATE");

        cell = row.createCell(COLUMN_INDEX_MODIFY_BY);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("MODIFY_BY");
    }

    public void writeDataRows(EmployeeDTO employeeDTO, Row row) {
        Cell cell = row.createCell(COLUMN_INDEX_CODE);
        cell.setCellValue(employeeDTO.getCode());

        cell = row.createCell(COLUMN_INDEX_NAME);
        cell.setCellValue(employeeDTO.getName());

        cell = row.createCell(COLUMN_INDEX_EMAIL);
        cell.setCellValue(employeeDTO.getEmail());

        cell = row.createCell(COLUMN_INDEX_PHONE);
        cell.setCellValue(employeeDTO.getPhone());

        cell = row.createCell(COLUMN_INDEX_AGE);
        cell.setCellValue(employeeDTO.getAge());

        cell = row.createCell(COLUMN_INDEX_PROVINCE_ID);
        cell.setCellValue(String.valueOf(employeeDTO.getProvinceId()));

        cell = row.createCell(COLUMN_INDEX_DISTRICT_ID);
        cell.setCellValue(String.valueOf(employeeDTO.getDistrictId()));

        cell = row.createCell(COLUMN_INDEX_COMMUNE_ID);
        cell.setCellValue(String.valueOf(employeeDTO.getCommuneId()));

        cell = row.createCell(COLUMN_INDEX_PROVINCE_NAME);
        cell.setCellValue(employeeDTO.getProvinceName());

        cell = row.createCell(COLUMN_INDEX_DISTRICT_NAME);
        cell.setCellValue(employeeDTO.getDistrictName());

        cell = row.createCell(COLUMN_INDEX_COMMUNE_NAME);
        cell.setCellValue(employeeDTO.getCommuneName());

        cell = row.createCell(COLUMN_INDEX_CREATE_DATE);
        cell.setCellValue(String.valueOf(employeeDTO.getCreateDate()));

        cell = row.createCell(COLUMN_INDEX_CREATE_BY);
        cell.setCellValue(employeeDTO.getCreatedBy());

        cell = row.createCell(COLUMN_INDEX_MODIFY_DATE);
        cell.setCellValue(String.valueOf(employeeDTO.getModifyDate()));

        cell = row.createCell(COLUMN_INDEX_MODIFY_BY);
        cell.setCellValue(employeeDTO.getModifiedBy());
    }

    // Create output file
    public void writeFileExcel(Workbook workbook, String excelFilePath) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }

    // Create CellStyle for header
    public CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 12); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.AUTOMATIC.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    public void setEmployeeValueFromExcel(EmployeeDTO employeeDTO, Row row) {
        int index = 0;
        employeeDTO.setCode(row.getCell(index++).getStringCellValue());
        employeeDTO.setName(row.getCell(index++).getStringCellValue());
        employeeDTO.setEmail(row.getCell(index++).getStringCellValue());
        employeeDTO.setPhone(row.getCell(index++).getStringCellValue());
        employeeDTO.setAge((int) row.getCell(index++).getNumericCellValue());
        employeeDTO.setProvinceId(UUID.fromString(row.getCell(index++).getStringCellValue()));
        employeeDTO.setDistrictId(UUID.fromString(row.getCell(index++).getStringCellValue()));
        employeeDTO.setCommuneId(UUID.fromString(row.getCell(index).getStringCellValue()));

    }

}
