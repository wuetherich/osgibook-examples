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

import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import org.osgibook.translation.TranslationEvent;
import org.osgibook.translation.TranslationListener;
import org.osgibook.translation.TranslationService;

/**
 * Eine TranslationService-Implementierung
 * 
 * <p>
 * Diese Implementierung liest die lokalisierten Nachrichten aus einem
 * java.util.ResourceBundle aus.
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
	private ResourceBundle greetings;

	private List<TranslationListener> listenerList = new Vector<TranslationListener>();

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
			String message = greetings.getString(key);
			fireEvent(key, message);
			return message;
		} catch (MissingResourceException e) {
			// Wenn der Key nicht bekannt ist, wird der Key selbst als Default-Wert
			// zurueckgegeben
			return key;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgibook.translation.TranslationService#addTranslationListener(org.osgibook.translation.TranslationListener)
	 */
	public synchronized void addTranslationListener(TranslationListener listener) {
		if (!listenerList.contains(listener)) {
			listenerList.add(listener);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgibook.translation.TranslationService#removeTranslationListener(org.osgibook.translation.TranslationListener)
	 */
	public synchronized void removeTranslationListener(
	    TranslationListener listener) {
		listenerList.remove(listener);
	}

	/**
	 * @param msg
	 * @param transMsg
	 */
	private void fireEvent(String msg, String transMsg) {
		TranslationListener[] listeners = listenerList
		    .toArray(new TranslationListener[0]);

		TranslationEvent translationEvent = new TranslationEvent(this, msg,
		    transMsg);

		for (int i = 0; i < listeners.length; i++) {
			try {
				listeners[i].translationOccured(translationEvent);
			} catch (Throwable throwable) {
				System.err.println("Could not deliver event to " + listeners[i]);
				throwable.printStackTrace();
			}
		}
	}
}
