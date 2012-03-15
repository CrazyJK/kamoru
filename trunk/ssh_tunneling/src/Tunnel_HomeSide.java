import com.jcraft.jsch.*;

/**
 * ref. http://www.beanizer.org/site/index.php/en/Articles/Java-ssh-tunneling-with-jsch.html
 * @author kamoru
 *
 */
public class Tunnel_HomeSide {
	public static void main(String[] args) {
		Tunnel_HomeSide t = new Tunnel_HomeSide();
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
        
        session.setPortForwardingR(53389, "localhost", 3389);
        System.out.println("\tRDP       ready. port 5_3389");
        // ssh
        session.setPortForwardingL(50022, "localhost", 50022);
        System.out.println("\toffice side ssh, connect 'ssh -P 50022 localhost'");
        // rdp for VirtualBox
        session.setPortForwardingL(54389, "localhost", 54389);
        System.out.println("\toffice side rdp, connect 'mstsc localhost:54389'");
        // vnc
        session.setPortForwardingL(5800, "localhost", 55800);
        session.setPortForwardingL(5801, "localhost", 55801);
        session.setPortForwardingL(5900, "localhost", 55900);
        session.setPortForwardingL(5901, "localhost", 55901);
        System.out.println("\toffice side vnc, connect to vnc viewer");

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