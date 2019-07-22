package com.example.socket.tcp.objectIO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ServerSocket serverSocket = new ServerSocket(8888);
		Socket socket = serverSocket.accept();
		InputStream inputStream = socket.getInputStream();
		OutputStream outputStream = socket.getOutputStream();

		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		for (int i = 0; i < 5; i++) {
			Userinfo userinfo = (Userinfo) objectInputStream.readObject();
			System.out.println("在服务端打印：" + (i + 1));
			System.out.println(userinfo.toString());
			Userinfo newUserinfo = new Userinfo();
			newUserinfo.setId(i + 1);
			newUserinfo.setUsername("serverUsername" + (i + 1));
			newUserinfo.setPassword("serverPassword" + (i + 1));

			objectOutputStream.writeObject(newUserinfo);
		}

		objectOutputStream.close();
		objectInputStream.close();

		outputStream.close();
		inputStream.close();

		socket.close();
		serverSocket.close();
	}
}
