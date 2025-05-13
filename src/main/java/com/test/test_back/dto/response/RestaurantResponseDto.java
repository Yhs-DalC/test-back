package com.test.test_back.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RestaurantResponseDto {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;

    private List<MenuResponseDto> menus;
}
