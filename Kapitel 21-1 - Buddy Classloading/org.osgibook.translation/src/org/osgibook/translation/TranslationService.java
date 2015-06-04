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
 * Ein TranslationService
 * 
 * <p>Der TranslationService gibt fuer einen uebergebenen Key
 * eine lokalisierte Nachricht zurueck
 * 
 * @author Gerd Wuetherich
 * @author Nils Hartmann
 * @author Bernd Kolb
 * @author Matthias Luebken
 */
public interface TranslationService {

  /**
   * Liefert die lokalisierte Nachricht fuer den uebergebenen
   * Key zurueck.
   * 
   * <p>Falls zu dem Key keine Nachricht gefunden wurde, wird
   * der Key selber als Default-Wert zurueck geliefert
   * 
   * @param key Key der Nachricht, die uebersetzt werden soll
   * @return Die uebersetzte Nachricht
   */
	public String getTranslation(String key);
}