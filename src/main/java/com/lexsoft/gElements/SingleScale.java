package com.lexsoft.gElements;

import javax.swing.*;
import java.awt.*;

import static com.lexsoft.ui.CONSTANTS.BACKGROUND;
import static com.lexsoft.ui.CONSTANTS.COLOR_POSITIVE;

public class SingleScale extends Scale {
    private SingleBar singleBar = new SingleBar();

    public SingleScale(ImageIcon icon, float max) {
        super(icon, max);
        this.add(this.singleBar);
    }

    public void repaint(float value) {
        super.repaint(value);
        if (singleBar != null) {
            singleBar.repaint(value);
        }

    }

    private class SingleBar extends JComponent {
        float value;
        float length;

        SingleBar() {
            this.setPreferredSize(new Dimension(width + 1, height + 1));
        }

        private void repaint(float value) {
            this.value = value;
            this.length = Math.abs((float)width / absMax * value);
            super.repaint();
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setColor(BACKGROUND);
            g2d.fillRect(0, 0, width, height);
            g2d.setColor(COLOR_POSITIVE);
            g2d.fillRect(0, 0, (int)this.length, height);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(0, 0, width, height);

            for(int i = 0; (float)i < absMax * 4.0F / 10.0F; ++i) {
                int n = (int)((float)width / (absMax * 2.0F / 10.0F) * (float)i);
                if (i % 2 == 0) {
                    g2d.drawLine(n, height - every10, n, height);
                } else if (absMax < 100.0F) {
                    g2d.drawLine(n, height - every5, n, height);
                }
            }

        }
    }
}
