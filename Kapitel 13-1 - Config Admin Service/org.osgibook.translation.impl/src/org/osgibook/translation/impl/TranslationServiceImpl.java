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
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgibook.translation.TranslationService;

/**
 * Eine "managbare" TranslationService-Implementierung
 * 
 * <p>
 * Diese Implementierung liest die lokalisierten Nachrichten aus einem
 * java.util.ResourceBundle aus.
 * 
 * <p>
 * Das verwendete Locale kann ueber die Properties <tt>translation.language</tt>
 * und <tt>translation.country</tt> ueber den Config Admin Service gesetzt
 * werden.
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
public class TranslationServiceImpl implements TranslationService,
    ManagedService {

	/** der key fuer die Property translation.country */
	private static final String TRANSLATION_COUNTRY = "translation.country";

	/** der key fuer die Property translation.language */
	private static final String TRANSLATION_LANGUAGE = "translation.language";

	/**
	 * Das ResourceBundle, das die lokalisierten Nachrichten enthaelt
	 */
	private ResourceBundle translations;

	private ServiceRegistration registration;

	/**
	 * Erzeugt einen neuen TranslationServiceImpl.
	 * 
	 * <p>
	 * Der Service ist fuer Clients erst verwendbar, wenn ueber die
	 * {@link #updated(Dictionary)} Methode die Properties fuer Sprach- und
	 * Laendercode gesetzt worden sind
	 * 
	 */
	public TranslationServiceImpl() {
		translations = null;
	}

	/**
	 * @see org.osgibook.translation.TranslationService#getTranslation(java.lang.String)
	 */
	public String getTranslation(String message) {
		try {
			return translations.getString(message);
		} catch (MissingResourceException e) {
			return "";
		}
	}

	/**
	 * Wird vom Config Admin Service aufgerufen, sobald der TranslationService
	 * registriert wurde und jedesmal, wenn sich die Properties fuer den Service
	 * veraendert haben
	 * 
	 * <p>
	 * Fuer den Service muessen die zwei Properties <tt>translation.language</tt>
	 * und <tt>translation.country</tt> mit dem Sprach- bzw. Laendercode gesetzt
	 * werden.
	 */
	@SuppressWarnings("unchecked")
	public void updated(Dictionary properties) throws ConfigurationException {

		// Ggf. Default-Werte setzen
		Dictionary config = properties == null ? getDefaultConfig() : properties;

		// Setzt die Properties fuer den TranslationService
		registration.setProperties(config);

		// Locale ermitteln
		Locale locale = getLocaleFromConfiguration(config);

		// Nachrichten einlesen
		translations = ResourceBundle.getBundle("translation", locale);
	}

	@SuppressWarnings("unchecked")
	Dictionary getDefaultConfig() {
		Dictionary defaultConfig = new Hashtable();
		defaultConfig.put(TRANSLATION_LANGUAGE, "de");
		defaultConfig.put(TRANSLATION_COUNTRY, "DE");
		defaultConfig
		    .put(Constants.SERVICE_PID, TranslationService.class.getName());
		return defaultConfig;
	}

	void setServiceRegistration(ServiceRegistration registration) {
		this.registration = registration;
	}

	/**
	 * <p>
	 * Liefert das zu der Konfiguration passende Locale zur&uuml;ck.
	 * </p>
	 * 
	 * @param properties
	 * @return
	 * @throws ConfigurationException
	 */
	@SuppressWarnings("unchecked")
	private Locale getLocaleFromConfiguration(Dictionary properties)
	    throws ConfigurationException {

		// Properties auslesen...
		String language = (String) properties.get(TRANSLATION_LANGUAGE);
		String country = (String) properties.get(TRANSLATION_COUNTRY);

		// ...ueberpruefen...
		if (language == null) {
			throw new ConfigurationException(TRANSLATION_LANGUAGE,
			    "Es muss eine Sprache angegeben sein.");
		}

		if (country == null) {
			throw new ConfigurationException(TRANSLATION_COUNTRY,
			    "Es muss ein Land angegeben sein.");
		}

		// ... Locale zurueckliefern
		return new Locale(language, country);
	}
}
