package com.test.test_back.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MenuResponseDto {
    private Long id;
    private final String name;
    private final Double price;
    private final String description;

    private long restaurantId;

    public static class Builder{

        private Long id;
        private final String name;
        private final Double price;
        private final String description;

        private long restaurantId;
        public Builder(String name, Double price, String description){
            this.name = name;
            this.price = price;
            this.description = description;
        }
        public Builder id(Long id){
            this.id = id;
            return this;
        }
        public Builder restaurantId(Long restaurantId){
            this.restaurantId = restaurantId;
            return this;
        }
        public MenuResponseDto build(){
            return new MenuResponseDto(id, name, price, description, restaurantId);
        }
    }


}
