package com.example.socket.tcp.objectIO;

import java.io.*;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Socket socket = new Socket("localhost", 8888);
		InputStream inputStream = socket.getInputStream();
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

		for (int i = 0; i < 5; i++) {
			Userinfo newUserinfo = new Userinfo();
			newUserinfo.setId(i + 1);
			newUserinfo.setUsername("clientUsername" + (i + 1));
			newUserinfo.setPassword("clientPassword" + (i + 1));

			objectOutputStream.writeObject(newUserinfo);

			Userinfo userinfo = (Userinfo) objectInputStream.readObject();
			System.out.println("在客户端打印" + (i + 1));
			System.out.println(userinfo);
		}
		objectOutputStream.close();
		objectInputStream.close();

		outputStream.close();
		inputStream.close();

		socket.close();
	}
}
