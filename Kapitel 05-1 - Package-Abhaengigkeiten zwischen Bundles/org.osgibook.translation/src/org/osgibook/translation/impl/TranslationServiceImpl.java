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
 package org.osgibook.translation.impl;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.osgibook.translation.TranslationService;

/**
 * <p>
 * Eine TranslationService-Implementierung, die die lokalisierten Nachrichten
 * aus einem java.util.ResourceBundle ausliest.
 * </p>
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
	private ResourceBundle	translations;

	/**
	 * <p>
	 * Erzeugt einen neuen TranslationServiceImpl.
	 * </p>
	 * <p>
	 * Die Nachrichten werden f&uuml;r das &uuml;bergebene Locale &uuml;bersetzt.
	 * </p>
	 * 
	 * @param locale
	 *          Das Locale, das festlegt, in welche Sprache die Nachrichten
	 *          &uuml;bersetzt werden sollen
	 */
	public TranslationServiceImpl(Locale locale) {

		// ACHTUNG: das ResourceBundle heisst zwar 'Bundle',
		// hat aber nichts mit den OSGi Bundles zu tun!
		try {
			translations = ResourceBundle.getBundle("translation", locale);
		} catch (MissingResourceException e) {
			translations = ResourceBundle.getBundle("translation", new Locale("de",
			    "DE"));
		}
	}

	/**
	 * <p>
	 * Liefert die &uuml;bersetzte Nachricht zu dem &uuml;bergebenen Key zur&uuml;ck.
	 * </p>
	 * 
	 * @param key
	 *          Der Key der Nachricht, die &uuml;bersetzt werden soll.
	 */
	public String getTranslation(String key) {
		try {
			return translations.getString(key);
		} catch (MissingResourceException e) {
			// Wenn der Key nicht bekannt ist, wird der Key selbst als Default-Wert
			// zurueckgegeben
			return key;
		}
	}
}
