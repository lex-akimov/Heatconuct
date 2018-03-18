//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.lexsoft.parsing;

public class ParserXLSX extends Parser {/*
    public ParserXLSX(String inputFile) throws ParsingException {
        try (FileInputStream fileInputStream = new FileInputStream(inputFile)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Row keysRow = sheet.getRow(0);
            Iterator<Cell> keys = keysRow.cellIterator();

            // Заносим существующие ключи в HashMap<"Название", номер столбца>
            int i = 0;
            while (keys.hasNext()){
                Cell cell = keys.next();
                String string = cell.getStringCellValue();
                if (string.startsWith("T") |
                        string.equals("N") |
                        string.equals("DATE") |
                        string.equals("Q_IN") | string.equals("Q_OUT") |
                        string.equals("A_IN") | string.equals("A_OUT")) {
                    this.parsingKeys.put(string, i);
                    if (string.startsWith("T")) {
                        temperaturesCount++;
                    }
                }
                i++;
            }


            // Считаем будущее количество кадров
            Column nColumn = sheet.getCol(this.parsingKeys.get("N"));
            for (int i = 2; i < nColumn.length; i++) {
                String str = nColumn[i].getContents();
                if (str.equals("")) break;
                this.frameCount++;
            }

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
                            if (cell.equals("T_AIR_OUT")) {
                                this.temperatures[i][temperaturesCount - 1] = (float) ((NumberCell) row[j]).getValue();
                            } else if (cell.equals("T_AIR_IN")) {
                                this.temperatures[i][0] = (float) ((NumberCell) row[j]).getValue();
                            } else {
                                int tempOrder = Integer.parseInt(cell.substring(1));
                                this.temperatures[i][tempOrder] = (float) ((NumberCell) row[j]).getValue();
                            }
                        }
                    }
                    catch (Exception exc) {
                        throw new ParsingException("Ключ: " + cell + ". N: " + (i + 1) + ". Ошибка чтения ячейки.\n ");
                    }
                }
            }
            super.findMinAndMax();
        }

        catch (ParsingException e) {
            throw e;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

}