package edu.eci.labinfo.controluserslogon;

import java.io.EOFException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {

    public static String nombre= "ControlUserLogOn";

    public static void record(String e){
        try{
            Logger logger=Logger.getLogger(nombre);/* busca o crea un nuevo logger con el nombre */
            logger.setUseParentHandlers(false);
            FileHandler file=new FileHandler(nombre+".log",true);/* crea un nuevo archivo o puede escribir un conjunto rotativo de archivos */
            file.setFormatter(new SimpleFormatter());
            logger.addHandler(file);
            logger.log(Level.SEVERE,e.toString(),e);
            file.close();
        }catch (EOFException ex){
            ex.printStackTrace();
        }catch (Exception exc){
            exc.printStackTrace();
            System.exit(0);
        }
    }
}
