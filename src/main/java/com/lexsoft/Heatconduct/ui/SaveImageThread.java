package com.lexsoft.Heatconduct.ui;

import com.lexsoft.Heatconduct.parsing.Parser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SaveImageThread extends Thread {

    private static int threadsCount = 0;
    private int threadIndex;

    private Parser parser;
    private String filePath;
    private RenderCanvas canvas;
    private BufferedImage img;


    SaveImageThread(Parser parser, String filePath) {
        this.parser = parser;
        this.filePath = filePath;
        this.canvas = new RenderCanvas();

        this.canvas.setVisible(true);
        this.canvas.setSize(1000, 600);
        this.canvas.activate(parser, 0);

        this.img = new BufferedImage(canvas.getWidth(), canvas.getHeight(), 1);

        this.threadIndex = threadsCount;
        threadsCount++;
    }

    @Override
    public void run() {

        for (int i = threadIndex; i < parser.getFrameCount(); i += threadsCount) {
            File file = new File(filePath + "_" + String.format("%06d", i + 1) + ".png");

            canvas.repaint(i);
            canvas.paint(img.getGraphics());

            try {
                ImageIO.write(img, "png", file);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            img.flush();
        }
    }

}
