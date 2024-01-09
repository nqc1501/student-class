package com.example.core.domain.result.domain;

import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {

    public static void writeByteToImageFile(String dataImage, String fileNameImage) throws Exception {
        byte[] bImg64 = dataImage.getBytes();
        byte[] bImg = Base64.getDecoder().decode(bImg64);
        try (FileOutputStream fos = new FileOutputStream(fileNameImage)) {
            fos.write(bImg);
        } catch (IOException ex) {
            throw new Exception("Write file to " + fileNameImage + " failed !!!");
        }
    }

    public static String[] getData(String base64) {
        if (!StringUtils.hasLength(base64))
            return null;

        String data[] = base64.split(",");
        if (data.length < 2)
            throw null;
        return data;
    }

    //data:image/jpeg;base64,/
    public static String extractMimeTypeBase64(final String encoded) {
        final Pattern mime = Pattern.compile("^data:([a-zA-Z0-9]+/[a-zA-Z0-9]+).*");
        final Matcher matcher = mime.matcher(encoded);
        if (!matcher.find())
            return "";
        return matcher.group(1).toLowerCase();
    }


    public static int deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            return deleteFile(file);
        } catch (Exception e) {
        }
        return -1;
    }

    public static int deleteFile(File file) {
        try {
            if (file != null) {
                if (file.delete()) {
                    //xóa thành công
                    return 0;
                } else {
                    return 1;
                }
            }
        } catch (Exception e) {
        }
        return -1;
    }

}

