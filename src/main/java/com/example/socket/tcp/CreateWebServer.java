package com.example.socket.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CreateWebServer {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(6666);
		Socket socket = serverSocket.accept();
		InputStream inputStream = socket.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String getString = "";
		while (!"".equals(getString = bufferedReader.readLine())) {
			System.out.println(getString);
		}

		OutputStream outputStream = socket.getOutputStream();
		outputStream.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
		outputStream.write("<html><body><a href='http://www.baidu.com'>i am baidu.com welcome you!</a></body></html>".getBytes());
		outputStream.flush();

		inputStream.close();
		outputStream.close();
		socket.close();
		serverSocket.close();
	}
}
