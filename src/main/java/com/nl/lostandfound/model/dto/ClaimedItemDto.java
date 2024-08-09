package com.nl.lostandfound.model.dto;

import lombok.Data;

@Data
public class ClaimedItemDto {
    private Long id;
    private Long userId;
    private int quantity;
    private String itemName;
    private String place;
}