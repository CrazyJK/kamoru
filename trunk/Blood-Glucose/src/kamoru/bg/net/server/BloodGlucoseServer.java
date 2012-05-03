package kamoru.bg.net.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kamoru.bg.net.server.biz.BloodGlucoseBiz;

public class BloodGlucoseServer implements Runnable {

	Log logger = LogFactory.getLog(this.getClass());

	protected final String iniFile = "blood-glucose.ini";
	
	protected Properties props;
	protected int port;
	protected int nThreads;
	protected boolean isStopped = false;
	protected ExecutorService threadPool;
	protected ServerSocket serverSocket = null;
	protected Thread runningThread = null;

	public BloodGlucoseServer() {
		parseProperties();
		this.port 		= Integer.valueOf(props.getProperty("serverport")).intValue();
		this.nThreads 	= Integer.valueOf(props.getProperty("nThreads")).intValue();
		this.threadPool 	= Executors.newFixedThreadPool(nThreads);
	}

	private void parseProperties() {
		this.props = new Properties();
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
	        if( cl == null ) cl = ClassLoader.getSystemClassLoader();
	        this.props.load(cl.getResourceAsStream(iniFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Cannot find ini file " + iniFile);
		} catch (IOException e) {
			throw new RuntimeException("Error reading ini file " + iniFile);
		}
	}

	public void run() {
		synchronized (this) {
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();
		while (!isStopped()) {
			Socket socket = null;
			try {
				logger.info("Waiting for a client connection");
				socket = this.serverSocket.accept(); 
			} catch (IOException e) {
				if (isStopped()) {
					logger.info("Server Stopped.");
					return;
				}
				throw new RuntimeException("Error accepting client connection", e);
			}
			logger.info("recevied data");
			this.threadPool.execute(new BloodGlucoseBiz(socket));
		}
		this.threadPool.shutdown();
		logger.info("Server Stopped.");
	}
	
	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(port);
			logger.info("Server start. port:" + this.port + " threadCount:" + this.nThreads);
		} catch (IOException e) {
			throw new RuntimeException("Cannot open port " + port, e);
		}
	}

	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	public synchronized void stop() {
		this.isStopped = true;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BloodGlucoseServer server = new BloodGlucoseServer();
		new Thread(server).start();
	}

}
