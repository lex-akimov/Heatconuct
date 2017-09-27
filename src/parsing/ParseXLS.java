//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package parsing;

import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import jxl.Cell;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

public class ParseXLS extends Parser {
    public ParseXLS(String inputFile) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(inputFile);
        Workbook workbook = Workbook.getWorkbook(fileInputStream);
        fileInputStream.close();
        Sheet sheet = workbook.getSheet(0);
        Cell[] keys = sheet.getRow(0);
        temperaturesCount = 0;
        this.parsingKeys = new HashMap();

        try {
            for(int i = 0; i < keys.length; ++i) {
                String string = keys[i].getContents();
                if (string.startsWith("T") | string.equals("N") | string.equals("DATE") | string.equals("Q_IN") | string.equals("Q_OUT") | string.equals("A_IN") | string.equals("A_OUT")) {
                    if (string.startsWith("T")) {
                        ++temperaturesCount;
                    }

                    this.parsingKeys.put(string, i);
                }
            }
        } catch (Exception var15) {
            throw new Exception("Ошибка обработки ключей");
        }

        this.frameCount = 0;
        Cell[] NColumn = sheet.getColumn(((Integer)this.parsingKeys.get("N")).intValue());

        int i;
        for(i = 2; i < NColumn.length; ++i) {
            String str = NColumn[i].getContents();
            if (str.equals("")) {
                break;
            }

            ++this.frameCount;
        }

        this.datesArr = new Date[this.frameCount];
        this.qIn = new float[this.frameCount];
        this.qOut = new float[this.frameCount];
        this.alphaIn = new float[this.frameCount];
        this.alphaOut = new float[this.frameCount];
        this.temperatures = new float[this.frameCount][temperaturesCount];

        for(i = 0; i < this.frameCount; ++i) {
            Cell[] row = sheet.getRow(i + 2);

            for(int j = 0; j < this.parsingKeys.size(); ++j) {
                String cell = keys[j].getContents();

                try {
                    if (cell.equals("DATE")) {
                        this.datesArr[i] = new Date(((DateCell)row[j]).getDate().getTime() - 25200000L);
                    } else if (cell.equals("Q_IN")) {
                        this.qIn[i] = (float)((NumberCell)row[j]).getValue();
                    } else if (cell.equals("Q_OUT")) {
                        this.qOut[i] = (float)((NumberCell)row[j]).getValue();
                    } else if (cell.equals("A_IN")) {
                        this.alphaIn[i] = (float)((NumberCell)row[j]).getValue();
                    } else if (cell.equals("A_OUT")) {
                        this.alphaOut[i] = (float)((NumberCell)row[j]).getValue();
                    } else if (cell.startsWith("T")) {
                        byte var12 = -1;
                        switch(cell.hashCode()) {
                        case 582667694:
                            if (cell.equals("T_AIR_OUT")) {
                                var12 = 1;
                            }
                            break;
                        case 711532197:
                            if (cell.equals("T_AIR_IN")) {
                                var12 = 0;
                            }
                        }

                        switch(var12) {
                        case 0:
                            this.temperatures[i][0] = (float)((NumberCell)row[j]).getValue();
                            break;
                        case 1:
                            this.temperatures[i][temperaturesCount - 1] = (float)((NumberCell)row[j]).getValue();
                            break;
                        default:
                            int tempOrder = Integer.parseInt(cell.substring(1));
                            this.temperatures[i][tempOrder] = (float)((NumberCell)row[j]).getValue();
                        }
                    }
                } catch (Exception var14) {
                    throw new Exception("Ключ: " + cell + ". N: " + (i + 1) + ". Ошибка чтения ячейки.\n " + var14.getMessage());
                }
            }
        }

        super.MinMax();
    }
}
