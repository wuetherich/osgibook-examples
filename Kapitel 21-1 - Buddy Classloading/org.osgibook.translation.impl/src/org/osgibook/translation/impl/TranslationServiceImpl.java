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

import org.osgi.service.component.ComponentContext;
import org.osgibook.translation.TranslationService;
import org.osgibook.xstream.XStreamService;

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
	 * Das MessageDictionary, das die lokalisierten Nachrichten enthaelt
	 */
	private MessageDictionary	dictionary;
	
	private XStreamService xstreamService;

	/**
	 * Wird von der Service Component Runtime aufgerufen, um eine Instanz des
	 * TranslationService zu erzeugen.
	 * 
	 */
	public TranslationServiceImpl() {
	}

	/**
	 * @param xstreamService
	 */
	public void setXStreamService(XStreamService xstreamService) {
		this.xstreamService = xstreamService;
	}

	/**
	 * @param xstreamService
	 */
	public void unsetXStreamService(XStreamService xstreamService) {
		this.xstreamService = null;
	}
	
	/**
	 * Liefert die uebersetzte Nachricht zu dem uebergebenen Key zurueck.
	 * 
	 * @param key
	 *          Der Key der Nachricht, die uebersetzt werden soll
	 */
	public String getTranslation(String key) {
		try {
			return dictionary.getMessage(key);
			// Wenn der Key nicht bekannt ist, wird der Key selbst als Default-Wert
			// zurueckgegeben
		} catch (Exception e) {
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
    String dictionaryFile = "translation_" + language + "_"
        + country + ".xml";
    dictionary = (MessageDictionary) xstreamService.readObject(dictionaryFile);
	}
}
