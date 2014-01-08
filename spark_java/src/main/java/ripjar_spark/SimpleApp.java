/*** SimpleApp.java ***/
package ripjar_spark;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import ripjar.Worker;
import scala.Tuple2;

public class SimpleApp {
	public static void main(String[] args) {
		String logFile = "pom.xml"; // Should be some file on your system
		JavaSparkContext sc = new JavaSparkContext("local", "Simple App",
				"/Users/Jeremy/spark_latest/incubator-spark",
				new String[] { "target/spark_java-1.0-SNAPSHOT.jar",
				"target/sample_zermq-1.0-SNAPSHOT.jar", "target/jzmq-3.0.1.jar"});
		JavaRDD<String> lines = sc.textFile(logFile).cache();

		// JavaRDD<String> thingy = lines.map(new Function<String, String>() {
		// public String call(String s) {
		// Worker worker = new Worker();
		// //
		// // ZMQ.Context context = ZMQ.context(1);
		// //
		// // String testStr =
		// "{\"config\": {\"text\": \"Hello World! When are we going to London?\"}}";
		// //
		// // // Socket to talk to server
		// // System.out.println("Connecting to 0mq server");
		// //
		// // ZMQ.Socket socket = context.socket(ZMQ.REQ);
		// //// socket.connect("tcp://10.0.1.6:5559");
		// // socket.connect("tcp://localhost:5555");
		// // String in = "rabbit";
		// // System.out.println("Sending " + in);
		// // socket.send(in.getBytes(), 0);
		// //
		// // byte[] reply = socket.recv(0);
		// // System.out.println("Client Received " + new String(reply));
		// //
		// // socket.close();
		// // context.term();
		//
		// return worker.doWork(s);
		//
		// }
		// });
		//
		// System.out.println("***************burble " + thingy.toString());

		// JavaRDD<String> lines = ctx.textFile(args[1], 1);

		JavaRDD<String> words = lines
				.flatMap(new FlatMapFunction<String, String>() {
					public Iterable<String> call(String s) {
						System.out.println("abc1");
						System.out.println("oink");
						Worker worker = new Worker();
						return Arrays.asList((worker.doWork(s).split(" ")));
					}
				});

		JavaPairRDD<String, Integer> ones = words
				.map(new PairFunction<String, String, Integer>() {
					public Tuple2<String, Integer> call(String s) {
						return new Tuple2<String, Integer>(s, 1);
					}
				});

		JavaPairRDD<String, Integer> counts = ones
				.reduceByKey(new Function2<Integer, Integer, Integer>() {
					public Integer call(Integer i1, Integer i2) {
						return i1 + i2;
					}
				});

		List<Tuple2<String, Integer>> output = counts.collect();
		for (Tuple2<?, ?> tuple : output) {
			System.out.println(tuple._1 + ": " + tuple._2);
		}
		System.exit(0);
	}
}