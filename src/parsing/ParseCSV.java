package parsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParseCSV extends Parser {
    protected ArrayList<String[]> input_array = new ArrayList();

    public ParseCSV(String inputfile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputfile));

        String string;
        while((string = bufferedReader.readLine()) != null) {
            String[] data = string.split(";");
            this.input_array.add(data);
        }

        bufferedReader.close();
        this.input_array.remove(0);
        int numberCols = ((String[])this.input_array.get(0)).length;

        int i;
        for(i = 0; i < this.input_array.size(); ++i) {
            if (numberCols != ((String[])this.input_array.get(i)).length) {
                if (i == 1) {
                    throw new IOException("Ошибка в файле - неверное количество столбцов в строке №: 1;");
                }

                throw new IOException("Ошибка в файле - неверное количество столбцов в строке №: " + (i + 1) + ";");
            }
        }

        for(i = 0; i < this.input_array.size(); ++i) {
            for(int j = 0; j < ((String[])this.input_array.get(i)).length; ++j) {
                if (((String[])this.input_array.get(i))[j].isEmpty()) {
                    throw new IOException("Ошибка в файле - Пустая клетка: Cтрока: " + (i + 1) + ", cтолбец: " + j + ";");
                }
            }
        }

    }
}
