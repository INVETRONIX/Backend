package com.invetronix.backend.APIsuppliers.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import com.invetronix.backend.APIsuppliers.entities.EntitySupplier;

public class Data {
    private static Data instance;

    private Data(){}

    public static Data getInstance(){
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

     public void write(HashMap<String, EntitySupplier> db) {
        try{
            FileOutputStream file = new FileOutputStream("DataProducts.dat");
            ObjectOutputStream escritor = new ObjectOutputStream(file);
            escritor.writeObject(db);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public HashMap<String, EntitySupplier> read() {
        try {
            FileInputStream file = new FileInputStream("DataProducts.dat");
            ObjectInputStream lector = new ObjectInputStream(file);
            return (HashMap<String, EntitySupplier>) lector.readObject();
        }  catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return new HashMap<String, EntitySupplier>();
        }
    }
}
