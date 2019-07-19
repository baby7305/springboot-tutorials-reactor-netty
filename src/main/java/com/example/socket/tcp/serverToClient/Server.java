package com.example.socket.tcp.serverToClient;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(8088);
			System.out.println("server 阻塞开始" + System.currentTimeMillis());
			Socket socket = serverSocket.accept();
			System.out.println("server 阻塞结束" + System.currentTimeMillis());

			OutputStream outputStream = socket.getOutputStream();
			outputStream.write("我是高洪岩，我来自server端！".getBytes());
			outputStream.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
