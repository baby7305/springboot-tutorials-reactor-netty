package com.example.socket.udp.demo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Client {
	//客户端要发送的数据字节长度为10
	//所以服务端只能最大去的10个数据
	public static void main(String[] args) throws IOException {
		DatagramSocket socket = new DatagramSocket();
		socket.connect(new InetSocketAddress("localhost", 8888));
		String newString = "1234567890";
		byte[] byteArray = newString.getBytes();
		DatagramPacket myPacket = new DatagramPacket(byteArray, 0, byteArray.length);
		socket.send(myPacket);
		socket.close();
	}
}
