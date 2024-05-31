package edu.eci.labinfo.controluserslogon;

import edu.eci.labinfo.persistencia.ServiciosPersistencia;

import javax.swing.*;
import java.util.Enumeration;
import java.net.SocketException;
import java.sql.SQLException;
import java.net.NetworkInterface;
import java.sql.Timestamp;
import java.util.Date;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.InetAddress;

public class Main
{
    /**
     * COMPLETADO
     * Arreglos en LogOn de CONTROLUSERS -> se hace sin seguridad la conexion con la base de datos de ControlUsers.
     * Revisar el application.properties para poder sacar los datos por alla
     * @param args
     */
    public static void main(final String[] args) {
        final String user = System.getProperty("user.name");
        String host = "";
        try {
            final InetAddress addr = InetAddress.getLocalHost();
            host = addr.getHostName();
        }
        catch (UnknownHostException ex) {
            ex.printStackTrace();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            final ServiciosPersistencia sp = new ServiciosPersistencia();
            final Registro pend = sp.logedOn(host);
            final Registro root = sp.logedOnRoot(host);
            final Timestamp now = new Timestamp(new Date().getTime());
            String ip = "";
            System.out.println("IP___________"+ip+"\n");
            boolean found = false;
            final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements() && !found) {
                final NetworkInterface intf = interfaces.nextElement();
                for (Enumeration<InetAddress> addr2 = intf.getInetAddresses(); addr2.hasMoreElements() && !found;) {
                    final String tmp = addr2.nextElement().getHostAddress();
                    System.out.println("IP___________"+tmp+"\n");
                    if (tmp.contains("10.2")) {
                        found = true;
                        ip = tmp;
                    }
                }
            }
            System.out.println("host: " + host + " IP: " + ip + " fecha: " + now);
            if (pend != null) {
                sp.close(now, ip);
            }
            if (root != null) {
                sp.closeRoot(root.getEquipo(), root.getLogOn(), now);
            }
            if (user.equalsIgnoreCase("root")) {
                sp.insertarRoot(host, now, null, ip);
            }
            else {
                sp.insertar(user, host, new Timestamp(new Date().getTime()), null, ip);
            }
        }
        catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException | SocketException ex4) {
            final Exception ex3 = null;
            final Exception ex2 = ex3;

            ex4.printStackTrace();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex2);
        }
    }
}
