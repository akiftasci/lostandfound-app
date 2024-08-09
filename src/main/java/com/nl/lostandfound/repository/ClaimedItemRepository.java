package com.nl.lostandfound.repository;

import com.nl.lostandfound.model.ClaimedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimedItemRepository extends JpaRepository<ClaimedItem, Long> {
}
