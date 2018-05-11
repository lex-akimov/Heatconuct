package com.lexsoft.Heatconduct.ui;

import com.lexsoft.Heatconduct.gElements.TemperatureChart;
import com.lexsoft.Heatconduct.parsing.ParserXLS;
import com.lexsoft.Heatconduct.parsing.Parser;
import com.lexsoft.Heatconduct.parsing.ParsingException;

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
import java.util.Date;

import static com.lexsoft.Heatconduct.ui.CONSTANTS.WINDOW_TITLE;

public class Window extends JFrame {
    private Integer current;
    private Parser parser;

    private JMenu menuFileExport;
    private JMenuItem menuSettingsDrawLayers;
    private JMenuItem menuSettingsDrawDegreeLines;

    private RenderCanvas canvas;
    private Navigator navigator;
    private Player player;

    // Стартовый метод
    public static void main(String[] args) {
        Window window = new Window();

        // Метод для прямого открытия файла при запуске
        // с параметром "имя_файла.xls" в консоли
        if (args.length > 0) {
            try {
                window.parser = new ParserXLS(args[0]);
                TemperatureChart.background = ImageIO.read(new File(args[1]));
                window.current = 0;
                window.activate();
            }
            catch (ParsingException exc) {
                JOptionPane.showMessageDialog(window, exc.getMessage(), "Ошибка при загрузке таблицы!", JOptionPane.ERROR_MESSAGE);
            }
            catch (IOException ioexc) {
                JOptionPane.showMessageDialog(window, ioexc.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void activate() {
        menuFileExport.setEnabled(true);
        this.add("Center", canvas = new RenderCanvas());
        canvas.activate(parser, current);
        navigator.activate(parser, canvas, current);
        player.activate(parser, canvas, current);
    }

    private Window() {
        int width = 1000;
        int height = 720;
        this.setTitle(WINDOW_TITLE);
        this.setPreferredSize(new Dimension(width, height));
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addMenuBar();
        this.addPanels();
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    /**
     * Инициализация меню
     */
    private void addMenuBar() {
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

        /**
         * Назначение ActionListeners
         * */
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

    /**
     * Добавляем панели на окно программы
     */
    private void addPanels() {
        JPanel lowPanel = new JPanel();
        lowPanel.setBorder(new EtchedBorder());
        lowPanel.setLayout(new BorderLayout(2, 0));

        lowPanel.add("West", navigator = new Navigator());
        lowPanel.add("East", player = new Player());

        this.add("South", lowPanel);
    }

    /**
     * Ниже приведены внутренние (Inner) классы для обработки событий
     * связанных с нажатиями на элементы графического интерфейса
     */
    private class OpenActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            //Задаём расширение файла для открытия диалоговым окном
            FileNameExtensionFilter extXLS = new FileNameExtensionFilter("MS Excel 97-2003 (*.xls)", "xls");
            //Создаём объект типа JFileChooser для открытия диалога выбота файла
            JFileChooser jFileChooser = new JFileChooser();
            //Указываем тип - выбор файла
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            //Указываем заголовок окна
            jFileChooser.setDialogTitle("Импорт файла");
            //Устанавливаем фильтр по расширению файла
            jFileChooser.setFileFilter(extXLS);
            int returnValue = jFileChooser.showOpenDialog(null);
            //Если объект jFileChooser покажет что был выбран файл, заходим в if:
            if (returnValue == 0) {
                try {
                    if (jFileChooser.getFileFilter().equals(extXLS)) {
                        //Если выбранных файл является файлом расширения XLS, то
                        // присваиваем ссылочном переменой parser новый объект типа ParserXLS,
                        // с адресом файла в качестве параметра.
                        parser = new ParserXLS(jFileChooser.getSelectedFile().toString());
                    }
                    current = 0;
                    //Далее вызываем функции активации некоторых компонент интерфейса программы
                    activate();
                    //Если возникает исключение, нам нужно его обработать!
                }
                catch (Exception var6) {
                    //Показываем информационное окно с сообщением об ошибке
                    JOptionPane.showMessageDialog(getParent(), var6.getMessage(), "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }

    // Экспорт одной картинки
    private class ExportSingleActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            SimpleDateFormat exportName = new SimpleDateFormat("yyyy-MM-dd HH-mm");
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setDialogTitle("Экспорт текущего кадра");
            jFileChooser.setSelectedFile(new File(exportName.format(parser.getDates()[current])));
            jFileChooser.setFileFilter(new FileNameExtensionFilter("Изображение в формате PNG", "png"));
            //Создаём диалоговое окно сохранения файла, автоматически указыаем имя файла по доате и времени
            int returnValue = jFileChooser.showSaveDialog(getParent());
            if (returnValue == 0) { //Если нажата кнопка сохранить, то:
                //Помещаем картинку в буфер
                BufferedImage img = new BufferedImage(canvas.getWidth(), canvas.getHeight(), 1);
                //Создаём новый файл
                File file = new File(jFileChooser.getSelectedFile().getAbsolutePath() + ".png");
                try {
                    //Сохраняем картинку из буфера в файл (отдельный метод см. ниже)
                    canvas.paint(img.getGraphics());
                    ImageIO.write(img, "png", file);
                    JOptionPane.showMessageDialog(getParent(), "Файл " + file.getName() + " сохранён.", "", JOptionPane.INFORMATION_MESSAGE);
                    //Сливаем буфер
                    img.flush();
                    //При возникающих исключениях ввода-вывода обпабатываем их:
                }
                catch (IOException var7) {
                    JOptionPane.showMessageDialog(getParent(), var7.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    //Экспорт всех картинок
    private class ExportAllActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setDialogTitle("Экспорт всех кадров");
            jFileChooser.setFileFilter(new FileNameExtensionFilter("Изображение в формате PNG", "png"));
            int returnValue = jFileChooser.showSaveDialog(getParent());
            if (returnValue == 0) {
                Date savingStart = new Date();

                String filePath = jFileChooser.getSelectedFile().getAbsolutePath();

                SaveImageThread thread0 = new SaveImageThread(parser, filePath);
                //SaveImageThread thread1 = new SaveImageThread(parser, filePath);
                //SaveImageThread thread2 = new SaveImageThread(parser, filePath);
                //SaveImageThread thread3 = new SaveImageThread(parser, filePath);

                thread0.start();
                //thread1.start();
                //thread2.start();
               // thread3.start();

                try {
                    thread0.join();
                    //thread1.join();
                    //thread2.join();
                    //thread3.join();

                    Date savingEnd = new Date();
                    JOptionPane.showMessageDialog(getParent(), parser.getFrameCount() +
                            " файлов успешно сохранёно за " + (savingEnd.getTime() - savingStart.getTime()), "", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    // Выход из программы
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
                    canvas.repaint(current);
                }

            }
            catch (IOException var3) {
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
                canvas.repaint(current);
            } else {
                TemperatureChart.drawLayerBound = true;
                menuSettingsDrawLayers.setText("Скрыть границы слоёв");
                canvas.repaint(current);
            }
        }
    }

    private class DrawDegreesActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (TemperatureChart.drawDegreeLines) {
                TemperatureChart.drawDegreeLines = false;
                menuSettingsDrawDegreeLines.setText("Показать линии градусов");
                canvas.repaint(current);
            } else {
                TemperatureChart.drawDegreeLines = true;
                menuSettingsDrawDegreeLines.setText("Скрыть линии градусов");
                canvas.repaint(current);
            }
        }
    }

}
