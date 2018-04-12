package com.lexsoft.Heatconduct.ui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.lexsoft.Heatconduct.ui.Window.currChart;


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

    private class PlayActionListener implements ActionListener {
        @Override
        //При нажатии на кнопку play
        public void actionPerformed(ActionEvent e) {
            //если не было воспроизведения
            if (playBtn.getText().equals("PLAY")) {
                //создаём отдельны потои и запускаем его
                playerThread = new PlayThread();
                playerThread.start();

            } else if (!playerThread.isInterrupted()) {
                //если воспроизведение уже шло, прерываем его
                playerThread.interrupt();
                playBtn.setText("PLAY");
                Window.mainPanel.repaint();
            }
        }
    }

    private class PlayThread extends Thread {
        public void run() {
            try {
                //длительность кадра анимации в милисекундах
                int playFPS = Math.round(1000 / getFPS());
                playBtn.setText("PAUSE");

                //запускаем цикл, пока не дойдём по последнего кадра или не прервём воспроизведение
                while (Window.currChart < Window.parser.getFrameCount() - 1 && !this.isInterrupted()) {
                    ++Window.currChart;
                    Thread.sleep(playFPS);
                    Window.slider.setValue(currChart);
                    Window.mainPanel.repaint();
                }
                //прерываем поток
                this.interrupt();
                playBtn.setText("PLAY");
            } catch (InterruptedException var2) {
                System.out.println(var2.toString());
            } catch (IllegalArgumentException var3) {
                //если неверно ввели частоту кадров, обрабатываем исключение
                PlayerPane.this.setFPS(30);
                JOptionPane.showMessageDialog(getParent(), "Введите целое положительное число", "Ошибка!", JOptionPane.ERROR_MESSAGE);
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
