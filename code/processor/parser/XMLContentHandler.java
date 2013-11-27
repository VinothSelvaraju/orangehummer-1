import java.io.BufferedWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import net.java.textilej.parser.MarkupParser;
import net.java.textilej.parser.builder.HtmlDocumentBuilder;
import net.java.textilej.parser.markup.mediawiki.MediaWikiDialect;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLContentHandler extends DefaultHandler {
	boolean title = false;
	boolean id = false;
	boolean text = false;
	boolean timeStamp = false;
	boolean conFlag = false;
	boolean page = false;
	int temp;
	StringBuffer sb = new StringBuffer();
	StringBuffer sbInfoBox = new StringBuffer();
	MyXMLPage xmlPage;
	public ArrayList<WikipediaDocument> XMLPageCollection = new ArrayList<WikipediaDocument>();

	File file = new File("D:\\infobox.xml");
	FileWriter fw;
	BufferedWriter bw;

	public String markupRemover(String input) {

		StringWriter writer = new StringWriter();
		HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
		builder.setEmitAsDocument(false);
		MarkupParser parser = new MarkupParser(new MediaWikiDialect());
		parser.setBuilder(builder);
		parser.parse(input);

		final String html = writer.toString();
		final StringBuilder cleaned = new StringBuilder();

		HTMLEditorKit.ParserCallback callback = new HTMLEditorKit.ParserCallback() {
			public void handleText(char[] data, int pos) {
				cleaned.append(new String(data)).append(' ');
			}
		};
		try {
			new ParserDelegator()
					.parse(new StringReader(html), callback, false);
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * System.out.println(input);
		 * System.out.println("---------------------------");
		 * System.out.println(html);
		 * System.out.println("---------------------------");
		 * System.out.println(cleaned);
		 */
		return cleaned.toString();
	}

	public String unwantedTextRemoval(String text) {
		String modifiedText = "";
		modifiedText = text.replaceAll("<ref.*</ref>", "");
		modifiedText = modifiedText.replaceAll("<ref.*?>", "");
		modifiedText = modifiedText.replaceAll("(?i)\\{\\{citation(.*?)\\}\\}","");
		modifiedText = modifiedText.replaceAll("(?i)\\{\\{fact(.*?)\\}\\}","");
		
		
		// parsing awards
		modifiedText = modifiedText.replaceAll("\\{\\{awd\\|(.*?)\\|\\}\\}",
				"XXXXXXXXXXXXX");

		// parsing URL
		modifiedText = modifiedText.replaceAll("\\{\\{URL\\|(.*?)\\}\\}", "$1");
		modifiedText = modifiedText.replaceAll("\\{\\{url\\|(.*?)\\}\\}", "$1");

		// parsing marriage
		Pattern p4 = Pattern.compile("\\{\\{Marriage\\|(.*?)\\}\\}",Pattern.CASE_INSENSITIVE);
		Matcher m4 = p4.matcher(modifiedText);
		String newString1 = "";
		// ArrayList<HashMap> coll = new ArrayList<HashMap>();
		while (m4.find()) {
			newString1 = m4.group(1);
			// System.out.println("Marriage matches found:" + newString1);
			newString1 = newString1.replaceAll("(.*?)\\|(.*)", "$1");
			// System.out.println("Modified string: "+ newString1);

			modifiedText = modifiedText.replaceAll("\\{\\{Marriage\\|(.*?)\\}\\}", newString1);
			modifiedText = modifiedText.replaceAll(	"\\{\\{marriage\\|(.*?)\\}\\}", newString1);
		}
		// System.out.println(rem1);
		/*
		 * parsing awards!!! Pattern p3 =
		 * Pattern.compile("\\{\\{awd\\|(.*?)\\}\\}", Pattern.CASE_INSENSITIVE);
		 * Matcher m3 = p3.matcher(rem1); String newString1 = "";
		 * ArrayList<HashMap> coll = new ArrayList<HashMap>(); while(m3.find()){
		 * newString1 = m3.group(1); System.out.println("AWARDS: "+newString1);
		 * String [] mapEntryArray = newString1.split(" *\\| *"); int k = 0;
		 * while(k<mapEntryArray.length){ HashMap<String,String> awardMap = new
		 * HashMap<String,String>(); String [] subEntryArray =
		 * mapEntryArray[k].split(" *= *");
		 * awardMap.put(subEntryArray[0].trim(), subEntryArray[1].trim());
		 * System.out.println(subEntryArray[0].trim()+"===="+
		 * subEntryArray[1].trim()); coll.add(awardMap); k++; } }
		 */
		/*
		 * //parsing Birthdate String regex1 = "\\{\\{B(.*?)\\}\\}"; Pattern p2
		 * = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE); Matcher m2 =
		 * p2.matcher(modifiedText); String newString = null; String workgroup1
		 * = ""; while(m2.find()){ workgroup1 = m2.group(1);
		 * System.out.println(workgroup1); newString =
		 * workgroup1.replaceAll("[^0-9|]", ""); newString =
		 * newString.replaceAll("\\|"," "); newString = newString.trim();
		 * newString = newString.replaceAll(" ", "-"); newString =
		 * newString.replaceAll("(age (.*))", ""); newString =
		 * toUtcDate(newString); System.out.println("NEW DATE: "+newString); }
		 * modifiedText=modifiedText.replaceAll("\\{\\{B(.*?)\\}\\}",
		 * newString);
		 * modifiedText=modifiedText.replaceAll("\\{\\{b(.*?)\\}\\}",
		 * newString); //System.out.println("modified text :: " + modifiedText);
		 * //modifiedText =modifiedText.replaceAll("\\| ?birth_date *=(.*)$",
		 * "|birth_date ="+newString);
		 * //System.out.println("TESTTTTTTTTTTTTTTTTTTTTT"+modifiedText);
		 */

		// parsing Birthdate
		String regex1 = "\\{\\{B(.*?)\\}\\}";
		Pattern p2 = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE);
		Matcher m2 = p2.matcher(modifiedText);
		String workgroup1 = "";
		while (m2.find()) {
			workgroup1 = m2.group(1);
			System.out.println(workgroup1);
			String s[] = workgroup1.split("\\|");
			int k = 0;
			int j = 0;
			String day = "";
			String month = "";
			String year = "";
			String date = "";
			String[] monthss = {"jan", "feb", "mar","apr", "may", "jun", "jul", "aug", "sep", "oct", "nov","dec"};
			Boolean flag = false;
			for (k = s.length-1; k >= 0; k--) {
				for(int m =0; m < monthss.length;m++){
					if(s[k].toLowerCase().contains(monthss[m])){
						flag = true;
						//break;
					}	
				}
				System.out.println(s[k] + " :: "+flag);
				if ((s[k].matches("[0-9]+") || flag ==true) && j <= 2) {
					flag = false;
					if (j == 0) {
						day = s[k];	
					} else if (j == 1) {
						month = s[k];		
					} else if (j == 2) {
							year = s[k];
					}
					j++;
				}
			}
			if (!year.equalsIgnoreCase("") && !month.equalsIgnoreCase("") && !day.equalsIgnoreCase(""))
				date = year + "-" + month + "-" + day;
			else
				date = day;
			System.out.println("date "+date);
			date = toUtcDate(date);
			modifiedText = modifiedText.replaceAll("\\{\\{B(.*?)\\}\\}", date);
			modifiedText = modifiedText.replaceAll("\\{\\{b(.*?)\\}\\}", date);	
		}
		Pattern p3 = Pattern.compile("\\| *birth_date *=(.*?)\\|", Pattern.CASE_INSENSITIVE);
		Matcher m3 = p3.matcher(modifiedText);
		String workgroup2 = "";
		while (m3.find()) {
			workgroup2 = m3.group(1);
			//System.out.println("NEW MATCH 1:"+ workgroup2);
			//System.out.println("TRUEEE");
			
			Pattern p5 = Pattern.compile("(\\(.*\\))", Pattern.CASE_INSENSITIVE);
			Matcher m5 = p5.matcher(workgroup2);
			String workgroup3 = "";
			
			while (m5.find()) {
				workgroup3 = m5.group(1);
				modifiedText = modifiedText.replace(workgroup3,"");	
				String rem = workgroup2.replace(workgroup3, "").trim();
				modifiedText = modifiedText.replace(rem,toUtcDate(rem));
			}
			Pattern p6 = Pattern.compile("([0-9]*-[0-9]*-[0-9]*T[0-9]*:[0-9]*:[0-9]*Z)", Pattern.CASE_INSENSITIVE);
			Matcher m6 = p6.matcher(workgroup2);
			String workgroup4 = "";
			while (m6.find()) {
				workgroup4 = m6.group();
				//System.out.println("NEW MATCH 4:"+ workgroup4);
				modifiedText = modifiedText.replace(workgroup2,workgroup4);
				workgroup2 = workgroup2.replace(workgroup2, workgroup4);
			}
			//String latest = workgroup2.replace(workgroup2, workgroup4);
			modifiedText = modifiedText.replace(workgroup2,toUtcDate(workgroup2.trim()));
		
		}
		return modifiedText;
	}

	public static String toUtcDate(String dateStr) {
		if (dateStr.isEmpty()) {
			return dateStr;
		} else {
			SimpleDateFormat out = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss'Z'");
			String[] dateFormats = {"yyyy-MMM-dd", "yyyy-MM-dd", "yyyy hh:mm:ss Z","dd MMM yyyy","MMMM dd, yyyy","MMMM yyyy", "MMM dd",  "yyyy"
					 };
			for (String dateFormat : dateFormats) {
				try {
					//System.out.println("naen :: "+dateFormat);
					return out.format(new SimpleDateFormat(dateFormat)
							.parse(dateStr));
				} catch (ParseException ignore) {
				}
			}
			throw new IllegalArgumentException("Invalid date: " + dateStr);
		}
	}

	public void xmlWriter(MyXMLPage page) {
		if (!page.infobox.isEmpty()) {
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				File xmlFile = new File("D:\\Infoboxinxml.xml");
				Document doc = null;
				Element rootElement = null;
				if (xmlFile.exists()) {
					try {
						doc = docBuilder.parse(xmlFile);
						rootElement = doc.getDocumentElement(); // gets the root
																// element
						rootElement.normalize();
					} catch (SAXException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} // creates the document based on an existing XML file
						// //and normalizes it
				} else {
					doc = docBuilder.newDocument(); // creates an empty document
					rootElement = doc.createElement("add"); // create the root
															// element
					doc.appendChild(rootElement); // and append it to the newly
													// created document
				}
				Element document = doc.createElement("doc");
				rootElement.appendChild(document);
				Element field1 = doc.createElement("field");
				field1.appendChild(doc.createTextNode(Integer.toString(page
						.getId())));
				field1.setAttribute("name", "id");
				document.appendChild(field1);
				Element field2 = doc.createElement("field");
				field2.appendChild(doc.createTextNode("person"));
				field2.setAttribute("name", "type");
				document.appendChild(field2);
				Iterator<Entry<String, String>> itr1 = page.infobox.entrySet()
						.iterator();
				System.out
						.println("-----------------Page start---------------------");
				while (itr1.hasNext()) {
					Map.Entry pairs = (Map.Entry) itr1.next();
					System.out.println(pairs.getKey() + "=== "
							+ pairs.getValue());
					Element field = doc.createElement("field");
					field.appendChild(doc.createTextNode(pairs.getValue()
							.toString()));
					field.setAttribute("name", pairs.getKey().toString()
							.replaceAll("_", ""));
					document.appendChild(field);
				}
				System.out
						.println("-------------------Page stop------------------------");
				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(
						"D:\\Infoboxinxml.xml"));
				// Output to console for testing
				StreamResult result1 = new StreamResult(System.out);
				transformer.transform(source, result);
				System.out.println("File saved!");
			} catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			} catch (TransformerException tfe) {
				tfe.printStackTrace();
			}
		}
	}

	@Override
	public void startDocument() throws SAXException {
		try {
			fw = new FileWriter(file.getAbsoluteFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		bw = new BufferedWriter(fw);

	}

	@Override
	public void startElement(String arg0, String arg1, String arg2,
			Attributes arg3) throws SAXException {

		if (arg1.equals("page")) {
			xmlPage = new MyXMLPage();
			page = true;
		}
		if (arg1.equals("title")) {

			title = true;
		}
		if (arg1.equals("id")) {

			temp = temp + 1;
			id = true;
		}
		if (arg1.equals("username") || arg1.equals("ip")) {
			conFlag = true;
		}
		if (arg1.equals("timestamp")) {
			timeStamp = true;
		}
		if (arg1.equals("text")) {
			text = true;
		}
	}

	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		if (page) {
			page = false;
		}
		if (title) {
			xmlPage.setTitle(new String(arg0, arg1, arg2));
			title = false;
		}
		if (id && temp == 1) {
			xmlPage.setiD(new String(arg0, arg1, arg2));
			id = false;
		}
		if (conFlag) {
			xmlPage.setAuthor(new String(arg0, arg1, arg2));
			conFlag = false;
		}
		if (text) {
			sb.append(arg0, arg1, arg2);
			xmlPage.setText(sb.toString());
		}
		if (timeStamp) {
			xmlPage.setPublishDate(new String(arg0, arg1, arg2));
			timeStamp = false;
		}
	}

	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		if (arg1 == "text") {
			text = false;
		}
		if (arg1 == "page") {
			temp = 0;
			xmlPage.setText(sb.toString());
			String regex = "\\{\\{Infobox person(.*)\\}\\}";
			Pattern p1 = Pattern.compile(regex, Pattern.DOTALL);
			Matcher m1 = p1.matcher(xmlPage.getText());
			String workgroup = "";
			while (m1.find()) {
				workgroup = m1.group();
			}

			// read char by char
			char[] ch = workgroup.toCharArray();
			int count = 0;
			int j = 0;
			boolean flag = false;
			StringBuffer newStringBuf = new StringBuffer();
			while (j <= (ch.length - 1)) {
				if (ch[j] == '{' && ch[j + 1] == '{' && flag == false) {
					count++;
					if (count == 0) {
						flag = true;
					}
				}
				if ((j + 1) < ch.length) {
					if (ch[j] == '}' && ch[j + 1] == '}') {
						count--;
						if (count == 0) {
							flag = true;
						}
					}
				}
				if (count > 0) {
					newStringBuf.append(ch[j]);
				}
				j++;
			}
			String formattedText = this.markupRemover(newStringBuf.toString());
			formattedText = unwantedTextRemoval(formattedText);

			String[] entry = formattedText.split(" *\\| *");
			HashMap<String, String> entryMap = new HashMap<String, String>();
			int itr = 0;
			try {
				while (itr < entry.length) {
					bw.append(entry[itr]);
					entry[itr] = entry[itr].replaceAll("\\{\\{Infobox person",
							"");
					String[] splitKeyValue = entry[itr].split("=");
					if (splitKeyValue.length == 2) {
						// System.out.println(splitKeyValue[0].trim() +"====="+
						// splitKeyValue[1].trim());
						if (splitKeyValue[0].trim().length() != 0
								&& splitKeyValue[1].trim().length() != 0) {
							// System.out.println(splitKeyValue[0]+"===="+splitKeyValue[1]);
							entryMap.put(splitKeyValue[0].trim(),
									splitKeyValue[1].trim());
						}
					}
					bw.append("\n");
					itr++;
				}
				System.out
						.println("----------------------------------------------------------------------------------------");
				xmlPage.setInfobox(entryMap);

			} catch (IOException e) {
				e.printStackTrace();
			}
			xmlWriter(xmlPage);
			// Iterate over the map to print the key value pair
			// System.out.println("PAGE STARTS");
			Iterator<Entry<String, String>> itr1 = xmlPage.getInfobox()
					.entrySet().iterator();
			while (itr1.hasNext()) {
				Map.Entry pairs = (Map.Entry) itr1.next();
				// System.out.println(pairs.getKey() + " ===== " +
				// pairs.getValue());
				// itr1.remove();
			}
			// System.out.println("PAGE ENDS");

			WikipediaDocument wikiDoc = WikipediaParser
					.wikipediaDocumentGenerator(xmlPage);
			XMLPageCollection.add(wikiDoc);

			sb = new StringBuffer("");
			page = false;
		}
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("PROGRAM ENDS - Time: "
				+ Long.toString(System.currentTimeMillis()));
	}
}