package com.globits.da.dto.search;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class SearchCommuneDto {
    private UUID id;
    private int pageIndex;
    private int pageSize;
    private String keyword;
}
