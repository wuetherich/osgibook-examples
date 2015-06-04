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

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedService;
import org.osgibook.translation.TranslationService;

/**
 * Die Aktivator-Klasse des translation-Bundles.
 * 
 * <p>
 * Dieser Aktivator instantiiert einen TranslationService und meldet ihn in der
 * OSGi ServiceRegistry an.
 * <p>
 * 
 * @author Gerd Wuetherich
 * @author Nils Hartmann
 * @author Bernd Kolb
 * @author Matthias Luebken
 */
public class Activator implements BundleActivator {

	/**
	 * Instantiiert beim Starten des Bundles einen TranslationService und meldet
	 * ihn in der OSGi Service Registry an.
	 * 
	 * <p>
	 * Der Service wird auch unter dem ManagedService-Interface registriert, um
	 * ihn fuer den Config Admin Service verfuegbar zu machen.
	 */
	public void start(BundleContext context) throws Exception {

		// Service erzeugen
		TranslationServiceImpl translationService = new TranslationServiceImpl();

		// Klassennamen
		String[] clazzes = new String[] { TranslationService.class.getName(),
		    ManagedService.class.getName() };

		// Service anmelden
		ServiceRegistration registration = context.registerService(clazzes,
		    translationService, translationService.getDefaultConfig());

		translationService.setServiceRegistration(registration);
	}

	/**
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}
}
