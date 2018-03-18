package com.lexsoft.ui;

import com.lexsoft.gElements.DoubleScale;
import com.lexsoft.gElements.SingleScale;
import com.lexsoft.gElements.TemperatureChart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import javax.swing.*;

import static com.lexsoft.ui.CONSTANTS.CHART_TITLE;
import static com.lexsoft.ui.Window.currChart;

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
        if (temperatureChart != null) {
            Window.navigationPanel.setNavCurrFrField(Window.currChart);
            temperatureChart.repaint();
        }

        if (leftPan != null) {
            leftPan.repaint();
        }

        if (rightPan != null) {
            rightPan.repaint();
        }

    }

    void Activate() {
        JLabel jLabel = new JLabel(CHART_TITLE);
        jLabel.setFont(CONSTANTS.FONT_TITLE);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(jLabel, "North");
        this.add(temperatureChart = new TemperatureChart(Window.parser.getTempMin(), Window.parser.getTempMax()));
        this.add(leftPan = new MainPane.LeftPan(), "West");
        this.add(rightPan = new MainPane.RightPan(), "East");
        this.repaint();
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
            this.AlphaOut = new SingleScale(new ImageIcon(this.getClass().getResource("/alphaOut.png")), Window.parser.getAlphaMaxAbs());
            this.QOut = new DoubleScale(new ImageIcon(this.getClass().getResource("/qOut.png")), Window.parser.getQMaxAbs());
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

        public void repaint() {
            if (Tout != null) {
                Tout.setText(String.format("tнар. = %+2.1f ˚C", Window.parser.getTemperatures()[currChart][Window.parser.getTemperaturesCount() - 1]));
            }

            if (AlphaOut != null) {
                AlphaOut.repaint(Window.parser.getAlphaOut()[currChart]);
            }

            if (QOut != null) {
                QOut.repaint(Window.parser.getQOut()[currChart]);
            }
            Window.slider.setValue(currChart);
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
            this.AlphaIn = new SingleScale(new ImageIcon(this.getClass().getResource("/alphaIn.png")), Window.parser.getAlphaMaxAbs());
            this.QIn = new DoubleScale(new ImageIcon(this.getClass().getResource("/qIn.png")), Window.parser.getQMaxAbs());
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

        public void repaint() {
            if (dateTimeLabel != null) {
                dateTimeLabel.setText(MainPane.dateTime.format(Window.parser.getDates()[currChart]));
            }

            if (Tin != null) {
                Tin.setText(String.format("tвн. = %+2.1f ˚C", Window.parser.getTemperatures()[currChart][0]));
            }

            if (AlphaIn != null) {
                AlphaIn.repaint(Window.parser.getAlphaIn()[currChart]);
            }

            if (QIn != null) {
                QIn.repaint(Window.parser.getQIn()[currChart]);
            }
        }
    }
}
