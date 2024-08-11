package com.nl.lostandfound.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.nl.lostandfound.model.ClaimedItem;
import com.nl.lostandfound.model.LostItem;
import com.nl.lostandfound.model.dto.ClaimedItemDto;
import com.nl.lostandfound.service.ClaimService;
import com.nl.lostandfound.service.LostItemService;
import com.nl.lostandfound.util.PDFExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private LostItemService lostItemService;

    @Autowired
    private PDFExtractor pdfExtractor;

    @Autowired
    private ClaimService claimService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadLostItems(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("lost_items", ".pdf");
            file.transferTo(tempFile);
            List<LostItem> lostItems = pdfExtractor.extractLostItemsFromPDF(tempFile);
            for (LostItem item : lostItems) {
                lostItemService.saveLostItem(item);
            }
            return ResponseEntity.ok("Lost item registered successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/get-claims")
    public ResponseEntity<List<ClaimedItemDto>> getClaims() {
        List<ClaimedItem> claimedItems = claimService.getAllClaimedItems();
        List<ClaimedItemDto> claimedItemDtos = claimedItems.stream().map( n -> {
            ClaimedItemDto claimedItemDto = new ClaimedItemDto();
            claimedItemDto.setId(n.getId());
            claimedItemDto.setUserId(n.getUserId());
            claimedItemDto.setItemName(n.getLostItem().getItemName());
            claimedItemDto.setQuantity(n.getLostItem().getQuantity());
            claimedItemDto.setPlace(n.getLostItem().getPlace());
            return claimedItemDto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(claimedItemDtos);
    }
}