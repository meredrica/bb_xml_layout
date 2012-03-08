/**
 * Copyright (c) 2012, Florian Westreicher aka meredrica All rights reserved.
 * Copyrights licensed under the Modified BSD License. See LICENSE.txt file for terms.
 */
package org.meredrica.bb.examples.xml;

import java.util.Hashtable;

import net.rim.device.api.ui.component.LabelField;

import org.meredrica.bb.gui.XMLScreen;

/**
 * A class demonstrating a small example how to use the XML screen class.
 */
public final class ExampleXMLScreen extends XMLScreen {
	public ExampleXMLScreen() {
		super("examplexml.xml");
		super.setAliases(getAliases());

	}

	private Hashtable getAliases() {
		Hashtable ht = new Hashtable();
		ht.put("top.left", new LabelField("top left"));
		ht.put("top", new LabelField("top"));
		ht.put("top.right", new LabelField("top.right"));
		ht.put("right", new LabelField("right"));
		ht.put("bottom.right", new LabelField("bottom.right"));
		ht.put("bottom", new LabelField("bottom"));
		ht.put("bottom.left", new LabelField("bottom.left"));
		ht.put("left", new LabelField("left"));
		ht.put("center", new ExampleButton("next demo", new RelativeLayoutScreen()));
		return ht;
	}
}
