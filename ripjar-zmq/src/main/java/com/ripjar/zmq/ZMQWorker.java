package com.ripjar.zmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.zeromq.ZMQ;

public class ZMQWorker {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ZMQWorker.class.getName());

	public String doWork(final String connectStr, final String in) {

		LOGGER.debug(String.format("called with connect %s and data %s",
				connectStr, in));

		ZMQ.Context context = null;
		ZMQ.Socket socket = null;
		String replStr = null;

		try {
			context = ZMQ.context(1);
			// Socket to talk to server
			LOGGER.debug(String.format("connecting to the server", connectStr));
			socket = context.socket(ZMQ.REQ);
			socket.connect(connectStr);

			LOGGER.debug(String.format("sending data"));
			socket.send(in.getBytes(), 0);

			LOGGER.debug(String.format("waiting to receive data"));
			byte[] reply = socket.recv(0);
			replStr = new String(reply);

			LOGGER.debug(String.format("doWork completed with output %s",
					replStr));

		} catch (Exception e) {
			LOGGER.error("problem during data transfer", e);

		} finally {
			socket.close();
			context.term();
		}
		return replStr;
	}
}
