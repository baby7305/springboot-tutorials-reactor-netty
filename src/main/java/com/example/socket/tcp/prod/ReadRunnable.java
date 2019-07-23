package com.example.socket.tcp.prod;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReadRunnable implements Runnable {

	private Socket socket;

	public ReadRunnable(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

			//输入开始
			Userinfo userinfo = (Userinfo) objectInputStream.readObject();
			System.out.println("获取对象");
			System.out.println(userinfo.toString());
			//输入结束

			objectInputStream.close();
			inputStream.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
