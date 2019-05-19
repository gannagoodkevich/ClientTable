package controller;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


public class DeleteController {
	
	private Client client;

	public DeleteController() throws IOException {
		this.client = new Client();
	}
	
	public int listenerSearchByFaculty(String faculty, String degreeeName) {
		// here i'll send to server my searching for deletation
		int numberOfDelete = 0;
		return numberOfDelete;
	}

	public int listenerSearchByName(String department, String name) {
		int numberOfDelete = 0;
		//server
		return numberOfDelete;
	}

	public int listenerSearchByYear(String year1, String year2) {
		int numberOfDelete = 0;
		//server part
		return numberOfDelete;
	}

}
