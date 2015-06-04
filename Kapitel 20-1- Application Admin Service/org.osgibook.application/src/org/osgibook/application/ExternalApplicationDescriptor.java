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
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.application.ApplicationDescriptor;
import org.osgi.service.application.ApplicationHandle;

public class ExternalApplicationDescriptor extends ApplicationDescriptor {

	/**
	 * Der BundleContext, der zum Registrieren der ApplicationHandles verwendet
	 * wird
	 */
	private BundleContext bundleContext;

	/**
	 * Die ServiceRegistration dieses Descriptors
	 */
	private ServiceRegistration descriptorServiceRegistration;

	/** Die Datei, die fuer diese Anwendung gestartet werden soll */
	private File executable;

	/** Gibt an, ob die Anwendung z.Zt. gesperrt ist */
	private boolean locked;

	/**
	 * Erzeugt einen neuen ExternalApplicationDescriptor
	 * 
	 * @param applicationId
	 *          Die eindeutige Application-Id
	 * @param executable
	 *          Die ausfuehrbare Datei
	 */
	protected ExternalApplicationDescriptor(BundleContext bundleContext,
	    String applicationId, File executable) {
		super(applicationId);
		this.bundleContext = bundleContext;
		this.executable = executable;
	}

	/**
	 * Fuehrt die Anwendung aus, die dieser Deskriptor beschreibt
	 * 
	 * @param arguments
	 *          Zusaetzliche Argumente fuer die Anwendung (wird von unserer
	 *          Implementierung nicht verwendet)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected ApplicationHandle launchSpecific(Map arguments) throws Exception {
		// Handle erzeugen, fuehrt zum Starten der Anwendung
		ExternalApplicationHandle handle = new ExternalApplicationHandle(this);

		// Handle registrieren
		ServiceRegistration serviceRegistration = bundleContext.registerService(
		    ApplicationHandle.class.getName(), handle, handle
		        .getServiceProperties());

		// Eigene ServiceRegistration dem Handle bekanntgeben
		handle.setServiceRegistration(serviceRegistration);
		return handle;
	}

	/**
	 * Gibt die Properties fuer diesen ApplicationDescriptor zurueck
	 * 
	 * @locale Locale, mit dem die Properties lokalisiert werden sollen
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getPropertiesSpecific(String locale) {
		// Unser ApplicationDescriptor unterstuetzt keine Locales
		return getServiceProperties();
	}

	@Override
	protected boolean isLaunchableSpecific() {
		// return (executable.isFile());
		return true;
	}

	/**
	 * Sperrt die Anwendung.
	 * 
	 * <p>
	 * Gesperrte Anwendungen koennen nicht gestartet werden
	 */
	@Override
	protected void lockSpecific() {
		locked = true;
		refreshServiceProperties();
	}

	@Override
	public boolean matchDNChain(String pattern) {
		return false;
	}

	/**
	 * Gibt die Anwendung wieder frei, falls sie gesperrt war
	 * 
	 * @see #lockSpecific()
	 */
	@Override
	protected void unlockSpecific() {
		locked = false;
		refreshServiceProperties();
	}

	/**
	 * Liefert die Properties zurueck, die die ServiceRegistration dieses
	 * Descriptors erhalten soll
	 * 
	 * @return Service Properties
	 */
	Hashtable<String, Object> getServiceProperties() {
		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.put(APPLICATION_NAME, executable.getName());
		properties.put(APPLICATION_VISIBLE, "true");
		properties.put(APPLICATION_LAUNCHABLE, isLaunchableSpecific());
		properties.put(APPLICATION_LOCKED, locked);
		properties.put(APPLICATION_CONTAINER,
		    "org.osgibook.externalapplication.container");
		properties.put("service.pid", getApplicationId());
		return properties;
	}

	/**
	 * Aktualisiert die Properties der ServiceRegistration
	 */
	private void refreshServiceProperties() {
		if (descriptorServiceRegistration != null) {
			descriptorServiceRegistration.setProperties(getServiceProperties());
		}
	}

	/**
	 * Liefert die ausfuehrbare Datei dieser Anwendung zurueck
	 * 
	 * @return
	 */
	File getExecutable() {
		return this.executable;
	}

	/**
	 * Beendet alle noch laufenden Instanzen dieser Anwendung
	 */
	public void shutdown() throws Exception {
		ServiceReference[] references = bundleContext.getServiceReferences(
		    ApplicationHandle.class.getName(),
		    "(&(application.state=RUNNING)(application.descriptor="
		        + getApplicationId() + "))");

		if (references != null) {
			for (ServiceReference reference : references) {
				ApplicationHandle handle = (ApplicationHandle) bundleContext
				    .getService(reference);
				if (handle != null) {
					System.out.println("Stoppe Instanz " + handle.getInstanceId());
					handle.destroy();
				}
			}
		}
	}
}
