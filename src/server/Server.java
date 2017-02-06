package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Server Server version 4.
 * 
 * @author 21cmPC
 */
public class Server {

    private static int clientIndex = 0;
    private ServerSocket serverSocket;
    private static Server instance;
    private final int SERVER_PORT = 7777;
    private final String PHRASES_FILE_PATH = "src/server/phrases.txt";
    private final String LOG_FILE_PATH = "src/server/log.txt";

    /**
     * Creates server instance.
     * 
     * @throws IOException
     */
    private Server() throws IOException {
        serverSocket = new ServerSocket(SERVER_PORT);
        cleanLogFile();
        System.err.println("Server started");
    }

    /*
     * Returns instance of Server.
     */
    public static Server getInstance() throws IOException {
        if (instance == null) {
            instance = new Server();
        }

        return instance;
    }

    /*
     * Start command for server.
     */
    public void serverStart() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            Thread newClientThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    Socket clientSocket = socket;

                    PrintWriter writer;
                    BufferedReader reader;
                    try {
                        writer = new PrintWriter(socket.getOutputStream());
                        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        String clientName = reader.readLine();
                        System.out.println(clientName + " exchange started");
                        log(clientName);

                        writer.println(generatePhrase());
                        writer.flush();

                        while (true) {
                            if (socket.isConnected()) {//TODO needs to detect, that connection closed.
                                System.out.println(
                                        "Connection with " + clientName + " closed successfully");
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            newClientThread.start();
        }
    }

    private void log(String clientName) throws IOException {
        clientName += "\n";
        Files.write(Paths.get(LOG_FILE_PATH), clientName.getBytes(), StandardOpenOption.APPEND,
                StandardOpenOption.CREATE);
    }

    private void cleanLogFile() {
        if (Files.exists(Paths.get(LOG_FILE_PATH))) {
            try {
                Files.delete(Paths.get(LOG_FILE_PATH));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String generatePhrase() throws IOException {
        String fileContent = new String(Files.readAllBytes(Paths.get(PHRASES_FILE_PATH)));
        String[] lines = fileContent.split("\n+");
        int index = (int) (Math.random() * lines.length);

        return lines[index].trim();
    }

    public static void main(String[] args) throws IOException {
        Server.getInstance().serverStart();
    }
}
