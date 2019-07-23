package com.example.socket.tcp.prod;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	public static void main(String[] args) throws IOException {
		Socket socket = new Socket("localhost", 8888);
		OutputStream outputStream = socket.getOutputStream();

		//输出开始
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		String message = "我是中国人";

		int allStrByteLength = message.getBytes().length;
		objectOutputStream.writeInt(allStrByteLength);
		objectOutputStream.flush();
		objectOutputStream.write(message.getBytes());
		objectOutputStream.flush();
		//输出结束

		objectOutputStream.close();
		outputStream.close();
		socket.close();
	}
}
