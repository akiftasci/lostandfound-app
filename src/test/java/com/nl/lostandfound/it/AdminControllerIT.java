package com.nl.lostandfound.it;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nl.lostandfound.model.ClaimedItem;
import com.nl.lostandfound.model.LostItem;
import com.nl.lostandfound.model.dto.ClaimedItemDto;
import com.nl.lostandfound.repository.ClaimedItemRepository;
import com.nl.lostandfound.repository.LostItemRepository;
import org.junit.jupiter.api.BeforeEach;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerIT {

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
    }

    @Test
    public void testUploadLostItemsPdf() throws Exception {
        InputStream pdfStream = new FileInputStream("src/test/resources/sample.pdf");
        MockMultipartFile file = new MockMultipartFile("file", "sample.pdf", "application/pdf", pdfStream);

        mockMvc.perform(multipart("/admin/upload")
                        .file(file))
                .andExpect(status().isOk());

        assertThat(lostItemRepository.count()).isEqualTo(4);

        var lostItems = lostItemRepository.findAll();
        assertThat(lostItems.get(0).getId()).isEqualTo(1);
        assertThat(lostItems.get(0).getItemName()).isEqualTo("Laptop");
        assertThat(lostItems.get(0).getQuantity()).isEqualTo(1);
        assertThat(lostItems.get(0).getPlace()).isEqualTo("Taxi");

        assertThat(lostItems).extracting("itemName")
                .containsExactlyInAnyOrder("Laptop", "Headphones", "Jewels", "Laptop");

        assertThat(lostItems).extracting("place")
                .containsExactlyInAnyOrder("Taxi", "Railway station", "Airport", "Airport");
    }

//    @Test
//    public void testGetClaims() throws Exception {
//        fillinDataBase();
//
//        String jsonResponse = mockMvc.perform(get("/admin/get-claims"))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        List<ClaimedItemDto> claimedItems = objectMapper.readValue(jsonResponse, new TypeReference<List<ClaimedItemDto>>() {});
//
//        assertThat(claimedItems).hasSize(2);
//
//        ClaimedItemDto claimedItem = claimedItems.get(0);
//        assertThat(claimedItem.getUserId()).isEqualTo(1001L);
//        assertThat(claimedItem.getItemName()).isEqualTo("Laptop");
//        assertThat(claimedItem.getQuantity()).isEqualTo(1);
//        assertThat(claimedItem.getPlace()).isEqualTo("Taxi");
//
//        ClaimedItemDto claimedItem1 = claimedItems.get(1);
//        assertThat(claimedItem1.getUserId()).isEqualTo(1002L);
//        assertThat(claimedItem1.getItemName()).isEqualTo("Headphones");
//        assertThat(claimedItem1.getQuantity()).isEqualTo(1);
//        assertThat(claimedItem1.getPlace()).isEqualTo("Airport");
//    }

    private void fillinDataBase() {
        LostItem lostItem = new LostItem();
        lostItem.setId(1L);
        lostItem.setItemName("Laptop");
        lostItem.setPlace("Taxi");
        lostItem.setQuantity(1);
        LostItem lostItem2 = new LostItem();
        lostItem2.setId(2L);
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