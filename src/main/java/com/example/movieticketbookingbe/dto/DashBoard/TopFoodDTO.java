package com.example.movieticketbookingbe.dto.DashBoard;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TopFoodDTO {
    private String foodName;
    private Long totalQuantity;

    public TopFoodDTO(String foodName, Long totalQuantity) {
        this.foodName = foodName;
        this.totalQuantity = totalQuantity;
    }
}
