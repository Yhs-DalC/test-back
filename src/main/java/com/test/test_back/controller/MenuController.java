package com.test.test_back.controller;

import com.test.test_back.common.ApiMappingPattern;
import com.test.test_back.dto.request.PostMenuRequestDto;
import com.test.test_back.dto.response.MenuResponseDto;
import com.test.test_back.dto.response.ResponseDto;
import com.test.test_back.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController// @ResponseBody + @Controller
@RequestMapping(ApiMappingPattern.MENU_API)
@RequiredArgsConstructor// 생성자 주입
public class MenuController {
    private final MenuService menuService;

    // 1) 메뉴 생성
    @PostMapping
    public ResponseEntity<ResponseDto<MenuResponseDto>> createMenu(
            @PathVariable Long restaurantId,
            @RequestBody PostMenuRequestDto dto
    ){

        ResponseDto<MenuResponseDto> response = menuService.createMenu(restaurantId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2) 메뉴 찾기(단건)
    @GetMapping("/{menuId}")
    public ResponseEntity<ResponseDto<MenuResponseDto>> findMenuByRestaurant(@PathVariable Long menuId){
        ResponseDto<MenuResponseDto> menu = menuService.findMenuByRestaurant(menuId);
        return ResponseEntity.status(HttpStatus.OK).body(menu);
    }
    // 3) 메뉴 찾기(전체)
    @GetMapping
    public ResponseEntity<ResponseDto<List<MenuResponseDto>>> findMenuById(
            @PathVariable Long restaurantId,
             @PathVariable Long menuId
            ) {
        ResponseDto<List<MenuResponseDto>> menus = menuService.findMenuById(restaurantId, menuId);
        return ResponseEntity.status(HttpStatus.OK).body(menus);
    }

    // 4) 메뉴 수정
    @PutMapping("/{menuId}")
    public ResponseEntity<ResponseDto<MenuResponseDto>> updateMenu(
            @PathVariable Long restaurantId,
            @PathVariable Long menuId,
            @RequestBody PostMenuRequestDto dto
    ){
        ResponseDto<MenuResponseDto> response = menuService.updateMenu(restaurantId, menuId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 5) 메뉴 삭제
    @DeleteMapping("/{menuId}")
    public ResponseEntity<ResponseDto<Void>> deleteMenu(
            @PathVariable Long restaurantId,
            @PathVariable Long menuId
    ) {
        ResponseDto<MenuResponseDto> response = menuService.deleteMenu(restaurantId, menuId);
        return ResponseEntity.noContent().build();
    }
}
