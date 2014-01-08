/*** SimpleApp.java ***/
package ripjar;
import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.Function;
import ripjar.Worker;

public class SimpleApp {
  public static void main(String[] args) {
    String logFile = args[1]; // Should be some file on your system
    JavaSparkContext sc = new JavaSparkContext("local", "Simple App",
      "/Users/Jeremy/spark_latest/incubator-spark", new String[]{"target/spark_java-1.0-SNAPSHOT.jar"});
    JavaRDD<String> logData = sc.textFile(logFile).cache();

    JavaRDD<String> thingy = logData.map(new Function<String, String>() {
        public String call(String s) { Worker worker = new Worker(); return worker.doWork(s);  }
    });

    System.out.println("burble "+ thingy.toString());
  }
}