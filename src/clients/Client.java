package clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Client Version 3
 * 
 * @author 21cmPC
 *
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
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		System.out.println(reader.readLine());

		PrintWriter writer = new PrintWriter(socket.getOutputStream());
		writer.write(getClientName());
		writer.flush();

		socket.close();
	}

	public String getClientName() {
		return clientName;
	}
}
