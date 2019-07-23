package com.example.socket.tcp.prod;

import java.io.*;
import java.net.Socket;

public class Client {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Socket socket = new Socket("localhost", 8888);
		OutputStream outputStream = socket.getOutputStream();
		InputStream inputStream = socket.getInputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

		//输出开始
		Userinfo newUserinfo = new Userinfo();
		newUserinfo.setId(666);
		newUserinfo.setUsername("admin");
		newUserinfo.setPassword("admin");
		objectOutputStream.writeObject(newUserinfo);
		//输出结束

		//输入开始
		Userinfo userinfo = (Userinfo) objectInputStream.readObject();
		System.out.println("获取对象");
		System.out.println(userinfo.toString());
		//输入结束

		objectOutputStream.close();
		objectInputStream.close();
		outputStream.close();
		inputStream.close();
		socket.close();
	}
}
