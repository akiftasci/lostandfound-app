package com.nl.lostandfound.model.dto;

import lombok.Data;

@Data
public class LostItemDto {
    private Long id;
    private String itemName;
    private int quantity;
    private String place;
}
