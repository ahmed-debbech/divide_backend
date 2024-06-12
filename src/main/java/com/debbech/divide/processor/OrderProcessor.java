package com.debbech.divide.processor;

import com.debbech.divide.processor.data.IOrderLiveDb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class OrderProcessor {

    @Autowired
    private IOrderLiveDb liveDb;

    public String launch(String picture){
        String id = OrderProcessor.generateId();
        return id;
    }



    private static String generateId(){
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
}
