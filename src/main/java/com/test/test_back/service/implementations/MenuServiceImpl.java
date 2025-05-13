package com.test.test_back.service.implementations;

import com.test.test_back.common.ResponseMessage;
import com.test.test_back.dto.request.PostMenuRequestDto;
import com.test.test_back.dto.response.MenuResponseDto;
import com.test.test_back.dto.response.ResponseDto;
import com.test.test_back.dto.response.RestaurantResponseDto;
import com.test.test_back.entity.Menu;
import com.test.test_back.entity.Restaurant;
import com.test.test_back.repository.MenuRepository;
import com.test.test_back.repository.RestaurantRepository;
import com.test.test_back.service.MenuService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    @Transactional(readOnly = false)
    public ResponseDto<MenuResponseDto> createMenu(Long restaurantId, PostMenuRequestDto dto) {
        MenuResponseDto responseDto = null;

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_RESTAURANT + restaurantId));

        Menu newMenu = Menu.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .build();

        restaurant.addMenu(newMenu);

        Menu savedMenu = menuRepository.save(newMenu);

        responseDto = MenuResponseDto.builder()
                .id(savedMenu.getId())
                .restaurantId(savedMenu.getRestaurant().getId())
                .name(savedMenu.getName())
                .price(savedMenu.getPrice())
                .description(savedMenu.getDescription())
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<MenuResponseDto>> findMenuByRestaurant(long restaurantId) {
        List<MenuResponseDto> responseDtos = null;

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_RESTAURANT + restaurantId));

        List<Menu> menus = menuRepository.findAll();

        responseDtos = menus.stream()
                .map(menu -> MenuResponseDto.builder()
                        .id(menu.getId())
                        .restaurantId(menu.getRestaurant().getId())
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .description(menu.getDescription())
                        .build())
                //.filter(menu.getRestaurant().getId() === restaurantId)// 식당 id === restaurantId 인것만 필터링
                .collect(Collectors.toList());

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<MenuResponseDto> findMenuById(long restaurantId, long id) {
//        RestaurantResponseDto responseDto = null;
//
//        Restaurant restaurant = restaurantRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_RESTAURANT + restaurantId));
//
//        Menu menu1 = menuRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_MENU + id));
//
//        MenuResponseDto menus = restaurant.getMenus().stream()
//                .map(menu -> MenuResponseDto.builder()
//                        .id(menu.getId())
//                        .restaurantId(menu.getRestaurant().getId())
//                        .name(menu.getName())
//                        .price(menu.getPrice())
//                        .description(menu.getDescription())
//                        .build())
//                .collect(Collectors.toList());
//
//        responseDto = RestaurantResponseDto.builder()
//                .id(restaurant.getId())
//                .name(restaurant.getName())
//                .address(restaurant.getAddress())
//                .phoneNumber(restaurant.getPhoneNumber())
//                .menus(menus)
//                .build();
//
//return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
        return null;
    }

    @Override
    @Transactional
    public ResponseDto<MenuResponseDto> updateMenu(Long restaurantId, Long menuId, PostMenuRequestDto dto) {
        MenuResponseDto responseDto = null;

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_MENU + menuId));

        if(!menu.getRestaurant().getId().equals(restaurantId)){
            throw new IllegalArgumentException("menu does not belong to the specified Restaurant");
        }

        menu.setName(dto.getName());
        menu.setPrice(dto.getPrice());
        menu.setDescription(dto.getDescription());

        Menu savedMenu = menuRepository.save(menu);

        responseDto = new MenuResponseDto.Builder(savedMenu.getName(), savedMenu.getPrice(), savedMenu.getDescription())
                .id(savedMenu.getId())
                .restaurantId(savedMenu.getRestaurant().getId())
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
    }

    @Override
    public ResponseDto<Void> deleteMenu(Long restaurantId, Long menuId) {
        if(!menuRepository.existsById(menuId)){
            throw new EntityNotFoundException(ResponseMessage.NOT_EXISTS_MENU + menuId);
        }
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_MENU + menuId));

        if(!menu.getRestaurant().getId().equals(restaurantId)){
            throw new IllegalArgumentException("menu does not belong to the specified Restaurant");
        }

        menu.getRestaurant().removeMenu(menu);

        menuRepository.delete(menu);

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
    }
}
