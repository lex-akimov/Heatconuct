package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

class DoubleScale extends Scale {
    private DoubleScale.DoubleBar doubleBar = new DoubleScale.DoubleBar();

    DoubleScale(ImageIcon icon, float max) {
        super(icon, max);
        this.add(this.doubleBar);
    }

    public void repaint(float value) {
        if (this.doubleBar != null) {
            this.doubleBar.repaint(value);
        }

        super.repaint(value);
    }

    private class DoubleBar extends JComponent {
        float value;
        float length;

        DoubleBar() {
            this.setPreferredSize(new Dimension(DoubleScale.this.width + 1, DoubleScale.this.height + 1));
        }

        private void repaint(float value) {
            this.value = value;
            this.length = Math.abs((float)(DoubleScale.this.width / 2) / DoubleScale.this.absMax * value);
            super.repaint();
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setColor(StaticFinals.background);
            g2d.fillRect(0, 0, DoubleScale.this.width, DoubleScale.this.height);
            if (this.value < 0.0F) {
                g2d.setColor(StaticFinals.negative);
                g2d.fillRect((int)((float)(DoubleScale.this.width / 2) - this.length), 0, (int)this.length + 1, DoubleScale.this.height);
            } else {
                g2d.setColor(StaticFinals.positive);
                g2d.fillRect(DoubleScale.this.width / 2, 0, (int)this.length, DoubleScale.this.height);
            }

            g2d.setColor(Color.BLACK);
            g2d.drawRect(0, 0, DoubleScale.this.width, DoubleScale.this.height);

            for(int i = 0; (float)i < DoubleScale.this.absMax * 4.0F / 10.0F; ++i) {
                int n = (int)((float)DoubleScale.this.width / (DoubleScale.this.absMax * 4.0F / 10.0F) * (float)i);
                if (i % 2 == 0) {
                    g2d.drawLine(n, DoubleScale.this.height - DoubleScale.this.every10, n, DoubleScale.this.height);
                } else if (DoubleScale.this.absMax < 100.0F) {
                    g2d.drawLine(n, DoubleScale.this.height - DoubleScale.this.every5, n, DoubleScale.this.height);
                }
            }

        }
    }
}
