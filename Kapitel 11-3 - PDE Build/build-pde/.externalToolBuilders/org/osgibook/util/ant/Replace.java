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
 package org.osgibook.util.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class Replace extends Task {

	private String	_string;

	private String	_resultProperty;

	private char	 _oldChar;

	private char	 _newChar;

	@Override
	public void execute() throws BuildException {
		String result = _string.replace(_oldChar, _newChar);
		getProject().setProperty(_resultProperty, result);
	}

	public void setString(String string) {
  	_string = string;
  }

	public void setResultProperty(String resultProperty) {
  	_resultProperty = resultProperty;
  }

	public void setOldChar(char oldChar) {
  	_oldChar = oldChar;
  }

	public void setNewChar(char newChar) {
  	_newChar = newChar;
  }

}
