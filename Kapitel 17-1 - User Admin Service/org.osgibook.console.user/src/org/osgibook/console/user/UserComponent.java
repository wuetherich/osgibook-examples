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
 package org.osgibook.console.user;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.useradmin.Authorization;
import org.osgi.service.useradmin.Group;
import org.osgi.service.useradmin.Role;
import org.osgi.service.useradmin.User;
import org.osgi.service.useradmin.UserAdmin;

public class UserComponent implements CommandProvider {

	private UserAdmin userAdmin;

	/** Der z.Zt. eingeloggte Benutzers oder null */
	private User currentUser;

	protected void setUserAdmin(UserAdmin userAdmin) {
		this.userAdmin = userAdmin;
	}

	protected void unsetUserAdmin(UserAdmin userAdmin) {
		this.userAdmin = null;
	}

	protected void activate(ComponentContext componentContext) {
		new UserRepositoryInitializer(userAdmin).initializeUserRepository();
	}

	/**
	 * Liefert den Hilfe-Text fuer die Equinox Konsole zurueck
	 */
	public String getHelp() {
		StringBuilder help = new StringBuilder();
		help.append("login <username> <password> - Anmelden\n").append(
		    "listgroups - Alle Gruppen ausgeben");
		return help.toString();
	}

	/**
	 * Kommando zum Einloggen in das System.
	 * 
	 * <p>
	 * <b>Format:</b>
	 * 
	 * <pre>
	 * login &lt;username&gt; &lt;password&gt;
	 * </pre>
	 * 
	 * @param commandInterpreter
	 */
	public void _login(CommandInterpreter commandInterpreter) {
		// Aktuellen Benutzer "ausloggen"
		currentUser = null;

		// Username und Password von der Konsole auslesen
		String userName = commandInterpreter.nextArgument();
		String password = commandInterpreter.nextArgument();

		if (userName == null || password == null) {
			commandInterpreter.println(getHelp());
			return;
		}

		// User ermitteln
		User user = userAdmin.getUser("login.name", userName);
		if (user == null) {
			commandInterpreter.println("User '" + userName + "' unbekannt!");
			return;
		}

		// Passwort ueberpruefen
		if (!user.hasCredential("login.password", password)) {
			commandInterpreter.println("Falsches Passwort!");
			return;
		}

		// Aktuellen Benutzer merken
		currentUser = user;
		commandInterpreter.println("User " + userName + " eingelogged");
	}

	/**
	 * Kommando zum Ausgeben aller vorhanden Rollen
	 * 
	 * <p>
	 * <b>Format:</b>
	 * 
	 * <pre>
	 * listroles
	 * </pre>
	 * 
	 * @param commandInterpreter
	 */
	public void _listroles(CommandInterpreter commandInterpreter) {

		// Role ueberpruefen
		if (!isAuthorized("Administrators")) {
			commandInterpreter.println("Fehlende Autorisierung.");
			return;
		}

		try {
			Role[] roles = userAdmin.getRoles(null);
			for (int i = 0; i < roles.length; i++) {
				printRole(commandInterpreter, roles[i]);
			}
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
	}

	private boolean isAuthorized(String requiredRole) {
		if (currentUser == null) {
			return false;
		}
		Authorization authorization = userAdmin.getAuthorization(currentUser);
		return authorization.hasRole(requiredRole);
	}

	/**
	 * Gibt eine Rolle ueber den uebergebenen CommandInterpreter aus
	 * 
	 * @param commandInterpreter
	 * @param role
	 */
	private void printRole(CommandInterpreter commandInterpreter, Role role) {

		StringBuilder buffer = new StringBuilder();

		switch (role.getType()) {
		case Group.USER: {
			buffer.append("User :");
			buffer.append(role.getName());
			buffer.append("\n  Properties: ");
			buffer.append(role.getProperties());
			break;
		}
		case Group.GROUP: {
			buffer.append("Group:");
			buffer.append(role.getName());
			buffer.append("\n  Properties: ");
			buffer.append(role.getProperties());
			buffer.append("\n  Members: [");
			Role members[] = ((Group) role).getMembers();
			for (Role member : members) {
				buffer.append(member.getName());
			}
			buffer.append("]");
			break;
		}
		}
		commandInterpreter.println(buffer.toString());
	}
}
