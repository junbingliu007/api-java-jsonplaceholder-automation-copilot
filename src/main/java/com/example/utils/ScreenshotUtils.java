
package com.example.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class ScreenshotUtils {
    public static byte[] renderTextToPng(String text) throws Exception {
        String[] lines = wrapText(text, 120);
        int width = 1200;
        int lineHeight = 18;
        int padding = 20;
        int height = padding * 2 + lines.length * lineHeight;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 14));
        int y = padding + 14;
        for (String line : lines) {
            g2d.drawString(line, padding, y);
            y += lineHeight;
        }
        g2d.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }

    private static String[] wrapText(String text, int maxCharsPerLine) {
        if (text == null) return new String[]{"(null)"};
        java.util.List<String> lines = new java.util.ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (char c : text.toCharArray()) {
            sb.append(c);
            count++;
            if (c == ' ' || count >= maxCharsPerLine) {
                lines.add(sb.toString());
                sb.setLength(0);
                count = 0;
            }
        }
        if (sb.length() > 0) lines.add(sb.toString());
        return lines.toArray(new String[0]);
    }
}
