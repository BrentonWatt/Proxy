import java.net.ServerSocket;

/**
 * Created by Brenton on 2/28/2016.
 */

import java.io.*;
import java.net.*;


public class ProxyServer
{
    public static void main(String[] args) throws IOException
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
            new ProxyThread(sock.accept()).start();
        }
        sock.close();
    }
}
