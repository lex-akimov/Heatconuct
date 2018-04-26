package com.lexsoft.Heatconduct.ui;

import com.lexsoft.Heatconduct.parsing.Parser;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

class Navigator extends JPanel implements KeyListener {
    private JButton navBeginBtn;
    private JButton navEndBtn;
    private JButton navPrevBtn;
    private JButton navNextBtn;
    private JTextField navCurrentFrameField;
    private JLabel navFrCntLabel;

    private Parser parser;
    private RenderCanvas canvas;
    private Integer current;

    Navigator() {
        this.setBorder(new EtchedBorder());
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.navBeginBtn = new JButton("<<");
        this.navBeginBtn.addActionListener(new NavBeginActionListener());
        this.navEndBtn = new JButton(">>");
        this.navEndBtn.addActionListener(new NavEndActionListener());
        this.navPrevBtn = new JButton("<");
        this.navPrevBtn.addActionListener(new NavPreviousActionListener());
        this.navNextBtn = new JButton(">");
        this.navNextBtn.addActionListener(new NavNextActionListener());
        this.navCurrentFrameField = new JTextField(6);
        this.navCurrentFrameField.setHorizontalAlignment(0);
        this.navCurrentFrameField.addKeyListener(this);
        this.navFrCntLabel = new JLabel("     ");
        this.navBeginBtn.setEnabled(false);
        this.navEndBtn.setEnabled(false);
        this.navPrevBtn.setEnabled(false);
        this.navNextBtn.setEnabled(false);
        this.navCurrentFrameField.setEnabled(false);
        this.navFrCntLabel.setEnabled(false);
        this.add(this.navBeginBtn);
        this.add(this.navPrevBtn);
        this.add(this.navCurrentFrameField);
        this.add(this.navFrCntLabel);
        this.add(this.navNextBtn);
        this.add(this.navEndBtn);
    }

    private void setCurrentFrameFieldText(int currentFrame) {
        this.navCurrentFrameField.setText(currentFrame + 1 + "");
    }

    void activate(Parser parser, RenderCanvas canvas, Integer current) {
        this.parser = parser;
        this.canvas = canvas;
        this.current = current;


        this.navBeginBtn.setEnabled(true);
        this.navEndBtn.setEnabled(true);
        this.navPrevBtn.setEnabled(true);
        this.navNextBtn.setEnabled(true);
        this.navCurrentFrameField.setEnabled(true);
        this.navFrCntLabel.setEnabled(true);
        this.setCurrentFrameFieldText(current);
        this.navFrCntLabel.setText(" из " + parser.getFrameCount());
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getSource().equals(this.navCurrentFrameField) && e.getKeyChar() == '\n') {
            try {
                int newValueOfCurrent = Integer.parseInt(this.navCurrentFrameField.getText()) - 1;
                if (newValueOfCurrent >= 0 && newValueOfCurrent < parser.getFrameCount()) {
                    current = newValueOfCurrent;
                }
                else if (newValueOfCurrent < 0) {
                    current = 0;
                } else if (newValueOfCurrent >= parser.getFrameCount()) {
                    current = parser.getFrameCount() - 1;
                }
            }
            catch (NumberFormatException var6) {
                System.out.println(var6.getMessage());
            }
            finally {
                setCurrentFrameFieldText(current);
                canvas.repaint(current);
            }
        }

    }

    public void keyReleased(KeyEvent e) {
    }

    private class NavBeginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (parser.getFrameCount() > 1) {
                current = 0;
                setCurrentFrameFieldText(current);
                canvas.repaint(current);
            }
        }
    }

    private class NavEndActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (parser.getFrameCount() > 1 && current < parser.getFrameCount() - 1) {
                current = parser.getFrameCount() - 1;
                setCurrentFrameFieldText(current);
                canvas.repaint(current);
            }
        }
    }

    private class NavPreviousActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (parser.getFrameCount() > 1 && current > 0) {
                --current;
                setCurrentFrameFieldText(current);
                canvas.repaint(current);
            }
        }
    }

    private class NavNextActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (parser.getFrameCount() > 1 && current < parser.getFrameCount() - 1) {
                ++current;
                setCurrentFrameFieldText(current);
                canvas.repaint(current);
            }
        }
    }
}
