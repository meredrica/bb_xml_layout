/**
 * Copyright (c) 2012, Florian Westreicher aka meredrica All rights reserved.
 * Copyrights licensed under the Modified BSD License. See LICENSE.txt file for terms.
 */
package org.meredrica.bb.examples.xml;

import java.util.Hashtable;

import net.rim.device.api.ui.Color;

import org.meredrica.bb.gui.XMLScreen;

/**
 * A class demonstrating a small example how to use the XML screen class with
 * relative layout.
 */
public final class RelativeLayoutScreen extends XMLScreen {
	public RelativeLayoutScreen() {
		super("relativelayout.xml");
		super.setAliases(getAliases());

	}

	private Hashtable getAliases() {
		Hashtable ht = new Hashtable();

		ht.put("anchor", new TransparentSquare(Color.GREEN, 0.1, 0.3));
		ht.put("bottom.left", new TransparentSquare(Color.ORANGE, 0.3, 0.12));
		ht.put("overlapping.bottom", new TransparentSquare(Color.BLUE, 0.075, 0.4));
		ht.put("relative.to.relative", new TransparentSquare(Color.RED, 0.3, 0.2));
		return ht;
	}
}
