package dev.jeffersonfreitas.jeffspring.web;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

public class JeffSpringApplication {
	
	public static void run() {
		try {
			
			Tomcat tomcat = new Tomcat();
			Connector connector = new Connector();
			connector.setPort(8080);
			tomcat.setConnector(connector);
			Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());
			Tomcat.addServlet(ctx, "JeffDispatchServlet", new JeffDispatchServlet());
			ctx.addServletMappingDecoded("/*", "JeffDispatchServlet");
			tomcat.start();
			tomcat.getServer().await();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
