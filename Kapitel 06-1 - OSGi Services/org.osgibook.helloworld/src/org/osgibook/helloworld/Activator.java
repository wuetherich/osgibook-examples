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
import org.osgi.framework.ServiceReference;
import org.osgibook.translation.TranslationService;

/**
 * <p>
 * Die Activator-Klasse des helloworld-Bundles.
 * </p>
 * <p>
 * Diese Implementierung gibt beim Starten und Stoppen des Bundles eine
 * Nachricht auf der Konsole aus.
 * </p>
 * <p>
 * Der TranslationService wird ueber die ServiceRegistry abgefragt. Er muss von
 * einem anderen Bundle dort registriert worden sein.
 * </p>
 * 
 * @author Gerd Wuetherich
 * @author Nils Hartmann
 * @author Bernd Kolb
 * @author Matthias Luebken
 */
public class Activator implements BundleActivator {

	/**
	 * Der Context des Bundles
	 */
	private BundleContext	bundleContext;

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
		this.bundleContext = context;
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

		// Referenz auf TranslationService von der ServiceRegistry holen
		ServiceReference serviceReference = bundleContext
		    .getServiceReference(TranslationService.class.getName());

		if (serviceReference != null) {
			// Service von der Registry holen
			TranslationService translationService = (TranslationService) bundleContext
			    .getService(serviceReference);

			if (translationService != null) {
				// Lokalisierte Nachricht zu uebergebenem Key suchen
				String translatedMsg = translationService.getTranslation(key);

				// Parameter ersetzen
				String message = String.format(translatedMsg, args);

				// Komplette Nachricht ausgeben
				System.out.println(message);
			}

			// Service-Referenz freigeben
			bundleContext.ungetService(serviceReference);
		} else {
			System.out.println("TranslationService z.Zt. nicht verfuegbar.");
		}
	}
}