import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UdpReceiver
{

    public UdpReceiver()
    {
    }

    public static void main(String args[]) throws Exception
    {
        byte msg[] = new byte[1024];
        int port = Integer.parseInt(args[0]);
        
        DatagramPacket p = new DatagramPacket(msg, msg.length);
        DatagramSocket s = new DatagramSocket(port);//, addr);
        System.out.println("포트:" + port + " 로 수신 대기중.");
        do
        {
            s.receive(p);
            String message = new String(p.getData(), 0, p.getLength());
            InetAddress from = p.getAddress();
            String currDate = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").format(new Date());
            String stdout = "[" + currDate + "] From[" + from.getHostAddress() + ":" + p.getPort() + "] message[" + message;
            System.out.println(stdout);
            PrintWriter writer = new PrintWriter(new FileWriter(from.getHostAddress()+"_"+p.getPort()+"_"+System.currentTimeMillis()),true);
            writer.println(message);
            writer.flush();
            writer.close();
        } while(true);
    }
}
