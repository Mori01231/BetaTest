package com.github.mori01231.utils;

import java.io.*;
import java.util.ArrayList;

public class GivenListStore {

    private File storageFile;
    private ArrayList<String> values;

    public GivenListStore(File file){
        this.storageFile = file;
        this.values = new ArrayList<String>();

        if(this.storageFile.exists() == false){
            try {
                this.storageFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadGivenList(){
        try {
            DataInputStream input = new DataInputStream(new FileInputStream(this.storageFile));
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String line;

            while ((line = reader.readLine()) != null){
                if (this.contains(line) == false){
                    this.values.add(line);
                }
            }

            reader.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveGivenList(){
        try {
            FileWriter stream = new FileWriter(this.storageFile);
            BufferedWriter out = new BufferedWriter(stream);

            for (String value : this.values){
                out.write(value);
                out.newLine();
            }

            out.close();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean contains(String value){
        return this.values.contains(value);
    }

    public void addGivenList(String value){
        if(this.contains(value) == false){
            this.values.add(value);
        }
    }

    public void removeGivenList(String value){
        this.values.remove(value);
    }

    public ArrayList<String> getValues(){
        return this.values;
    }

}
