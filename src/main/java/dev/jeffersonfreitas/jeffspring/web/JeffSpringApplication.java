package dev.jeffersonfreitas.jeffspring.web;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import dev.jeffersonfreitas.jeffspring.annotations.JeffGetMethod;
import dev.jeffersonfreitas.jeffspring.annotations.JeffPostMethod;
import dev.jeffersonfreitas.jeffspring.data_structures.ControllersMap;
import dev.jeffersonfreitas.jeffspring.data_structures.RequestControllerData;
import dev.jeffersonfreitas.jeffspring.explorer.ClassExplorer;
import dev.jeffersonfreitas.jeffspring.utils.JeffLogger;

public class JeffSpringApplication {

	public static void run(Class<?> sourceClass) {

		java.util.logging.Logger.getLogger("org.apache").setLevel(java.util.logging.Level.OFF);
		long init, fim;

		try {
			JeffLogger.showBanner();
			JeffLogger.log("Embedded Web Container", "Starting JeffSpringApplication");

			extractMetaData(sourceClass);

			init = System.currentTimeMillis();
			Tomcat tomcat = new Tomcat();
			Connector connector = new Connector();
			connector.setPort(8080);
			JeffLogger.log("Embedded Web Container", "Web Container started port: 8080");
			tomcat.setConnector(connector);
			Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());
			Tomcat.addServlet(ctx, "JeffDispatchServlet", new JeffDispatchServlet());
			ctx.addServletMappingDecoded("/*", "JeffDispatchServlet");
			tomcat.start();
			fim = System.currentTimeMillis();
			JeffLogger.log("Embedded Web Container",
					"JeffSpring Web Application starten in " + ((double) (fim - init) / 1000) + " secounds");
			tomcat.getServer().await();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void extractMetaData(Class<?> sourceClass) throws Exception{
		List<String> allClasses = ClassExplorer.retrieveAllClasses(sourceClass);
		for (String jeffClass : allClasses) {
			Annotation[] annotations = Class.forName(jeffClass).getAnnotations();

			for (Annotation annotation : annotations) {
				if (annotation.annotationType().getName().equals("dev.jeffersonfreitas.jeffspring.annotations.JeffController")) {
					JeffLogger.log("Metadata Explorer", "Found controller " + jeffClass);
					extractMethod(jeffClass);
				}
			}
		}
	}
	
	private static void extractMethod(String classStr) throws Exception{
		String httpMethod = "";
		String path = "";
		for(Method method: Class.forName(classStr).getDeclaredMethods()) {
			for(Annotation annotation : method.getDeclaredAnnotations()) {
				
				if(annotation.annotationType().getName().equals("dev.jeffersonfreitas.jeffspring.annotations.JeffGetMethod")) {
					path = ((JeffGetMethod)annotation).value();
					httpMethod = "GET";
				} else if(annotation.annotationType().getName().equals("dev.jeffersonfreitas.jeffspring.annotations.JeffPostMethod")) {
					path = ((JeffPostMethod)annotation).value();
					httpMethod = "POST";
				}
				RequestControllerData postData = new RequestControllerData(httpMethod, path, classStr, method.getName());
				ControllersMap.values.put(httpMethod+path, postData);
			}
		}
	}

}
