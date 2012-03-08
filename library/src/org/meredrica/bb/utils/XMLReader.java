/**
 * Copyright (c) 2012, Florian Westreicher aka meredrica All rights reserved.
 * Copyrights licensed under the Modified BSD License. See LICENSE.txt file for terms.
 */
package org.meredrica.bb.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.rim.device.api.ui.UiApplication;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Helper class for common XML actions.
 * 
 * @author Florian Westreicher aka meredrica
 * 
 */
public class XMLReader {

	private static Logger log = Logger.getLogger(XMLReader.class);

	/**
	 * Reads the given file and passes it to the handler.
	 * 
	 * @param filename
	 *          The filename to read.
	 * @param handler
	 *          The {@link DefaultHandler} that shall handle the file.
	 */
	public static void readFile(final String filename, final DefaultHandler handler) {
		// FIXME: exception handling

		String file = filename.startsWith("/") ? filename : "/" + filename;
		InputStream is = null;
		try {
			is = UiApplication.getUiApplication().getClass().getResourceAsStream(file);
			readStream(is, handler);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * Reads the given stream and passes it to the handler. <br>
	 * <b>NOTE:</b> The stream will NOT be closed.
	 * 
	 * @param is
	 *          The {@link InputStream} to read.
	 * @param handler
	 *          The {@link DefaultHandler} that shall handle the stream.
	 */
	public static void readStream(final InputStream is, final DefaultHandler handler) {
		// FIXME: error handling
		SAXParser parser;
		try {
			parser = SAXParserFactory.newInstance().newSAXParser();
			parser.parse(is, handler);
		} catch (ParserConfigurationException e) {
			log.fatal(e + e.getMessage());
		} catch (SAXException e) {
			log.fatal(e + e.getMessage());
		} catch (IOException e) {
			log.fatal(e + e.getMessage());
		}
	}
}
