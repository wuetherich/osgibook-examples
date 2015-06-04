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
 * Die Activator-Klasse des helloworld-Bundles.
 * 
 * <p>
 * Diese Implementierung verwendet einen OSGi-basierten TranslationService um
 * die Grussbotschaften auszugeben.
 * 
 * <p>
 * Der TranslationService wird ueber die ServiceRegistry abgefragt. Er muss von
 * einem anderen Bundle dort registriert worden sein.
 * 
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
	private ServiceTracker	translationServiceTracker;

	/**
	 * Die start-Methode wird vom OSGi Framework beim Starten des Bundles
	 * aufgerufen
	 * 
	 * <p>
	 * Diese Methode erstellt einen ServiceTracker, um das an- und abmelden des
	 * TranslationService zu verfolgen
	 * 
	 * @param context
	 *          BundleContext mit Informationen ueber das Bundle
	 */
	public void start(BundleContext context) throws Exception {
		// ServiceTracker erstellen
		translationServiceTracker = new TranslationServiceTracker(context);

		// ServiceTracker oeffnen, um mit der Ueberwachung zu beginnen
		translationServiceTracker.open();
	}

	/**
	 * Die stop-Methode wird vom OSGi Framework vor dem Stoppen des Bundles
	 * aufgerufen
	 * 
	 * <p>
	 * Diese Methode schliesst den ServiceTracker
	 * 
	 * @param context
	 *          BundleContext mit Informationen ueber das Bundle
	 */
	public void stop(BundleContext context) throws Exception {
		translationServiceTracker.close();
	}

	/**
	 * Gibt die uebergebene Nachricht auf der Konsole aus.
	 * 
	 * @param translationService
	 *          Der TranslationService, der zum uebersetzen der Nachricht
	 *          verwendet wird
	 * @param key
	 *          Key der Nachricht, die uebersetzt werden soll.
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
