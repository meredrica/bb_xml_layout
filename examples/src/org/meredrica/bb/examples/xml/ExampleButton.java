/**
 * Copyright (c) 2012, Florian Westreicher aka meredrica All rights reserved.
 * Copyrights licensed under the Modified BSD License. See LICENSE.txt file for terms.
 */
package org.meredrica.bb.examples.xml;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;

import org.meredrica.bb.gui.XMLScreen;

public class ExampleButton extends ButtonField {

	public ExampleButton(final String label, final XMLScreen toLaunch) {
		super(label);
		setRunnable(new Runnable() {
			public void run() {
				UiApplication.getUiApplication().pushScreen(toLaunch);
			}
		});
	}

	public int getPreferredWidth() {
		return getFont().getAdvance(getLabel()) * 2;
	};

	public int getPreferredHeight() {
		return getFont().getHeight() * 2;
	}
}
