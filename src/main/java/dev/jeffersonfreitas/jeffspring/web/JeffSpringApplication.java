package dev.jeffersonfreitas.jeffspring.web;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import dev.jeffersonfreitas.jeffspring.utils.JeffLogger;

public class JeffSpringApplication {
	
	public static void run() {
		
		java.util.logging.Logger.getLogger("org.apache").setLevel(java.util.logging.Level.OFF);
		long init, fim;
		
		try {
			JeffLogger.showBanner();
			JeffLogger.log("Main Module", "Starting JeffSpringApplication");
			init = System.currentTimeMillis();
			Tomcat tomcat = new Tomcat();
			Connector connector = new Connector();
			connector.setPort(8080);
			tomcat.setConnector(connector);
			Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());
			Tomcat.addServlet(ctx, "JeffDispatchServlet", new JeffDispatchServlet());
			ctx.addServletMappingDecoded("/*", "JeffDispatchServlet");
			tomcat.start();
			fim = System.currentTimeMillis();
			JeffLogger.log("Main Module", "JeffSpring Web Application starten in "+ ((double)(fim - init) /1000)+" secounds");
			tomcat.getServer().await();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
