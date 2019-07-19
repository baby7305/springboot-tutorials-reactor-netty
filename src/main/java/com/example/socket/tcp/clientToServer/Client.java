package com.example.socket.tcp.clientToServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) {
		try {
			System.out.println("socket begin" + System.currentTimeMillis());
			Socket socket = new Socket("localhost", 8088);
			System.out.println("socket begin" + System.currentTimeMillis());
			Thread.sleep(3000);
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write("我是外星人".getBytes());
			outputStream.close();
			socket.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
