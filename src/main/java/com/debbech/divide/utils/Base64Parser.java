package com.debbech.divide.utils;
import javax.xml.bind.DatatypeConverter;

public class Base64Parser {
    public static byte[] base64ToBytes(String base64) throws Exception {
        byte[] data;

        if(base64.contains(",")) {
            String[] strings = base64.split(",");
            try {
                data = DatatypeConverter.parseBase64Binary(strings[1]);
            } catch (Exception e) {
                throw new Exception("could not parse image.");
            }
        }else{
            try {
                data = DatatypeConverter.parseBase64Binary(base64);
            } catch (Exception e) {
                throw new Exception("could not parse image.");
            }
        }
        return data;
    }
}
