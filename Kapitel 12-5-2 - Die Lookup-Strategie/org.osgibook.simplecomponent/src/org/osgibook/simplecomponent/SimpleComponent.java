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
 package org.osgibook.simplecomponent;

import org.osgi.service.component.ComponentContext;
import org.osgibook.translation.TranslationService;

public class SimpleComponent implements SimpleService {

	private ComponentContext	componentContext;

	public void sayHello() {

		String translation = getTranslationService().getTranslation("hello");

		// Parameter ersetzen
		String message = String.format(translation, componentContext
		    .getBundleContext().getBundle().getSymbolicName());

		System.out.println(message);
	}

	protected void activate(ComponentContext context) {
		componentContext = context;
		sayHello();
	}

	protected void deactivate(ComponentContext context) {
		componentContext = null;
	}

	private TranslationService getTranslationService() {
		if (this.componentContext != null) {
			return (TranslationService) this.componentContext
			    .locateService("translationService");
		}
		return null;
	}
}
