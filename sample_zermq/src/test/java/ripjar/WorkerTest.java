package ripjar;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WorkerTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDoWork() {
		Worker worker = new Worker();
		String resp = worker.doWork("stuff");
        assertEquals(resp, "Oink");
	}

}
