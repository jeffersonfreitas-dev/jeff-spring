package dev.jeffersonfreitas.jeffspring.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JeffLogger {

	public static final String GREEN 	= "\u001B[32m";
	public static final String YELLOW 	= "\u001B[33m";
	public static final String WHITE 	= "\u001B[37m";
	public static final String RESET 	= "\u001B[0m";
	public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public static void showBanner() {
		System.out.println(GREEN);
		System.out.println("           _       __  __ ____             _                   ");
		System.out.println(" _ _      | | ___ / _|/ _/ ___| _ __  _ __(_)_ __   __ _   _ _ ");
		System.out.println("(_|_)  _  | |/ _ \\ |_| |_\\___ \\| '_ \\| '__| | '_ \\ / _` | (_|_)");
		System.out.println(" _ _  | |_| |  __/  _|  _|___) | |_) | |  | | | | | (_| |  _ _ ");
		System.out.println("(_|_)  \\___/ \\___|_| |_| |____/| .__/|_|  |_|_| |_|\\__, | (_|_)");
		System.out.println("                               |_|                 |___/       		");
		System.out.println(RESET);
	}
	
	public static void log(String module, String message) {
		String time = LocalDateTime.now().format(DTF);
		System.out.printf(GREEN+"%15s " + YELLOW+"%-30s:"+WHITE+" %s\n"+RESET, time, module, message);
	}
	
}
