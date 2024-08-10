package com.nl.lostandfound.service;

import java.util.List;

import com.nl.lostandfound.model.ClaimedItem;
import com.nl.lostandfound.model.LostItem;
import com.nl.lostandfound.repository.ClaimedItemRepository;
import com.nl.lostandfound.repository.LostItemRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClaimService {
    @Autowired
    private ClaimedItemRepository claimedItemRepository;

    @Autowired
    private LostItemRepository lostItemRepository;

    @Transactional
    public ClaimedItem claimLostItem(ClaimedItem claimedItem) {
        LostItem lostItem = lostItemRepository.findById(claimedItem.getLostItem().getId()).orElseThrow(
                () -> new EntityNotFoundException("Lost item not found")
        );
        int stock = lostItem.getQuantity();
        if (claimedItem.getQuantity() > stock) {
            throw new IllegalArgumentException("Can not request more quantity");
        }
        //Decrease the claimed item quantity and update db
        lostItem.setQuantity(stock - claimedItem.getQuantity());
        lostItemRepository.save(lostItem);

        claimedItem.setLostItem(lostItem);
        return claimedItemRepository.save(claimedItem);
    }

    public List<ClaimedItem> getAllClaimedItems() {
        return claimedItemRepository.findAll();
    }
}