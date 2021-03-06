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
 package org.osgibook.helloworld.application;

import javax.swing.JOptionPane;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

public class HelloWorldGuiApplication implements IApplication {

	public Object start(IApplicationContext context) throws Exception {

		String translatedMsg = Activator.getTranslationService()
				.getTranslation("hello");
		String message = String.format(translatedMsg, Activator
				.getBundleSymbolicName());

		JOptionPane.showMessageDialog(null, message,
				"Hello World Gui Application", JOptionPane.INFORMATION_MESSAGE);

		return IApplication.EXIT_OK;
	}

	public void stop() {
		//
	}
}
