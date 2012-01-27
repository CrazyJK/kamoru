import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UdpReceiver extends Thread
{
	public int port;
 
	public UdpReceiver(int port)
    {
    	this.port = port;
    }

    public void run(){
    	try {
			receive(port);
		} catch (IOException e) {
			System.err.println("[" + this.getName() + "] port:" + port + " Error:" + e.getMessage());
			e.printStackTrace();
		}
    }

    public void receive(int port) throws IOException {
        byte msg[] = new byte[1024];
        DatagramPacket p = new DatagramPacket(msg, msg.length);
		DatagramSocket s = new DatagramSocket(port);
        do
        {
            System.out.println("[" + this.getName() + "] Waiting to receive by port " + port);
            s.receive(p);
            String message = new String(p.getData(), 0, p.getLength());
            InetAddress from = p.getAddress();
            String currDate = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").format(new Date());
            String stdout = "\n[" + currDate + "] [" + this.getName() + "] [PORT:" + port + "] From[" + from.getHostAddress() + ":" + p.getPort() + "]" +
            		"\n[" + message + "]";
            System.out.println(stdout);
            
            MessageBox msgbox = new MessageBox("Notify Box (" + port + ")", stdout);
            
            
            
            /*
            File file = new File("Message_by_"+from.getHostAddress() + "_" + System.currentTimeMillis() + ".log");
            System.out.println(file.getAbsolutePath());
            PrintWriter writer = new PrintWriter(new FileWriter(file),true);
            writer.println(message);
            writer.flush();
            writer.close();
            */
        } while(true);
    }
    
    public static void main(String args[]) throws Exception
    {
    	System.out.println("Start UDP receiver...");
    	for(int i=0; i<args.length; i++)
    	{
    		UdpReceiver reveiver = new UdpReceiver(Integer.parseInt(args[i]));
    		reveiver.start();
    	}
    }
}
