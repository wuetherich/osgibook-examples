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
 package org.osgibook.console.cm;

import java.util.Properties;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Ein CommandProvider, der ein Equinox Kommando zur Verwendung des Config Admin
 * Service bereitstellt
 * 
 * @author Gerd Wuetherich
 * @author Nils Hartmann
 * @author Bernd Kolb
 * @author Matthias Luebken
 */
public class Activator implements BundleActivator, CommandProvider {

	/** ServiceTracker zur Verfolgung des Config Admin Service */
	private ServiceTracker configurationAdminTracker;

	/**
	 * Oeffnet den ServiceTracker fuer den Config Admin Service und registriert
	 * den CommandProvider in der ServiceRegistry
	 */
	public void start(BundleContext context) throws Exception {

		// Tracker anlegen und oeffnen
		configurationAdminTracker = new ServiceTracker(context,
		    ConfigurationAdmin.class.getName(), null);
		configurationAdminTracker.open();

		// Diese Activator-Instanz als CommandProvider registrieren
		context.registerService(CommandProvider.class.getName(), this, null);
	}

	/**
	 * Schliesst beim Beenden des Bundles den ServiceTracker
	 */
	public void stop(BundleContext context) throws Exception {
		configurationAdminTracker.close();
	}

	/**
	 * Implementierung des <tt>configure</tt>-Kommandos
	 * 
	 * <p>
	 * <b>Syntax:</b>
	 * 
	 * <pre>
	 * configure &lt;pid&gt; (&lt;key&gt;=&lt;value&gt;)+
	 * </pre>
	 * 
	 * @param commandInterpreter
	 *          Der CommandInterpreter zur Interaktion mit der Equinox Console
	 */
	public void _configure(CommandInterpreter commandInterpreter) {

		ConfigurationAdmin configurationAdmin = (ConfigurationAdmin) configurationAdminTracker
		    .getService();

		if (configurationAdmin == null) {
			commandInterpreter.println("Service ConfigurationAdmin not available!");
			return;
		}

		// Kommandozeile parsen
		String pid = commandInterpreter.nextArgument();

		if (pid == null) {
			commandInterpreter.print("Usage: " + getHelp());
			return;
		}

		// Key-Value-Paare von der Kommandozeile lesen
		Properties properties = new Properties();
		String property = commandInterpreter.nextArgument();
		while (property != null) {
			String[] keyValue = property.split("=");
			properties.put(keyValue[0], keyValue[1]);
			property = commandInterpreter.nextArgument();
		}

		// Service mit Properties konfigurieren
		try {
			Configuration configuration = configurationAdmin.getConfiguration(pid,
			    null);
			configuration.update(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gibt den Hilfetext fuer das configure-Kommando zurueck
	 */
	public String getHelp() {
		return "configure <pid> (<key>=<value>)+";
	}
}
