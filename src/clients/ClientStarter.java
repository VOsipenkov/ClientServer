package clients;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientStarter {
    private static final String IP_SERVER = "127.0.0.1";
    private static final int PORT = 7777;
    private static Client client;

    public static void main(String[] args) throws UnknownHostException, IOException {

        
        for (int i = 0; i < 10; i++) {
            client = new Client(ClientNameGenerator.getName(), IP_SERVER, PORT);
            Thread iClient = new Thread(new Runnable() {
                
                @Override
                public void run() {
                    try {
                        Client currentClient = client;
                        currentClient.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            
            iClient.start();
        }

    }

}
