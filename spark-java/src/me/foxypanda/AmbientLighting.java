package me.foxypanda;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.TimerTask;

public class AmbientLighting extends TimerTask {

    private OutputStream output;

    public AmbientLighting(OutputStream output) {
        this.output = output;
    }

    public char to36(int num) {
        num = (int) ((double) num / 7.08333333);
        if(num < 0) return '0';
        if(num > 35) return 'z';
        if(num < 11) {
            return (char) (num + 48);
        } else {
            return (char) (num + 97 - 10);
        }
    }

    public void run() {
        Robot r;

        try {
            r = new Robot();
        } catch(Exception e) {
            System.out.print("Could not create a Robot.");
            System.exit(1);
            return;
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) screenSize.getWidth();
        final int height = (int) screenSize.getHeight();
        BufferedImage image = r.createScreenCapture(new Rectangle(width, height));
        final int step = 5;
        final int widthSteps = width / step - 3;
        final int heightSteps = height / step - 3;
        int pixelCount = 0;
        int red = 0;
        int green = 0;
        int blue = 0;

        for(int i = 0; i < widthSteps; i++) {
            for(int k = 0; k < heightSteps; k++) {
                int x = i * step;
                int y = k * step;
                Color color = new Color(image.getRGB(x, y));
                red += color.getRed();
                green += color.getGreen();
                blue += color.getBlue();
                pixelCount++;
            }
        }
        red /= pixelCount;
        green /= pixelCount;
        blue /= pixelCount;
        String colourString = "" + to36(red) + to36(green) + to36(blue);
        try {
            output.write(colourString.getBytes());
        } catch (Exception e) {
        }

    }
}
