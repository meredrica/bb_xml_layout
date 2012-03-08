/**
 * Copyright (c) 2012, Florian Westreicher aka meredrica All rights reserved.
 * Copyrights licensed under the Modified BSD License. See LICENSE.txt file for terms.
 */
package org.meredrica.bb.gui;

import java.util.Vector;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.XYPoint;

import org.meredrica.bb.utils.Logger;

/**
 * A manager that can position fields based on XML.
 * 
 * @author Florian Westreicher aka meredrica
 * 
 */
public class PositionManager extends Manager {

	// FIXME: general exception handling

	// TODO: scrollable fields
	// TODO: support absolute and relative layout (max of height, width etc)
	protected Vector datas;

	private static final Logger log = Logger.getLogger(PositionManager.class);
	static {
		// enabling logging for this class will be VERY bad for performance, so
		// let's rather not do it if we dont need it
		log.setLocalMask(Logger.FATAL);
	}

	/**
	 * @param datas
	 *          A Vector containing {@link PositionData}s.
	 */
	public PositionManager(final Vector datas) {
		super(Manager.HORIZONTAL_SCROLL | Manager.VERTICAL_SCROLL);
		this.datas = datas;
		for (int i = 0; i < datas.size(); i++) {
			PositionData rpd = (PositionData) datas.elementAt(i);
			add(rpd.getField());
		}
	}

	public int getPreferredWidth() {
		return Display.getWidth();
	}

	public int getPreferredHeight() {
		return Display.getHeight();
	}

	protected void sublayout(final int width, final int height) {
		log.debug("layout");

		// at the end of this method these two int values will hold the maximum
		// width and height this manager must have
		int myWidth = 0;
		int myHeight = 0;

		for (int i = 0; i < datas.size(); i++) {
			PositionData pd = (PositionData) datas.elementAt(i);
			Field field = pd.getField();

			// layout the child so we get correct width and height later
			int w = (int) (pd.getMaxWidth() * Display.getWidth());
			w = w < 0 ? field.getPreferredWidth() : w;

			int h = (int) (pd.getMaxHeight() * Display.getHeight());
			h = h < 0 ? field.getPreferredWidth() : h;
			layoutChild(field, w, h);

			int x = 0;
			int y = 0;
			XYPoint point;
			// field is relative to some other field
			// we assume that the other field was already added to this manager
			if (pd.isRelative()) {

				Field relative = pd.getRelativeField();
				// log.debug("relative field: " + field + " to " + relative);
				// get the x/y coords from the other field.
				x = relative.getLeft();
				y = relative.getTop();

				// calculate the correct point where we want to attach
				switch (pd.getRelativeAttachPoint()) {
				case PositionData.CENTER:
					x += relative.getWidth() / 2;
					y += relative.getHeight() / 2;
					break;
				// nothing to do for north west
				case PositionData.NORTH:
					x += relative.getWidth() / 2;
					break;
				case PositionData.NORTH_EAST:
					x += relative.getWidth();
					break;
				case PositionData.EAST:
					x += relative.getWidth();
					y += relative.getHeight() / 2;
					break;
				case PositionData.SOUTH_EAST:
					x += relative.getWidth();
					y += relative.getHeight();
					break;
				case PositionData.SOUTH:
					x += relative.getWidth() / 2;
					y += relative.getHeight();
					break;
				case PositionData.SOUTH_WEST:
					y += relative.getHeight();
					break;
				case PositionData.WEST:
					y += relative.getHeight() / 2;
					break;
				}

				// add the offset between the relative attach point
				// and the one from the field we are just positioning
				x += Display.getWidth() * pd.getX(); // this only works because we set a
																							// value for x/y already.
				y += Display.getHeight() * pd.getY(); // saves a cast :)

				// get the correct point based on the correctly "offset" point
				point = getCorrectPoint(x, y, field, pd.getAttachPoint());

			} else {
				// field is not relative positioned so we do an absolute one

				// equal to the "north-west" corner and most easy to calculate.
				x += Display.getWidth() * pd.getX(); // this only works because we set a
																							// value for x/y already.
				y += Display.getHeight() * pd.getY(); // saves a cast :)

				// calculate the correct point based from where we are
				// on the screen and where we want to attach
				point = getCorrectPoint(x, y, field, pd.getAttachPoint());
			}
			log.debug("point.x: " + point.x);
			log.debug("point.x: " + point.y);
			// set the new position for the field
			setPositionChild(field, point.x, point.y);

			// calculate maxwidth/height
			myWidth = Math.max(myWidth, point.x + field.getWidth());
			myHeight = Math.max(myHeight, point.y + field.getHeight());

			log.debug("myWidth: " + myWidth);
			log.debug("myHeight: " + myHeight);

		}
		// finally, all fields are layouted and we can set our extent
		setExtent(Display.getWidth(), Display.getHeight());
		setVirtualExtent(myWidth, myHeight);
	}

	/**
	 * Based on a given XY point in the manager and an attach point this will
	 * calculate the real point where the field has to be placed.<br>
	 * <b>NOTE:</b> This does NOT work for relative attach points.
	 * 
	 * @param initialX
	 *          The initial x coordinate.
	 * @param initialY
	 *          The initial y coordinate.
	 * @param field
	 *          The field that will be positioned.
	 * @param attachPoint
	 *          The type of attachpoint. PositionData.NORTH etc.
	 * @return The real XY point for positioning.
	 */
	private XYPoint getCorrectPoint(final int initialX, final int initialY, final Field field, final int attachPoint) {
		int x = initialX;
		int y = initialY;
		switch (attachPoint) {
		case PositionData.CENTER:
			x -= field.getPreferredWidth() / 2;
			y -= field.getPreferredHeight() / 2;
			break;
		case PositionData.NORTH:
			x -= field.getPreferredWidth() / 2;
			break;
		case PositionData.NORTH_EAST:
			x -= field.getPreferredWidth();
			break;
		case PositionData.EAST:
			x -= field.getPreferredWidth();
			y -= field.getPreferredHeight() / 2;
			break;
		case PositionData.SOUTH_EAST:
			x -= field.getPreferredWidth();
			y -= field.getPreferredHeight();
			break;
		case PositionData.SOUTH:
			x -= field.getPreferredWidth() / 2;
			y -= field.getPreferredHeight();
			break;
		case PositionData.SOUTH_WEST:
			y -= field.getPreferredHeight();
			break;
		case PositionData.WEST:
			y -= field.getPreferredHeight() / 2;
			break;
		}

		return new XYPoint(x, y);
	}

}
