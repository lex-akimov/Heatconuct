package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

class NavigationPane extends JPanel implements KeyListener {
    private JButton navBeginBtn;
    private JButton navEndBtn;
    private JButton navPrevBtn;
    private JButton navNextBtn;
    private JTextField navCurrFrField;
    private JLabel navFrCntLabel;

    NavigationPane() {
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
        this.navCurrFrField = new JTextField(6);
        this.navCurrFrField.setHorizontalAlignment(0);
        this.navCurrFrField.addKeyListener(this);
        this.navFrCntLabel = new JLabel("     ");
        this.navBeginBtn.setEnabled(false);
        this.navEndBtn.setEnabled(false);
        this.navPrevBtn.setEnabled(false);
        this.navNextBtn.setEnabled(false);
        this.navCurrFrField.setEnabled(false);
        this.navFrCntLabel.setEnabled(false);
        this.add(this.navBeginBtn);
        this.add(this.navPrevBtn);
        this.add(this.navCurrFrField);
        this.add(this.navFrCntLabel);
        this.add(this.navNextBtn);
        this.add(this.navEndBtn);
    }

    void setNavCurrFrField(int CurrChartN) {
        this.navCurrFrField.setText(CurrChartN + 1 + "");
    }

    private int getNavCurrFrField() {
        return Integer.parseInt(this.navCurrFrField.getText()) - 1;
    }

    void Activate() {
        this.navBeginBtn.setEnabled(true);
        this.navEndBtn.setEnabled(true);
        this.navPrevBtn.setEnabled(true);
        this.navNextBtn.setEnabled(true);
        this.navCurrFrField.setEnabled(true);
        this.navFrCntLabel.setEnabled(true);
        this.setNavCurrFrField(Window.currChart);
        this.navFrCntLabel.setText(" из " + Window.parser.getFrameCount());
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getSource().equals(this.navCurrFrField) && e.getKeyChar() == '\n') {
            try {
                int newcurrChart = this.getNavCurrFrField();
                if (newcurrChart >= 0 && newcurrChart < Window.parser.getFrameCount()) {
                    Window.currChart = newcurrChart;
                } else if (newcurrChart >= Window.parser.getFrameCount()) {
                    Window.currChart = Window.parser.getFrameCount() - 1;
                } else if (newcurrChart < 0) {
                    Window.currChart = 0;
                }
            } catch (NumberFormatException var6) {
                System.out.println(var6.getMessage());
            } finally {
                Window.mainPanel.repaint();
            }
        }

    }

    public void keyReleased(KeyEvent e) {
    }

    private class NavBeginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Window.parser.getFrameCount() > 1) {
                Window.currChart = 0;
                Window.mainPanel.repaint();
            }
        }
    }

    private class NavEndActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Window.parser.getFrameCount() > 1 && Window.currChart < Window.parser.getFrameCount() - 1) {
                Window.currChart = Window.parser.getFrameCount() - 1;
                Window.mainPanel.repaint();
            }
        }
    }

    private class NavPreviousActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Window.parser.getFrameCount() > 1 && Window.currChart > 0) {
                --Window.currChart;
                Window.mainPanel.repaint();
            }
        }
    }

    private class NavNextActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Window.parser.getFrameCount() > 1 && Window.currChart < Window.parser.getFrameCount() - 1) {
                ++Window.currChart;
                Window.mainPanel.repaint();
            }
        }
    }
}
