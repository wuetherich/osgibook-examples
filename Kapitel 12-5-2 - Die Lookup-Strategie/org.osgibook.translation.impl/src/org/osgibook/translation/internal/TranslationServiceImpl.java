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
package org.osgibook.translation.internal;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.osgibook.translation.TranslationService;

/**
 * Eine TranslationService-Implementierung
 * 
 * <p>
 * Diese Implementierung liest die lokalisierten Nachrichten aus einem
 * java.util.ResourceBundle aus.
 * 
 * <p>
 * Clients, die diese Implementierung nutzen wollen, muessen ueber die
 * TranslationPermission verfuegen, andernfalls wird eine AccessControlException
 * beim Zugriff auf den Service geworfen.
 * 
 * 
 * @author Gerd Wuetherich
 * @author Nils Hartmann
 * @author Bernd Kolb
 * @author Matthias Luebken
 */
public class TranslationServiceImpl implements TranslationService {

	/**
	 * Das ResourceBundle, das die lokalisierten Nachrichten enthaelt
	 */
	private ResourceBundle	greetings;

	/**
	 * Erzeugt einen neuen TranslationServiceImpl.
	 * 
	 * <p>
	 * Die Nachrichten werden fuer das uebergebene Locale uebersetzt.
	 * 
	 * @param locale
	 *          Das Locale, das festlegt, in welche Sprache die Nachrichten
	 *          uebersetzt werden sollen
	 */
	public TranslationServiceImpl(Locale locale) {

		// ACHTUNG: das ResourceBundle heisst zwar 'Bundle',
		// hat aber nichts mit den OSGi Bundles zu tun!
		try {
			greetings = ResourceBundle.getBundle("translation", locale);
		} catch (MissingResourceException e) {
			greetings = ResourceBundle.getBundle("translation",
			    new Locale("de", "DE"));
		}
	}

	/**
	 * Liefert die uebersetzte Nachricht zu dem uebergebenen Key zurueck.
	 * 
	 * @param key
	 *          Der Key der Nachricht, die uebersetzt werden soll
	 */
	public String getTranslation(String key) {

		try {
			return greetings.getString(key);
		} catch (MissingResourceException e) {
			// Wenn der Key nicht bekannt ist, wird der Key selbst als Default-Wert
			// zurueckgegeben
			return key;
		}
	}
}
