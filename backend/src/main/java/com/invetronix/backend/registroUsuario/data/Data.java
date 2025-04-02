package com.invetronix.backend.registroUsuario.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import com.invetronix.backend.registroUsuario.models.User;

public class Data implements Serializable {
    private static Data instance;

    private Data() {}

    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    public void write(HashMap<String, User> db) {
        try{
            FileOutputStream file = new FileOutputStream("DataBase.dat");
            ObjectOutputStream escritor = new ObjectOutputStream(file);
            escritor.writeObject(db);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public HashMap<String, User> read() {
        try {
            FileInputStream file = new FileInputStream("DataBase.dat");
            ObjectInputStream lector = new ObjectInputStream(file);
            return (HashMap<String, User>) lector.readObject();
        }  catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return new HashMap<String, User>();
        }
    }


}