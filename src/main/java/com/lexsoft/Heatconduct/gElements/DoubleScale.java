package com.lexsoft.Heatconduct.gElements;

import com.lexsoft.Heatconduct.ui.CONSTANTS;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import static com.lexsoft.Heatconduct.ui.CONSTANTS.COLOR_POSITIVE;

public class DoubleScale extends Scale {
    private DoubleBar doubleBar = new DoubleBar();

    public DoubleScale(ImageIcon icon, float max) {
        super(icon, max);
        this.add(doubleBar);
    }

    public void repaint(float value) {
        super.repaint(value);
        if (doubleBar != null) {
            doubleBar.repaint(value);
        }

    }

    private class DoubleBar extends JComponent {
        float value;
        float length;

        DoubleBar() {
            this.setPreferredSize(new Dimension(width + 1, height + 1));
        }

        private void repaint(float value) {
            this.value = value;
            length = Math.abs((float) (width / 2) / absMax * value);
            super.repaint();
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(CONSTANTS.BACKGROUND);
            g2d.fillRect(0, 0, width, height);
            if (this.value < 0.0F) {
                g2d.setColor(CONSTANTS.COLOR_NEGATIVE);
                g2d.fillRect((int) ((float) (width / 2) - length), 0, (int) length + 1, height);
            } else {
                g2d.setColor(COLOR_POSITIVE);
                g2d.fillRect(width / 2, 0, (int) length, height);
            }

            g2d.setColor(Color.BLACK);
            g2d.drawRect(0, 0, width, height);

            for (int i = 0; (float) i < absMax * 4.0F / 10.0F; ++i) {
                int n = (int) ((float) width / (absMax * 4.0F / 10.0F) * (float) i);
                if (i % 2 == 0) {
                    g2d.drawLine(n, height - every10, n, height);
                } else if (absMax < 100.0F) {
                    g2d.drawLine(n, height - every5, n, height);
                }
            }

        }
    }
}
