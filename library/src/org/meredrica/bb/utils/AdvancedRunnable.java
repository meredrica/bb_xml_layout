/**
 * Copyright (c) 2012, Florian Westreicher aka meredrica All rights reserved.
 * Copyrights licensed under the Modified BSD License. See LICENSE.txt file for terms.
 */
package org.meredrica.bb.utils;

import net.rim.device.api.ui.UiApplication;

/**
 * @author Florian Westreicher aka meredrica
 * 
 */
public abstract class AdvancedRunnable implements Runnable {

	public void invokeLater() {
		UiApplication.getUiApplication().invokeLater(this);
	}

}
