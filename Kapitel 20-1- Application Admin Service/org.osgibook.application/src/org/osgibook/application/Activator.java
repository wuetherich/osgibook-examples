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
 package org.osgibook.application;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.application.ApplicationDescriptor;

/**
 * Stellt das Kommando <tt>registerApp</tt> zum registrieren beliebiger,
 * ausfuehrbarer, Anwendungen zur Verfuegung.
 * 
 */
public class Activator implements BundleActivator, CommandProvider {

	/** Der BundleContext */
	private BundleContext bundleContext;

	/** Alle registrierten Application Descriptoren */
	private List<ExternalApplicationDescriptor> descriptors = new LinkedList<ExternalApplicationDescriptor>();

	@SuppressWarnings("unchecked")
	public void start(BundleContext context) throws Exception {
		bundleContext = context;

		// Diese Activator-Instanz als CommandProvider registrieren
		context.registerService(CommandProvider.class.getName(), this, null);
	}

	public void _registerApp(CommandInterpreter commandInterpreter) {
		String command = commandInterpreter.nextArgument();

		File executable = new File(command);
		String applicationId = executable.getName();

		commandInterpreter.println("Registriere Anwendung " + applicationId);

		ExternalApplicationDescriptor descriptor = new ExternalApplicationDescriptor(
		    bundleContext, applicationId, executable);
		bundleContext.registerService(ApplicationDescriptor.class.getName(),
		    descriptor, descriptor.getServiceProperties());

		descriptors.add(descriptor);
	}

	/**
	 * Beendet beim Stoppen des Bundles alle noch laufenden Anwendungen
	 */
	public void stop(BundleContext context) throws Exception {
		// beendet alle noch laufenden Anwendungen
		for (ExternalApplicationDescriptor descriptor : descriptors) {
			descriptor.shutdown();
		}
	}

	/**
	 * Liefert den Hilfstext fuer die Equinox Konsole zurueck
	 */
	public String getHelp() {
		return "registerApp <command> - Registriert die angegebene Anwendung. Das command muss ausfuehrbar sein";
	}

}
