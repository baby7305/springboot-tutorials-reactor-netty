package com.example.socket.tcp.pngTransmit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		try {
			byte[] byteArray = new byte[2048];
			ServerSocket serverSocket = new ServerSocket(8088);
			Socket socket = serverSocket.accept();

			InputStream inputStream = socket.getInputStream();
			int readLength = inputStream.read(byteArray);

			FileOutputStream pngOutputStream = new FileOutputStream(new File("E:\\129225new.jpg"));

			while (readLength != -1) {
				pngOutputStream.write(byteArray, 0, readLength);
				readLength = inputStream.read(byteArray);
			}
			pngOutputStream.close();
			inputStream.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
