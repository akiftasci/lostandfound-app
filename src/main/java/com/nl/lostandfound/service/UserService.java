package com.nl.lostandfound.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Mock method to simulate getting user information
    public String getUserName(Long userIds) {
        return switch (Math.toIntExact(userIds)) {
            case 1001 -> "James";
            case 1002 -> "Ron";
            case 1003 -> "Micheal";
            case 1004 -> "Oscar";
            default -> throw new IllegalArgumentException("User unknown");
        };
    }
}
