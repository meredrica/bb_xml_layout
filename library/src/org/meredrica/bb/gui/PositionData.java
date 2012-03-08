/**
 * Copyright (c) 2012, Florian Westreicher aka meredrica All rights reserved.
 * Copyrights licensed under the Modified BSD License. See LICENSE.txt file for terms.
 */
package org.meredrica.bb.gui;

import net.rim.device.api.ui.Field;

/**
 * Encapsulates data about positioning of a field, obtained from an XML file.
 * 
 * @author Florian Westreicher aka meredrica
 * 
 */
public final class PositionData {

	public static final int CENTER = 0;
	public static final int NORTH_WEST = 1;
	public static final int NORTH = 2;
	public static final int NORTH_EAST = 3;
	public static final int EAST = 4;
	public static final int SOUTH_EAST = 5;
	public static final int SOUTH = 6;
	public static final int SOUTH_WEST = 7;
	public static final int WEST = 8;

	private final Field field;
	private double x;
	private double y;
	private int attachPoint;
	private Field relativeField;
	private int relativeAttachPoint;
	private double maxHeight;
	private double maxWidth;
	private final String alias;

	public PositionData(final String alias, final Field field, final double x, final double y, final double maxWidth,
			final double maxHeight, final int attachPoint, final Field relativeField, final int relativeAttachPoint) {
		super();
		this.alias = alias;
		this.field = field;
		this.x = x;
		this.y = y;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		this.attachPoint = attachPoint;
		this.relativeField = relativeField;
		this.relativeAttachPoint = relativeAttachPoint;
	}

	public String toString() {
		return "PositionData field: " + field + " x: " + x + " y: " + y + " attachpoint: " + attachPoint
				+ " relativeField: " + relativeField + " relativeAttachPoint: " + relativeAttachPoint;
	}

	public String getAlias() {
		return alias;
	}

	public Field getField() {
		return field;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getAttachPoint() {
		return attachPoint;
	}

	public Field getRelativeField() {
		return relativeField;
	}

	public boolean isRelative() {
		return relativeField != null;
	}

	public int getRelativeAttachPoint() {
		return relativeAttachPoint;
	}

	public double getMaxWidth() {
		return maxWidth;
	}

	public double getMaxHeight() {
		return maxHeight;
	}

	public void setX(final double x) {
		this.x = x;
	}

	public void setY(final double y) {
		this.y = y;
	}

	public void setAttachPoint(final int attachPoint) {
		this.attachPoint = attachPoint;
	}

	public void setRelativeField(final Field relativeField) {
		this.relativeField = relativeField;
	}

	public void setRelativeAttachPoint(final int relativeAttachPoint) {
		this.relativeAttachPoint = relativeAttachPoint;
	}

	public void setMaxHeight(final double maxHeight) {
		this.maxHeight = maxHeight;
	}

	public void setMaxWidth(final double maxWidth) {
		this.maxWidth = maxWidth;
	}
}
