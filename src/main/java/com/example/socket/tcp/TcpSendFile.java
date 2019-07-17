package com.example.socket.tcp;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpClient;
import reactor.netty.tcp.TcpServer;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class TcpSendFile {
	public static void main(String[] args) throws CertificateException, SSLException {
		SelfSignedCertificate cert = new SelfSignedCertificate();
		SslContextBuilder sslContextBuilder =
				SslContextBuilder.forServer(cert.certificate(), cert.privateKey());
		DisposableServer server =
				TcpServer.create()   // Prepares a TCP server for configuration.
						.port(0)    // Configures the port number as zero, this will let the system pick up
						// an ephemeral port when binding the server.
						.secure(spec -> spec.sslContext(sslContextBuilder))   // Use self singed certificate.
						.wiretap(true)  // Applies a wire logger configuration.
						.handle((in, out) -> in.receive().asString().flatMap(s -> {
							try {
								Path file = Paths.get(TcpSendFile.class.getResource(s).toURI());
								return out.sendFile(file).then();
							} catch (URISyntaxException e) {
								return Mono.error(e);
							}
						}).log("tcp-server"))
						.bindNow(); // Starts the server in a blocking fashion, and waits for it to finish initializing.

		CountDownLatch latch = new CountDownLatch(1);
		
		Connection client = TcpClient.create() // Prepares a TCP client for configuration.
				.port(server.port()) // Obtains the server's port and provide it as a port to which this
										// client should connect.
				// Configures SSL providing an already configured SslContext.
				.secure(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build())
				.wiretap(true) // Applies a wire logger configuration.
				.handle((in, out) -> out.sendString(Mono.just("/index.html"))
						.then(in.receive().asByteArray().doOnNext(actualBytes -> {
							try {
								Path file = Paths.get(TcpSendFile.class.getResource("/index.html").toURI());
								byte[] expectedBytes = Files.readAllBytes(file);
								if (Arrays.equals(expectedBytes, actualBytes)) {
									latch.countDown();
								}
							} catch (URISyntaxException | IOException e) {
								e.printStackTrace();
							}
						}).log("tcp-client").then()))
				.connectNow(); // Blocks the client and returns a Connection.

		server.disposeNow(); // Stops the server and releases the resources.

		client.disposeNow(); // Stops the client and releases the resources.
	}
}
