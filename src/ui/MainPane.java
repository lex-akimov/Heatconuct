package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import javax.swing.*;

import parsing.Parser;

class MainPane extends JPanel {
    private static final SimpleDateFormat dateTime = new SimpleDateFormat("dd.MM.YYYY  HH:mm");
    private TemperatureChart temperatureChart;
    private MainPane.LeftPan leftPan;
    private MainPane.RightPan rightPan;

    MainPane() {
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
    }

    public void repaint() {
        if (this.temperatureChart != null) {
            this.temperatureChart.repaint();
        }

        if (this.leftPan != null) {
            this.leftPan.repaint();
        }

        if (this.rightPan != null) {
            this.rightPan.repaint();
        }

        super.repaint();
    }

    void Activate() {
        JLabel jLabel = new JLabel("Температурное поле при периодических тепловых процессах в наружном ограждении");
        jLabel.setFont(StaticFinals.title);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(jLabel, "North");
        this.add(this.temperatureChart = new TemperatureChart(Window.parser.getTempMin(), Window.parser.getTempMax()));
        this.add(this.leftPan = new MainPane.LeftPan(), "West");
        this.add(this.rightPan = new MainPane.RightPan(), "East");
    }

    private class RightPan extends JPanel {
        private SingleScale AlphaOut;
        private DoubleScale QOut;
        private JLabel Tout;

        RightPan() {
            this.setPreferredSize(new Dimension(250, 250));
            this.setOpaque(false);
            this.Tout = new JLabel();
            this.Tout.setFont(StaticFinals.fontLarge);
            this.AlphaOut = new SingleScale(new ImageIcon(this.getClass().getResource("/res/alphaOut.png")), Window.parser.getAlphaMaxAbs());
            this.QOut = new DoubleScale(new ImageIcon(this.getClass().getResource("/res/qOut.png")), Window.parser.getQMaxAbs());
            this.add(this.Tout, this);
            this.add(this.AlphaOut, this);
            this.add(this.QOut, this);
            SpringLayout layout = new SpringLayout();
            this.setLayout(layout);
            layout.putConstraint("East", this.QOut, -10, "East", this);
            layout.putConstraint("East", this.AlphaOut, -10, "East", this);
            layout.putConstraint("East", this.Tout, -40, "East", this);
            layout.putConstraint("South", this.QOut, -50, "South", this);
            layout.putConstraint("South", this.AlphaOut, -10, "North", this.QOut);
            layout.putConstraint("South", this.Tout, -10, "North", this.AlphaOut);
        }

        public void repaint() {
            if (this.Tout != null) {
                this.Tout.setText(String.format("tнар. = %+2.1f ˚C", Window.parser.getTemperaturesArray()[Window.currChart][Parser.temperaturesCount - 1]));
            }

            if (this.AlphaOut != null) {
                this.AlphaOut.repaint(Window.parser.getAlphaOut()[Window.currChart]);
            }

            if (this.QOut != null) {
                this.QOut.repaint(Window.parser.getQOut()[Window.currChart]);
            }

            super.repaint();
        }
    }

    private class LeftPan extends JPanel {
        private JLabel dateTimeLabel;
        private SingleScale AlphaIn;
        private DoubleScale QIn;
        private JLabel Tin;

        LeftPan() {
            this.setPreferredSize(new Dimension(250, 250));
            this.setOpaque(false);
            this.dateTimeLabel = new JLabel();
            this.dateTimeLabel.setFont(StaticFinals.fontLarge);
            this.Tin = new JLabel();
            this.Tin.setFont(StaticFinals.fontLarge);
            this.AlphaIn = new SingleScale(new ImageIcon(this.getClass().getResource("/res/alphaIn.png")), Window.parser.getAlphaMaxAbs());
            this.QIn = new DoubleScale(new ImageIcon(this.getClass().getResource("/res/qIn.png")), Window.parser.getQMaxAbs());
            this.add(this.dateTimeLabel);
            this.add(this.Tin);
            this.add(this.AlphaIn);
            this.add(this.QIn);
            SpringLayout layout = new SpringLayout();
            this.setLayout(layout);
            layout.putConstraint("West", this.QIn, 10, "West", this);
            layout.putConstraint("West", this.AlphaIn, 10, "West", this);
            layout.putConstraint("West", this.Tin, 40, "West", this);
            layout.putConstraint("West", this.dateTimeLabel, 10, "West", this);
            layout.putConstraint("South", this.QIn, -50, "South", this);
            layout.putConstraint("South", this.AlphaIn, -10, "North", this.QIn);
            layout.putConstraint("South", this.Tin, -10, "North", this.AlphaIn);
            layout.putConstraint("North", this.dateTimeLabel, 20, "North", this);
        }

        public void repaint() {
            if (this.dateTimeLabel != null) {
                this.dateTimeLabel.setText(MainPane.dateTime.format(Window.parser.getDatesArr()[Window.currChart]));
            }

            if (this.Tin != null) {
                this.Tin.setText(String.format("tвн. = %+2.1f ˚C", Window.parser.getTemperaturesArray()[Window.currChart][0]));
            }

            if (this.AlphaIn != null) {
                this.AlphaIn.repaint(Window.parser.getAlphaIn()[Window.currChart]);
            }

            if (this.QIn != null) {
                this.QIn.repaint(Window.parser.getQIn()[Window.currChart]);
            }

            super.repaint();
        }
    }
}
