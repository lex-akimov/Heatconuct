package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

class SingleScale extends Scale {
    private SingleScale.SingleBar singleBar = new SingleScale.SingleBar();

    SingleScale(ImageIcon icon, float max) {
        super(icon, max);
        this.add(this.singleBar);
    }

    public void repaint(float value) {
        if (this.singleBar != null) {
            this.singleBar.repaint(value);
        }

        super.repaint(value);
    }

    private class SingleBar extends JComponent {
        float value;
        float length;

        SingleBar() {
            this.setPreferredSize(new Dimension(SingleScale.this.width + 1, SingleScale.this.height + 1));
        }

        private void repaint(float value) {
            this.value = value;
            this.length = Math.abs((float)SingleScale.this.width / SingleScale.this.absMax * value);
            super.repaint();
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setColor(StaticFinals.background);
            g2d.fillRect(0, 0, SingleScale.this.width, SingleScale.this.height);
            g2d.setColor(StaticFinals.positive);
            g2d.fillRect(0, 0, (int)this.length, SingleScale.this.height);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(0, 0, SingleScale.this.width, SingleScale.this.height);

            for(int i = 0; (float)i < SingleScale.this.absMax * 4.0F / 10.0F; ++i) {
                int n = (int)((float)SingleScale.this.width / (SingleScale.this.absMax * 2.0F / 10.0F) * (float)i);
                if (i % 2 == 0) {
                    g2d.drawLine(n, SingleScale.this.height - SingleScale.this.every10, n, SingleScale.this.height);
                } else if (SingleScale.this.absMax < 100.0F) {
                    g2d.drawLine(n, SingleScale.this.height - SingleScale.this.every5, n, SingleScale.this.height);
                }
            }

        }
    }
}
