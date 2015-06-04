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
 package org.osgibook.helloworld;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * <p>
 * Die Activator-Klasse des helloworld-Bundles.
 * </p>
 * <p>
 * Diese Implementierung gibt beim Starten und Stoppen des Bundles eine
 * Nachricht auf der Konsole aus.
 * </p>
 * 
 * @author Gerd Wuetherich
 * @author Nils Hartmann
 * @author Bernd Kolb
 * @author Matthias Luebken
 */
public class Activator implements BundleActivator {

	/**
	 * <p>
	 * Die <code>start</code>-Methode wird vom OSGi Framework beim Starten des
	 * Bundles aufgerufen.
	 * </p>
	 * 
	 * @param context
	 *          der BundleContext
	 */
	public void start(BundleContext context) throws Exception {
		greet("Hello OSGi World from bundle %s!", context.getBundle()
		    .getSymbolicName());
	}

	/**
	 * <p>
	 * Die <code>stop</code>-Methode wird vom OSGi Framework beim dem Stoppen
	 * des Bundles aufgerufen.
	 * </p>
	 * 
	 * @param context
	 *          der BundleContext
	 */
	public void stop(BundleContext context) throws Exception {
		greet("Goodbye OSGi World from bundle %s!", context.getBundle()
		    .getSymbolicName());
	}

	/**
	 * <p>
	 * Gibt die uebergebene Nachricht auf der Konsole aus.
	 * </p>
	 * 
	 * @param msg
	 *          Die Nachricht, die ausgegeben werden soll
	 * @param args
	 *          Eine Anzahl von Parametern, die in der Nachricht Platzhalter
	 *          ersetzt
	 * 
	 * @see String#format(String, Object...)
	 * 
	 */
	protected void greet(String msg, Object... args) {
		String message = String.format(msg, args);
		System.out.println(message);
	}
}