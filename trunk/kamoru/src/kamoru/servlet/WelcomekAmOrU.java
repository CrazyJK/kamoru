package kamoru.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WelcomekAmOrU
 */
public class WelcomekAmOrU extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WelcomekAmOrU() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		welcome(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		welcome(request, response);
	}

	void welcome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Welcome message
		print("**********************************");
		print("* Welcome kAmOrU Server");
		print("* Server start...");
		print("* URL " + request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				 + request.getContextPath() + "/");
		print("**********************************");
	}
	
	void print(Object o) throws ServletException, IOException {
		System.out.println(o);
	}
}
