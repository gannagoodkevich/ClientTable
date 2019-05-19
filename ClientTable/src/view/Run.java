package view;


import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import view.WindowUserCom;

public class Run {
	private static final int AREA_WIDTH = 1920;
	private static final int AREA_HIGHT = 1080;
	
	public static void main(String[] args)
			throws IOException, ParserConfigurationException, SAXException, TransformerException {
		WindowUserCom main = new WindowUserCom();
		main.run(main, AREA_WIDTH, AREA_HIGHT);
		}	
	
}
