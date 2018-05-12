package com.cbers;


import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Context;
import org.apache.catalina.deploy.ErrorPage;
import org.apache.catalina.startup.Tomcat;

public class Main {

	public static final Optional<String> CBERS_PORT = Optional.ofNullable(System.getenv("CBERS_PORT"));
	public static final Optional<String> CBERS_HOSTNAME = Optional.ofNullable(System.getenv("CBERS_HOSTNAME"));

	public static void main(String[] args) throws Exception {
		System.out.println("Inside Main. \n"
				+ "CBERS_PORT: "+CBERS_PORT+", CBERS_HOSTNAME: "+CBERS_HOSTNAME);
		String contextPath = "/cbers" ;
		String appBase = ".";
		Tomcat tomcat = new Tomcat();   
		tomcat.setPort(Integer.valueOf(CBERS_PORT.orElse("8080") ));
		tomcat.setHostname(CBERS_HOSTNAME.orElse("localhost"));
		tomcat.getHost().setAppBase(appBase);
		Context appContext = tomcat.addWebapp(contextPath, appBase);

		ErrorPage errorPage = new ErrorPage();
		errorPage.setErrorCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		errorPage.setExceptionType("java.lang.Exception");
		errorPage.setLocation("/error.jsp");
		appContext.addErrorPage(errorPage);

		tomcat.start();

		System.out.println("Server started.");
		System.out.println("Tomcat started on " + tomcat.getHost());
		System.out.println("Deployed " + appContext.getBaseName() + ", looking for " + appContext.findServletMapping("/health_check"));

		tomcat.getServer().await();

		System.out.println("Waiting for requests.");
	}

}