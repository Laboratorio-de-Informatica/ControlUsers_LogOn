package edu.eci.labinfo.persistencia;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion
{
    public static Connection conectar() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        final Properties conf = new Properties();

        String userName = null;
        String password = null;
        String url = null;
        try {
            System.out.println(System.getProperty("user.dir"));
            final InputStream input = new FileInputStream(new File(System.getProperty("user.dir"), "applicationconfig.properties"));
            conf.load(input);
            userName = conf.getProperty("user");
            password = conf.getProperty("pwd");
            url = conf.getProperty("url");
        }
        catch (FileNotFoundException ex) {
            System.out.println("properties file not found");
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex2) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex2);
        }

        Connection conn = null;
        conn = DriverManager.getConnection(url, userName, password);
        return conn;
    }
}
