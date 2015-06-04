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
 package org.osgibook.helloworld;

import org.osgi.service.component.ComponentContext;
import org.osgibook.translation.TranslationService;

/**
 * Eine Komponente, die bei ihrer Aktivierung und Deaktivierung eine Botschaft
 * ausgibt.
 * 
 * @author Gerd Wuetherich
 * @author Nils Hartmann
 * @author Bernd Kolb
 * @author Matthias Luebken
 */
public class HelloWorldComponent {

	/**
	 * Der TranslationService, der zum Uebersetzen der Gruss-Botschaft genutzt
	 * wird
	 */
	private TranslationService translationService;

	/**
	 * Der ComponentContext
	 */
	private ComponentContext componentContext;

	/**
	 * Wird von der Service Component Runtime aufgerufen, sobald ein
	 * TranslationService verfuegbar ist
	 * 
	 * @param translationService
	 *          Der verfuegbare TranslationService
	 */
	protected void setTranslationService(TranslationService translationService) {
		this.translationService = translationService;
	}

	/**
	 * Wird von der Service Component Runtime aufgerufen, sobald der
	 * TranslationService nicht mehr zur Verfuegung steht
	 * 
	 * @param translationService
	 *          TranslationService, der nicht laenger zur Verfuegung steht
	 */
	protected void unsetTranslationService(TranslationService translationService) {
		this.translationService = null;
	}

	/**
	 * Wird von der Service Component Runtime bei der Aktivierung der Komponente
	 * aufgerufen und gibt einen Gruss auf der Konsole aus
	 * 
	 * @param componentContext
	 *          Der Kontext der Komponente
	 */
	protected void activate(ComponentContext componentContext) {
		this.componentContext = componentContext;

		greet("hello");
	}

	/**
	 * Wird von der Service Component Runtime vor der Deaktivierung der Komponente
	 * aufgerufen und gibt noch eine Abschiedsbotschaft aus
	 * 
	 * @param componentContext
	 *          Der Kontext der Komponente
	 */
	protected void deactivate(ComponentContext componentContext) {
		greet("goodbye");

		this.componentContext = null;
	}

	private void greet(String key) {
		String translatedMsg = translationService.getTranslation(key);

		// Parameter ersetzen
		String bundleName = componentContext.getBundleContext().getBundle()
		    .getSymbolicName();

		String message = String.format(translatedMsg, bundleName);

		System.out.println(message);
	}

}
