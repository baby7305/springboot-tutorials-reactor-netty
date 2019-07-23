package com.example.socket.tcp.prod;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	public static void main(String[] args) throws IOException {
		Socket socket = new Socket("localhost", 8888);
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

		//输出开始
		Userinfo newUserinfo = new Userinfo();
		newUserinfo.setId(666);
		newUserinfo.setUsername("admin");
		newUserinfo.setPassword("admin");
		objectOutputStream.writeObject(newUserinfo);
		//输出结束

		objectOutputStream.close();
		outputStream.close();
		socket.close();
	}
}
