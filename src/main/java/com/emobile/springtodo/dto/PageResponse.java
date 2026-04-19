package com.emobile.springtodo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PageResponse {
    private List<TaskDto> tasks;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}
