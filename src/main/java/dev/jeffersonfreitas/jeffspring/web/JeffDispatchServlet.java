package dev.jeffersonfreitas.jeffspring.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JeffDispatchServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		if(req.getRequestURL().toString().endsWith("favicon.ico")) {
			return;
		}
		
		PrintWriter out = new PrintWriter(resp.getWriter());
		out.println("Hello world JeffSpring");
		out.close();
	}

}
