package com.test.test_back.service;

import com.test.test_back.dto.request.PostMenuRequestDto;
import com.test.test_back.dto.request.PostRestaurantRequestDto;
import com.test.test_back.dto.response.MenuResponseDto;
import com.test.test_back.dto.response.ResponseDto;

import java.util.List;

public interface MenuService {
    //메뉴 생성
    ResponseDto<MenuResponseDto> createMenu(Long restaurantId, PostMenuRequestDto dto);

    //특정 식당의 모든 메뉴 조회
    ResponseDto<List<MenuResponseDto>> findMenuByRestaurant(long restaurantId);

    //특정 메뉴 ID 조회
    ResponseDto<MenuResponseDto> findMenuById(long restaurantId, long id);
    
    //메뉴 정보 수정
    ResponseDto<MenuResponseDto> updateMenu(Long restaurantId, Long menuId, PostMenuRequestDto dto);

    //메뉴 삭제
    ResponseDto<Void> deleteMenu(Long restaurantId, Long menuId);
}
