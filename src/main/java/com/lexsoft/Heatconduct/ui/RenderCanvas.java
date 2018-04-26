package com.lexsoft.Heatconduct.ui;

import com.lexsoft.Heatconduct.gElements.DoubleScale;
import com.lexsoft.Heatconduct.gElements.SingleScale;
import com.lexsoft.Heatconduct.gElements.TemperatureChart;
import com.lexsoft.Heatconduct.parsing.Parser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import javax.swing.*;

import static com.lexsoft.Heatconduct.ui.CONSTANTS.CHART_TITLE;

class RenderCanvas extends JPanel {
    private static final SimpleDateFormat dateTime = new SimpleDateFormat("dd.MM.YYYY  HH:mm");
    private TemperatureChart temperatureChart;
    private LeftPan leftPan;
    private RightPan rightPan;

    private Parser parser;

    RenderCanvas() {
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
    }

    public void repaint(int frame) {
        if (temperatureChart != null) {
            temperatureChart.repaint(frame);
        }

        if (leftPan != null) {
            leftPan.repaint(frame);
        }

        if (rightPan != null) {
            rightPan.repaint(frame);
        }
    }

    void activate(Parser parser, Integer current) {
        this.parser = parser;

        JLabel jLabel = new JLabel(CHART_TITLE);
        jLabel.setFont(CONSTANTS.FONT_TITLE);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(jLabel, "North");
        this.add(temperatureChart = new TemperatureChart(parser, current));
        this.add(leftPan = new LeftPan(), "West");
        this.add(rightPan = new RightPan(), "East");
        this.repaint(0);
    }

    private class RightPan extends JPanel {
        private SingleScale AlphaOut;
        private DoubleScale QOut;
        private JLabel Tout;

        RightPan() {
            this.setPreferredSize(new Dimension(250, 250));
            this.setOpaque(false);
            this.Tout = new JLabel();
            this.Tout.setFont(CONSTANTS.FONT_LARGE);
            this.AlphaOut = new SingleScale(new ImageIcon(this.getClass().getResource("/alphaOut.png")), parser.getAlphaMaxAbs());
            this.QOut = new DoubleScale(new ImageIcon(this.getClass().getResource("/qOut.png")), parser.getQMaxAbs());
            this.add(this.Tout, this);
            this.add(this.AlphaOut, this);
            this.add(this.QOut, this);
            SpringLayout layout = new SpringLayout();
            this.setLayout(layout);
            layout.putConstraint("East", QOut, -10, "East", this);
            layout.putConstraint("East", AlphaOut, -10, "East", this);
            layout.putConstraint("East", Tout, -40, "East", this);
            layout.putConstraint("South", QOut, -50, "South", this);
            layout.putConstraint("South", AlphaOut, -10, "North", this.QOut);
            layout.putConstraint("South", Tout, -10, "North", this.AlphaOut);
        }

        void repaint(int frame) {
            if (Tout != null) {
                Tout.setText(String.format("tнар. = %+2.1f ˚C", parser.getTemperatures()[frame][parser.getTemperaturesCount() - 1]));
            }

            if (AlphaOut != null) {
                AlphaOut.repaint(parser.getAlphaOut()[frame]);
            }

            if (QOut != null) {
                QOut.repaint(parser.getQOut()[frame]);
            }
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
            this.dateTimeLabel.setFont(CONSTANTS.FONT_LARGE);
            this.Tin = new JLabel();
            this.Tin.setFont(CONSTANTS.FONT_LARGE);
            this.AlphaIn = new SingleScale(new ImageIcon(this.getClass().getResource("/alphaIn.png")), parser.getAlphaMaxAbs());
            this.QIn = new DoubleScale(new ImageIcon(this.getClass().getResource("/qIn.png")), parser.getQMaxAbs());
            this.add(dateTimeLabel);
            this.add(Tin);
            this.add(AlphaIn);
            this.add(QIn);
            SpringLayout layout = new SpringLayout();
            this.setLayout(layout);
            layout.putConstraint("West", QIn, 10, "West", this);
            layout.putConstraint("West", AlphaIn, 10, "West", this);
            layout.putConstraint("West", Tin, 40, "West", this);
            layout.putConstraint("West", dateTimeLabel, 10, "West", this);
            layout.putConstraint("South", QIn, -50, "South", this);
            layout.putConstraint("South", AlphaIn, -10, "North", this.QIn);
            layout.putConstraint("South", Tin, -10, "North", this.AlphaIn);
            layout.putConstraint("North", dateTimeLabel, 20, "North", this);
        }

        void repaint(int frame) {
            if (dateTimeLabel != null) {
                dateTimeLabel.setText(RenderCanvas.dateTime.format(parser.getDates()[frame]));
            }

            if (Tin != null) {
                Tin.setText(String.format("tвн. = %+2.1f ˚C", parser.getTemperatures()[frame][0]));
            }

            if (AlphaIn != null) {
                AlphaIn.repaint(parser.getAlphaIn()[frame]);
            }

            if (QIn != null) {
                QIn.repaint(parser.getQIn()[frame]);
            }
        }
    }
}