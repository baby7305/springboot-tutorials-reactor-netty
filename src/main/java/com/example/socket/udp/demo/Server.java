package com.example.socket.udp.demo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
	public static void main(String[] args) throws IOException {
		DatagramSocket socket = new DatagramSocket(8888);
		byte[] byteArray = new byte[12];

		//构造方法第二个参数也要写上10个，代表要接手数据的长度为10
		//和客户端发送数据的长度要一致
		DatagramPacket myPacket = new DatagramPacket(byteArray, 10);
		socket.receive(myPacket);
		socket.close();

		System.out.println("包中数据的长度：" + myPacket.getLength());
		System.out.println(new String(myPacket.getData(), 0, myPacket.getLength()));
	}
}
