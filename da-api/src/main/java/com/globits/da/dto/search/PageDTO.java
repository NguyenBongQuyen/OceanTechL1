package com.globits.da.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> {
    private int totalPages;
    private long totalElements;
    private T data;
}
