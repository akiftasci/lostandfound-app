package com.nl.lostandfound.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Mock method to simulate getting user information
    public String getUserName(Long userIds) {
       switch (Math.toIntExact(userIds)){
           case 1001 : return "James";
           case 1002 : return "Ron";
           case 1003 : return "Micheal";
           case 1004 : return "Oscar";
           default : throw new IllegalArgumentException("User unknown");
       }
    }
}
