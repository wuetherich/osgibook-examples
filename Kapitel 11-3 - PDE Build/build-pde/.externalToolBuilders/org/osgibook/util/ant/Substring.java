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

/**
 * @author Wuetherich-extern
 */
/**
 * @author Wuetherich-extern
 * 
 */
public class Substring extends Task {

	private String	_string;

	private int	   _beginIndex	= -1;

	private int	   _endIndex	 = -1;

	private String	_resultProperty;

	/**
	 * @param string
	 */
	public void setString(String string) {
		_string = string;
	}

	/**
	 * @param beginIndex
	 */
	public void setBeginIndex(int beginIndex) {
		_beginIndex = beginIndex;
	}

	/**
	 * @param endIndex
	 */
	public void setEndIndex(int endIndex) {
		_endIndex = endIndex;
	}

	/**
	 * @param resultProperty
	 */
	public void setResultProperty(String resultProperty) {
		_resultProperty = resultProperty;
	}

	@Override
	public void execute() throws BuildException {
		//
		String result = _string.substring(_beginIndex);
		getProject().setProperty(_resultProperty, result);
	}
}
