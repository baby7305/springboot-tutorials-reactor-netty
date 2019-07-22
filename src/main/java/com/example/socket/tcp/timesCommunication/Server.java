package com.example.socket.tcp.timesCommunication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(8088);
			Socket socket = serverSocket.accept();

			//输入开始
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			int byteLength = objectInputStream.readInt();
			byte[] byteArray = new byte[byteLength];
			objectInputStream.readFully(byteArray);
			String newString = new String(byteArray);
			System.out.println(newString);
			//输入结束

			//输出开始
			OutputStream outputStream = socket.getOutputStream();
			String StrA = "客户端你好 A\n";
			String StrB = "客户端你好 B\n";
			String StrC = "客户端你好 C\n";

			int allStrByteLength = (StrA + StrB + StrC).getBytes().length;

			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeInt(allStrByteLength);
			objectOutputStream.flush();

			objectOutputStream.write(StrA.getBytes());
			objectOutputStream.write(StrB.getBytes());
			objectOutputStream.write(StrC.getBytes());
			objectOutputStream.flush();
			//输出结束

			//输入开始
			byteLength = objectInputStream.readInt();
			byteArray = new byte[byteLength];
			objectInputStream.readFully(byteArray);
			newString = new String(byteArray);
			System.out.println(newString);
			//输入结束

			//输出开始
			StrA = "客户端你好 A\n";
			StrB = "客户端你好 B\n";
			StrC = "客户端你好 C\n";

			allStrByteLength = (StrA + StrB + StrC).getBytes().length;

			objectOutputStream.writeInt(allStrByteLength);
			objectOutputStream.flush();

			objectOutputStream.write(StrA.getBytes());
			objectOutputStream.write(StrB.getBytes());
			objectOutputStream.write(StrC.getBytes());
			objectOutputStream.flush();
			//输出结束

			inputStream.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
