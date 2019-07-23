package com.example.socket.tcp.prod;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server {

	private ServerSocket serverSocket;
	private Executor pool;

	public Server(int port, int poolSize) {
		try {
			serverSocket = new ServerSocket(port);
			pool = Executors.newFixedThreadPool(poolSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startService() {
		try {
			for (; ; ) {
				Socket socket = serverSocket.accept();
				pool.execute(new ReadRunnable(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server(8888, 10000);
		server.startService();
	}
}
