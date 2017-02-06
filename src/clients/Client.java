package clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Client Version 4
 * 
 * @author 21cmPC
 */
public class Client {
    String clientName;
    String ipAddress;
    int port;

    public Client(String clientName, String ipAddress, int port) {
        this.clientName = clientName;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public void start() throws UnknownHostException, IOException {
        Socket socket = new Socket(ipAddress, port);

        System.err.println("Client started");
        int millis = (int) (Math.random() * 20) * 1000;
        System.out.println(getClientName() + " :" + millis / 1000);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //------------>request
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.write(getClientName());
        writer.flush();

        //<---------response
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socket.close();
    }

    public String getClientName() {
        return clientName;
    }
}
