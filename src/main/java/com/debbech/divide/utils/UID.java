package com.debbech.divide.utils;

import java.util.Random;

public class UID {
    public static String generate(){
        StringBuilder sb = new StringBuilder();
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String uppercase = lowercase.toUpperCase();
        String digits = "0123456789";

        String allChars = lowercase + uppercase + digits;

        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(allChars.length());
            char randomChar = allChars.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
