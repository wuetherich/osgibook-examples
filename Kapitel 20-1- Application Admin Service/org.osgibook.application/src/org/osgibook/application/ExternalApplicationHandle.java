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

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.ServiceRegistration;
import org.osgi.service.application.ApplicationHandle;

/**
 * Repraensentiert eine <b>laufende</b> Instanz einer externen Anwendung
 */
public class ExternalApplicationHandle extends ApplicationHandle implements
    Runnable {
	/** Zaehler, um eindeutige Instanz-IDs zu generieren */
	static int INSTANCE = 0;

	/**
	 * Die Registration, unter der der Handle in der ServiceRegistration
	 * angemeldet ist
	 */
	private ServiceRegistration serviceRegistration;

	/**
	 * Der {@link Process} der Anwendung
	 */
	private Process process;

	/** Der Thread, der die laufende Anwendung ueberwacht */
	private Thread thread;

	/** Der aktuelle Zustand der Anwendung */
	private String state = RUNNING;

	/**
	 * Erzeugt einen neuen Handle und startet die Anwendung
	 * 
	 * @param descriptor
	 * @throws IOException
	 */
	protected ExternalApplicationHandle(ExternalApplicationDescriptor descriptor)
	    throws IOException {
		super(descriptor.getApplicationId() + ":" + INSTANCE++, descriptor);

		// Anwendung starten
		String command = descriptor.getExecutable().getPath();
		System.out.println("Executing command: " + command);
		process = Runtime.getRuntime().exec(command);

		// Anwendung in eigenen Thread ueberwachen
		thread = new Thread(this, getInstanceId());
		thread.start();
	}

	/**
	 * Stoppt die Anwendung
	 */
	@Override
	protected void destroySpecific() {
		state = STOPPING;

		// Properties aktualisieren
		serviceRegistration.setProperties(getServiceProperties());

		// Anwendung beenden
		thread.interrupt();
	}

	@Override
	public String getState() {
		return state;
	}

	/**
	 * Implementierung des {@link Runnable}-Interfaces.
	 * 
	 * <p>
	 * In dieser Methode wartet der Thread auf das Ende der Anwendung
	 */
	public void run() {
		try {
			process.waitFor();
			// Anwendung wurde beendet: Handle schliessen
			destroy();
		} catch (InterruptedException ie) {
			// Anwendung wurde durch Application Admin
			// beendet -> Thread und Handle schliessen
			process.destroy();
			try {
				process.waitFor();
			} catch (InterruptedException iee) {
			}
		} catch (Exception e) {
		}

		// Von der ServiceRegistry abmelden
		serviceRegistration.unregister();
	}

	/**
	 * Liefert die Properties, die fuer diesen ApplicationHandle in dessen
	 * ServiceRegistration eingetragen werden sollen
	 * 
	 * @return Die ServiceProperties
	 */
	Dictionary<String, String> getServiceProperties() {
		Hashtable<String, String> p = new Hashtable<String, String>();
		p.put(APPLICATION_PID, getInstanceId());
		p.put(APPLICATION_STATE, getState());
		p
		    .put(APPLICATION_DESCRIPTOR, getApplicationDescriptor()
		        .getApplicationId());
		return p;
	}

	/**
	 * Setzt die ServiceRegistration, unter der dieser Handle in der
	 * ServiceRegistry angemeldet wurde
	 * 
	 * @param serviceRegistration
	 */
	void setServiceRegistration(ServiceRegistration serviceRegistration) {
		this.serviceRegistration = serviceRegistration;
	}

}
