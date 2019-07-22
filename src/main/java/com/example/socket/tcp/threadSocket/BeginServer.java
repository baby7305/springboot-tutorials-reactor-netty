package com.example.socket.tcp.threadSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BeginServer {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(8888);
			int runTag = 1;
			while (runTag == 1) {
				Socket socket = serverSocket.accept();
				BeginThread beginThread = new BeginThread(socket);
				beginThread.start();
			}
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
