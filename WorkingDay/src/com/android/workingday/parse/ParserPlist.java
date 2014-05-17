package com.android.workingday.parse;

// COMPANY NAME : SILICON IT HUB PVT LTD

// DEVELOPER NAME : Nilay Sheth

// PROJECT NAME : workingday 

// DEVELOPING DATE : 21-11-2013

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.android.workingday.beanclass.HolidayClass;

public class ParserPlist {

	// parse Plist and fill in arraylist
	public ArrayList<HolidayClass> parsePlist(String xml) {
		
		final ArrayList<HolidayClass> dataModels = new ArrayList<HolidayClass>();
		// Get the xml string from assets
		HolidayClass model;
		
		final Document doc = XMLfromString(xml);

		final NodeList nodes_key = doc.getElementsByTagName("key");
		final NodeList nodes_array = doc.getElementsByTagName("array");
		
		for (int index = 0; index < nodes_key.getLength(); index++) {

			model = new HolidayClass();
			final Node node = nodes_key.item(index);
			String yr = "";
				
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				final Element e = (Element) nodes_key.item(index);
				
				yr = e.getChildNodes().item(0).getNodeValue();

			}
			
			final Element e = (Element) nodes_array.item(index);
			
			final NodeList nodeKey = e.getElementsByTagName("string");
			
			ArrayList<String> dates = new ArrayList<String>();
			
			for (int i = 0; i < nodeKey.getLength(); i++) {

				final Element eleString = (Element) nodeKey.item(i);
				
				dates.add(eleString.getChildNodes().item(0).getNodeValue());
				
			}
			
			model.setYear(yr);
			model.setDate(dates);
			dataModels.add(model);
		}
	
		return dataModels;
	}

	// Create xml document object from XML String
	private Document XMLfromString(String xml) {
		Document doc = null;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);
		} catch (ParserConfigurationException e) {
			System.out.println("XML parse error: " + e.getMessage());
			return null;
		} catch (SAXException e) {
			System.out.println("Wrong XML file structure: " + e.getMessage());
			return null;
		} catch (IOException e) {
			System.out.println("I/O exeption: " + e.getMessage());
			return null;
		}

		return doc;
	}

}
