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
 package org.osgibook.simplecomponent;

import org.osgi.service.component.ComponentContext;

public class SimpleComponent implements SimpleService {

	public void sayHello() {
		System.out.println("hello");
	}

	protected void activate(ComponentContext context) {
		System.out.println("activate");
	}

	protected void deactivate(ComponentContext context) {
		System.out.println("deactivate");
	}
}
