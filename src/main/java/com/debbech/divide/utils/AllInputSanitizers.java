package com.debbech.divide.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllInputSanitizers {

    public static boolean isValidEmail(String email) {
        String EMAIL_REGEX =
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String isUid(String uid){
        if(uid.length() != 10) return "wrong uid length";
        int i=0;
        while(i < 10){
            if(((uid.charAt(i) >= 'a') && (uid.charAt(i) <= 'z'))
                    || ((uid.charAt(i) >= 'A') && (uid.charAt(i) <= 'Z'))
                    || ((uid.charAt(i) >= '0') && (uid.charAt(i) <= '9'))){
                i++;
            }else{
                return "incorrect uid";
            }
        }
        return "";
    }
}
