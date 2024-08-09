package com.nl.lostandfound.service;

import java.util.List;

import com.nl.lostandfound.model.LostItem;
import com.nl.lostandfound.repository.LostItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LostItemService {
    @Autowired
    private LostItemRepository lostItemRepository;

    public List<LostItem> getAllLostItems() {
        return lostItemRepository.findAll();
    }

    public LostItem saveLostItem(LostItem lostItem) {
        return lostItemRepository.save(lostItem);
    }
}