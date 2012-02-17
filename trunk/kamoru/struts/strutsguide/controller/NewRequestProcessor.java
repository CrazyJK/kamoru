package strutsguide.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.TilesRequestProcessor;

public class NewRequestProcessor extends TilesRequestProcessor {

	protected boolean processPreprocess(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch(Exception e) {
			// do nothing;
		}
		return true;
	}
}
