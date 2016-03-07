import java.net.ServerSocket;

/**
 * Created by Brenton on 2/28/2016.
 */

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProxyServer
{
    public static void main(String[] args)
    {
        ServerSocket sock = null;
        boolean running = true;
        int port = 55555;

        try
        {
            sock = new ServerSocket(port);
            System.out.println("Server started on port " + port);
        }
        catch(IOException ie)
        {
            System.out.println("Could not start server on that port");
            System.exit(-1);
        }

        while(running)
        {
            try {
                new ProxyThread(sock.accept()).start();
            } catch (IOException ex) {
                Logger.getLogger(ProxyServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            sock.close();
        } catch (IOException ex) {
            Logger.getLogger(ProxyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
