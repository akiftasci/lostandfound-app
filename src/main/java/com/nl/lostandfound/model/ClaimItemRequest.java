package com.nl.lostandfound.model;

import lombok.Data;

@Data
public class ClaimItemRequest {
    private Long lostItemId;
    private Long userId;
    private int quantity;
}
