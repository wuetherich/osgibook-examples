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
import org.osgi.framework.ServiceRegistration;
import org.osgibook.translation.TranslationService;

/**
 * Die Aktivator-Klasse des translation-Bundles.
 * 
 * <p>
 * Dieser Aktivator instantiiert einen TranslationService und meldet ihn in der
 * OSGi ServiceRegistry an.
 * <p>
 * Beim Beenden des Bundles wird der TranslationService aus der Registry
 * entfernt.
 * 
 * @author Gerd Wuetherich
 * @author Nils Hartmann
 * @author Bernd Kolb
 * @author Matthias Luebken
 */
public class Activator implements BundleActivator {

	/**
	 * Die ServiceRegistration, unter der der TranslationService registriert wurde
	 */
	private ServiceRegistration serviceRegistration;

	/**
	 * Instantiiert beim Starten des Bundles einen TranslationService und meldet
	 * ihn in der OSGi Service Registry an
	 */
	public void start(BundleContext context) throws Exception {
		// Properties, die den Service beschreiben
		Dictionary<String, Object> properties = new Hashtable<String, Object>();
		properties.put(Locale.class.getName(), Locale.getDefault());

		// Service anmelden
		serviceRegistration = context
		    .registerService(TranslationService.class.getName(),
		        new TranslationServiceImpl(Locale.getDefault()), properties);
	}

	/**
	 * Entfernt den registrierten TranslationService beim Beenden des Bundles aus
	 * der ServiceRegistry
	 */
	public void stop(BundleContext context) throws Exception {
		serviceRegistration.unregister();
	}
}
