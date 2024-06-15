package com.debbech.divide.processor;

import com.debbech.divide.services.impl.AuthService;

import java.time.LocalDateTime;
import java.util.Random;

public class OrderProcessorUtils {
    static String generateId(){
        StringBuilder sb = new StringBuilder();
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String uppercase = lowercase.toUpperCase();
        String digits = "0123456789";

        String allChars = lowercase + uppercase + digits;

        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(allChars.length());
            char randomChar = allChars.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
    static String generateFileName(){
        String uid = AuthService.getLoggedInUser();
        return uid + LocalDateTime.now();
    }
}
