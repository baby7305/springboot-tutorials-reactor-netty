package com.example.socket.charStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

public class StreamUtil {
	private static final Logger log = LoggerFactory.getLogger(StreamUtil.class);

	public static void close(Closeable p) {
		if (p == null) {
			return;
		}
		try {
			p.close();
		} catch (IOException e) {
//			e.printStackTrace();
			log.error("Error on close {}", p.getClass().getName(), e);
		}
	}
}
