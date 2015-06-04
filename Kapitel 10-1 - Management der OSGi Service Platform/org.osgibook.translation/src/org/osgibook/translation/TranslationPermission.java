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

import java.security.BasicPermission;

/**
 * Eine Permission, die Clients besitzen muessen, um den TranslationServiceImpl
 * benutzen zu duerfen.
 * 
 * @author Gerd Wuetherich
 * @author Nils Hartmann
 * @author Bernd Kolb
 * @author Matthias Luebken
 */
public final class TranslationPermission extends BasicPermission {

	/** serialVersionUID, da Permissions Serializable sind */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Erfoderlicher Konstruktor, der vom Java Security Framework verwendet wird,
	 * um eine Instanz dieser Permission zu erstellen
	 * 
	 * @param name
	 *          Der Name der Permission ("translate")
	 */
	public TranslationPermission(String name) {
		super(name);
	}
}
