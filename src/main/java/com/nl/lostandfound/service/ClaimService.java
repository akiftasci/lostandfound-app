package com.nl.lostandfound.service;

import java.util.List;

import com.nl.lostandfound.model.ClaimedItem;
import com.nl.lostandfound.model.LostItem;
import com.nl.lostandfound.repository.ClaimedItemRepository;
import com.nl.lostandfound.repository.LostItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClaimService {
    @Autowired
    private ClaimedItemRepository claimedItemRepository;

    @Autowired
    private LostItemRepository lostItemRepository;

    public ClaimedItem saveClaimedItem(ClaimedItem claimedItem) {
        LostItem lostItem = lostItemRepository.findById(claimedItem.getLostItem().getId()).orElseThrow(
                () -> new IllegalArgumentException("Lost item not found")
        );
        claimedItem.setLostItem(lostItem);
        return claimedItemRepository.save(claimedItem);
    }

    public List<ClaimedItem> getAllClaimedItems() {
        return claimedItemRepository.findAll();
    }
}