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
 package org.osgibook.eventclient;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgibook.translation.TranslationEvent;
import org.osgibook.translation.TranslationListener;

public class Activator implements BundleActivator {

	public void start(BundleContext context) throws Exception {
		TranslationListener translationListener = new TranslationListener() {
			public void translationOccured(TranslationEvent translationEvent) {
				System.out.println("Translated " + translationEvent.getMessage()
				    + " to " + translationEvent.getTranslatedMessage());
			}
		};

		context.registerService(TranslationListener.class.getName(),
		    translationListener, null);
	}

	public void stop(BundleContext context) throws Exception {
	}
}