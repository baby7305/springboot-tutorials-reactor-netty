package com.example.socket.tcp;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.CertificateException;

public class TcpSendFile {
	public static void main(String[] args) throws CertificateException {
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

		server.disposeNow(); // Stops the server and releases the resources.
	}
}
