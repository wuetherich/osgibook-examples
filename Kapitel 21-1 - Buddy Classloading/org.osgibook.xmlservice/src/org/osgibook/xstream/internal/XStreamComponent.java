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
 package org.osgibook.xstream.internal;

import java.io.InputStreamReader;
import java.net.URL;

import org.osgi.framework.Bundle;
import org.osgi.service.component.ComponentContext;
import org.osgibook.xstream.XStreamService;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XStreamComponent implements XStreamService {

	private XStream	xstream;

	private Bundle	bundle;

	protected void activate(ComponentContext context) {
		bundle = context.getUsingBundle();
		this.xstream = new XStream(new DomDriver());
	}

	public Object readObject(String name) {
		// XML aus InputStream einlesen und Objekt erzeugen

		URL url = bundle.getResource(name);
		try {
			return xstream.fromXML(new InputStreamReader(url.openStream()));
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
