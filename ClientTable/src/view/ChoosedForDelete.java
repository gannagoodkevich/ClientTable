package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;

import controller.ChatMessage;
import controller.Client;
import controller.UniversityController;

public class ChoosedForDelete {
	Object[] headers = { "Факультет", "Название кафедры", "ФИО преподавателя", "Ученое звание", "Ученая степень",
			"Стаж работы" };
	JScrollPane scroll;
	JTable table;
	WindowUserCom currentWindow;
	Client client;
	boolean connected;

	public ChoosedForDelete(WindowUserCom currentWindow) throws IOException {
		this.currentWindow = currentWindow;
	}

	public void listenerSearchChooser(JMenuItem menu, int index) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (index == 0) {
					try {
						listenerSearchByFaculty();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (index == 1) {
					try {
						listenerSearchByName();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (index == 2) {
					try {
						listenerSearchByYear();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}

		};
		menu.addActionListener(actionListener);

	}

	public void listenerSearchChooser(JButton button) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel myPanel = new JPanel();
				myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
				Object[] options = { "Faculty && DegreeName", "Name && Department", "Year" };
				int result = JOptionPane.showOptionDialog(null, myPanel, "Введите данные о новом преподавателе",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				if (result == 0) {
					try {
						listenerSearchByFaculty();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (result == 1) {
					try {
						listenerSearchByName();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (result == 2) {
					try {
						listenerSearchByYear();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		};
		button.addActionListener(actionListener);
	}

	public void listenerSearchByFaculty() throws IOException {
		
		UniversityController uniController = new UniversityController(currentWindow.uni);
		String[] faculties = uniController.getFaculties().toArray(new String[0]);
		String[] degreeT = uniController.getDegrees().toArray(new String[0]);
		JComboBox<String> comboBoxF = new JComboBox<String>(faculties);
		JComboBox<String> comboBoxDn = new JComboBox<String>(degreeT);
		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		myPanel.add(new JLabel("Факультет:"));
		myPanel.add(comboBoxF);
		myPanel.add(new JLabel("Ученое звание:"));
		myPanel.add(comboBoxDn);
		int result = JOptionPane.showConfirmDialog(null, myPanel, "Выберите способ поиска",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			client = new Client(currentWindow.serverAdress, 1500, "HAHAHA", currentWindow);
			// test if we can start the Client
			if (!client.start())
				return;
			
			connected = true;
			client.sendMessage(new ChatMessage(ChatMessage.DELETE_FAC, (String) comboBoxF.getSelectedItem(), (String) comboBoxDn.getSelectedItem()));
		
		}

	}

	public void listenerSearchByName() throws IOException {

		JTextField nameField = new JTextField();

		UniversityController uniContr = new UniversityController(currentWindow.uni);
		String[] departments = uniContr.getDepartments().toArray(new String[0]);

		JComboBox<String> comboBoxD = new JComboBox<String>(departments);

		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		myPanel.add(new JLabel("Фамилия:"));
		myPanel.add(nameField);

		myPanel.add(new JLabel("Кафедра:"));
		myPanel.add(comboBoxD);

		int result = JOptionPane.showConfirmDialog(null, myPanel, "Введите данные для поиска",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			client = new Client(currentWindow.serverAdress, 1500, "HAHAHA", currentWindow);
			// test if we can start the Client
			if (!client.start())
				return;
			
			connected = true;
			client.sendMessage(new ChatMessage(ChatMessage.DELETE_NAME, (String) comboBoxD.getSelectedItem(), nameField.getText()));
		
				
		}

	}

	public void listenerSearchByYear() throws IOException {

		JTextField yearFieldFrom = new JTextField(10);
		JTextField yearFieldTo = new JTextField(10);

		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

		myPanel.add(new JLabel("Стаж работы с:"));
		myPanel.add(yearFieldFrom);
		myPanel.add(new JLabel("Стаж работы по:"));
		myPanel.add(yearFieldTo);
		JPanel pan = new JPanel();
		int result = JOptionPane.showConfirmDialog(null, myPanel, "Введите данные для поиска и удаления",
				JOptionPane.OK_CANCEL_OPTION);
		String uare1 = yearFieldFrom.getText();
		String uare2 = yearFieldTo.getText();

		if (result == JOptionPane.OK_OPTION) {
			client = new Client(currentWindow.serverAdress, 1500, "HAHAHA", currentWindow);
			// test if we can start the Client
			if (!client.start())
				return;
			
			connected = true;
			client.sendMessage(new ChatMessage(ChatMessage.DELETE_YEAR, uare1, uare2));
		
				
		}

	}

	/*
	 * public void updateTable(Uni uni) { UniversityController uniContr = new
	 * UniversityController(); List<String[]> rowList = uniContr.getUniversity(uni);
	 * String[][] data = rowList.toArray(new String[0][]); table = new JTable(data,
	 * headers); table.repaint(); table.setPreferredScrollableViewportSize(new
	 * Dimension(400, 500)); table.setRowHeight(50); scroll.setViewportView(table);
	 * }
	 */

}
