package com.nl.lostandfound.repository;

import com.nl.lostandfound.model.LostItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostItemRepository extends JpaRepository<LostItem, Long> {
}
