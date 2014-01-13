package com.ripjar.zmq;

import org.zeromq.ZMQ;

public class Server {
	public static void main(String[] args) throws Exception {
		ZMQ.Context context = ZMQ.context(1);
		System.out.println(ZMQ.getVersionString());
		// Socket to talk to clients
		ZMQ.Socket socket = context.socket(ZMQ.REP);
		socket.bind("tcp://*:35555");

		while (!Thread.currentThread().isInterrupted()) {
			byte[] reply = socket.recv(0);
			System.out.println("Server Received " + new String(reply));
			String request = "Oink";
			socket.send(request.getBytes(), 0);
		}
		socket.close();
		context.term();
	}
}
