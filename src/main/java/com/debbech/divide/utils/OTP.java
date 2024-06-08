package com.debbech.divide.utils;

import java.util.Random;

public class OTP {
    public static String generate(){
        Random random = new Random();
        String otp = random.ints(4, 0, 10).mapToObj(Integer::toString)
                .reduce((a, b) -> a + b).get();
        return otp;
    }
}
