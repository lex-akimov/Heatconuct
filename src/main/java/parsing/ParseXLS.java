//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package parsing;

import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;

import jxl.Workbook;
import jxl.Cell;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;

public class ParseXLS extends Parser {
    //Данный метод является кнструктором класса,
    // т.е. вызывается при создании экземпляра класса
    public ParseXLS(String inputFile) throws Exception {
        //создаём файловый поток, заполняем его открытым файлом.
        FileInputStream fileInputStream = new FileInputStream(inputFile);
        //Заполняем объект Workbook документом Excel
        Workbook workbook = Workbook.getWorkbook(fileInputStream);
        //Закрываем файловый поток
        fileInputStream.close();
        //Заполняем объект типа Sheet нулевым листом из документа
        Sheet sheet = workbook.getSheet(0);
        //присваиваем массиву ячеек с ключами нулевую строку из листа
        Cell[] keys = sheet.getRow(0);
        temperaturesCount = 0;
        this.parsingKeys = new HashMap();

        try {
            //Далее в цикле заполняем HashMap прописанными ключами
            for (int i = 0; i < keys.length; ++i) {
                String string = keys[i].getContents();
                if (string.startsWith("T") | string.equals("N") | string.equals("DATE") | string.equals("Q_IN") | string.equals("Q_OUT") | string.equals("A_IN") | string.equals("A_OUT")) {
                    if (string.startsWith("T")) {
                        ++temperaturesCount;
                    }

                    this.parsingKeys.put(string, i);
                }
            }
            //В случае возникновения исключения обрабатываем его
        } catch (Exception var15) {
            throw new Exception("Ошибка обработки ключей");
        }

        this.frameCount = 0;
        //Заполняем массив ячеек с номерами измерений
        Cell[] NColumn = sheet.getColumn(((Integer) this.parsingKeys.get("N")).intValue());

        int i;
        for (i = 2; i < NColumn.length; ++i) {
            String str = NColumn[i].getContents();
            if (str.equals("")) {
                break;
            }
            //инкрементируем переменную frameCount при каждом измерении.
            //frameCount покажет сколько измерений загружено в программу
            ++this.frameCount;
        }

        //Далее заполнение массивов дат, темперватур, тпеловых потоков
        this.datesArr = new Date[this.frameCount];
        this.qIn = new float[this.frameCount];
        this.qOut = new float[this.frameCount];
        this.alphaIn = new float[this.frameCount];
        this.alphaOut = new float[this.frameCount];
        this.temperatures = new float[this.frameCount][temperaturesCount];

        for (i = 0; i < this.frameCount; ++i) {
            Cell[] row = sheet.getRow(i + 2);

            for (int j = 0; j < this.parsingKeys.size(); ++j) {
                String cell = keys[j].getContents();

                try {
                    if (cell.equals("DATE")) {
                        this.datesArr[i] = new Date(((DateCell) row[j]).getDate().getTime() - 25200000L);
                    } else if (cell.equals("Q_IN")) {
                        this.qIn[i] = (float) ((NumberCell) row[j]).getValue();
                    } else if (cell.equals("Q_OUT")) {
                        this.qOut[i] = (float) ((NumberCell) row[j]).getValue();
                    } else if (cell.equals("A_IN")) {
                        this.alphaIn[i] = (float) ((NumberCell) row[j]).getValue();
                    } else if (cell.equals("A_OUT")) {
                        this.alphaOut[i] = (float) ((NumberCell) row[j]).getValue();
                    } else if (cell.startsWith("T")) {
                        if (cell.equals("T_AIR_OUT")) {
                            this.temperatures[i][temperaturesCount - 1] = (float) ((NumberCell) row[j]).getValue();
                        } else if (cell.equals("T_AIR_IN")) {
                            this.temperatures[i][0] = (float) ((NumberCell) row[j]).getValue();
                        } else {
                            int tempOrder = Integer.parseInt(cell.substring(1));
                            this.temperatures[i][tempOrder] = (float) ((NumberCell) row[j]).getValue();
                        }
                    }
                } catch (Exception var14) {
                    throw new Exception("Ключ: " + cell + ". N: " + (i + 1) + ". Ошибка чтения ячейки.\n " + var14.getMessage());
                }
            }
        }
        //После заполнения всех массивов, вызываем метод поиска минимальных и максимальных значений из дочернего класса
        super.MinMax();
    }
}