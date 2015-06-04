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

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.osgibook.translation.TranslationEvent;
import org.osgibook.translation.TranslationListener;
import org.osgibook.translation.TranslationService;

public class TranslationServiceImpl implements TranslationService {

	private BundleContext bundleContext;

	private ResourceBundle greetings;

	public TranslationServiceImpl(BundleContext context, Locale locale) {
		bundleContext = context;

		// ACHTUNG: das ResourceBundle heisst zwar 'Bundle',
		// hat aber nichts mit den OSGi Bundles zu tun!
		try {
			greetings = ResourceBundle.getBundle("translation", locale);
		} catch (MissingResourceException e) {
			greetings = ResourceBundle.getBundle("translation",
			    new Locale("de", "DE"));
		}
	}

	public String getTranslation(String message) {

		String translatedMessage = message;

		try {
			translatedMessage = greetings.getString(message);
		} catch (MissingResourceException e) {
		}

		fireEvent(message, translatedMessage);

		return translatedMessage;
	}

	private void fireEvent(String message, String translatedMessage) {

		ServiceTracker listenerTracker = new ServiceTracker(bundleContext,
		    TranslationListener.class.getName(), null);
		listenerTracker.open();

		try {
			TranslationEvent translationEvent = new TranslationEvent(this, message,
			    translatedMessage);

			Object[] services = listenerTracker.getServices();
			for (Object service : services) {
				TranslationListener translationListener = (TranslationListener) service;
				translationListener.translationOccured(translationEvent);
			}
		} finally {
			listenerTracker.close();
		}
	}
}
