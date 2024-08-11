package com.nl.lostandfound.model.dto;

import lombok.Data;

@Data
public class ClaimedItemDto {
    private Long id;
    private String userName;
    private int quantity;
    private String itemName;
    private String place;
}