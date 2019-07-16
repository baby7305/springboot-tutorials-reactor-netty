package com.example.socket.udp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.resources.LoopResources;
import reactor.netty.udp.UdpClient;
import reactor.netty.udp.UdpServer;

import java.util.concurrent.CountDownLatch;


public class UdpEchoServer {
	public static void main(String[] args) throws InterruptedException {
		LoopResources loopResources = LoopResources.create("echo-test-udp");
		Connection server = UdpServer.create() // Prepares a UDP server for configuration.
				.port(0) // Configures the port number as zero, this will let the system pick up an
							// ephemeral port when binding the server.
				.runOn(loopResources) // Configures the UDP server to run on a specific LoopResources.
				.wiretap(true) // Applies a wire logger configuration.
				.handle((in, out) -> in.receiveObject() // Obtains the inbound Flux.
						.map(o -> { // Transforms the datagram.
							if (o instanceof DatagramPacket) {
								// Incoming DatagramPacket
								DatagramPacket p = (DatagramPacket) o;
								ByteBuf buf1 = Unpooled.copiedBuffer("Hello ", CharsetUtil.UTF_8);
								// Creates a new ByteBuf using the incoming DatagramPacket content.
								ByteBuf buf2 = Unpooled.copiedBuffer(buf1, p.content().retain());
								// Creates a new DatagramPacket with the ByteBuf and the sender
								// information from the incoming DatagramPacket.
								return new DatagramPacket(buf2, p.sender());
							} else {
								return Mono.error(new Exception("Unexpected type of the message: " + o));
							}
						}).flatMap(out::sendObject).log("udp-server"))
				.bind() // Binds the UDP server and returns a Mono<Connection>.
				.block(); // Blocks and waits the server to finish initialising.

		CountDownLatch latch = new CountDownLatch(1);
		Connection client =
				UdpClient.create()               // Prepares a UDP client for configuration.
						.port(server.address()  // Obtains the server's port and provide it as a port to which this
								.getPort()) // client should connect.
						.runOn(loopResources)   // Configures the UDP client to run on a specific LoopResources.
						.wiretap(true)              // Applies a wire logger configuration.
						.handle((in, out) -> out.sendString(Mono.just("World!")).then(in.receiveObject().doOnNext(o -> {
							if (o instanceof DatagramPacket) {
								// Incoming DatagramPacket
								DatagramPacket p = (DatagramPacket) o;
								if ("Hello World!".equals(p.content().toString(CharsetUtil.UTF_8))) {
									latch.countDown();
								}
							}
						}).then()).then().log("udp-client"))
						.connect()              // Connects the client.
						.block();               // Subscribes to the returned Mono<Connection> and block.

		server.disposeNow(); // Stops the server and releases the resources.

		client.disposeNow();       // Stops the client and releases the resources.
	}
}
