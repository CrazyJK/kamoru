package timeout;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Timeout {

	public static void main(String[] args) {

		ExecutorService executor = Executors.newCachedThreadPool();
		Callable<Object> task = new Callable<Object>() {
			public Object call() {
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null; // something.blockingMethod();
			}
		};
		Future<Object> future = executor.submit(task);
		try {
			Object result = future.get(5, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			// handle the timeout
			e.printStackTrace();
		} catch (InterruptedException e) {
			// handle the interrupts
			e.printStackTrace();
		} catch (ExecutionException e) {
			// handle other exceptions
			e.printStackTrace();
		} finally {
			future.cancel(true);
		}
	}
}
