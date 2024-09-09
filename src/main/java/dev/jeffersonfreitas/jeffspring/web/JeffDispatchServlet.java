package dev.jeffersonfreitas.jeffspring.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import com.google.gson.Gson;

import dev.jeffersonfreitas.jeffspring.data_structures.ControllersInstances;
import dev.jeffersonfreitas.jeffspring.data_structures.ControllersMap;
import dev.jeffersonfreitas.jeffspring.data_structures.RequestControllerData;
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
		Gson gson = new Gson();
		
		String uri = req.getRequestURI();
		String httpMethod = req.getMethod();
		String key = httpMethod + uri;
		RequestControllerData data = ControllersMap.values.get(key);
		
		Object controller;
		try {
			controller = ControllersInstances.instances.get(data.controllerClass);
			if(controller == null) {
				controller = Class.forName(data.getControllerClass()).getDeclaredConstructor().newInstance();
				ControllersInstances.instances.put(data.getControllerClass(), controller);
			}
			
			Method m = null;
			for (Method method: controller.getClass().getMethods()) {
				if(method.getName().equals(data.getControllerMethod())) {
					m = method;
					break;
				}
			}
			
			
			out.println(gson.toJson(m.invoke(controller)));
			out.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
