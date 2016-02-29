/**
 * Created by Brenton on 2/28/2016.
 */

import java.net.*;
import java.io.*;
import java.nio.Buffer;
import java.util.*;


public class ProxyThread extends Thread
{
    private Socket sock = new Socket();
    private int BUFFER = 32768;
    public ProxyThread(Socket sock)
    {
        super("ProxyThread");
        this.sock = sock;
    }

    public void run()
    {
        try
        {
            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            BufferedReader inCli = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            String inputLine, outLine;
            int count = 0;
            String URLCALL = "";
            while((inputLine = inCli.readLine())!=null)
            {
                try
                {
                    StringTokenizer tokenizer = new StringTokenizer(inputLine);
                    tokenizer.nextToken();
                }
                catch(Exception e)
                {
                    break;
                }
                if(count==0)
                {
                    String[] tokens = inputLine.split(" ");
                    URLCALL = tokens[1];
                    System.out.println("Request for: " + URLCALL);
                }
                count++;
            }
            BufferedReader reader = null;
            try {
                URL url = new URL(URLCALL);
                URLConnection connection = url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(false);
                InputStream inStream = null;
                HttpURLConnection hucc = (HttpURLConnection) connection;
                    try
                    {
                        inStream = hucc.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(inStream));
                    } catch (IOException ie) {
                        System.out.println("IO error");
                    }
                byte b[] = new byte[BUFFER];
                int index = inStream.read(b, 0, BUFFER);
                while (index != -1) {
                    out.write(b, 0, index);
                    index = inStream.read(b, 0, BUFFER);
                }
                out.flush();
            }
            catch(Exception e)
            {
                System.out.println("Shit went wrong");
            }

            if(reader != null)
            {
                reader.close();
            }
            if(inCli != null)
            {
                inCli.close();
            }
            if(out != null)
            {
                out.close();
            }
            if(sock != null)
            {
                sock.close();
            }
        }
        catch(Exception e)
        {
            System.out.println("Exception");
        }
    }
}
