package ui;

import parsing.ParseXLS;
import parsing.Parser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static ui.CONSTANTS.WINDOW_TITLE;

public class Window extends JFrame {
    public static void main(String[] args) {
        Window window = new Window();
        if (args.length > 0) {
            window.OpenXLS(args[0]);
        }
    }

    private void OpenXLS(String file) {
        try {
            parser = new ParseXLS(file);
            currChart = 0;
            this.menuFileExport.setEnabled(true);
            navigationPanel.Activate();
            playPanel.Activate();
            mainPanel.Activate();
        } catch (Exception var3) {
            JOptionPane.showMessageDialog(this, var3.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
        }
    }


    private JMenu menuFileExport;
    private JMenuItem menuSettingsDrawLayers;
    private JMenuItem menuSettingsDrawDegreeLines;

    static MainPane mainPanel;
    static NavigationPane navigationPanel;
    private static PlayerPane playPanel;
    static int currChart;
    static Parser parser;


    private Window() {
        int width = 1000;
        int height = 700;
        this.setTitle(WINDOW_TITLE);
        this.setPreferredSize(new Dimension(width, height));
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addJMenuBar();
        this.addPanels();
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void addJMenuBar() {
        JMenu menuFile = new JMenu("Файл");
        menuFileExport = new JMenu("Экспорт");
        JMenu menuSettings = new JMenu("Настройки");
        JMenu menuAbout = new JMenu("О программе");
        JMenuItem menuFileOpen = new JMenuItem("Импорт файла измерений");
        JMenuItem menuFileExportSingle = new JMenuItem("Текущего кадра...");
        JMenuItem menuFileExportAll = new JMenuItem("Всех кадров...");
        JMenuItem menuFileExit = new JMenuItem("Выход");
        JMenuItem menuSettingsLoadBack = new JMenuItem("Загрузить подложку");
        this.menuSettingsDrawLayers = new JMenuItem("Скрыть границы слоёв");
        this.menuSettingsDrawDegreeLines = new JMenuItem("Скрыть линии градусов");
        JMenuItem menuAboutHelp = new JMenuItem("Общие указания");
        JMenuItem menuAboutCreators = new JMenuItem("Об авторе");
        JMenuBar menuBar = new JMenuBar();
        menuFileOpen.addActionListener(new OpenActionListener());
        menuFileExport.setEnabled(false);
        menuFileExportSingle.addActionListener(new ExportSingleActionListener());
        menuFileExportAll.addActionListener(new ExportAllActionListener());
        menuFileExport.add(menuFileExportSingle);
        menuFileExport.add(menuFileExportAll);
        menuFileExit.addActionListener(new ExitActionListener());
        menuSettingsLoadBack.addActionListener(new DrawBackgroundActionListener());
        menuSettingsDrawLayers.addActionListener(new DrawLayersActionListener());
        menuSettingsDrawDegreeLines.addActionListener(new DrawDegreesActionListener());
        menuAboutHelp.addActionListener(new HelpActionListener());
        menuAboutCreators.addActionListener(new CreatorsActionListener());
        menuFile.add(menuFileOpen);
        menuFile.add(this.menuFileExport);
        menuFile.add(menuFileExit);
        menuSettings.add(menuSettingsLoadBack);
        menuSettings.add(this.menuSettingsDrawLayers);
        menuSettings.add(this.menuSettingsDrawDegreeLines);
        menuAbout.add(menuAboutHelp);
        menuAbout.add(menuAboutCreators);
        menuBar.add(menuFile);
        menuBar.add(menuSettings);
        menuBar.add(menuAbout);
        this.setJMenuBar(menuBar);
    }

    private void addPanels() {
        JPanel lowPanel = new JPanel();
        lowPanel.setBorder(new EtchedBorder());
        lowPanel.setLayout(new GridLayout(1, 0));
        lowPanel.add(navigationPanel = new NavigationPane());
        lowPanel.add(playPanel = new PlayerPane());
        this.add("Center", mainPanel = new MainPane());
        this.add("South", lowPanel);
    }

    private void savePicture(BufferedImage img, File outputFile) throws IOException {
        mainPanel.paint(img.getGraphics());
        ImageIO.write(img, "png", outputFile);
    }

    private class OpenActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FileNameExtensionFilter extXLS = new FileNameExtensionFilter("MS Excel 97-2003 (*.xls)", "xls");
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jFileChooser.setDialogTitle("Импорт файла");
            jFileChooser.setFileFilter(extXLS);
            int returnValue = jFileChooser.showOpenDialog(null);
            if (returnValue == 0) {
                try {
                    if (jFileChooser.getFileFilter().equals(extXLS)) {
                        parser = new ParseXLS(jFileChooser.getSelectedFile().toString());
                    }
                    currChart = 0;
                    menuFileExport.setEnabled(true);
                    navigationPanel.Activate();
                    playPanel.Activate();
                    mainPanel.Activate();
                } catch (Exception var6) {
                    JOptionPane.showMessageDialog(getParent(), var6.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class ExportSingleActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SimpleDateFormat exportName = new SimpleDateFormat("yyyy-MM-dd HH-mm");
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setDialogTitle("Экспорт текущего кадра");
            jFileChooser.setSelectedFile(new File(exportName.format(parser.getDatesArr()[currChart])));
            jFileChooser.setFileFilter(new FileNameExtensionFilter("Изображение в формате PNG", "png"));
            int returnValue = jFileChooser.showSaveDialog(getParent());
            if (returnValue == 0) {
                BufferedImage img = new BufferedImage(mainPanel.getWidth(), mainPanel.getHeight(), 1);
                File file = new File(jFileChooser.getSelectedFile().getAbsolutePath() + ".png");
                try {
                    savePicture(img, file);
                    JOptionPane.showMessageDialog(getParent(), "Файл " + file.getName() + " сохранён.", "", JOptionPane.INFORMATION_MESSAGE);
                    img.flush();
                } catch (IOException var7) {
                    JOptionPane.showMessageDialog(getParent(), var7.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class ExportAllActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setDialogTitle("Экспорт всех кадров");
            jFileChooser.setFileFilter(new FileNameExtensionFilter("Изображение в формате PNG", "png"));
            int returnValue = jFileChooser.showSaveDialog(getParent());
            if (returnValue == 0) {
                Date date1 = new Date();

                BufferedImage img = new BufferedImage(mainPanel.getWidth(), mainPanel.getHeight(), 1);

                try {
                    int numOfThreads = 4;
                    ArrayList<SaveImageThread> imageSavingThreads = new ArrayList<>();

                    for (int i = 0; i < numOfThreads; i++) {
                        SaveImageThread thread = new SaveImageThread(i, numOfThreads);
                        thread.start();
                        imageSavingThreads.add(thread);
                    }

                    for (SaveImageThread imageSavingThread : imageSavingThreads) {
                        imageSavingThread.join();
                    }

                    for (int i = 0; i < parser.getFrameCount(); ++i) {
                        currChart = i;
                        mainPanel.repaint();
                        File file = new File(jFileChooser.getSelectedFile().getAbsolutePath() + "_" + String.format("%06d", i + 1) + ".png");
                        savePicture(img, file);
                    }

                    img.flush();
                    Date date2 = new Date();
                    JOptionPane.showMessageDialog(getParent(), parser.getFrameCount() + " файлов успешно сохранёно за " + (date2.getTime() - date1.getTime()), "", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException var6) {
                    JOptionPane.showMessageDialog(getParent(), var6.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private class ExitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class HelpActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO help dialog
        }
    }

    private class CreatorsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(getParent(), "" +
                    "Акимов Алексей\n" +
                    "НГАСУ Сибстрин\n" +
                    "Кафедра ИСТ\n" +
                    "2017", "Об авторе:", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private class DrawBackgroundActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                FileNameExtensionFilter pictures = new FileNameExtensionFilter("Изображения (*.jpg, *.png, *.bmp)", "jpg", "png", "bmp");
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jFileChooser.setDialogTitle("Загрузить подложку");
                jFileChooser.setFileFilter(pictures);
                int returnValue = jFileChooser.showOpenDialog(getParent());
                if (returnValue == 0) {
                    TemperatureChart.background = ImageIO.read(jFileChooser.getSelectedFile());
                    mainPanel.repaint();
                }

            } catch (IOException var3) {
                var3.printStackTrace();
            }
        }
    }

    private class DrawLayersActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (TemperatureChart.drawLayerBound) {
                TemperatureChart.drawLayerBound = false;
                menuSettingsDrawLayers.setText("Показать границы слоёв");
                mainPanel.repaint();
            } else {
                TemperatureChart.drawLayerBound = true;
                menuSettingsDrawLayers.setText("Скрыть границы слоёв");
                mainPanel.repaint();
            }
        }
    }

    private class DrawDegreesActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (TemperatureChart.drawDegreeLines) {
                TemperatureChart.drawDegreeLines = false;
                menuSettingsDrawDegreeLines.setText("Показать линии градусов");
                mainPanel.repaint();
            } else {
                TemperatureChart.drawDegreeLines = true;
                menuSettingsDrawDegreeLines.setText("Скрыть линии градусов");
                mainPanel.repaint();
            }
        }
    }

    private class SaveImageThread extends Thread {
        int id;
        int quantity;

        SaveImageThread(int id, int quantity) {
            this.id = id;
            this.quantity = quantity;
        }

        @Override
        public void run() {
            for (int i = id - 1; i < currChart; i += quantity) {

            }
        }
    }
}
