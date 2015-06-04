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
 package org.osgibook.translation;

import java.util.Locale;

import org.osgibook.translation.impl.TranslationServiceImpl;

/**
 * <p>
 * Eine Factory, die eine Instanz eines TranslationService f&uuml;r ein
 * bestimmtes Locale zur&uuml;ckliefert.
 * </p>
 * 
 * @author Gerd Wuetherich
 * @author Nils Hartmann
 * @author Bernd Kolb
 * @author Matthias Luebken
 */
public class TranslationServiceFactory {

	private static TranslationService	translationService;

	/**
	 * <p>
	 * Gibt einen TranslationService zur&uuml;ck.
	 * </p>
	 */
	public static TranslationService getTranslationService() {

		if (translationService == null) {
			translationService = new TranslationServiceImpl(Locale.getDefault());
		}

		return translationService;
	}
}