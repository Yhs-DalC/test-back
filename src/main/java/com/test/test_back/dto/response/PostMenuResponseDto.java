package com.test.test_back.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostMenuResponseDto {
    private Long id;
    private String name;
    private Double price;
    private String description;

    private RestaurantResponseDto restaurant;
}
