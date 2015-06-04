/*******************************************************************************
 * Copyright (c) 2007-2008 Wuetherich/Hartmann/Kolb/Luebken.
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Gerd Wuetherich, Nils Hartmann, Bernd Kolb, Matthias Luebken -
 * initial implementation
 ******************************************************************************/
 package org.osgibook.helloworld.servlet;

import java.util.Properties;

import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;

public class HelloWorldServletComponent {

	protected void setHttpService(HttpService httpService) {
		// Gemeinsamen Context fuer alle Servlets und Resourcen verwenden
		HttpContext context = httpService.createDefaultHttpContext();
		try {
			// Servlet registrieren
			httpService.registerServlet("/helloworld", new HelloWorldServlet(),
			    new Properties(), context);

			// /html-Verzeichnis des Bundles als Root-Verzeichnis registrieren
			httpService.registerResources("/", "/html", context);

			// /resources-Verzeichnis des Bundles wird unter /images registriert
			httpService.registerResources("/images", "/resources", context);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void unsetHttpService(HttpService httpService) {
		// Registrierte Resourcen freigeben...
		httpService.unregister("/helloworld");
		httpService.unregister("/");
		httpService.unregister("/images");
	}
}
