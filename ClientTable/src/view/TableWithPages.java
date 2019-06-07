package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.ChatMessage;
import controller.Client;
import controller.UniversityController;

public class TableWithPages {

	Object[] headers = { "Р¤Р°РєСѓР»СЊС‚РµС‚", "РќР°Р·РІР°РЅРёРµ РєР°С„РµРґСЂС‹", "Р¤Р�Рћ РїСЂРµРїРѕРґР°РІР°С‚РµР»СЏ", "РЈС‡РµРЅРѕРµ Р·РІР°РЅРёРµ", "РЈС‡РµРЅР°СЏ СЃС‚РµРїРµРЅСЊ",
			"РЎС‚Р°Р¶ СЂР°Р±РѕС‚С‹" };


	Object[][] data;

	JTable table;
	public JScrollPane scroll;
	private Client client;
	private boolean connected;

	JPanel pane;

	JButton leftButton;
	JButton rightButton;
	JButton firstButton;
	JButton lastButton;
	JButton changeRowsButton;

	public JLabel lableNumberOfElements;
	public JLabel lableNumberOnPage;
	int currPage = 1;

	JLabel numberOfEl;
	JLabel currNumofEl;

	public static int numOfRows = 10;
	int numOfRowsEnd = numOfRows;
	int numOfRowsStart = 0;

	List<String[]> rowList;
	JPanel currenrPanel;

	WindowUserCom t;

	public TableWithPages(WindowUserCom t, List<String[]> rowList, JPanel currenrPanel) {

		this.rowList = rowList;
		this.t = t;
		this.currenrPanel = currenrPanel;
		//this.numOfRows = numOfRows;
		pane = new JPanel();
		if(rowList == null) {
			table = new JTable();
		}
		else {
			data = rowList.toArray(new String[0][]);
			//System.out.println("OOPSTable" + rowList.get(0)[0]);
			// System.out.println(data[1][1]);
			Vector<String[]> dataCurr = new Vector<String[]>();
			if (rowList.size() < numOfRows) {
				numOfRowsEnd = rowList.size();
			}
			for (int i = numOfRowsStart; i < numOfRowsEnd; i++) {
				dataCurr.add(new String[] { (String) data[i][0], (String) data[i][1], (String) data[i][2],
						(String) data[i][3], (String) data[i][4], (String) data[i][5] });
			}

			String[][] dataCurr1 = dataCurr.toArray(new String[0][]);
			table = new JTable(dataCurr1, headers);
			
			if (rowList.size() >= numOfRows) {
				lableNumberOfElements = new JLabel(
						"Number of elementson page: " + numOfRows + " from total " + rowList.size());
			} else {
				lableNumberOfElements = new JLabel(
						"Number of elementson page: " + rowList.size() + " from total " + rowList.size());
			}
			pane.add(lableNumberOfElements);
			// lableNumberOfElements.setBounds(750, 100, 200, 70);
			if (rowList.size() % numOfRows != 0) {
				lableNumberOnPage = new JLabel(
						" Number of page: " + currPage + " from " + (rowList.size() / numOfRows + 1));
			} else {
				lableNumberOnPage = new JLabel(" Number of page: " + currPage + "from " + (rowList.size() / numOfRows));
			}
			pane.add(lableNumberOnPage);
			
		}
		leftButton = new JButton("Go to privious");
		rightButton = new JButton("Go to next");
		firstButton = new JButton("Go to head");
		lastButton = new JButton("Go to tail");
		changeRowsButton = new JButton("Change rows");
		
		
		scroll = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(new Dimension(400, 500));
		table.setRowHeight(50);
		currenrPanel.setLayout(new BoxLayout(currenrPanel, BoxLayout.Y_AXIS));
		currenrPanel.add(scroll);
		currenrPanel.add(pane);
		pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
		pane.add(leftButton);
		pane.add(Box.createRigidArea(new Dimension(10, 0)));
		pane.add(rightButton);
		// leftButton.setBounds(1600, 100, 70, 70);
		// rightButton.setBounds(1500, 100, 70, 70);
		pane.add(Box.createRigidArea(new Dimension(10, 0)));
		pane.add(firstButton);
		pane.add(Box.createRigidArea(new Dimension(10, 0)));
		// firstButton.setBounds(1700, 100, 100, 70);
		pane.add(lastButton);
		pane.add(Box.createRigidArea(new Dimension(10, 0)));
		// lastButton.setBounds(1370, 100, 100, 70);
		pane.add(changeRowsButton);
		pane.add(Box.createRigidArea(new Dimension(10, 0)));
		// changeRowsButton.setBounds(1200, 100, 150, 70);
		
		currenrPanel.repaint();
		
		// System.out.println(data[1][1]+ "problem");

		listenerTurnLeft(rightButton);
		listenerTurnRight(leftButton, rowList);
		listenerToHead(firstButton, rowList);
		listenerToTail(lastButton, rowList);
		listenerChangeNum(changeRowsButton);
	}

	public void updateTable(List<String[]> university, JPanel main) {
	
		data = university.toArray(new String[0][]);
		List<String[]> dataCurr = new ArrayList<String[]>();
		for (int i = numOfRowsStart; i < numOfRowsEnd; i++) {
			dataCurr.add(new String[] { (String) data[i][0], (String) data[i][1], (String) data[i][2],
					(String) data[i][3], (String) data[i][4], (String) data[i][5] });
		}
		String[][] dataCurr1 = dataCurr.toArray(new String[0][]);

		table = new JTable(dataCurr1, headers);
		scroll.setViewportView(table);
	}
	
	public void delete() {
		leftButton.setVisible(false);
		rightButton.setVisible(false);
		firstButton.setVisible(false);
		lastButton.setVisible(false);
		changeRowsButton.setVisible(false);
		scroll.setVisible(false);
		lableNumberOfElements.setVisible(false);
		lableNumberOnPage.setVisible(false);
	}

	public void listenerTurnLeft(JButton button) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("New File Button begin");
				String username = "User";
				client = new Client(t.serverAdress, t.defaultPort, username, t);
				// test if we can start the Client
				if (!client.start())
					return;
				
				connected = true;
				client.sendMessage(new ChatMessage(ChatMessage.TURN_LEFT, numOfRows));
				System.out.println(numOfRows);
				try {
					client.thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.out.println("CREATE SMTH");
				String[][] dataCurr1 = client.rowList.toArray(new String[0][]);
				table = new JTable(dataCurr1, headers);
				currenrPanel.invalidate();
				currenrPanel.validate();
				currenrPanel.repaint();
				table.setPreferredScrollableViewportSize(new Dimension(400, 500));
				table.setRowHeight(50);
				scroll.setViewportView(table);
				System.out.println("Pressed");
				lableNumberOfElements.setText(client.lableNumberOfElements);
				lableNumberOnPage.setText(client.lableNumberOnPage);
				/*
				table = new JTable(dataCurr1, headers);
				table.repaint();
				table.setPreferredScrollableViewportSize(new Dimension(400, 500));
				table.setRowHeight(50);
				scroll.setViewportView(table);*/
			}
		};
		button.addActionListener(actionListener);
	}

	public void listenerTurnRight(JButton button, List<String[]> rowList) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("New File Button begin");
				String username = "User";
				client = new Client(t.serverAdress, t.defaultPort, username, t);
				// test if we can start the Client
				if (!client.start())
					return;
				
				connected = true;
				client.sendMessage(new ChatMessage(ChatMessage.TURN_RIGHT, numOfRows));
				System.out.println(numOfRows);
				try {
					client.thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.out.println("CREATE SMTH");
				String[][] dataCurr1 = client.rowList.toArray(new String[0][]);
				table = new JTable(dataCurr1, headers);
				currenrPanel.invalidate();
				currenrPanel.validate();
				currenrPanel.repaint();
				table.setPreferredScrollableViewportSize(new Dimension(400, 500));
				table.setRowHeight(50);
				scroll.setViewportView(table);
				System.out.println("Pressed");
				lableNumberOfElements.setText(client.lableNumberOfElements);
				lableNumberOnPage.setText(client.lableNumberOnPage);
				
			}
		};
		button.addActionListener(actionListener);
	}

	public void listenerToHead(JButton button, List<String[]> rowList) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("New File Button begin");
				String username = "User";
				client = new Client(t.serverAdress, t.defaultPort, username, t);
				// test if we can start the Client
				if (!client.start())
					return;
				
				connected = true;
				client.sendMessage(new ChatMessage(ChatMessage.GO_TO_HEAD, numOfRows));
				System.out.println(numOfRows);
				try {
					client.thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.out.println("CREATE SMTH");
				String[][] dataCurr1 = client.rowList.toArray(new String[0][]);
				table = new JTable(dataCurr1, headers);
				currenrPanel.invalidate();
				currenrPanel.validate();
				currenrPanel.repaint();
				table.setPreferredScrollableViewportSize(new Dimension(400, 500));
				table.setRowHeight(50);
				scroll.setViewportView(table);
				System.out.println("Pressed");
				lableNumberOfElements.setText(client.lableNumberOfElements);
				lableNumberOnPage.setText(client.lableNumberOnPage);

				
			}
		};
		button.addActionListener(actionListener);
	}

	public void listenerToTail(JButton button, List<String[]> rowList) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("New File Button begin");
				String username = "User";
				client = new Client(t.serverAdress, t.defaultPort, username, t);
				// test if we can start the Client
				if (!client.start())
					return;
				
				connected = true;
				client.sendMessage(new ChatMessage(ChatMessage.GO_TO_TAIL, numOfRows));
				System.out.println(numOfRows);
				try {
					client.thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.out.println("CREATE SMTH");
				
				String[][] dataCurr1 = client.rowList.toArray(new String[0][]);
				table = new JTable(dataCurr1, headers);
				currenrPanel.invalidate();
				currenrPanel.validate();
				currenrPanel.repaint();
				table.setPreferredScrollableViewportSize(new Dimension(400, 500));
				table.setRowHeight(50);
				scroll.setViewportView(table);
				System.out.println("Pressed");
				lableNumberOfElements.setText(client.lableNumberOfElements);
				lableNumberOnPage.setText(client.lableNumberOnPage);

			}
		};
		button.addActionListener(actionListener);
	}

	public void listenerChangeNum(JButton button) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pressed");
				String[] rows = { "5", "10", "20" };
				JPanel curr = new JPanel();
				JComboBox<String> comboBoxD = new JComboBox<String>(rows);
				curr.add(comboBoxD);
				int result = JOptionPane.showConfirmDialog(null, curr, "Р’С‹Р±РµСЂРёС‚Рµ РєРѕР»РёС‡РµСЃС‚РІРѕ СЌР»РµРјРµРЅС‚РѕРІ РЅР° СЃС‚СЂР°РЅРёС†Рµ",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					numOfRows = Integer.parseInt((String) comboBoxD.getSelectedItem());
					numOfRowsEnd = numOfRows;
					numOfRowsStart = 0;
					System.out.println(numOfRows);
				}
			}
		};
		button.addActionListener(actionListener);
	}
	
	public void listenerlect(JButton button) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Open File Button begin");
				String username = "User";

				// try creating a new Client with GUI
				client = new Client("localhost", 1500, username, t);
				// test if we can start the Client
				if (!client.start())
					return;
				/*
				 * tf.setText(""); label.setText("Enter your message below");
				 */
				
				
				JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
				fileChooser.showSaveDialog(null);
				String FileName = fileChooser.getSelectedFile().getAbsolutePath();
				// Open the save dialog
				
				
				
				
				connected = true;
				System.out.println("Open Fiel");
				client.sendMessage(new ChatMessage(ChatMessage.FILE_OPEN, FileName));
				//System.out.println("Trying" + uni.get(0)[0]);
				/*try {
					paintGUI();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				//System.out.println("OOPS" + uni.get(0)[0]);
				//currentTableWithLecturers = new TableWithPages(cg, uni, mainPanel);
				//System.out.println("End of Open file Button proccessing");
			}
		};
		button.addActionListener(actionListener);
	}
	
	

}
