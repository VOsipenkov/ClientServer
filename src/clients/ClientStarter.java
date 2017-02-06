package clients;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientStarter {
    private static final String IP_SERVER = "127.0.0.1";
    private static final int PORT = 7777;

    public static void main(String[] args) throws UnknownHostException, IOException {

        Client client;
        for (int i = 0; i < 10; i++) {
            client = new Client(ClientNameGenerator.getName(), IP_SERVER, PORT);
            client.start();
        }

    }

}
