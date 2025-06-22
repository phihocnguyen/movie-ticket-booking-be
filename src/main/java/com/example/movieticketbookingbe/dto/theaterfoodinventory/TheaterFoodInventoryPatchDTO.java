package com.example.movieticketbookingbe.dto.theaterfoodinventory;

import lombok.Data;
 
@Data
public class TheaterFoodInventoryPatchDTO {
    private Integer quantity;
    private Boolean isActive;
}