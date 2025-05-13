package com.test.test_back.service.implementations;

import com.test.test_back.common.ResponseMessage;
import com.test.test_back.dto.request.PostRestaurantRequestDto;
import com.test.test_back.dto.response.MenuResponseDto;
import com.test.test_back.dto.response.ResponseDto;
import com.test.test_back.dto.response.RestaurantResponseDto;
import com.test.test_back.entity.Restaurant;
import com.test.test_back.repository.RestaurantRepository;
import com.test.test_back.service.RestaurantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    @Transactional
    public ResponseDto<RestaurantResponseDto> createRestaurant(PostRestaurantRequestDto dto) {
        RestaurantResponseDto responseDto = null;

        Restaurant newRestaurant = Restaurant.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .phoneNumber(dto.getPhoneNumber())
                .build();
        Restaurant saved = restaurantRepository.save(newRestaurant);

        responseDto = RestaurantResponseDto.builder()
                .id(saved.getId())
                .name(saved.getName())
                .address(saved.getAddress())
                .phoneNumber(saved.getPhoneNumber())
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<RestaurantResponseDto> getRestaurantById(long id) {
        RestaurantResponseDto responseDto = null;

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_RESTAURANT + id));

        List<MenuResponseDto> menus = restaurant.getMenus().stream()
                .map(menu -> MenuResponseDto.builder()
                        .id(menu.getId())
                        .restaurantId(menu.getRestaurant().getId())
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .description(menu.getDescription())
                        .build())
                .collect(Collectors.toList());

        responseDto = RestaurantResponseDto.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .phoneNumber(restaurant.getPhoneNumber())
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<RestaurantResponseDto>> getAllRestaurant() {
        List<RestaurantResponseDto> responseDtos = null;

        List<Restaurant> restaurants = restaurantRepository.findAll();

        responseDtos = restaurants.stream()
                .map(restaurant -> RestaurantResponseDto.builder()
                        .id(restaurant.getId())
                        .name(restaurant.getName())
                        .address(restaurant.getAddress())
                        .phoneNumber(restaurant.getPhoneNumber())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<RestaurantResponseDto> updateRestaurant(Long id, PostRestaurantRequestDto dto) {
        RestaurantResponseDto responseDto = null;

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_RESTAURANT + id));

        restaurant.setName(dto.getName());
        restaurant.setAddress(dto.getAddress());
        restaurant.setPhoneNumber(dto.getPhoneNumber());

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        responseDto = RestaurantResponseDto.builder()
                .id(updatedRestaurant.getId())
                .name(updatedRestaurant.getName())
                .address(updatedRestaurant.getAddress())
                .phoneNumber(updatedRestaurant.getPhoneNumber())
                .menus(updatedRestaurant.getMenus().stream()
                        .map(menu -> MenuResponseDto.builder()
                                .id(menu.getId())
                                .restaurantId(menu.getRestaurant().getId())
                                .name(menu.getName())
                                .price(menu.getPrice())
                                .description(menu.getDescription())
                                .build())
                        .collect(Collectors.toList())
                )
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    public ResponseDto<Void> deleteRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_RESTAURANT + id));

        restaurant.getMenus().forEach(restaurant::removeMenu);

        restaurantRepository.deleteById(id);

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
    }
}
