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
import org.osgibook.translation.TranslationService;
import org.osgibook.translation.TranslationServiceFactory;

/**
 * <p>
 * Die Activator-Klasse des helloworld-Bundles.
 * </p>
 * <p>
 * Diese Implementierung gibt beim Starten und Stoppen des Bundles eine
 * Nachricht auf der Konsole aus.
 * </p>
 * <p>
 * Diese Implementierung verwendet einen TranslationService, der von einem
 * weiteren Bundle zur Verf&uuml;gung gestellt wird.
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
		greet("hello", context.getBundle().getSymbolicName());
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
		greet("goodbye", context.getBundle().getSymbolicName());
	}

	/**
	 * <p>
	 * Gibt die Nachricht mit dem &uuml;bergebenen Key auf der Konsole aus.
	 * </p>
	 * 
	 * @param key
	 *          der Key der Nachricht, die &uuml;bersetzt werden soll.
	 * @param args
	 *          Eine Anzahl von Parameter, die in die lokalisierte Nachricht fuer
	 *          Platzhalter eingesetzt werden
	 * 
	 * @see String#format(String, Object...)
	 * 
	 */
	protected void greet(String key, Object... args) {
		TranslationService translationService = TranslationServiceFactory
		    .getTranslationService();

		// Lokalisierte Nachricht zu uebergebenem Key suchen
		String translatedMsg = translationService.getTranslation(key);

		// Parameter ersetzen
		String message = String.format(translatedMsg, args);

		// Komplette Nachricht ausgeben
		System.out.println(message);
	}
}
