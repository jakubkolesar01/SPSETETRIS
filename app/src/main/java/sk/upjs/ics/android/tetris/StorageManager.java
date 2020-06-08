package sk.upjs.ics.android.tetris;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageManager {

    private Context context;

    public StorageManager(Context context) {
        this.context = context;
    }

    public Map<String, Integer> loadScores() throws FileNotFoundException {
        Map<String, Integer> integerMap = new HashMap<>();
        BufferedReader wordsReader = new BufferedReader(new InputStreamReader(context.openFileInput("highscores.txt")));
        String line;
        try {
            while ((line = wordsReader.readLine()) != null){
                String[] splitLine = line.split("%");
                integerMap.put(splitLine[0], Integer.parseInt(splitLine[1]));
            }
            wordsReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return integerMap;
    }

    public void saveScores(Map<String, Integer> scores) throws IOException {
        if (scores != null){
            OutputStreamWriter writer = new OutputStreamWriter(context.openFileOutput("highscores.txt", Context.MODE_PRIVATE));
            for (String s : scores.keySet())
                writer.write(s+"%"+scores.get(s));
            writer.close();
        }
    }
}
