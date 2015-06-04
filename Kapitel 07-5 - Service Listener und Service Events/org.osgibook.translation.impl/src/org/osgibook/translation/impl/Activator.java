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

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgibook.translation.TranslationService;

/**
 * <p>
 * Die Aktivator-Klasse des translation-Bundles. Dieser Aktivator instantiiert
 * einen TranslationService und meldet ihn in der OSGi ServiceRegistry an.
 * </p>
 * <p>
 * Beim Beenden des Bundles wird der TranslationService aus der Registry
 * entfernt.
 * </p>
 * 
 * @author Gerd Wuetherich
 * @author Nils Hartmann
 * @author Bernd Kolb
 * @author Matthias Luebken
 */
public class Activator implements BundleActivator {

	/**
	 * <p>
	 * Instantiiert beim Starten des Bundles einen TranslationService und meldet
	 * ihn in der OSGi Service Registry an.
	 * </p>
	 * 
	 * @param context
	 *          der BundleContext
	 */
	public void start(BundleContext context) throws Exception {
		Locale locale = Locale.getDefault();

		// Properties, die den Service beschreiben
		Dictionary<String, String> properties = new Hashtable<String, String>();
		properties.put(Locale.class.getName(), locale.toString());

		// Service anmelden
		context.registerService(TranslationService.class.getName(),
		    new TranslationServiceImpl(locale), properties);
	}

	/**
	 * <p>
	 * Entfernt den registrierten TranslationService beim Beenden des Bundles aus
	 * der ServiceRegistry.
	 * </p>
	 * 
	 * @param context
	 *          der BundleContext
	 */
	public void stop(BundleContext context) throws Exception {
		// Deregistrierung erfolgt automatisch
	}
}
