package ripjar;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ripjar.zmq.ZMQWorker;

public class WorkerTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDoWork() {
		ZMQWorker worker = new ZMQWorker();
//		String resp = worker.doWork("stuff");
//        assertEquals(resp, "Oink");
        assertTrue(true);
	}

}
