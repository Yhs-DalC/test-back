package com.test.test_back.service;

import com.test.test_back.dto.request.PostRestaurantRequestDto;
import com.test.test_back.dto.response.ResponseDto;
import com.test.test_back.dto.response.RestaurantResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {
    // 식당 생성
    public ResponseDto<RestaurantResponseDto> createRestaurant(PostRestaurantRequestDto dto);

    //식당 단일 조회
    ResponseDto<RestaurantResponseDto> getRestaurantById(long id);

    //식당 전체 조회
    ResponseDto<List<RestaurantResponseDto>> getAllRestaurant();

    //식당 정보 수정
    ResponseDto<RestaurantResponseDto> updateRestaurant(Long id, PostRestaurantRequestDto dto);

    //식당 삭제
    ResponseDto<Void> deleteRestaurant(Long id);
}
