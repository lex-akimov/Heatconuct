package com.lexsoft.gElements;

import com.lexsoft.ui.CONSTANTS;
import com.lexsoft.ui.Window;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TemperatureChart extends JPanel implements KeyListener {
    private int max;
    private int min;
    private float[] layers;
    private JLabel thickness;
    private ArrayList<JTextField> thicknessByLayers;
    public static Image background;
    public static boolean drawLayerBound = true;
    public static boolean drawDegreeLines = true;

    public TemperatureChart(float min, float max) {
        this.layers = new float[Window.parser.getTemperatures()[0].length - 2];
        this.thicknessByLayers = new ArrayList<>();
        this.min = (int)(min - min % 5.0F - 5.0F);
        this.max = (int)(max - max % 5.0F + 5.0F);
        this.setPreferredSize(new Dimension(490, 570));
        this.setOpaque(false);
        this.setLayout(null);
        this.layers[0] = 0.0F;

        int i;
        for(i = 1; i < this.layers.length; ++i) {
            this.layers[i] = this.layers[i - 1] + 100.0F;
        }

        for(i = 1; i < this.layers.length; ++i) {
            int x = (int)(199.0F * (this.layers[i - 1] + this.layers[i]) / this.layers[this.layers.length - 1] + 45.0F);
            JTextField thickField = new JTextField(String.format("%.0f", this.layers[i] - this.layers[i - 1]));
            thickField.setFont(CONSTANTS.FONT_SMALL);
            thickField.setBorder(null);
            thickField.setOpaque(false);
            thickField.setBounds(x - 12, 530, 25, 15);
            thickField.setHorizontalAlignment(0);
            thickField.addKeyListener(this);
            this.thicknessByLayers.add(thickField);
            this.add(this.thicknessByLayers.get(i - 1));
        }

        this.thickness = new JLabel(String.format("%.0f", this.layers[this.layers.length - 1]));
        this.thickness.setFont(CONSTANTS.FONT_SMALL);
        this.thickness.setBounds(230, 550, 35, 15);
        this.add(this.thickness);
    }

    protected void paintComponent(Graphics g) {
        float tempInside = Window.parser.getTemperatures()[Window.currChart][0];
        float tempOutside = Window.parser.getTemperatures()[Window.currChart][Window.parser.getTemperaturesCount() - 1];
        int heightInside = (int)((tempInside - (float)this.min) * 500.0F / (float)(this.max - this.min));
        int heightOutside = (int)((tempOutside - (float)this.min) * 500.0F / (float)(this.max - this.min));
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(new Color(232, 148, 23));
        g2d.fillRect(33, 520 - heightInside, 7, heightInside);
        g2d.setColor(new Color(127, 191, 255));
        g2d.fillRect(448, 520 - heightOutside, 7, heightOutside);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(33, 20, 7, 500);
        g2d.drawRect(448, 20, 7, 500);
        if (background != null) {
            g2d.drawImage(background, 45, 20, 398, 500, this);
        }

        float one = 398.0F / this.layers[this.layers.length - 1];

        int i;
        int x;
        for(i = 1; i < this.layers.length; ++i) {
            x = (int)(45.0F + this.layers[i - 1] * one);
            int xx = (int)(45.0F + this.layers[i] * one);
            if (drawLayerBound) {
                int width = xx - x;
                g2d.drawRect(x, 20, width, 500);
            }

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(1.5F));
            g2d.drawLine(x, 520 - (int)((Window.parser.getTemperatures()[Window.currChart][i] - (float)this.min) * 500.0F / (float)(this.max - this.min)), xx, 520 - (int)((Window.parser.getTemperatures()[Window.currChart][i + 1] - (float)this.min) * 500.0F / (float)(this.max - this.min)));
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g2d.setStroke(new BasicStroke(1.0F));
            g2d.setColor(Color.BLACK);
        }

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(CONSTANTS.FONT_SMALL);

        for(i = this.min; i <= this.max; i += 5) {
            if (drawDegreeLines && i < this.max) {
                g2d.setStroke(new BasicStroke(0.5F));
                g2d.drawLine(28, 520 - (i - this.min) * 500 / (this.max - this.min), 460, 520 - (i - this.min) * 500 / (this.max - this.min));
                g2d.drawLine(28, 520 - (i + 1 - this.min) * 500 / (this.max - this.min), 460, 520 - (i + 1 - this.min) * 500 / (this.max - this.min));
                g2d.drawLine(28, 520 - (i + 2 - this.min) * 500 / (this.max - this.min), 460, 520 - (i + 2 - this.min) * 500 / (this.max - this.min));
                g2d.drawLine(28, 520 - (i + 3 - this.min) * 500 / (this.max - this.min), 460, 520 - (i + 3 - this.min) * 500 / (this.max - this.min));
                g2d.drawLine(28, 520 - (i + 4 - this.min) * 500 / (this.max - this.min), 460, 520 - (i + 4 - this.min) * 500 / (this.max - this.min));
                g2d.setStroke(new BasicStroke(1.0F));
            }

            if (i % 10 != 0) {
                g2d.drawLine(28, 520 - (i - this.min) * 500 / (this.max - this.min), 33, 520 - (i - this.min) * 500 / (this.max - this.min));
                if ((this.max - this.min) / 10 < 30) {
                    g2d.drawString(i + "", 5, 524 - (i - this.min) * 500 / (this.max - this.min));
                }

                g2d.drawLine(455, 520 - (i - this.min) * 500 / (this.max - this.min), 460, 520 - (i - this.min) * 500 / (this.max - this.min));
                if ((this.max - this.min) / 10 < 30) {
                    g2d.drawString(i + "", 470, 524 - (i - this.min) * 500 / (this.max - this.min));
                }
            } else {
                g2d.drawLine(23, 520 - (i - this.min) * 500 / (this.max - this.min), 33, 520 - (i - this.min) * 500 / (this.max - this.min));
                g2d.drawString(i + "", 5, 524 - (i - this.min) * 500 / (this.max - this.min));
                g2d.drawLine(455, 520 - (i - this.min) * 500 / (this.max - this.min), 465, 520 - (i - this.min) * 500 / (this.max - this.min));
                if ((this.max - this.min) / 10 < 30) {
                    g2d.drawString(i + "", 470, 524 - (i - this.min) * 500 / (this.max - this.min));
                }
            }
        }

        g2d.drawLine(40, 545, 448, 545);
        g2d.drawLine(40, 565, 448, 565);

        for(i = 0; i < this.layers.length; ++i) {
            x = (int)(45.0F + this.layers[i] * one);
            if (i != 0 && i != this.layers.length - 1) {
                g2d.drawLine(x, 550, x, 525);
            } else {
                g2d.drawLine(x, 570, x, 525);
            }
        }

    }

    public void repaint() {
        super.repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getSource() instanceof JTextField && e.getKeyChar() == '\n') {
            int i;
            for(i = 1; i < this.layers.length; ++i) {
                this.layers[i] = this.layers[i - 1] + (float)Integer.parseInt(this.thicknessByLayers.get(i - 1).getText());
            }

            for(i = 1; i < this.layers.length; ++i) {
                int x = (int)(199.0F * (this.layers[i - 1] + this.layers[i]) / this.layers[this.layers.length - 1] + 45.0F);
                this.thicknessByLayers.get(i - 1).setText(String.format("%.0f", this.layers[i] - this.layers[i - 1]));
                this.thicknessByLayers.get(i - 1).setBounds(x - 12, 530, 25, 15);
            }

            this.thickness.setText(String.format("%.0f", this.layers[this.layers.length - 1]));
            this.repaint();
        }

    }

    public void keyReleased(KeyEvent e) {
    }
}
