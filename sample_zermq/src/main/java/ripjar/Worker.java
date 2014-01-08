package ripjar;

import org.zeromq.ZMQ;

public class Worker {

	public String doWork(final String in) {
		ZMQ.Context context = ZMQ.context(1);
		
		String testStr = "{\"config\": {\"text\": \"Hello World! When are we going to London?\"}}";

		// Socket to talk to server
		System.out.println("Connecting to 0mq server");

		ZMQ.Socket socket = context.socket(ZMQ.REQ);
//		socket.connect("tcp://10.0.1.6:5559");
		socket.connect("tcp://localhost:5555");

		System.out.println("Sending " + in);
		socket.send(in.getBytes(), 0);

		byte[] reply = socket.recv(0);
		System.out.println("Client Received " + new String(reply));

		socket.close();
		context.term();
		return new String(reply);
	}

}
