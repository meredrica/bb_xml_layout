/**
 * Copyright (c) 2012, Florian Westreicher aka meredrica All rights reserved.
 * Copyrights licensed under the Modified BSD License. See LICENSE.txt file for terms.
 */
package org.meredrica.bb.gui;

import java.util.Hashtable;
import java.util.Vector;

import net.rim.device.api.ui.Field;

import org.meredrica.bb.utils.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Handler Implementation for the XML Layout files.
 * 
 * @author Florian Westreicher aka meredrica
 * 
 */
public class XMLLayoutHandler extends DefaultHandler {
	// FIXME: general exception handling

	private static final Logger log = Logger.getLogger(XMLLayoutHandler.class);

	private final Hashtable aliases;
	private final Vector datas = new Vector();

	private double posX = 0;
	private double posY = 0;
	private Field currentField = null;
	private Field realativeField = null;
	private int attachPoint = PositionData.CENTER;
	private int relativeAttachPoint = PositionData.CENTER;
	private double maxHeight;
	private double maxWidth;
	private String alias;

	public XMLLayoutHandler(final Hashtable aliases) {
		this.aliases = aliases;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(final String uri, final String localName, final String qName, final Attributes attributes)
			throws SAXException {
		// TODO: change to dispatcher / routing switch approach before adding
		// support for more fields

		if (localName.equals("field")) {
			alias = attributes.getValue("alias");
			currentField = fieldForAlias(alias);
			posX = Double.parseDouble(attributes.getValue("x"));
			posY = Double.parseDouble(attributes.getValue("y"));
			attachPoint = parseDirection(attributes.getValue("attachpoint"));
			realativeField = fieldForAlias(attributes.getValue("relativeto"));
			relativeAttachPoint = parseDirection(attributes.getValue("relativeattachpoint"));
			maxWidth = parseNullDouble(attributes.getValue("maxwidth"));
			maxHeight = parseNullDouble(attributes.getValue("maxheight"));
		}

	}

	private double parseNullDouble(final String s) {
		return (s != null) ? Double.parseDouble(s) : -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void endElement(final String uri, final String localName, final String qName) throws SAXException {
		if (localName.equals("field")) {
			if (currentField != null) {
				PositionData pd = new PositionData(alias, currentField, posX, posY, maxWidth, maxHeight, attachPoint,
						realativeField, relativeAttachPoint);
				datas.addElement(pd);
				log.info("added new PositionData:" + pd);
			}
			reset();
		}
	}

	private Field fieldForAlias(final String alias) {
		if (alias == null || alias.equals("")) {
			return null;
		}
		Object o = aliases.get(alias);
		if (o != null && o instanceof Field) {
			return (Field) o;
		} else {
			return null;
		}
	}

	private int parseDirection(final String direction) {
		if (direction == null || direction.equals("")) {
			return PositionData.CENTER;
		} else if (direction.equals("north-west")) {
			return PositionData.NORTH_WEST;
		} else if (direction.equals("north")) {
			return PositionData.NORTH;
		} else if (direction.equals("north-east")) {
			return PositionData.NORTH_EAST;
		} else if (direction.equals("east")) {
			return PositionData.EAST;
		} else if (direction.equals("south-east")) {
			return PositionData.SOUTH_EAST;
		} else if (direction.equals("south")) {
			return PositionData.SOUTH;
		} else if (direction.equals("south-west")) {
			return PositionData.SOUTH_WEST;
		} else if (direction.equals("west")) {
			return PositionData.WEST;
		} else {
			return PositionData.CENTER;
		}
	}

	private void reset() {
		currentField = null;
		realativeField = null;
		alias = null;
		posX = 0;
		posY = 0;
		attachPoint = PositionData.CENTER;
		relativeAttachPoint = PositionData.CENTER;
	}

	public Vector getDatas() {
		return datas;
	}

}
