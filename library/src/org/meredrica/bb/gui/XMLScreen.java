/**
 * Copyright (c) 2012, Florian Westreicher aka meredrica All rights reserved.
 * Copyrights licensed under the Modified BSD License. See LICENSE.txt file for terms.
 */
package org.meredrica.bb.gui;

import java.util.Hashtable;
import java.util.Vector;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.container.MainScreen;

import org.meredrica.bb.utils.AdvancedRunnable;
import org.meredrica.bb.utils.XMLReader;

/**
 * This screen makes life a bit easier by defining a few basic methods for XML
 * screens.
 * 
 * @author Florian Westreicher aka meredrica
 * 
 */
public class XMLScreen extends MainScreen {

	// FIXME: general exception handling

	private final String xmlFile;

	private PositionManager tpm;
	private Vector datas = new Vector();

	/**
	 * Creates a screen with the layout defined that will remain empty until
	 * {@link XMLScreen#setAliases(Hashtable)} is called.
	 * 
	 * @param xmlFile
	 *          The name of the file that defines the layout.
	 */
	public XMLScreen(final String xmlFile) {
		this(xmlFile, Manager.NO_HORIZONTAL_SCROLL | Manager.NO_VERTICAL_SCROLL);
	}

	/**
	 * Creates a screen with the layout defined that will remain empty until
	 * {@link XMLScreen#setAliases(Hashtable)} is called.
	 * 
	 * @param xmlFile
	 *          The name of the file that defines the layout.
	 * @param style
	 *          the style to use.
	 */
	public XMLScreen(final String xmlFile, final long style) {
		super(style);
		this.xmlFile = xmlFile;
	}

	/**
	 * Creates a screen with the given params.
	 * 
	 * @param xmlFile
	 *          The name of the file that defines the layout.
	 * @param aliases
	 *          The aliases for {@link XMLScreen#setAliases(Hashtable)}
	 */
	public XMLScreen(final String xmlFile, final Hashtable aliases) {
		this(xmlFile);
		setAliases(aliases);

	}

	/**
	 * Sets the aliases this Screen uses. Before calling this method, this screen
	 * will delete all it's content.
	 * 
	 * @param aliases
	 *          The aliases to use as Hashtable<String, Field>
	 */
	public synchronized void setAliases(final Hashtable aliases) {
		deleteAll();
		XMLLayoutHandler handler = new XMLLayoutHandler(aliases);
		XMLReader.readFile(xmlFile, handler);
		datas = handler.getDatas();
		new AdvancedRunnable() {
			public void run() {
				if (tpm != null) {
					delete(tpm);
					tpm.deleteAll();
				}
				tpm = new PositionManager(datas);
				add(tpm);
			}
		}.invokeLater();
	}

	/**
	 * Returns the {@link PositionData} that belongs to the given field.
	 * 
	 * @param f
	 *          The field.
	 * @return The retrieved PositionData or null if it was not found.
	 */
	public PositionData dataForField(final Field f) {
		for (int i = 0; i < datas.size(); i++) {
			PositionData pd = (PositionData) datas.elementAt(i);
			if (pd.getField().equals(f)) {
				return pd;
			}
		}
		return null;
	}

	/**
	 * Returns the {@link PositionData} that belongs to the given alias.
	 * 
	 * @param alias
	 *          The alias of the field.
	 * @return The retrieved PositionData or null if it was not found.
	 */
	public PositionData dataForAlias(final String alias) {
		for (int i = 0; i < datas.size(); i++) {
			PositionData pd = (PositionData) datas.elementAt(i);
			if (pd.getAlias().equals(alias)) {
				return pd;
			}
		}
		return null;
	}
}
