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

import java.util.Dictionary;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.osgi.service.component.ComponentContext;
import org.osgibook.translation.TranslationService;

/**
 * Eine TranslationService-Implementierung
 * 
 * <p>
 * Diese Implementierung liest die lokalisierten Nachrichten aus einem
 * java.util.ResourceBundle aus.
 * 
 * <p>
 * Diese TranslationService-Implementierung kann ueber die Deklarativen Services
 * verwaltet werden.
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
	private ResourceBundle translations;

	/**
	 * Wird von der Service Component Runtime aufgerufen, um eine Instanz des
	 * TranslationService zu erzeugen.
	 * 
	 */
	public TranslationServiceImpl() {
	}

	/**
	 * Liefert die uebersetzte Nachricht zu dem uebergebenen Key zurueck.
	 * 
	 * @param key
	 *          Der Key der Nachricht, die uebersetzt werden soll
	 */
	public String getTranslation(String key) {
		try {
			return translations.getString(key);
			// Wenn der Key nicht bekannt ist, wird der Key selbst als Default-Wert
			// zurueckgegeben
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Wird von der Component Service Runtime aufgerufen, nach dem die Instanz
	 * erzeugt wurde.
	 * 
	 * @param componentContext
	 *          der Component Context, der u.a. die Properties fuer diese
	 *          Komponente enthaelt
	 */
	@SuppressWarnings("unchecked")
	protected void activate(ComponentContext componentContext) {

		// Properties mit Sprach- und Laendercode auslesen
		Dictionary properties = componentContext.getProperties();
		String language = (String) properties.get("translation.language");
		String country = (String) properties.get("translation.country");

		// Sprach-Datei einlesen
		Locale locale = new Locale(language, country);
		translations = ResourceBundle.getBundle("translation", locale);
	}
}
