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
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgibook.translation.TranslationEvent;
import org.osgibook.translation.TranslationListener;
import org.osgibook.translation.TranslationService;

public class Activator implements BundleActivator, TranslationListener {

	TranslationServiceTracker translationServiceTracker;

	public void start(BundleContext context) throws Exception {
		translationServiceTracker = new TranslationServiceTracker(context);
		translationServiceTracker.open();
	}

	public void stop(BundleContext context) throws Exception {
		translationServiceTracker.close();
	}

	public void translationOccured(TranslationEvent translationEvent) {
		System.out.println("Translated " + translationEvent.getMessage() + " to "
		    + translationEvent.getTranslatedMessage());
	}

	class TranslationServiceTracker extends ServiceTracker {
		public TranslationServiceTracker(BundleContext bundleContext) {
			super(bundleContext, TranslationService.class.getName(), null);
		}

		@Override
		public Object addingService(ServiceReference reference) {
			TranslationService translationService = (TranslationService) context
			    .getService(reference);
			if (translationService != null) {
				translationService.addTranslationListener(Activator.this);
			}
			return translationService;
		}

		@Override
		public void removedService(ServiceReference reference, Object service) {
			TranslationService translationService = (TranslationService) service;
			translationService.removeTranslationListener(Activator.this);
			context.ungetService(reference);
		}
	}
}
