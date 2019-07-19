package com.example.socket.charStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(Server.class);

	private final ExecutorService threadPool = Executors.newCachedThreadPool();
	private ServerSocket serverSocket = null;

	public void init() throws IOException {
		serverSocket = new ServerSocket(9000);
		threadPool.execute(this);
	}

	@Override
	public void run() {
		Socket socket = null;
		try {
			socket = serverSocket.accept();
			while (socket != null) {
//				log.info("Client {} connected", socket.getRemoteSocketAddress());
				threadPool.execute(new Processor(socket));
			}
		} catch (IOException e) {
//			e.printStackTrace();
			log.error("Error on process connection", e);
		}

	}

	public void close() {
		if (serverSocket != null && serverSocket.isClosed()) {
			try {
				serverSocket.close();
			} catch (IOException e) {
//				e.printStackTrace();
				log.error("Error on close server socket", e);
			}
		}
		threadPool.shutdown();
	}

	public static void main(String[] args) {
		Server server = new Server();
		BufferedReader input = null;
		try {
			server.init();

			System.out.println("Enter 'exit' to exit");
			input = new BufferedReader(new InputStreamReader(System.in));
			String cmd = null;
			while ((cmd = input.readLine()) != null) {
				if ("exit".equalsIgnoreCase(cmd)) {
					break;
				}
			}
		} catch (IOException e) {
//			e.printStackTrace();
			log.error("Error on run server", e);
		} finally {
			StreamUtil.close(input);
			server.close();
		}

	}
}
