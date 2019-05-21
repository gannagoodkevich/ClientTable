package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import controller.DeleteController;
import controller.SearchController;
import controller.UniversityController;

public class ChooserForSearch {

	Object[] headers = { "���������", "�������� �������", "��� �������������", "������ ������", "������ �������",
			"���� ������" };
	JScrollPane scroll;
	JTable table;
	WindowUserCom t;

	ChooserForSearch(WindowUserCom t) {
		this.t = t;
	}

	public void listenerSearchChooser(JMenuItem menu, int index) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel myPanel = new JPanel();
				myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
				if (index == 0) {
					try {
						listenerSearchByFaculty();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (index == 1) {
					try {
						listenerSearchByName();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (index == 2) {
					try {
						listenerSearchByYear();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
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
				//System.out.println(t.currentUniversity.getFaculty(0).getTitle());
				Object[] options = { "Faculty && DegreeName", "Name && Department", "Year" };
				int result = JOptionPane.showOptionDialog(null, myPanel, "�������� ������ ������ ��� ��������",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				if (result == 0) {
					try {
						listenerSearchByFaculty();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (result == 1) {
					try {
						listenerSearchByName();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (result == 2) {
					try {
						listenerSearchByYear();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};
		button.addActionListener(actionListener);
	}

	public void listenerSearchByFaculty() throws IOException {
		UniversityController uniController = new UniversityController();
		String[] faculties = uniController.getFaculties().toArray(new String[0]);
		String[] degreeT = uniController.getDegrees().toArray(new String[0]);
		JComboBox<String> comboBoxF = new JComboBox<String>(faculties);
		JComboBox<String> comboBoxDn = new JComboBox<String>(degreeT);

		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

		myPanel.add(new JLabel("���������:"));
		myPanel.add(comboBoxF);

		myPanel.add(new JLabel("������ ������:"));
		myPanel.add(comboBoxDn);

		int result = JOptionPane.showConfirmDialog(null, myPanel, "������� ������ ��� ������ � ��������",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			SearchController searchcontr = new SearchController();
			List<String[]> rowList = searchcontr.listenerSearchByFaculty((String) comboBoxF.getSelectedItem(),
					(String) comboBoxDn.getSelectedItem());
			// String[][] data = rowList.toArray(new String[0][]);
			JPanel pan = new JPanel();

			/*
			 * JTable table1 = new JTable(data, headers); scroll = new JScrollPane(table1);
			 * table1.setPreferredScrollableViewportSize(new Dimension(1800, 500));
			 * table1.setRowHeight(50);
			 */
			// pan.setLayout(null);
			TableWithPages currTable = new TableWithPages(t, rowList, pan);
			// pan.add(currTable.scroll);

			UIManager.put("OptionPane.minimumSize", new Dimension(1800, 500));

			JOptionPane.showMessageDialog(null, pan, "Table", JOptionPane.OK_CANCEL_OPTION);

		}

	}

	public void listenerSearchByName() throws IOException {

		JTextField nameField = new JTextField();

		UniversityController uniContr = new UniversityController();
		String[] departments = uniContr.getDepartments().toArray(new String[0]);

		JComboBox<String> comboBoxD = new JComboBox<String>(departments);

		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		myPanel.add(new JLabel("�������:"));
		myPanel.add(nameField);

		myPanel.add(new JLabel("�������:"));
		myPanel.add(comboBoxD);

		int result = JOptionPane.showConfirmDialog(null, myPanel, "������� ������ ��� ������",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			SearchController searchcontr = new SearchController();
			List<String[]> rowList = searchcontr.listenerSearchByName( (String) comboBoxD.getSelectedItem(),
					nameField.getText());

			// String[][] data = rowList.toArray(new String[0][]);
			JPanel pan = new JPanel();

			/*
			 * JTable table1 = new JTable(data, headers); scroll = new JScrollPane(table1);
			 * table1.setPreferredScrollableViewportSize(new Dimension(1800, 500));
			 * table1.setRowHeight(50); pan.add(scroll);
			 */
			TableWithPages currTable = new TableWithPages(t, rowList, pan);
			UIManager.put("OptionPane.minimumSize", new Dimension(1800, 500));
			JOptionPane.showMessageDialog(null, pan, "Table", JOptionPane.OK_CANCEL_OPTION);

		}
	}

	public void listenerSearchByYear() throws IOException {

		JTextField yearFieldFrom = new JTextField(10);
		JTextField yearFieldTo = new JTextField(10);

		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

		myPanel.add(new JLabel("���� ������ �:"));
		myPanel.add(yearFieldFrom);
		myPanel.add(new JLabel("���� ������ ��:"));
		myPanel.add(yearFieldTo);
		JPanel pan = new JPanel();
		int result = JOptionPane.showConfirmDialog(null, myPanel, "������� ������ ��� ������ � ��������",
				JOptionPane.OK_CANCEL_OPTION);
		String uare1 = yearFieldFrom.getText();
		String uare2 = yearFieldTo.getText();
		if (result == JOptionPane.OK_OPTION) {
			SearchController searchcontr = new SearchController();
			List<String[]> rowList = searchcontr.listenerSearchByYear(uare1, uare2);
			TableWithPages currTable = new TableWithPages(t, rowList, pan);
			UIManager.put("OptionPane.minimumSize", new Dimension(1800, 500));

			JOptionPane.showMessageDialog(null, pan, "Table", JOptionPane.OK_CANCEL_OPTION);
		}
	}

}