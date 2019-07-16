package com.example.socket.udp;

import reactor.netty.Connection;
import reactor.netty.udp.UdpServer;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

public class UdpEchoServer {
	public static void main(String[] args) {
		Connection server = UdpServer.create() // Prepares a UDP server for configuration.
				.port(0) // Configures the port number as zero, this will let the system pick up an
							// ephemeral port when binding the server.
				.bind() // Binds the UDP server and returns a Mono<Connection>.
				.block(); // Blocks and waits the server to finish initialising.

		assertNotNull(server);

		server.disposeNow(); // Stops the server and releases the resources.
	}
}
