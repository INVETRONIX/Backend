package com.invetronix.backend.APIretuns.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import com.invetronix.backend.APIretuns.entities.EntityDevolution;

public class Data {
    private static Data instance;

    private Data(){}

    public static Data getInstance(){
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    public void write(HashMap<String, EntityDevolution> db) {
        try{
            FileOutputStream file = new FileOutputStream("DBDevoluciones.dat");
            ObjectOutputStream escritor = new ObjectOutputStream(file);
            escritor.writeObject(db);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public HashMap<String, EntityDevolution> read() {
        try {
            FileInputStream file = new FileInputStream("DBDevoluciones.dat");
            ObjectInputStream lector = new ObjectInputStream(file);
            return (HashMap<String, EntityDevolution>) lector.readObject();
        }  catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return new HashMap<String, EntityDevolution>();
        }
    }
}
