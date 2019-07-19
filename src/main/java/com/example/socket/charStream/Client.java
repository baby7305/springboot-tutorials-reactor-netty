package com.example.socket.charStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	private static final Logger log = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) {
		BufferedReader input = null;
		BufferedReader output = null;
		String cmd = null;
		PrintWriter printWriter = null;
		Socket socket = null;

		try {
			socket = new Socket("127.0.0.1", 9000);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			printWriter = new PrintWriter(socket.getOutputStream(), true);
			output = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Say 'bye' to exit");

			while ((cmd = output.readLine()) != null) {
				printWriter.println(cmd);
				String result = input.readLine();
				System.out.println(String.format("Server say %s", result));
				if ("bye".equalsIgnoreCase(result)) {
					break;
				}
			}
		} catch (IOException e) {
//			e.printStackTrace();
			log.error("Error on connection", e);
		} finally {
			StreamUtil.close(input);
			StreamUtil.close(output);
			StreamUtil.close(printWriter);
			StreamUtil.close(socket);
		}
		System.out.println("bye");
	}
}
