package dev.jeffersonfreitas.jeffspring.web;

import java.io.File;
import java.util.List;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import dev.jeffersonfreitas.jeffspring.explorer.ClassExplorer;
import dev.jeffersonfreitas.jeffspring.utils.JeffLogger;

public class JeffSpringApplication {
	
	public static void run(Class<?> sourceClass) {
		
		java.util.logging.Logger.getLogger("org.apache").setLevel(java.util.logging.Level.OFF);
		long init, fim;
		
		try {
			JeffLogger.showBanner();
			JeffLogger.log("Embedded Web Container", "Starting JeffSpringApplication");
			
			List<String> allClasses = ClassExplorer.retrieveAllClasses(sourceClass);
			for(String s: allClasses) {
				JeffLogger.log("Class Explorer", s);
			}
			
			init = System.currentTimeMillis();
			Tomcat tomcat = new Tomcat();
			Connector connector = new Connector();
			connector.setPort(8080);
			JeffLogger.log("Embedded Web Container", "Web Container started port: 8080" );
			tomcat.setConnector(connector);
			Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());
			Tomcat.addServlet(ctx, "JeffDispatchServlet", new JeffDispatchServlet());
			ctx.addServletMappingDecoded("/*", "JeffDispatchServlet");
			tomcat.start();
			fim = System.currentTimeMillis();
			JeffLogger.log("Embedded Web Container", "JeffSpring Web Application starten in "+ ((double)(fim - init) /1000)+" secounds");
			tomcat.getServer().await();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
