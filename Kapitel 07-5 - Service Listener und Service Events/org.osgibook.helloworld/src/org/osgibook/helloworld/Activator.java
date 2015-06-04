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
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
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
 * <p>
 * Diese Klasse implementiert ausserdem das ServiceListener-Interface um auf
 * Ver&auml;nderungen an der OSGi Service Registry reagieren zu k&ouml;nnen.
 * 
 * @author Gerd Wuetherich
 * @author Nils Hartmann
 * @author Bernd Kolb
 * @author Matthias Luebken
 */
public class Activator implements BundleActivator, ServiceListener {

	/**
	 * Der Context des Bundles
	 */
	private BundleContext	bundleContext;

	/**
	 * Die start-Methode wird vom OSGi Framework beim Starten des Bundles
	 * aufgerufen
	 * 
	 * <p>
	 * Diese Methode registriert einen ServiceListener, um reagieren zu koennen,
	 * sobald ein TranslationService an der OSGi ServiceRegistry an- oder
	 * abgemeldet wurde
	 * 
	 * @param context
	 *          BundleContext mit Informationen ueber das Bundle
	 */
	public void start(BundleContext context) throws Exception {
		this.bundleContext = context;

		// ServiceListener installieren, um Benachrichtigung zu erhalten,
		// sobald der TranslationService spaeter installiert oder deinstalliert wird
		String filter = "(" + Constants.OBJECTCLASS + "="
		    + TranslationService.class.getName() + ")";
		context.addServiceListener(this, filter);

		greet("hello", context.getBundle().getSymbolicName());
	}

	/**
	 * Die stop-Methode wird vom OSGi Framework vor dem Stoppen des Bundles
	 * aufgerufen
	 * 
	 * <p>
	 * Diese Methode entfernt den ServiceListener und gibt die ServiceReference
	 * auf den TranslationService wieder frei
	 * 
	 * @param context
	 *          BundleContext mit Informationen ueber das Bundle
	 */
	public void stop(BundleContext context) throws Exception {
		// Abschiedsgruss ausgeben
		greet("goodbye", context.getBundle().getSymbolicName());

		// ServiceListener entfernen
		context.removeServiceListener(this);
	}

	/**
	 * Implementierung der serviceChanged-Methode aus dem ServiceListener
	 * Interface.
	 * 
	 * <p>
	 * Diese Methode wird vom OSGi Framework aufgerufen, sobald sich am Zustand
	 * des Services etwas aendert, also wenn der Service registriert, abgemeldet
	 * oder dessen Properties veraendert wurden.
	 * 
	 * @param event
	 *          Der ServiceEvent der die Art der Zustandsaenderung des Services
	 *          beschreibt
	 */
	public void serviceChanged(ServiceEvent event) {
		switch (event.getType()) {
		case ServiceEvent.REGISTERED:
			// Service wurde an der Registry angemeldet: Grussbotschaft ausgeben
			greet("hello", bundleContext.getBundle().getSymbolicName());
			break;
		case ServiceEvent.UNREGISTERING:
			// Service wurde aus der OSGi Service Registry entfernt
			System.out.println("TranslationService wurde abgemeldet.");
			break;
		default:
			break;
		}
	}

	/**
	 * Gibt die uebergebene Nachricht auf der Konsole aus.
	 * 
	 * @param key
	 *          Key der Nachricht, die uebersetzt werden soll.
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