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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.UniversityController;

public class TableWithPages {

	Object[] headers = { "Р¤Р°РєСѓР»СЊС‚РµС‚", "РќР°Р·РІР°РЅРёРµ РєР°С„РµРґСЂС‹", "Р¤Р�Рћ РїСЂРµРїРѕРґР°РІР°С‚РµР»СЏ", "РЈС‡РµРЅРѕРµ Р·РІР°РЅРёРµ", "РЈС‡РµРЅР°СЏ СЃС‚РµРїРµРЅСЊ",
			"РЎС‚Р°Р¶ СЂР°Р±РѕС‚С‹" };


	Object[][] data;

	JTable table;
	public JScrollPane scroll;

	JPanel pane;

	JButton leftButton;
	JButton rightButton;
	JButton firstButton;
	JButton lastButton;
	JButton changeRowsButton;

	JLabel lableNumberOfElements;
	JLabel lableNumberOnPage;
	int currPage = 1;

	JLabel numberOfEl;
	JLabel currNumofEl;

	public int numOfRows = 10;
	int numOfRowsEnd = numOfRows;
	int numOfRowsStart = 0;

	List<String[]> rowList;
	JPanel currenrPanel;

	WindowUserCom t;

	public TableWithPages(WindowUserCom t, List<String[]> rowList, JPanel currenrPanel) {

		this.rowList = rowList;
		this.t = t;
		this.currenrPanel = currenrPanel;

		data = rowList.toArray(new String[0][]);
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
		scroll = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(new Dimension(400, 500));
		table.setRowHeight(50);
		currenrPanel.setLayout(new BoxLayout(currenrPanel, BoxLayout.Y_AXIS));
		currenrPanel.add(scroll);
		pane = new JPanel();
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
		// System.out.println(data[1][1]+ "problem");

		listenerTurnLeft(rightButton, rowList);
		listenerTurnRight(leftButton, rowList);
		listenerToHead(firstButton, rowList);
		listenerToTail(lastButton, rowList);
		listenerChangeNum(changeRowsButton);
	}

	public void updateTable(List<String[]> university) {
	
		data = university.toArray(new String[0][]);
		List<String[]> dataCurr = new ArrayList<String[]>();
		for (int i = numOfRowsStart; i < numOfRowsEnd; i++) {
			dataCurr.add(new String[] { (String) data[i][0], (String) data[i][1], (String) data[i][2],
					(String) data[i][3], (String) data[i][4], (String) data[i][5] });
		}
		String[][] dataCurr1 = dataCurr.toArray(new String[0][]);

		table = new JTable(dataCurr1, headers);
		table.repaint();
		table.setRowHeight(50);
		scroll.setViewportView(table);
	}

	public void listenerTurnLeft(JButton button, List<String[]> rowList) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pressed");
				data = rowList.toArray(new String[0][]);
				/// lost data
				// System.out.println(data[1][1]);
				//System.out.println(t.currentUniversity.getFaculty(0).getTitle());
				if (rowList.size() <= numOfRows) {
					numOfRowsEnd = rowList.size();
					numOfRowsStart = 0;
					lableNumberOfElements
							.setText("Number of elementson page: " + rowList.size() + " from total " + rowList.size());
					lableNumberOnPage.setText(" Number of page: " + 1 + " from " + (rowList.size() / numOfRows + 1));
				} else {
					if (numOfRowsEnd != rowList.size()) {
						if (numOfRowsEnd <= rowList.size() - numOfRows) {
							numOfRowsEnd += numOfRows;
							numOfRowsStart += numOfRows;
							currPage += 1;
							lableNumberOnPage.setText(
									" Number of page: " + (currPage) + " from " + (rowList.size() / numOfRows + 1));
							lableNumberOfElements.setText(
									"Number of elementson page: " + numOfRows + " from total " + rowList.size());
						} else {
							numOfRowsStart = numOfRowsEnd;
							numOfRowsEnd = rowList.size();
							currPage += 1;
							lableNumberOnPage.setText(" Number of page: " + (rowList.size() / numOfRows + 1) + " from "
									+ (rowList.size() / numOfRows + 1));
							lableNumberOfElements.setText("Number of elementson page: " + rowList.size() % numOfRows
									+ " from total " + rowList.size());
						}
					} else {
						numOfRowsEnd = numOfRows;
						numOfRowsStart = 0;
						currPage = 1;
						lableNumberOnPage
								.setText(" Number of page: " + currPage + " from " + (rowList.size() / numOfRows + 1));
						lableNumberOfElements
								.setText("Number of elementson page: " + numOfRows + " from total " + rowList.size());
					}
				}
				// data = rowList.toArray(new String[0][]);
				List<String[]> dataCurr = new ArrayList<String[]>();
				for (int i = numOfRowsStart; i < numOfRowsEnd; i++) {
					dataCurr.add(new String[] { (String) data[i][0], (String) data[i][1], (String) data[i][2],
							(String) data[i][3], (String) data[i][4], (String) data[i][5] });
				}
				String[][] dataCurr1 = dataCurr.toArray(new String[0][]);
				table = new JTable(dataCurr1, headers);
				table.repaint();
				table.setPreferredScrollableViewportSize(new Dimension(400, 500));
				table.setRowHeight(50);
				scroll.setViewportView(table);
			}
		};
		button.addActionListener(actionListener);
	}

	public void listenerTurnRight(JButton button, List<String[]> rowList) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pressed");
			
				data = rowList.toArray(new String[0][]);

				if (rowList.size() <= numOfRows) {
					numOfRowsEnd = rowList.size();
					numOfRowsStart = 0;
					lableNumberOnPage.setText(" Number of page: " + 1 + " from " + (rowList.size() / numOfRows + 1));
					lableNumberOfElements
							.setText("Number of elementson page: " + rowList.size() + " from total " + rowList.size());
				} else {
					if (numOfRowsEnd == numOfRows) {
						numOfRowsEnd = rowList.size();
						numOfRowsStart = numOfRowsEnd - rowList.size() % numOfRows;
						currPage = (rowList.size() / numOfRows + 1);
						lableNumberOnPage
								.setText(" Number of page: " + currPage + " from " + (rowList.size() / numOfRows + 1));
						lableNumberOfElements.setText("Number of elementson page: " + rowList.size() % numOfRows
								+ " from total " + rowList.size());
					} else {
						if (numOfRowsEnd != rowList.size()) {
							if (numOfRowsEnd >= 2 * numOfRows) {
								numOfRowsEnd -= numOfRows;
								numOfRowsStart -= numOfRows;
							} else {
								numOfRowsStart = 0;
								numOfRowsEnd = numOfRows;
							}
							currPage -= 1;
							lableNumberOnPage.setText(
									" Number of page: " + currPage + " from " + (rowList.size() / numOfRows + 1));
							lableNumberOfElements.setText(
									"Number of elementson page: " + numOfRows + " from total " + rowList.size());
						} else {
							numOfRowsEnd = rowList.size() - rowList.size() % numOfRows;
							numOfRowsStart = numOfRowsEnd - numOfRows;
							currPage -= 1;
							lableNumberOnPage.setText(
									" Number of page: " + currPage + " from " + (rowList.size() / numOfRows + 1));
							lableNumberOfElements.setText(
									"Number of elementson page: " + numOfRows + " from total " + rowList.size());
						}
					}
				}
				// data = rowList.toArray(new String[0][]);
				List<String[]> dataCurr = new ArrayList<String[]>();
				for (int i = numOfRowsStart; i < numOfRowsEnd; i++) {
					dataCurr.add(new String[] { (String) data[i][0], (String) data[i][1], (String) data[i][2],
							(String) data[i][3], (String) data[i][4], (String) data[i][5] });
				}
				String[][] dataCurr1 = dataCurr.toArray(new String[0][]);
				table = new JTable(dataCurr1, headers);
				table.repaint();
				currPage++;
				table.setPreferredScrollableViewportSize(new Dimension(400, 500));
				table.setRowHeight(50);
				scroll.setViewportView(table);
			}
		};
		button.addActionListener(actionListener);
	}

	public void listenerToHead(JButton button, List<String[]> rowList) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pressed");

				data = rowList.toArray(new String[0][]);
				if (rowList.size() > numOfRows) {
					currPage = 1;
					lableNumberOnPage
							.setText(" Number of page: " + currPage + " from " + (rowList.size() / numOfRows + 1));
					lableNumberOfElements
							.setText("Number of elementson page: " + numOfRows + " from total " + rowList.size());
				} else {
					currPage = 1;
					lableNumberOnPage
							.setText(" Number of page: " + currPage + " from " + (rowList.size() / numOfRows + 1));
					lableNumberOfElements
							.setText("Number of elementson page: " + rowList.size() + " from total " + rowList.size());
				}
				numOfRowsEnd = numOfRows;
				numOfRowsStart = 0;
				data = rowList.toArray(new String[0][]);
				List<String[]> dataCurr = new ArrayList<String[]>();
				for (int i = numOfRowsStart; i < numOfRowsEnd; i++) {
					dataCurr.add(new String[] { (String) data[i][0], (String) data[i][1], (String) data[i][2],
							(String) data[i][3], (String) data[i][4], (String) data[i][5] });
				}
				String[][] dataCurr1 = dataCurr.toArray(new String[0][]);
				table = new JTable(dataCurr1, headers);
				table.repaint();
				table.setPreferredScrollableViewportSize(new Dimension(400, 500));
				table.setRowHeight(50);
				scroll.setViewportView(table);
			}
		};
		button.addActionListener(actionListener);
	}

	public void listenerToTail(JButton button, List<String[]> rowList) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pressed to head");

				data = rowList.toArray(new String[0][]);

				if (rowList.size() % numOfRows != 0) {
					numOfRowsEnd = rowList.size();
					numOfRowsStart = rowList.size() - rowList.size() % numOfRows;
					currPage = (rowList.size() / numOfRows + 1);
					lableNumberOnPage
							.setText(" Number of page: " + currPage + " from " + (rowList.size() / numOfRows + 1));
					lableNumberOfElements.setText("Number of elementson page: " + rowList.size() % numOfRows
							+ " from total " + rowList.size());
				} else {
					numOfRowsEnd = rowList.size();
					numOfRowsStart = rowList.size() - numOfRows;
					currPage = (rowList.size() / numOfRows + 1);
					lableNumberOnPage
							.setText(" Number of page: " + currPage + " from " + (rowList.size() / numOfRows + 1));
					lableNumberOfElements
							.setText("Number of elementson page: " + numOfRows + " from total " + rowList.size());

				}
				data = rowList.toArray(new String[0][]);
				List<String[]> dataCurr = new ArrayList<String[]>();
				for (int i = numOfRowsStart; i < numOfRowsEnd; i++) {
					dataCurr.add(new String[] { (String) data[i][0], (String) data[i][1], (String) data[i][2],
							(String) data[i][3], (String) data[i][4], (String) data[i][5] });
				}
				String[][] dataCurr1 = dataCurr.toArray(new String[0][]);
				table = new JTable(dataCurr1, headers);
				table.repaint();
				table.setPreferredScrollableViewportSize(new Dimension(400, 500));
				table.setRowHeight(50);
				scroll.setViewportView(table);
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
				}
			}
		};
		button.addActionListener(actionListener);
	}

}
