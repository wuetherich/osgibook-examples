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
 package org.osgibook.timerservice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

/**
 * <p>
 * Die TimerServiceComponent startet einen {@link Timer}, der jede Sekunden die
 * aktuelle Uhrzeit per Event verschickt.
 * 
 * @author Gerd Wuetherich
 * @author Nils Hartmann
 * @author Bernd Kolb
 * @author Matthias Luebken
 */
public class TimerServiceComponent extends TimerTask {

	/** */
	private EventAdmin eventAdmin;

	/**
	 * Der Timer, der den {@link EventSenderTask} ausfuehrt
	 */
	private Timer timer;

	/**
	 * @param eventAdmin
	 */
	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

	/**
	 * @param eventAdmin
	 */
	public void unsetEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = null;
	}

	protected void activate(ComponentContext componentContext) {
		// Timer erzeugen
		timer = new Timer();

		// EventSenderTask ab sofort jede Sekunde sek ausfuehren
		timer.scheduleAtFixedRate(this, 0, 1000);
	}

	protected void deactivate(ComponentContext componentContext) {
		// Beim stoppen des Bundles Timer ebenfalls beenden
		timer.cancel();
	}

	/**
	 * Wird vom Timer aufgerufen und verschickt die aktuelle Systemzeit
	 */
	@Override
	public void run() {
		DateFormat format = SimpleDateFormat.getDateTimeInstance(
		    SimpleDateFormat.LONG, SimpleDateFormat.LONG);

		String time = format.format(new Date());

		// Properties fuer den Event
		Dictionary<String, String> eventProperties = new Hashtable<String, String>();
		eventProperties.put("time", time);

		// Event erzeugen
		Event event = new Event("org/osgibook/timerservice/Timer", eventProperties);

		// Event verschicken
		eventAdmin.sendEvent(event);
	}
}
