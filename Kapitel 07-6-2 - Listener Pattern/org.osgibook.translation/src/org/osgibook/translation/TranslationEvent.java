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

import java.util.EventObject;

public class TranslationEvent extends EventObject {

	/** serialVersionUID */
	private static final long serialVersionUID = 3105670163148123878L;

	private String message;

	private String translatedMessage;

	public TranslationEvent(Object source, String message,
	    String translatedMessage) {
		super(source);

		this.message = message;
		this.translatedMessage = translatedMessage;
	}

	public String getMessage() {
		return message;
	}

	public String getTranslatedMessage() {
		return translatedMessage;
	}
}
