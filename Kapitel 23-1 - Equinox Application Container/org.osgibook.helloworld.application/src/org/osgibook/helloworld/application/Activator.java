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
 package org.osgibook.helloworld.application;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.osgibook.translation.TranslationService;

public class Activator implements BundleActivator {

	/** Der ServiceTracker zur Ueberwachung des TranslationService */
	private static ServiceTracker translationServiceTracker;

	private static Bundle bundle;

	/**
	 * Die start-Methode wird vom OSGi Framework beim Starten des Bundles
	 * aufgerufen
	 * 
	 * <p>
	 * Diese Methode erstellt einen ServiceTracker, um das an- und abmelden des
	 * TranslationService zu verfolgen
	 * 
	 * @param context
	 *            BundleContext mit Informationen ueber das Bundle
	 */
	public void start(BundleContext context) throws Exception {
		bundle = context.getBundle();

		// ServiceTracker erstellen
		translationServiceTracker = new ServiceTracker(context,
				TranslationService.class.getName(), null);

		// ServiceTracker oeffnen, um mit der Ueberwachung zu beginnen
		translationServiceTracker.open();
	}

	/**
	 * Die stop-Methode wird vom OSGi Framework vor dem Stoppen des Bundles
	 * aufgerufen
	 * 
	 * <p>
	 * Diese Methode schliesst den ServiceTracker
	 * 
	 * @param context
	 *            BundleContext mit Informationen ueber das Bundle
	 */
	public void stop(BundleContext context) throws Exception {
		translationServiceTracker.close();
	}

	public static String getBundleSymbolicName() {
		return bundle.getSymbolicName();
	}

	public static TranslationService getTranslationService() {
		return (TranslationService) translationServiceTracker.getService();
	}
}
