/**
 * Copyright (c) 2012, Florian Westreicher aka meredrica All rights reserved.
 * Copyrights licensed under the Modified BSD License. See LICENSE.txt file for terms.
 */
package org.meredrica.bb.examples.xml;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;

/**
 * A very simple transparent field that does absolutely nothing.
 * 
 * @author Florian Westreicher aka meredrica
 * 
 */
public class TransparentSquare extends Field {

	private final double height;
	private final double width;
	private final int color;

	public TransparentSquare(final int color, final double width, final double height) {
		this.color = color;
		this.width = width;
		this.height = height;
	}

	protected void layout(final int width, final int height) {
		setExtent(Math.min(getPreferredWidth(), width), Math.min(getPreferredHeight(), height));
	}

	public int getPreferredHeight() {
		return (int) (Display.getHeight() * height);
	}

	public int getPreferredWidth() {
		return (int) (Display.getWidth() * width);
	}

	protected void paint(final Graphics g) {
		int alpha = g.getGlobalAlpha();
		g.setGlobalAlpha(100);
		g.setColor(color);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setGlobalAlpha(alpha);
	}
}
