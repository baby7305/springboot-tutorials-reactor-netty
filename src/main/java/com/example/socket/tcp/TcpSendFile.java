package com.example.socket.tcp;

import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

public class TcpSendFile {
	public static void main(String[] args) {
		DisposableServer server =
				TcpServer.create()   // Prepares a TCP server for configuration.
						.port(0)    // Configures the port number as zero, this will let the system pick up
						// an ephemeral port when binding the server.
						.bindNow(); // Starts the server in a blocking fashion, and waits for it to finish initializing.

		server.disposeNow(); // Stops the server and releases the resources.
	}
}
