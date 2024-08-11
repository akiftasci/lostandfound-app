package com.nl.lostandfound.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.nl.lostandfound.model.ClaimItemRequest;
import com.nl.lostandfound.model.ClaimedItem;
import com.nl.lostandfound.model.LostItem;
import com.nl.lostandfound.model.dto.LostItemDto;
import com.nl.lostandfound.service.ClaimService;
import com.nl.lostandfound.service.LostItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private LostItemService lostItemService;

    @Autowired
    private ClaimService claimService;

    @GetMapping("/lost")
    public ResponseEntity<List<LostItemDto>> getLostItems() {
        List<LostItem> lostItems = lostItemService.getAllLostItems();
        List<LostItemDto> lostItemDtos = lostItems.stream().map(n -> {
            LostItemDto lostItemDto = new LostItemDto();
            lostItemDto.setId(n.getId());
            lostItemDto.setItemName(n.getItemName());
            lostItemDto.setPlace(n.getPlace());
            lostItemDto.setQuantity(n.getQuantity());
            return lostItemDto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(lostItemDtos);
    }

    @PostMapping("/claim")
    public ResponseEntity<String> claimLostItem(@RequestBody ClaimItemRequest claimItemRequest) {
        ClaimedItem item = claimService.claimLostItem(claimItemRequest);
        return ResponseEntity.ok("Item claimed succesfuly: " + item.getLostItem().getItemName());
    }
}
