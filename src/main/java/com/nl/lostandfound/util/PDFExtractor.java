package com.nl.lostandfound.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.nl.lostandfound.model.LostItem;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

@Component
public class PDFExtractor {

    public List<LostItem> extractLostItemsFromPDF(File file) throws IOException {
        try (PDDocument document = Loader.loadPDF(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            return parseLostItems(text);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not extract lost items from pdf file: " + e.getMessage());
        }
    }

    private static List<LostItem> parseLostItems(String text) {
        List<LostItem> lostItems = new ArrayList<>();
        String[] records = text.split("\\n\\s*\\n");

        for (String record : records) {
            LostItem lostItem = new LostItem();
            String[] lines = record.trim().split("\\n");
            lostItem.setItemName(lines[0].split(":")[1].trim());
            lostItem.setQuantity(Integer.parseInt(lines[1].split(":")[1].trim()));
            lostItem.setPlace(lines[2].split(":")[1].trim());
            lostItems.add(lostItem);
        }
        return lostItems;
    }
}