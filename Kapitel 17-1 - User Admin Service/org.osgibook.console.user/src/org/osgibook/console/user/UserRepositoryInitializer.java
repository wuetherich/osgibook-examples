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

import org.osgi.service.useradmin.Group;
import org.osgi.service.useradmin.Role;
import org.osgi.service.useradmin.User;
import org.osgi.service.useradmin.UserAdmin;

public class UserRepositoryInitializer {

	private UserAdmin userAdmin;

	public UserRepositoryInitializer(UserAdmin userAdmin) {
		this.userAdmin = userAdmin;
	}

	public void initializeUserRepository() {
		Group adminGroup = createGroup("Administrators");
		Group userGroup = createGroup("Users");

		createUser("andi", "secret", userGroup);
		createUser("admin", "osgi", adminGroup);
	}

	/**
	 * Legt die Gruppen Administrator und Users an
	 * 
	 * @param userAdminService
	 */
	private Group createGroup(String name) {
		return (Group) this.userAdmin.createRole(name, Role.GROUP);
	}

	@SuppressWarnings("unchecked")
	private void createUser(String name, String password, Group group) {
		User user = (User) userAdmin.createRole(name, Role.USER);
		if (user != null) {
			user.getProperties().put("login.name", name);
			user.getCredentials().put("login.password", password);
			group.addMember(user);
		}
	}
}
