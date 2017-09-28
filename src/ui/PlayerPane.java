package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

class PlayerPane extends JPanel {
    private JButton playBtn;
    private JButton stopBtn;
    private final JLabel frameRate = new JLabel(" Частота кадров: ");
    private JTextField enterFPS;
    private PlayThread playerThread;

    private int getFPS() {
        return Integer.parseInt(this.enterFPS.getText());
    }

    private void setFPS(int FPS) {
        this.enterFPS.setText(FPS + "");
    }

    PlayerPane() {
        this.setBorder(new EtchedBorder());
        this.setLayout(new FlowLayout(1));
        this.playBtn = new JButton("PLAY");
        this.playBtn.addActionListener(new PlayActionListener());
        this.stopBtn = new JButton("STOP");
        this.stopBtn.addActionListener(new StopActionListener());
        this.enterFPS = new JTextField(3);
        this.enterFPS.setHorizontalAlignment(0);
        this.playBtn.setEnabled(false);
        this.stopBtn.setEnabled(false);
        this.frameRate.setEnabled(false);
        this.enterFPS.setEnabled(false);
        this.add(playBtn);
        this.add(stopBtn);
        this.add(frameRate);
        this.add(enterFPS);
    }

    void Activate() {
        this.playBtn.setEnabled(true);
        this.stopBtn.setEnabled(true);
        this.frameRate.setEnabled(true);
        this.enterFPS.setEnabled(true);
        this.setFPS(30);
    }

    private class PlayThread extends Thread {
        public void run() {
            try {
                int playFPS = Math.round(1000 / getFPS());
                playBtn.setText("PAUSE");

                while (Window.currChart < Window.parser.getFrameCount() - 1 && !this.isInterrupted()) {
                    ++Window.currChart;
                    Thread.sleep(playFPS);
                    Window.mainPanel.repaint();
                }
                this.interrupt();
                playBtn.setText("PLAY");
            } catch (InterruptedException var2) {
                System.out.println(var2.toString());
            } catch (IllegalArgumentException var3) {
                PlayerPane.this.setFPS(30);
                JOptionPane.showMessageDialog(getParent(), "Введите целое положительное число", "Ошибка!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class PlayActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (playBtn.getText().equals("PLAY")) {
                playerThread = new PlayThread();
                playerThread.start();

            } else if (!playerThread.isInterrupted()) {
                playerThread.interrupt();
                playBtn.setText("PLAY");
                Window.mainPanel.repaint();
            }
        }
    }

    private class StopActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!playerThread.isInterrupted()) {
                playerThread.interrupt();
                Window.currChart = 0;
                playBtn.setText("PLAY");
                Window.mainPanel.repaint();
            }
        }
    }
}
