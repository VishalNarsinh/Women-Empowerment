package com.we.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Long categoryId;
    private String name;
//    private List<SubCategoryDto> subCategories=new ArrayList<>();
}
