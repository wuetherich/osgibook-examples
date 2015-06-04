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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ein Servlet, das eine persoenliche Begruessung ausgibt oder eine
 * Fehlermeldung ausgibt, wenn es ohne Benutzername aufgerufen wurde
 * 
 * @author Gerd Wuetherich
 * @author Nils Hartmann
 * @author Bernd Kolb
 * @author Matthias Luebken
 */
public class HelloWorldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {

		// Namen aus den Request-Parametern auslesen
		String name = req.getParameter("name");
		if (name == null || name.length() < 1) {
			// kein Name eingegeben -> Fehlermeldung ausgeben
			resp.sendRedirect("/fehler.html");
			return;
		}

		// HTML-Seite bauen
		StringBuilder builder = new StringBuilder();
		builder.append("<html><head><title>Hello World!</title></head>").append(
		    "<body><h1>Hallo</h1>").append(
		    "<p>Willkommen in der fabelhaften Welt von OSGi,<b>").append(name)
		    .append("</b>").append("</body></html>");

		// Antwort senden
		resp.getOutputStream().print(builder.toString());
		resp.getOutputStream().flush();
	}
}