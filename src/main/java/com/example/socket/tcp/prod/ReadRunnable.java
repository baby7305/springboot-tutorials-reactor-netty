package com.example.socket.tcp.prod;

import java.io.*;
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
			OutputStream outputStream = socket.getOutputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

			//输入开始
			Userinfo userinfo = (Userinfo) objectInputStream.readObject();
			System.out.println("获取对象");
			System.out.println(userinfo.toString());
			//输入结束

			//输出开始
			Userinfo newUserinfo = new Userinfo();
			newUserinfo.setId(666);
			newUserinfo.setUsername("我是server用户");
			newUserinfo.setPassword("我是server密码");
			objectOutputStream.writeObject(newUserinfo);
			//输出结束

			objectInputStream.close();
			objectOutputStream.close();
			inputStream.close();
			outputStream.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
