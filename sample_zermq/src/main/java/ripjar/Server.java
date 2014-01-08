package ripjar;

import org.zeromq.ZMQ;

public class Server {
	public static void main(String[] args) throws Exception {
		ZMQ.Context context = ZMQ.context(1);
		// Socket to talk to clients
		ZMQ.Socket socket = context.socket(ZMQ.REP);
		socket.bind("tcp://*:5555");

		while (!Thread.currentThread().isInterrupted()) {
			byte[] reply = socket.recv(0);
			System.out.println("Server Received " + new String(reply));
			Thread.sleep(1000); // Do some 'work'
			String request = "Oink";
			socket.send(request.getBytes(), 0);
		}
		socket.close();
		context.term();
	}
}
