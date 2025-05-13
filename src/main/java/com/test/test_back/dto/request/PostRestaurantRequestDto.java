package com.test.test_back.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRestaurantRequestDto {
    private String name;
    private String address;
    private String phoneNumber;
}
