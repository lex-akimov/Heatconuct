//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.lexsoft.parsing;

import jxl.*;
import jxl.read.biff.BiffException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

public class ParserXLS extends Parser {
    @Override
    public int getFrameCount() {
        return super.getFrameCount();
    }

    public ParserXLS(String inputFile) throws ParsingException {
        try (FileInputStream fileInputStream = new FileInputStream(inputFile)) {
            Workbook workbook = Workbook.getWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheet(0);
            Cell[] keys = sheet.getRow(0);

            // Заносим существующие ключи в HashMap<"Название", номер столбца>
            for (int i = 0; i < keys.length; i++) {
                String string = keys[i].getContents();
                if (string.startsWith("T") | string.equals("DATE") |
                        string.equals("Q_IN") | string.equals("Q_OUT") |
                        string.equals("A_IN") | string.equals("A_OUT")) {
                    this.parsingKeys.put(string, i);
                    if (string.startsWith("T")) {
                        temperaturesCount++;
                    }
                }
            }

            // Считаем будущее количество картинок по столбцу "Дата"
            int dateColumnOrder = this.parsingKeys.get("DATE"); // Номер столбца
            Cell[] dateColumn = sheet.getColumn(dateColumnOrder);
            for (Cell dateCell : dateColumn) {
                if (dateCell.getContents().length() > 0) frameCount++;
                else break;
            }
            this.frameCount -= 2;

            // Зная размеры, проинициализируем массивы
            this.datesArr = new Date[this.frameCount];
            this.qIn = new float[this.frameCount];
            this.qOut = new float[this.frameCount];
            this.alphaIn = new float[this.frameCount];
            this.alphaOut = new float[this.frameCount];
            this.temperatures = new float[this.frameCount][temperaturesCount];

            for (int i = 0; i < this.frameCount; i++) {
                Cell[] row = sheet.getRow(i + 2);
                for (int j = 0; j < this.parsingKeys.size(); j++) {
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
                            switch (cell) {
                                case "T_AIR_OUT":
                                    this.temperatures[i][temperaturesCount - 1] = (float) ((NumberCell) row[j]).getValue();
                                    break;
                                case "T_AIR_IN":
                                    this.temperatures[i][0] = (float) ((NumberCell) row[j]).getValue();
                                    break;
                                default:
                                    int tempOrder = Integer.parseInt(cell.substring(1));
                                    this.temperatures[i][tempOrder] = (float) ((NumberCell) row[j]).getValue();
                                    break;
                            }
                        }
                    }
                    catch (NumberFormatException exc) {
                        throw new ParsingException("Столбец: " + cell + ". Строка: " + (i + 2) + ". Ошибка чтения ячейки.\n ");
                    }
                }
            }
            super.findMinAndMax();
        }
        catch (FileNotFoundException e) {
            throw new ParsingException("Файл не найден.\n ");

        }
        catch (IOException | BiffException e) {
            throw new ParsingException("Ошибка чтения файла.\n ");
        }
    }
}