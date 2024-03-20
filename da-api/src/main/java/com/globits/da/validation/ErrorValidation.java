package com.globits.da.validation;

import lombok.Getter;

@Getter
public enum ErrorValidation {
    EMPLOYEE_ID_NOTNULL("E1", "Province id cannot be null"),
    EMPLOYEE_ID_NOT_FOUND("E2", "Province id does not exist"),

    EMPLOYEE_CODE_NOTNULL("E3", "Employee code cannot be null"),
    EMPLOYEE_CODE_DUPLICATE("E4", "Employee code cannot be duplicate"),
    EMPLOYEE_CODE_NOT_BLANK("E5", "Employee code cannot be blank"),
    EMPLOYEE_CODE_LENGTH("E6", "Employee code length is not valid"),

    EMPLOYEE_NAME_NOTNULL("E7", "Employee name cannot be null"),

    EMPLOYEE_EMAIL_NOTNULL("E8", "Employee email cannot be null"),
    EMPLOYEE_EMAIL_WRONG_FORMAT("E9", "Employee email wrong format"),

    EMPLOYEE_PHONE_NOTNULL("E10", "Employee phone cannot be null"),
    EMPLOYEE_PHONE_WRONG_FORMAT("E11", "Employee phone wrong format"),

    EMPLOYEE_AGE_NOTNULL("E12", "Employee age cannot be null"),
    EMPLOYEE_AGE_NEGATIVE("E13", "Employee age cannot be negative"),

    PROVINCE_ID_NOTNULL("P1", "Province id cannot be null"),
    PROVINCE_ID_NOT_FOUND("P2", "Province id does not exist"),
    PROVINCE_NAME_NOTNULL("P3", "Province name cannot be null"),
    PROVINCE_NAME_DUPLICATE("P4", "Province name cannot be duplicate"),

    DISTRICT_ID_NOTNULL("D1", "District id cannot be null"),
    DISTRICT_ID_NOT_FOUND("D2", "District id does not exist"),
    DISTRICT_NAME_NOTNULL("D3", "District name cannot be null"),
    DISTRICT_NAME_DUPLICATE("D4", "District name cannot be duplicate"),
    DISTRICT_NOT_IN_PROVINCE("D5", "District is not in province"),

    COMMUNE_ID_NOTNULL("C1", "Commune id cannot be null"),
    COMMUNE_ID_NOT_FOUND("C2", "Commune id does not exist"),
    COMMUNE_NAME_NOTNULL("C3", "Commune name cannot be null"),
    COMMUNE_NAME_DUPLICATE("C4", "Commune name cannot be duplicate"),
    COMMUNE_NOT_IN_DISTRICT("C5", "Commune is not in district"),

    CERTIFICATE_ID_NOTNULL("CF1", "Certificate id cannot be null"),
    CERTIFICATE_ID_NOT_FOUND("CF2", "Certificate id does not exist"),
    CERTIFICATE_NAME_NOTNULL("CF3", "Certificate name cannot be null"),
    CERTIFICATE_NAME_DUPLICATE("CF3", "Certificate name cannot be duplicate"),
    CERTIFICATE_START_DAY_NOTNULL("CF3", "Certificate start day cannot be null"),
    CERTIFICATE_END_DAY_NOTNULL("CF4", "Certificate end day cannot be null"),

    CERTIFICATE_DETAIL_ID_NOTNULL("CD1", "Certificate detail id cannot be null"),
    CERTIFICATE_DETAIL_ID_NOT_FOUND("CD2", "Certificate detail id does not exist"),
    CERTIFICATE_DETAIL_IS_BEING_USED("CD3", "Certificate detail is being used by this employee"),
    CERTIFICATE_DETAIL_IS_LIMITED("CD4", "Certificate detail is limited"),

    EXCEL_FILE_NULL("EX1", "File is null"),
    EXCEL_FILE_WRONG_FORMAT("EX2", "Excel file wrong format"),
    CAN_NOT_EXPORT_EXCEL_FILE("EX3", "Unable to export Excel file");

    private final String errorCode;
    private final String errorMessage;

    ErrorValidation(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
