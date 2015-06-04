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
 package org.osgibook.logwriter;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

public class LogWriterComponent {

	private LogService logService;

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	public void unsetLogService(LogService logService) {
		this.logService = null;
	}

	protected void activate(ComponentContext context) {
		logService.log(LogService.LOG_INFO, "Komponente wird aktiviert");
	}

	protected void deactivate(ComponentContext context) {
		logService.log(LogService.LOG_INFO, "Komponente wird deaktiviert");
	}
}
