import com.jcraft.jsch.*;

/**
 * ref. http://www.beanizer.org/site/index.php/en/Articles/Java-ssh-tunneling-with-jsch.html
 * @author kamoru
 *
 */
public class Tunnel_OfficeSide {
	public static void main(String[] args) {
		Tunnel_OfficeSide t = new Tunnel_OfficeSide();
		try {
			t.go();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void go() throws Exception{
        String host		= "123.212.190.111";
        String user		= "kamoru";
        String password	= "k@m0rU3806";
        int    port		= 22;

        System.out.println("\nSSH Tunneling is started\n");
        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        session.setPassword(password);
        localUserInfo lui = new localUserInfo();
        session.setUserInfo(lui);
        System.out.print("\tServer connect");
        session.connect();
        System.out.println("ed\n");
        
        session.setPortForwardingL(53389, "localhost", 53389);
        System.out.println("\tHome side rdp, connect to rdp app by port 53389");
        // ssh
        session.setPortForwardingR(50022, "localhost", 22);
        System.out.println("\tssh            ready. port 500_22");
        // rdp for VirtualBox
        session.setPortForwardingR(54389, "localhost", 4389);
        System.out.println("\tvirtualbox rdp ready. port 5_4389");
        // vnc
        session.setPortForwardingR(55800, "localhost", 5800);
        session.setPortForwardingR(55801, "localhost", 5801);
        session.setPortForwardingR(55900, "localhost", 5900);
        session.setPortForwardingR(55901, "localhost", 5901);
        System.out.println("\tvnc            ready. port 5_5800,5801,5900,5901");

        System.out.println("\nSSH Tunneling was constructed\n");
    
    }

	class localUserInfo implements UserInfo {
		String passwd;

		public String getPassword() {
			return passwd;
		}
		public boolean promptYesNo(String str) {
			return true;
		}
		public String getPassphrase() {
			return null;
		}
		public boolean promptPassphrase(String message) {
			return true;
		}
		public boolean promptPassword(String message) {
			return true;
		}
		public void showMessage(String message) {
		}
	}
}