package backend;

import java.io.*;
import java.util.ArrayList;

public class StoryHistory {
    public static void saveExpression(final String _expression) throws IOException {
        try(FileWriter file = new FileWriter(FILE_PATH,true)) {
            file.write(_expression + "\n");
        }
    }

   public static String[] getHistory()  throws IOException {
      FileReader reader = new FileReader(FILE_PATH);
      BufferedReader bufferReader = new BufferedReader(reader);
      String line;
      ArrayList<String> operations = new ArrayList<>();

       while ((line = bufferReader.readLine()) != null) {
           operations.add(line);
       }

       reader.close();
       bufferReader.close();

       if (operations.isEmpty()) {
           throw new NullPointerException("File is empty");
       }

       return operations.toArray(new String[0]);
    }

  public static void clearHistory() throws IOException {
        FileWriter fileWriter = new FileWriter(FILE_PATH,false);
        fileWriter.close();
    }

    private static String FILE_PATH = "/home/ruslan/IdeaProjects/SmartCalculator/src/backend/history/history.txt";
}

