package portman.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import com.google.gson.Gson;

public class Portafolios extends HashMap<String, Portafolio>{
    public static final String path = "portafolios.json";

    public Portafolios() {
        super();
    }

    public void save() {
        File file = new File(path);
        Gson gson = new Gson();
        String jsonString = gson.toJson(this);

        try {
            file.createNewFile();
            
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(jsonString);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void get() {
        File file = new File(path);
        Gson gson = new Gson();
        String jsonString = "";
        Portafolios tmp;
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            jsonString = br.readLine();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tmp = gson.fromJson(jsonString, Portafolios.class);

        tmp.forEach((k, v) -> this.put(k, v));
    }
}
