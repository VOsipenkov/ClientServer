package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Server Server version 2.
 * 
 * @author 21cmPC
 *
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

						writer.println(generatePhrase());
						writer.flush();
						String clientName = reader.readLine();
						System.out.println(clientName);
						log(clientName);

						clientSocket.close();
						if (clientSocket.isClosed()) {
							System.out.println("Connection with " + clientName + " closed successfully");
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
		Files.write(Paths.get(LOG_FILE_PATH), clientName.getBytes());
	}

	private String generatePhrase() throws IOException {
		String fileContent = new String(Files.readAllBytes(Paths.get(PHRASES_FILE_PATH)));
		String[] lines = fileContent.split("\n+");
		int index = (int) (Math.random() * lines.length);

		return lines[index];
	}

	/**
	 * Deprecated
	 * 
	 * @return
	 */
	private String generateNameForClient() {
		return "Client" + clientIndex++;
	}

	public static void main(String[] args) throws IOException {
		Server.getInstance().serverStart();
	}
}
