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
 package org.osgibook.translation.impl;

import java.util.Map;
import java.util.MissingResourceException;

public class MessageDictionary {
  
  private Map<String, String> entries; 
  
  public MessageDictionary() {
    super();
  }

  public String getMessage(String key) {
    if (!entries.containsKey(key)) {
      throw new MissingResourceException("Key nicht im Dictionary vorhanden.", MessageDictionary.class.getName(), key);
    }
    return entries.get(key);
  }
  
  @Override
  public String toString() {
    return "[MessageDictionary, entries: " + entries + "]";
  }
}
