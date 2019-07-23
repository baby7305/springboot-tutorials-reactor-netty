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

			//输入开始
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			int byteLength = objectInputStream.readInt();
			byte[] byteArray = new byte[byteLength];
			objectInputStream.readFully(byteArray);
			String newString = new String(byteArray);
			System.out.println(newString);
			//输入结束

			objectInputStream.close();
			inputStream.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
