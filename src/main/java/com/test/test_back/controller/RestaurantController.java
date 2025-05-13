package com.test.test_back.controller;

import com.test.test_back.common.ApiMappingPattern;
import com.test.test_back.dto.request.PostRestaurantRequestDto;
import com.test.test_back.dto.response.ResponseDto;
import com.test.test_back.dto.response.RestaurantResponseDto;
import com.test.test_back.entity.Restaurant;
import com.test.test_back.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.RESTAURANT_API)
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<ResponseDto<RestaurantResponseDto>> createRestaurant(@RequestBody PostRestaurantRequestDto dto) {
        ResponseDto<RestaurantResponseDto> restaurant = restaurantService.createRestaurant(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<ResponseDto<RestaurantResponseDto>> getRestaurantById(@PathVariable Long id){
        ResponseDto<RestaurantResponseDto> restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.status(HttpStatus.OK).body(restaurant);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<RestaurantResponseDto>>> getAllRestaurants(){
        ResponseDto<List<RestaurantResponseDto>> restaurants = restaurantService.getAllRestaurant();
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<ResponseDto<RestaurantResponseDto>> updateRestaurant(
            @PathVariable Long id,
            @RequestBody PostRestaurantRequestDto dto
    ){
        ResponseDto<RestaurantResponseDto> response = restaurantService.updateRestaurant(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<ResponseDto<Void>> deleteRestaurant(@PathVariable Long id){
        ResponseDto<Void> response = restaurantService.deleteRestaurant(id);
        return  ResponseEntity.noContent().build();
    }
}
