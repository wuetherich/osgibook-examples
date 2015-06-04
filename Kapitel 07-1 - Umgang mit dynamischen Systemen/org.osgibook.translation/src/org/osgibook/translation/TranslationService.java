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

/**
 * <p>
 * Der TranslationService gibt f&uuml;r einen &uuml;bergebenen Key eine lokalisierte
 * Nachricht zur&uuml;ck.
 * </p>
 * 
 * @author Gerd Wuetherich
 * @author Nils Hartmann
 * @author Bernd Kolb
 * @author Matthias Luebken
 */
public interface TranslationService {

	/**
	 * <p>
	 * Liefert die lokalisierte Nachricht f&uuml;r den &uuml;bergebenen Key zur&uuml;ck.
	 * </p>
	 * <p>
	 * Falls zu dem Key keine Nachricht gefunden wurde, wird der Key selber als
	 * Default-Wert zur&uuml;ck geliefert.
	 * </p>
	 * 
	 * @param key
	 *          der Key der Nachricht, die &uuml;bersetzt werden soll.
	 * @return Die &uuml;bersetzte Nachricht
	 */
	public String getTranslation(String key);
}