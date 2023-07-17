package backend;

import java.io.*;
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
      String[] operations = null;

       while(((line = bufferReader.readLine()))!=null) {
            operations = line.split("\n");
       }

       reader.close();
       bufferReader.close();
       if(operations == null || operations.length == 0) {
           throw new NullPointerException("File is empty()");
       }

       return operations;
    }

  public static void clearHistory() throws IOException {
        FileWriter fileWriter = new FileWriter(FILE_PATH,false);
        fileWriter.close();
    }

    private static String FILE_PATH = "/home/ruslan/IdeaProjects/SmartCalculator/src/backend/history/history.txt";
}

