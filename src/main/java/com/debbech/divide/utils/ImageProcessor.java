package com.debbech.divide.utils;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageProcessor {

    public static byte[] getThumbnail(byte[] bufferedImage) throws Exception{
        ByteArrayInputStream bais = new ByteArrayInputStream(bufferedImage);
        BufferedImage bimg = ImageIO.read(bais);

        BufferedImage bi = Scalr.resize(bimg, 500);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "jpg", baos);
        } catch (IOException e) {
            throw new Exception("could not create thumbnail");
        }
        byte[] b = baos.toByteArray();
        return b;
    }
}
