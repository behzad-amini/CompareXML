package com.passports.QA;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.xml.sax.SAXException;

public class CompareXML {

	public static void main(String[] args) throws SAXException {

		String expectedXML;
		String actualXML;

		try {
			URI f1 = new URI("file://" + args[0]);// expectedXML (path to file)
			System.out.println(f1.toString());
			URI f2 = new URI("file://" + args[1]);// actualXML (path to file)
			System.out.println(f2.toString());

			expectedXML = new String(Files.readAllBytes(Paths.get(f1)), "UTF-8");
			actualXML = new String(Files.readAllBytes(Paths.get(f2)), "UTF-8");

			assertXMLEquals(expectedXML, actualXML);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void assertXMLEquals(String expectedXML, String actualXML) throws SAXException, IOException {
		
		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.setIgnoreAttributeOrder(true); //Set it to True to ignore the order of attributes
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);
		
		DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(expectedXML, actualXML));
		List<?> allDifferences = diff.getAllDifferences();
		Assert.assertEquals("Differences found: " + diff.toString(), 0, allDifferences.size());
	}

}
