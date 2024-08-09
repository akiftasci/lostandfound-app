package com.nl.lostandfound.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class LostItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String itemName;
    private int quantity;
    private String place;
    @OneToMany(mappedBy = "lostItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClaimedItem> claimedItems;
}
