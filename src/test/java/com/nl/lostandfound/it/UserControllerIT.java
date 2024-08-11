package com.nl.lostandfound.it;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nl.lostandfound.model.ClaimItemRequest;
import com.nl.lostandfound.model.ClaimedItem;
import com.nl.lostandfound.model.LostItem;
import com.nl.lostandfound.model.dto.ClaimedItemDto;
import com.nl.lostandfound.repository.ClaimedItemRepository;
import com.nl.lostandfound.repository.LostItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LostItemRepository lostItemRepository;

    @Autowired
    private ClaimedItemRepository claimedItemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        claimedItemRepository.deleteAll();
        lostItemRepository.deleteAll();
        fillinDatabase();
    }


    @Test
    public void testGetLostItems() throws Exception {
        String jsonResponse = mockMvc.perform(get("/user/lost"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<ClaimedItemDto> claimedItems = objectMapper.readValue(jsonResponse, new TypeReference<List<ClaimedItemDto>>() {});

        assertThat(claimedItems).hasSize(2);

    }

    @Test
    public void testClaimLostItem() throws Exception {
        LostItem lostItem = lostItemRepository.findAll().get(0);
        ClaimItemRequest claimedItem = new ClaimItemRequest();
        claimedItem.setLostItemId(1L);
        claimedItem.setUserId(1001L);
        claimedItem.setQuantity(1);
        // Convert the ClaimedItem object to JSON string
        String claimedItemJson = objectMapper.writeValueAsString(claimedItem);

        mockMvc.perform(post("/user/claim")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(claimedItemJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<ClaimedItem> claimedItemsList = claimedItemRepository.findAll();
        assertThat(claimedItemsList).hasSize(3);
        assertThat(claimedItemsList.get(2).getUserId()).isEqualTo(1001L);
    }

    private void fillinDatabase() {
        LostItem lostItem = new LostItem();
        lostItem.setItemName("Laptop");
        lostItem.setPlace("Taxi");
        lostItem.setQuantity(1);
        LostItem lostItem2 = new LostItem();
        lostItem2.setItemName("Headphones");
        lostItem2.setPlace("Airport");
        lostItem2.setQuantity(1);
        lostItemRepository.saveAll(List.of(lostItem, lostItem2));
        ClaimedItem claimedItem = new ClaimedItem();
        claimedItem.setLostItem(lostItem);
        claimedItem.setUserId(1001L);
        claimedItem.setQuantity(1);
        ClaimedItem claimedItem2 = new ClaimedItem();
        claimedItem2.setLostItem(lostItem2);
        claimedItem2.setUserId(1002L);
        claimedItem2.setQuantity(1);
        claimedItemRepository.saveAll(List.of(claimedItem, claimedItem2));
    }
}