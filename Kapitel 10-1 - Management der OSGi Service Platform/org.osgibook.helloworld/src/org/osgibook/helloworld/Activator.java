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

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.osgibook.translation.TranslationService;

/**
 * Die Activator-Klasse des helloworld-Bundles.
 * 
 * <p>
 * Dieser Activator registriert einen CommandProvider in der ServiceRegistry,
 * der ein neues Kommando fuer die Equinox Konsole bereitstellt: "greet".
 * 
 * <p>
 * Die HelloWorld-Botschaft wird nicht mehr beim Start bzw. Stop des Bundles
 * ausgegeben, sondern nur dann, wenn der Anwender das "greet"-Kommando auf der
 * Equinox Konsole ausgefuehrt hat.
 * 
 * <p>
 * Der benoetigte TranslationService wird bei Bedarf ueber einen ServiceTracker
 * von der ServiceRegistry abgefragt. Er muss von einem anderen Bundle dort
 * registriert worden sein. Wenn der TranslationService nicht zur Verfuegung
 * steht, wird eine Warnung ausgegeben.
 * 
 * @author Gerd Wuetherich
 * @author Nils Hartmann
 * @author Bernd Kolb
 * @author Matthias Luebken
 */
public class Activator implements BundleActivator, CommandProvider {

	/** Der BundleContext des helloworld-Bundles */
	private BundleContext	 bundleContext;

	/** Der ServiceTracker zur Ueberwachung des TranslationService */
	private ServiceTracker	translationServiceTracker;

	/**
	 * Die start-Methode wird vom OSGi Framework beim Starten des Bundles
	 * aufgerufen
	 * 
	 * <p>
	 * Diese Methode erstellt einen ServiceTracker, um das an- und abmelden des
	 * TranslationService zu verfolgen
	 * 
	 * @param context
	 *          BundleContext mit Informationen ueber das Bundle
	 */
	public void start(BundleContext context) throws Exception {
		this.bundleContext = context;

		context.registerService(CommandProvider.class.getName(), this, null);

		translationServiceTracker = new ServiceTracker(context,
		    TranslationService.class.getName(), null);
		translationServiceTracker.open();
	}

	/**
	 * Die stop-Methode wird vom OSGi Framework vor dem Stoppen des Bundles
	 * aufgerufen
	 * 
	 * <p>
	 * Diese Methode schliesst den ServiceTracker
	 * 
	 * @param context
	 *          BundleContext mit Informationen ueber das Bundle
	 */
	public void stop(BundleContext context) throws Exception {
		translationServiceTracker.close();
	}

	/**
	 * Die Implementierung des "greet"-Kommandos: gibt eine Nachricht auf der
	 * Konsole aus, sofern der TranslationService zur Verfuegung steht.
	 * 
	 * <p>
	 * Das greet-Kommando erwartet einen optionalen Parameter: der Key der
	 * Nachricht, die uebersetzt werden soll, also "hello" oder "goodbye". Wenn
	 * kein optionaler Parameter eingegeben wurde, wird "hello" ausgegeben.
	 * 
	 * <p>
	 * Diese Methode wird von Equinox aufgerufen, wenn der Anwender "greet" auf
	 * der Equinox Konsole eingegeben hat.
	 * 
	 * @param commandInterpreter
	 *          Der CommandInterpreter, der weitere Informationen ueber das
	 *          eingegeben Kommando enthaelt
	 */
	public void _greet(CommandInterpreter commandInterpreter) {

		TranslationService translationService = (TranslationService) translationServiceTracker
		    .getService();

		if (translationService == null) {
			commandInterpreter.println("TranslationService z.Zt. nicht verfuegbar.");
			return;
		}

		// Pruefen, ob der Anwender ein zusaetzliches Argument eingegeben hat
		String key = commandInterpreter.nextArgument();
		if (key == null) {
			// Wenn keine Nachricht eingegeben wurde, hello ausgeben
			key = "hello";
		}

		// Lokalisierte Nachricht zu uebergebenem Key suchen
		String translatedMsg = translationService.getTranslation(key);

		// Parameter ersetzen
		String message = String.format(translatedMsg, bundleContext.getBundle()
		    .getSymbolicName());

		// Komplette Nachricht auf der Konsole ausgeben
		commandInterpreter.println(message);
	}

	/**
	 * Die Methode getHelp() wird von Equinox aufgerufen, wenn der Anwender "help"
	 * auf der Konsole eingegeben hat.
	 * 
	 * <p>
	 * Die Methode muss einen String zurueckliefern, der dann von Equinox
	 * angezeigt wird. Der String sollte Erlaeuterungen zu den Kommandos
	 * enthalten, die dieser CommandProvider zur Verfuegung stellt.
	 * 
	 * @return Ein String mit dem Hilfe-Text
	 */
	public String getHelp() {
		StringBuilder help = new StringBuilder();
		help.append("\n--- Hello World Commands ---");
		help.append("\n\tgreet [hello|goodbye] - ");
		help.append("display the \"Hello World !\" message");
		return help.toString();
	}
}