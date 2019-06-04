package controller;

import java.net.*;
import java.awt.Dimension;
import java.io.*;
import java.util.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import view.TableWithPages;
import view.WindowUserCom;

/*
 * The Client that can be run both as a console or a GUI
 */
public class Client {

	// for I/O
	private ObjectInputStream sInput; // to read from the socket
	private ObjectOutputStream sOutput; // to write on the socket
	private Socket socket;

	// if I use a GUI or not
	private WindowUserCom cg;
	public List<String[]> rowList;
	
	// the server, the port and the username
	private String serverAdress, username;
	private int port;
	public ListenFromServer thread;
	
	

	/*
	 * Constructor called by console mode server: the server address port: the port
	 * number username: the username
	 */
	Client(String server, int port, String username) {
		// which calls the common constructor with the GUI set to null
		this(server, port, username, null);
	}

	/*
	 * Constructor call when used from a GUI in console mode the ClienGUI parameter
	 * is null
	 */
	public Client(String serverAdress, int port, String username, WindowUserCom cg) {
		this.serverAdress = serverAdress;
		this.port = port;
		this.username = username;
		// save if we are in GUI mode or not
		this.cg = cg;
	}

	/*
	 * To start the dialog
	 */
	public boolean start() {
		// try to connect to the server
		try {
			socket = new Socket(serverAdress, port);
		//	InetAddress adresse = new InetAdress();
			//socket = new Socket();
		}
		// if it failed not much I can so
		catch (Exception ec) {
			display("Error connectiong to server:" + ec);
			return false;
		}

		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		display(msg);

		/* Creating both Data Stream */
		try {
			sInput = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
			return false;
		}

		// creates the Thread to listen from the server
		thread = new ListenFromServer();
		thread.start();
		// Send our username to the server this is the only message that we
		// will send as a String. All other messages will be ChatMessage objects
		try {
			sOutput.writeObject(username);
		} catch (IOException eIO) {
			display("Exception doing login : " + eIO);
			disconnect();
			return false;
		}
		// success we inform the caller that it worked
		return true;
	}

	/*
	 * To send a message to the console or the GUI
	 */
	private void display(String msg) {
		if (cg == null)
			System.out.println(msg); // println in console mode
		else
			System.out.println(msg);
		// cg.append(msg + "\n"); // append to the ClientGUI JTextArea (or whatever)
	}

	/*
	 * To send a message to the server
	 */
	public void sendMessage(ChatMessage msg) {
		try {
			sOutput.writeObject(msg);
			System.out.println("Wrote msg at server");
		} catch (IOException e) {
			System.out.println("Can't write at server");
			display("Exception writing to server: " + e);
		}
	}

	/*
	 * When something goes wrong Close the Input/Output streams and disconnect not
	 * much to do in the catch clause
	 */
	private void disconnect() {
		try {
			if (sInput != null)
				sInput.close();
		} catch (Exception e) {
		} // not much else I can do
		try {
			if (sOutput != null)
				sOutput.close();
		} catch (Exception e) {
		} // not much else I can do
		try {
			if (socket != null)
				socket.close();
		} catch (Exception e) {
		} // not much else I can do

		// inform the GUI
		if (cg != null)
			cg.connectionFailed();

	}
	/*
	 * To start the Client in console mode use one of the following command > java
	 * Client > java Client username > java Client username portNumber > java Client
	 * username portNumber serverAddress at the console prompt If the portNumber is
	 * not specified 1500 is used If the serverAddress is not specified "localHost"
	 * is used If the username is not specified "Anonymous" is used > java Client is
	 * equivalent to > java Client Anonymous 1500 localhost are eqquivalent
	 * 
	 * In console mode, if an error occurs the program simply stops when a GUI id
	 * used, the GUI is informed of the disconnection
	 */

	/*
	 * a class that waits for the message from the server and append them to the
	 * JTextArea if we have a GUI or simply System.out.println() it in console mode
	 */
	public class ListenFromServer extends Thread {

		public void run() {
			while (true) {
				try {
					List<String[]> msg = (List<String[]>) sInput.readObject();
					// if console mode print the message and add back the prompt
					if (cg == null) {
						System.out.println(msg);
						System.out.print("> ");
					} else {
						// cg.append(msg);
						System.out.println("Server answered");
						// Client.cg.setUni(msg);

						if (msg.get(0)[0].equals("SEARCH")) {
							JPanel pan = new JPanel();
							pan.setLayout(null);
							rowList = new ArrayList<String[]>();
							for (int i = 1; i < msg.size(); i++) {
								rowList.add(msg.get(i));
							}
							//System.out.println("FOUND SMTH");
							/*TableWithPages currTable = new TableWithPages(cg, rowList, pan);
							pan.add(currTable.scroll);
							UIManager.put("OptionPane.minimumSize", new Dimension(1800, 500));
							JOptionPane.showMessageDialog(null, pan, "Table", JOptionPane.OK_CANCEL_OPTION);*/
						} else {
							if (msg.get(0)[0].equals("DELETE")) {
								JPanel pan1 = new JPanel();
								// pan.setLayout(null);
								JLabel ans = new JLabel();
								ans.setText(msg.get(0)[1]);
								pan1.add(ans);
								System.out.println(msg.get(0)[1]);
								UIManager.put("OptionPane.minimumSize", new Dimension(1800, 500));
								JOptionPane.showMessageDialog(null, pan1, "Hohoho", JOptionPane.OK_CANCEL_OPTION);
							} else {
								if (msg.get(0)[0].equals("TURN")) {
									List<String[]> rowList = new ArrayList<String[]>();
									for (int i = 1; i < msg.size(); i++) {
										rowList.add(msg.get(i));
									}
									cg.drawTable(cg.mainPanel, rowList);
									cg.currentTableWithLecturers.lableNumberOfElements.setText(msg.get(0)[2]);
									cg.currentTableWithLecturers.lableNumberOnPage.setText(msg.get(0)[1]);
									cg.mainFrame.invalidate();
									cg.mainFrame.validate();
									cg.mainFrame.repaint();
								}
								else{
									cg.drawTable(cg.mainPanel, msg);
									//cg.currentTableWithLecturers.lableNumberOfElements.setText("Hello");
									cg.mainFrame.invalidate();
									cg.mainFrame.validate();
									cg.mainFrame.repaint();
									}
							}
						}
						// SwingUtilities.updateComponentTreeUI(cg.mainFrame);
						// System.out.println(msg.get(0)[4]);
					}
				} catch (IOException e) {
					display("Server has close the connection: " + e);
					if (cg != null)
						cg.connectionFailed();
					break;
				}
				// can't happen with a String object but need the catch anyhow
				catch (ClassNotFoundException e2) {
				}
			}
		}
	}
}
