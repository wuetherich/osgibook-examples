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
import org.osgi.util.tracker.ServiceTracker;
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
 * <p>
 * Diese Klasse verwendet einen {@link ServiceTracker}, um den
 * TranslationService von der OSGi ServiceRegistry zu holen bzw. um auf
 * Veraenderungen des Services zu reagieren
 * 
 * @author Gerd Wuetherich
 * @author Nils Hartmann
 * @author Bernd Kolb
 * @author Matthias Luebken
 */
public class Activator implements BundleActivator {

	/** Der ServiceTracker zur Ueberwachung des TranslationService */
	private ServiceTracker translationServiceTracker;

	/**
	 * <p>
	 * Die <code>start</code>-Methode wird vom OSGi Framework beim Starten des
	 * Bundles aufgerufen.
	 * </p>
	 * <p>
	 * Diese Methode erstellt einen ServiceTracker, um das an- und abmelden des
	 * TranslationService zu verfolgen
	 * 
	 * @param context
	 *          der BundleContext
	 */
	public void start(BundleContext context) throws Exception {

		// ServiceTracker erstellen
		translationServiceTracker = new TranslationServiceTracker(context);

		// ServiceTracker oeffnen, um mit der Ueberwachung zu beginnen
		translationServiceTracker.open();
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
		translationServiceTracker.close();
	}

	/**
	 * <p>
	 * Gibt die Nachricht mit dem &uuml;bergebenen Key auf der Konsole aus.
	 * </p>
	 * 
	 * @param translationService
	 *          Der TranslationService, der zum uebersetzen der Nachricht
	 *          verwendet wird
	 * @param key
	 *          der Key der Nachricht, die &uuml;bersetzt werden soll.
	 * @param args
	 *          Eine Anzahl von Parameter, die in die lokalisierte Nachricht fuer
	 *          Platzhalter eingesetzt werden
	 * 
	 * @see String#format(String, Object...)
	 * 
	 */
	protected void greet(TranslationService translationService, String key,
	    Object... args) {

		if (translationService != null) {
			// Lokalisierte Nachricht zu uebergebenem Key suchen
			String translatedMsg = translationService.getTranslation(key);

			// Parameter ersetzen
			String message = String.format(translatedMsg, args);

			// Komplette Nachricht ausgeben
			System.out.println(message);
		} else {
			System.out.println("TranslationService z.Zt. nicht verfuegbar.");
		}
	}

	/**
	 * Ein ServiceTracker, der bei Veraenderungen des TranslationService die
	 * entsprechende Grussbotschaft ausgibt
	 */
	class TranslationServiceTracker extends ServiceTracker {

		public TranslationServiceTracker(BundleContext bundleContext) {
			super(bundleContext, TranslationService.class.getName(), null);
		}

		public Object addingService(ServiceReference reference) {
			// TranslationService wurde an der Registry angemeldet...
			TranslationService translationService = (TranslationService) context
			    .getService(reference);
			// Gruss ausgeben
			greet(translationService, "hello", context.getBundle().getSymbolicName());
			return translationService;
		}

		public void removedService(ServiceReference reference, Object service) {
			// Service soll aus der ServiceRegistry entfernt werden: Gruss ausgeben,
			// und Referenz freigeben
			greet((TranslationService) service, "goodbye", context.getBundle()
			    .getSymbolicName());

			// Referenz freigeben
			context.ungetService(reference);
		}
	}
}
