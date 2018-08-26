package ir.barasm;

import java.io.*;
import java.util.ArrayList;

import org.json.*;

public class Helper {
    public static ArrayList<String> readFile(String filePath) {
        String line;
        ArrayList<String> lines = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return lines;
    }

    public static void writeToFile(String filePath, String text) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(text);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<String> getLanguageStopwords(String lang) {
        String json = readFile(Variables.stopwordsFile).get(0);
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray(lang);
        ArrayList<String> stopwords = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            stopwords.add(jsonArray.get(i).toString());
        }
        return stopwords;
    }

    public static String[] getSubdirectories(String directoryPath) {
        File file = new File(directoryPath);
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        return directories;
    }
}
