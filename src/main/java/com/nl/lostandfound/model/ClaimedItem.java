package com.nl.lostandfound.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ClaimedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "lost_item_id", nullable = false)
    private LostItem lostItem;
    private Long userId;
    private int quantity;
    @Transient
    private String userName;
}
