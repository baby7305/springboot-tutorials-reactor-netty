package com.example.socket.tcp.pngTransmit;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) {
		try {
			String pngFile = "F:\\129225.jpg";
			FileInputStream pngStream = new FileInputStream(new File(pngFile));
			byte[] byteArray = new byte[2048];

			System.out.println("socket begin" + System.currentTimeMillis());
			Socket socket = new Socket("localhost", 8088);
			System.out.println("socket end" + System.currentTimeMillis());

			OutputStream outputStream = socket.getOutputStream();

			int readLength = pngStream.read(byteArray);
			while (readLength != -1) {
				outputStream.write(byteArray, 0, readLength);
				readLength = pngStream.read(byteArray);
			}
			outputStream.close();
			pngStream.close();
			socket.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
