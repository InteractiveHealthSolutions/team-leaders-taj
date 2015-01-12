package org.ihsinformatics.textadder;

/*
 @author Ali Habib
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.GregorianCalendar;

public class FileFinder {

    private String text;
    private String yearPlaceholder;

    private int counter;
    private int totCounter;

    public FileFinder() {
	text = null;
	yearPlaceholder = null;
	counter = 0;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    public String getYearPlaceholder() {
	return yearPlaceholder;
    }

    public void setYearPlaceholder(String yearPlaceholder) {
	this.yearPlaceholder = yearPlaceholder;
    }

    public int getCounter() {
	return counter;
    }

    public void setCounter(int counter) {
	this.counter = counter;
    }

    public int getTotCounter() {
	return totCounter;
    }

    public void setTotCounter(int totCounter) {
	this.totCounter = totCounter;
    }

    public void search(File startPath) throws IOException {
	File[] list = startPath.listFiles();

	for (int i = 0; i < list.length; i++) {
	    if (list[i].isDirectory()) {
		search(list[i]);
	    }

	    else {
		totCounter++;
		parseAndReplace(list[i]);
		// System.out.println(list[i].getAbsolutePath());

	    }
	}
    }

    public void parseAndReplace(File path) throws IOException {
	String extension = getFileExtension(path);

	if (extension == null) {
	    return;
	}

	long lastMod = path.lastModified();
	GregorianCalendar gc = new GregorianCalendar();
	gc.setTimeInMillis(lastMod);
	int year = gc.get(GregorianCalendar.YEAR);

	String fileText = getFileText(path);

	if (extension.equalsIgnoreCase(Constants.JAVA_EXT)
		|| extension.equalsIgnoreCase(Constants.SQL_EXT)) {

	    if (fileText.indexOf(text.replaceAll(yearPlaceholder, (year + ""))) == -1) {
		setFileText(
			path,
			Constants.JAVA_COMMENT_START + " "
				+ text.replaceAll(yearPlaceholder, (year + ""))
				+ " " + Constants.JAVA_COMMENT_END + "\n"
				+ fileText);
		counter++;
	    }
	}

	else if (extension.equalsIgnoreCase(Constants.HTML_EXT)) {

	    if (fileText.indexOf(text.replaceAll(yearPlaceholder, (year + ""))) == -1) {
		setFileText(
			path,
			Constants.HTML_COMMENT_START + " "
				+ text.replaceAll(yearPlaceholder, (year + ""))
				+ " " + Constants.HTML_COMMENT_END + "\n"
				+ fileText);
		counter++;
	    }
	}

	else if (extension.equalsIgnoreCase(Constants.XML_EXT)) {
	    if (fileText.indexOf(text.replaceAll(yearPlaceholder, (year + ""))) == -1) {

		int xmlTagIndex = findXmlTagIndex(fileText);

		if (xmlTagIndex < 0)
		    return;

		StringBuffer sb = new StringBuffer(fileText);

		sb.insert(xmlTagIndex + 1, "\n" + Constants.HTML_COMMENT_START
			+ " " + text.replaceAll(yearPlaceholder, (year + ""))
			+ " " + Constants.HTML_COMMENT_END + "\n");

		setFileText(path, sb.toString());
		counter++;
	    }
	}

	else if (extension.equalsIgnoreCase(Constants.JSP_EXT)
		|| extension.equalsIgnoreCase(Constants.XML_EXT)) {

	    if (fileText.indexOf(text.replaceAll(yearPlaceholder, (year + ""))) == -1) {
		setFileText(
			path,
			Constants.JSP_COMMENT_START + " "
				+ text.replaceAll(yearPlaceholder, (year + ""))
				+ " " + Constants.JSP_COMMENT_END + "\n"
				+ fileText);
		counter++;
	    }
	}
    }

    public String getFileExtension(File file) {

	String ext = null;
	int i = file.getAbsolutePath().lastIndexOf('.');
	if (i > 0) {
	    ext = file.getAbsolutePath().substring(i + 1);
	}
	return ext;
    }

    public String getFileText(File path) throws IOException {
	String ftext = "";

	StringBuilder sb = new StringBuilder(512);

	FileInputStream fis = new FileInputStream(path);
	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	String line = "";
	while ((line = br.readLine()) != null) {
	    ftext += line + "\n";
	}

	br.close();
	fis.close();

	return ftext;
    }

    public void setFileText(File path, String ftext) throws IOException {

	BufferedWriter bw = new BufferedWriter(new FileWriter(path));
	bw.write(ftext);
	bw.flush();
	bw.close();

    }

    public int findXmlTagIndex(String text) {
	// int index = 0;

	int tagStartIndex = text.indexOf("<?xml");

	if (tagStartIndex < 0)
	    return tagStartIndex;

	int tagEndIndex = text.indexOf('>', tagStartIndex);

	return tagEndIndex;
    }

}
