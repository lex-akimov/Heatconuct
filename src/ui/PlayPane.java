package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

class PlayPane extends JPanel implements ActionListener {
    private JButton playBtn;
    private JButton stopBtn;
    private final JLabel frameRate = new JLabel(" Частота кадров: ");
    private JTextField enterFPS;
    private PlayPane.Play playerThread;

    private int getFPS() {
        return Integer.parseInt(this.enterFPS.getText());
    }

    private void setFPS(int FPS) {
        this.enterFPS.setText(FPS + "");
    }

    PlayPane() {
        this.setBorder(new EtchedBorder());
        this.setLayout(new FlowLayout(1));
        this.playBtn = new JButton("PLAY");
        this.playBtn.addActionListener(this);
        this.stopBtn = new JButton("STOP");
        this.stopBtn.addActionListener(this);
        this.enterFPS = new JTextField(3);
        this.enterFPS.setHorizontalAlignment(0);
        this.playBtn.setEnabled(false);
        this.stopBtn.setEnabled(false);
        this.frameRate.setEnabled(false);
        this.enterFPS.setEnabled(false);
        this.add(this.playBtn);
        this.add(this.stopBtn);
        this.add(this.frameRate);
        this.add(this.enterFPS);
    }

    void Activate() {
        this.playBtn.setEnabled(true);
        this.stopBtn.setEnabled(true);
        this.frameRate.setEnabled(true);
        this.enterFPS.setEnabled(true);
        this.setFPS(30);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.playBtn)) {
            this.buttonPlayAction();
        }

        if (e.getSource().equals(this.stopBtn)) {
            this.buttonStopAction();
        }

    }

    private void buttonPlayAction() {
        if (this.playBtn.getText().equals("PLAY")) {
            this.playerThread = new PlayPane.Play();
            this.playerThread.start();
        } else if (this.playBtn.getText().equals("PAUSE")) {
            this.playerThread.interrupt();
            this.playBtn.setText("PLAY");
            Window.mainPanel.repaint();
        }

    }

    private void buttonStopAction() {
        if (Window.parser.getFrameCount() > 1) {
            this.playerThread.interrupt();
            Window.currChart = 0;
            this.playBtn.setText("PLAY");
            Window.mainPanel.repaint();
        }

    }

    private class Play extends Thread {
        private Play() {
        }

        public void run() {
            try {
                int playFPS = Math.round((float)(1000 / PlayPane.this.getFPS()));
                PlayPane.this.playBtn.setText("PAUSE");

                while(Window.currChart < Window.parser.getFrameCount() - 1 && !this.isInterrupted()) {
                    ++Window.currChart;
                    Thread.sleep((long)playFPS);
                    SwingUtilities.invokeLater(() -> {
                        Window.mainPanel.repaint();
                    });
                }

                PlayPane.this.playBtn.setText("PLAY");
                this.interrupt();
            } catch (InterruptedException var2) {
                System.out.println(var2.toString());
            } catch (IllegalArgumentException var3) {
                PlayPane.this.setFPS(30);
                JOptionPane.showMessageDialog(null, "Введите целое положительное число", "Ошибка!", 0);
            }

        }
    }
}
