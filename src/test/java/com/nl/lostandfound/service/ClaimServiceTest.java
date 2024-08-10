package com.nl.lostandfound.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.nl.lostandfound.model.ClaimedItem;
import com.nl.lostandfound.model.LostItem;
import com.nl.lostandfound.repository.ClaimedItemRepository;
import com.nl.lostandfound.repository.LostItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClaimServiceTest {

    @InjectMocks
    private ClaimService claimService;

    @Mock
    private ClaimedItemRepository claimedItemRepository;

    @Mock
    private LostItemRepository lostItemRepository;

    @Test
    public void testSaveClaimedItem() {
        LostItem lostItem = new LostItem();
        lostItem.setId(1L);
        lostItem.setItemName("Laptop");
        lostItem.setQuantity(2);

        ClaimedItem claimedItem = new ClaimedItem();
        claimedItem.setLostItem(lostItem);
        claimedItem.setUserId(1001L);
        claimedItem.setQuantity(1);

        when(lostItemRepository.findById(anyLong())).thenReturn(Optional.of(lostItem));
        when(claimedItemRepository.save(any(ClaimedItem.class))).thenReturn(claimedItem);

        ClaimedItem result = claimService.claimLostItem(claimedItem);

        assertNotNull(result);
        assertEquals(1L, result.getLostItem().getId());
        verify(claimedItemRepository, times(1)).save(claimedItem);
    }

    @Test
    public void testSaveClaimedItemQuantityZero() {
        LostItem lostItem = new LostItem();
        lostItem.setId(1L);
        lostItem.setItemName("Laptop");
        lostItem.setQuantity(1);

        ClaimedItem claimedItem = new ClaimedItem();
        claimedItem.setLostItem(lostItem);
        claimedItem.setUserId(1001L);
        claimedItem.setQuantity(1);

        when(lostItemRepository.findById(anyLong())).thenReturn(Optional.of(lostItem));
        when(claimedItemRepository.save(any(ClaimedItem.class))).thenReturn(claimedItem);

        ClaimedItem result = claimService.claimLostItem(claimedItem);

        assertNotNull(result);
        assertEquals(1L, result.getLostItem().getId());
        verify(claimedItemRepository, times(1)).save(claimedItem);
    }

    @Test
    public void testSaveClaimedItem_RequestMoreQuantity() {
        LostItem lostItem = new LostItem();
        lostItem.setId(1L);
        lostItem.setItemName("Laptop");
        lostItem.setQuantity(1);

        ClaimedItem claimedItem = new ClaimedItem();
        claimedItem.setLostItem(lostItem);
        claimedItem.setUserId(1001L);
        claimedItem.setQuantity(2);

        when(lostItemRepository.findById(anyLong())).thenReturn(Optional.of(lostItem));

        assertThrows(IllegalArgumentException.class, () -> claimService.claimLostItem(claimedItem));
    }

    @Test
    public void testSaveClaimedItem_LostItemNotFound() {
        ClaimedItem claimedItem = new ClaimedItem();
        claimedItem.setLostItem(new LostItem());
        claimedItem.getLostItem().setId(1L);

        when(lostItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> claimService.claimLostItem(claimedItem));
    }
}
