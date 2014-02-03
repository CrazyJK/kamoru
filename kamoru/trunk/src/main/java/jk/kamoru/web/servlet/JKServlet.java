package jk.kamoru.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import jk.kamoru.app.video.VideoCore;
import jk.kamoru.app.video.dao.VideoDao;
import jk.kamoru.app.video.service.VideoService;
import lombok.extern.slf4j.Slf4j;

/**
 * Servlet implementation class JKServlet
 */
@Component("jkServlet")
@Slf4j
public final class JKServlet implements HttpRequestHandler {
	
	@Autowired private VideoService videoService;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JKServlet() {
        super();
        log.info("JKServlet creat");
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		log.info("JKServlet init");
		videoService.reload();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("I'm alive!");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/manager/jk.kamoru.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.info("I'm alive, too!");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/manager/jk.kamoru.jsp");
		dispatcher.forward(request, response);
	}

}
