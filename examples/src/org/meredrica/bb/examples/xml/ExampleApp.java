/**
 * Copyright (c) 2012, Florian Westreicher aka meredrica All rights reserved.
 * Copyrights licensed under the Modified BSD License. See LICENSE.txt file for terms.
 */
package org.meredrica.bb.examples.xml;

import net.rim.device.api.ui.UiApplication;

public class ExampleApp extends UiApplication {
	public static void main(final String[] args) {
		ExampleApp theApp = new ExampleApp();
		theApp.enterEventDispatcher();
	}

	public ExampleApp() {
		pushScreen(new ExampleXMLScreen());
	}
}
