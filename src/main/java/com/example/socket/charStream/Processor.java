package com.example.socket.charStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Processor implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(Processor.class);

	private Socket socket;

	public Processor(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader input = null;
		PrintWriter printWriter = null;

		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			printWriter = new PrintWriter(socket.getOutputStream(), true);
			while (!Thread.interrupted()) {
				String result = input.readLine();
				System.out.println(String.format("%s say %s", socket.getRemoteSocketAddress(), result));
				printWriter.println(result);
				if ("byte".equalsIgnoreCase(result)) {
					break;
				}
			}
		} catch (IOException e) {
//			e.printStackTrace();
			log.error("Error on process command", e);
		} finally {
			StreamUtil.close(input);
			StreamUtil.close(printWriter);
			StreamUtil.close(socket);
		}
	}
}
