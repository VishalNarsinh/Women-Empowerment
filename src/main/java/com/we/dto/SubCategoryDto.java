package com.we.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategoryDto {
    private Long subCategoryId;
    private String name;
    private Long categoryId;
    private List<CourseDto> courses=new ArrayList<>();
}
