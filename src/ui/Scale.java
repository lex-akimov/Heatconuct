package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

abstract class Scale extends JPanel {
    float absMax;
    int width = 200;
    int height = 30;
    int every10 = 7;
    int every5 = 5;
    private float value;

    Scale(Icon icon, float max) {
        this.absMax = max - max % 10.0F + 10.0F;
        this.setOpaque(false);
        this.setBorder(new LineBorder(Color.BLUE));
        this.setPreferredSize(new Dimension(this.width + 20, this.height + 70));
        JLabel nameLabel = new JLabel();
        nameLabel.setIcon(icon);
        this.add(nameLabel);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(StaticFinals.fontLarge);
        g2d.drawString(String.format("%.1f", this.value), 90, 90);
    }

    public void repaint(float value) {
        this.value = value;
        super.repaint();
    }
}
