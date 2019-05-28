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

import view.WindowUserCom;


public class DeleteController {
	
	private Client client;
	private boolean connected;
	WindowUserCom t;
	
	
	public DeleteController(WindowUserCom t) throws IOException {
		//this.client = new Client();
		this.t = t;
	}
	
	public int listenerSearchByFaculty(String faculty, String degreeeName) throws IOException {
		// here i'll send to server my searching for deletation
		//System.out.println(client.dis.readUTF());
		System.out.println("Fac delete Button begin");
		String username = "User";
		client = new Client("localhost", 1500, username, t);
		// test if we can start the Client
		if (!client.start())
			return 0;
		
		connected = true;
		client.sendMessage(new ChatMessage(ChatMessage.DELETE_FAC, " hoping"));
		int numberOfDelete = 0;
		return numberOfDelete;
	}

	public int listenerSearchByName(String department, String name) {
		int numberOfDelete = 0;
		//server
		System.out.println("Name delete Button begin");
		String username = "User";
		client = new Client("localhost", 1500, username, t);
		// test if we can start the Client
		if (!client.start())
			return 0;
		
		connected = true;
		client.sendMessage(new ChatMessage(ChatMessage.DELETE_NAME, " hoping"));
		
		return numberOfDelete;
	}

	public int listenerSearchByYear(String year1, String year2) {
		
		System.out.println("Year delete Button begin");
		String username = "User";
		client = new Client("localhost", 1500, username, t);
		// test if we can start the Client
		if (!client.start())
			return 0;
		
		connected = true;
		client.sendMessage(new ChatMessage(ChatMessage.DELETE_YEAR, " hoping"));
		
		int numberOfDelete = 0;
		//server part
		return numberOfDelete;
	}

}
