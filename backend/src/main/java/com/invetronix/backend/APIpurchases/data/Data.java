package com.invetronix.backend.APIpurchases.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import com.invetronix.backend.APIpurchases.entities.EntityPurchase;

public class Data implements Serializable{
    private static Data instance;

    private Data(){}

    public static Data getInstance(){
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    public void write(HashMap<String, EntityPurchase> db) {
        try{
            FileOutputStream file = new FileOutputStream("DBCompras.dat");
            ObjectOutputStream escritor = new ObjectOutputStream(file);
            escritor.writeObject(db);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public HashMap<String, EntityPurchase> read() {
        try {
            FileInputStream file = new FileInputStream("DBCompras.dat");
            ObjectInputStream lector = new ObjectInputStream(file);
            return (HashMap<String, EntityPurchase>) lector.readObject();
        }  catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return new HashMap<String, EntityPurchase>();
        }
    }
}
