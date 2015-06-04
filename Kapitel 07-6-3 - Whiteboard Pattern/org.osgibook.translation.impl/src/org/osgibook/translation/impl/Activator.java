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

public class Activator implements BundleActivator {

	private ServiceRegistration serviceRegistration;

	public void start(BundleContext context) throws Exception {

		Dictionary<String, Locale> properties = new Hashtable<String, Locale>();
		properties.put(Locale.class.getName(), Locale.getDefault());

		serviceRegistration = context.registerService(TranslationService.class
		    .getName(), new TranslationServiceImpl(context, Locale.getDefault()),
		    properties);
	}

	public void stop(BundleContext context) throws Exception {
		serviceRegistration.unregister();
	}
}
