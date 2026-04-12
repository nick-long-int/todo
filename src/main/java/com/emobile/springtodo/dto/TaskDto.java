package com.emobile.springtodo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskDto{
    private String id;
    @NotBlank
    @Size(min = 5, max = 20)
    private String title;
    @NotBlank
    @Size(min = 5, max = 50)
    private String description;
    private String status;
}
