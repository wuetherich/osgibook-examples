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
 package org.osgibook.helloworld.swing;

import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;

public class ActionDialog extends JDialog implements IExtensionChangeHandler {

	private static final long serialVersionUID = 1L;

	public static final String EXTENSION_POINT_ID = "org.osgibook.helloworld.swing.action";

	private ExtensionTracker tracker;

	public ActionDialog() {
		setTitle("Extension Point Example");
		getContentPane().setLayout(
		    new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		initializeExtensions();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 200);
	}

	@Override
	public void dispose() {
		tracker.unregisterHandler(this);
		super.dispose();
	}

	private void initializeExtensions() {
		IExtensionRegistry registry = RegistryFactory.getRegistry();

		IExtensionPoint extensionPoint = registry
		    .getExtensionPoint(EXTENSION_POINT_ID);
		IExtensionTracker tracker = new ExtensionTracker(registry);

		for (IExtension extension : extensionPoint.getExtensions()) {
			addExtension(tracker, extension);
		}

		// Kuenftige Installation/De-Installation von Extensions fuer unseren
		// Extension Point verfolgen
		tracker.registerHandler(this, ExtensionTracker
		    .createExtensionPointFilter(extensionPoint));
	}

	public void addExtension(IExtensionTracker tracker, IExtension extension) {
		Action action;
		try {
			action = createActionFromExtension(extension);
		} catch (CoreException ex) {
			ex.printStackTrace();
			return;
		}
		JButton button = new JButton(action);
		tracker.registerObject(extension, button, IExtensionTracker.REF_STRONG);
		getContentPane().add(button);
		pack();
		repaint();
	}

	public void removeExtension(IExtension extension, Object[] objects) {
		for (Object handle : objects) {
			JButton button = (JButton) handle;
			getContentPane().remove(button);
			pack();
			repaint();
		}
	}

	private Action createActionFromExtension(IExtension extension)
	    throws CoreException {

		IConfigurationElement actionElement = extension.getConfigurationElements()[0];
		Action action = (Action) actionElement.createExecutableExtension("class");
		String title = actionElement.getAttribute("title");
		action.putValue(Action.NAME, title);

		return action;
	}
}
