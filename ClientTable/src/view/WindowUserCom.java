package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import controller.DeleteController;
import controller.UniversityController;

public class WindowUserCom {

	List<String[]> uni;
	public JFrame mainFrame;
	public String FileName;
	public TableWithPages currentTableWithLecturers;
	public List<JMenuItem> itemsSearch;
	public List<JMenuItem> itemsDelete;

	public WindowUserCom() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		UniversityController uniContr = new UniversityController();
		this.uni = uniContr.getUniversity();
		
		mainFrame = new JFrame();
		List<JMenu> menusFile = new ArrayList<JMenu>();
		menusFile.add(new JMenu("File"));
		List<JMenuItem> itemsFile = new ArrayList<JMenuItem>();
		itemsFile.add(new JMenuItem("Create new"));
		itemsFile.add(new JMenuItem("Open"));
		List<JMenu> menusSearch = new ArrayList<JMenu>();
		menusSearch.add(new JMenu("Search"));
		itemsSearch = new ArrayList<JMenuItem>();
		itemsSearch.add(new JMenuItem("by faculty && degree"));
		itemsSearch.add(new JMenuItem("by name && department"));
		itemsSearch.add(new JMenuItem("by year"));
		for (JMenuItem item : itemsFile) {
			menusFile.get(0).add(item);
		}
		for (JMenuItem item : itemsSearch) {
			menusSearch.get(0).add(item);
		}
		JMenuBar menuBar = new JMenuBar();
		for (JMenu jmemu : menusFile) {
			menuBar.add(jmemu);
		}
		for (JMenu jmemu : menusSearch) {
			menuBar.add(jmemu);
		}
		List<JMenu> menusDelete = new ArrayList<JMenu>();
		menusDelete.add(new JMenu("Delete"));
		itemsDelete = new ArrayList<JMenuItem>();
		itemsDelete.add(new JMenuItem("by faculty && degree"));
		itemsDelete.add(new JMenuItem("by name && department"));
		itemsDelete.add(new JMenuItem("by year"));
		for (JMenuItem item : itemsDelete) {
			menusDelete.get(0).add(item);
		}
		for (JMenu jmemu : menusDelete) {
			menuBar.add(jmemu);
		}
		List<JMenu> menusAdd = new ArrayList<JMenu>();
		menusAdd.add(new JMenu("Add"));
		List<JMenuItem> itemsAdd = new ArrayList<JMenuItem>();
		itemsAdd.add(new JMenuItem("add new lecturer"));
		for (JMenuItem item : itemsAdd) {
			menusAdd.get(0).add(item);
		}
		for (JMenu jmemu : menusAdd) {
			menuBar.add(jmemu);
		}
		mainFrame.setJMenuBar(menuBar);

		openFile(itemsFile.get(1));
		createNewFile(itemsFile.get(0));
		listenerAdd(itemsAdd.get(0));
	}

	public static void run(final WindowUserCom frame, final int wigth, final int hight) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.mainFrame.setTitle(frame.getClass().getSimpleName());
				frame.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.mainFrame.setSize(wigth, hight);
				frame.mainFrame.setVisible(true);
			}
		});
	}

	public void paintGUI() throws IOException {
		JButton addButton = new JButton("Add");
		JButton searchButton = new JButton("Search");
		JButton deleteButton = new JButton("Delete");
		JPanel mainPanel = new JPanel();
		mainFrame.add(mainPanel, BorderLayout.NORTH);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		// System.out.println(currentUniversity.getFaculty(0).getTitle() +
		// "paintGuiBefore");
		currentTableWithLecturers = new TableWithPages(this, uni, mainPanel);
		// System.out.println(currentUniversity.getFaculty(0).getTitle() +
		// "paintGuiAfter");
		// mainPanel.setLayout(null);
		// mainFrame.add(currentTableWithLecturers.scroll,
		// BorderLayout.BEFORE_FIRST_LINE);
		// mainPanel.setBounds(50, 550, 1800, 500);
		// mainPanel.setLayout(null);
		JPanel sPane = new JPanel();
		mainPanel.add(sPane);
		sPane.setLayout(new BoxLayout(sPane, BoxLayout.X_AXIS));
		sPane.add(Box.createRigidArea(new Dimension(0, 50)));
		sPane.add(addButton);
		sPane.add(Box.createRigidArea(new Dimension(10, 0)));
		// addButton.setBounds(0, 100, 100, 70);
		sPane.add(searchButton);
		sPane.add(Box.createRigidArea(new Dimension(10, 0)));
		// searchButton.setBounds(150, 100, 100, 70);
		sPane.add(deleteButton);
		sPane.add(Box.createRigidArea(new Dimension(10, 0)));
		// deleteButton.setBounds(300, 100, 100, 70);
		// lableNumberOnPage.setBounds(550, 100, 200, 70);
		listenerAdd(addButton);
		ChooserForSearch chooser = new ChooserForSearch(this);
		chooser.listenerSearchChooser(searchButton);
		chooser.listenerSearchChooser(itemsSearch.get(0), 0);
		chooser.listenerSearchChooser(itemsSearch.get(1), 1);
		chooser.listenerSearchChooser(itemsSearch.get(2), 2);
		ChoosedForDelete delete = new ChoosedForDelete(this);
		delete.listenerSearchChooser(deleteButton);
		delete.listenerSearchChooser(itemsDelete.get(0), 0);
		delete.listenerSearchChooser(itemsDelete.get(1), 1);
		delete.listenerSearchChooser(itemsDelete.get(2), 2);
		mainFrame.setVisible(true);
	}

	public void createNewFile(JMenuItem menu) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// closing resources
				System.out.println("New File Button begin");
				/*try {
					Client client = new Client();

					// the following loop performs the exchange of
					// information between client and client handler
					while (true) {
						//Client client = new Client();
						System.out.println(client.dis.readUTF());
						String tosend = client.scn.nextLine();
						client.dos.writeUTF(tosend);
						if (tosend.equals("Exit")) {
							System.out.println("Closing this connection : " + client.s);
							System.out.println("Connection closed");
							break;
						}
						// If client sends exit,close this connection
						// and then break from the while loop

						String received = client.dis.readUTF();
						System.out.println(received);
						// printing date or time as requested by client

					}

					// closing resources
					client.scn.close();
					client.dis.close();
					client.dos.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				System.out.println("New File Button ended");
				/*String fileSeparator = System.getProperty("file.separator");
				JTextField nameOfFile = new JTextField(10);

				JPanel myPanel = new JPanel();
				myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

				myPanel.add(new JLabel("РЎС‚Р°Р¶ СЂР°Р±РѕС‚С‹ СЃ:"));
				myPanel.add(nameOfFile);
				JPanel pan = new JPanel();
				int result = JOptionPane.showConfirmDialog(null, myPanel,
						"Р’РІРµРґРёС‚Рµ РґР°РЅРЅС‹Рµ РґР»СЏ РїРѕРёСЃРєР° Рё СѓРґР°Р»РµРЅРёСЏ",
						JOptionPane.OK_CANCEL_OPTION);
				String relativePath = System.getProperty("user.dir") + fileSeparator + nameOfFile.getText() + ".xml";

				// server part for creating file

				/*
				 * File newFile = new File(relativePath); DocumentBuilderFactory
				 * documentBuilderFactory = DocumentBuilderFactory.newInstance();
				 * DocumentBuilder documentBuilder; try { documentBuilder =
				 * documentBuilderFactory.newDocumentBuilder(); Document document =
				 * documentBuilder.newDocument(); } catch (ParserConfigurationException e3) { //
				 * TODO Auto-generated catch block e3.printStackTrace(); }
				 * 
				 * FileName = nameOfFile.getText() + ".xml";
				 * 
				 * try { BufferedWriter bw = new BufferedWriter(new FileWriter(newFile, true));
				 * bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
				 * bw.close(); } catch (IOException e2) { // TODO Auto-generated catch block
				 * e2.printStackTrace(); }
				 */

				/*JTextField nameField = new JTextField(20);
				JTextField surnameField = new JTextField(10);
				JTextField secondNameField = new JTextField(10);
				JTextField yearField = new JTextField(10);
				JTextField faculty = new JTextField(20);
				JTextField department = new JTextField(10);
				String[] degreeN = { "РґРѕРєС‚РѕСЂ С„РёР·РёРєРѕ-РјР°С‚РµРјР°С‚РёС‡РµСЃРєРёС… РЅР°СѓРє",
						"РґРѕРєС‚РѕСЂ С‚РµС…РЅРёС‡РµСЃРєРёС… РЅР°СѓРє",
						"РґРѕРєС‚РѕСЂ РіСѓРјР°РЅРёС‚Р°СЂРЅС‹С… РЅР°СѓРє",
						"РєР°РЅРґРёРґР°С‚ С„РёР·РёРєРѕ-РјР°С‚РµРјР°С‚РёС‡РµСЃРєРёС… РЅР°СѓРє",
						"РєР°РЅРґРёРґР°С‚ С‚РµС…РЅРёС‡РµСЃРєРёС… РЅР°СѓРє",
						"РєР°РЅРґРёРґР°С‚ РіСѓРјР°РЅРёС‚Р°СЂРЅС‹С… РЅР°СѓРє",
						"РјР°РіРёСЃС‚СЂ С„РёР·РёРєРѕ-РјР°С‚РµРјР°С‚РёС‡РµСЃРєРёС… РЅР°СѓРє" };
				String[] degreeT = { "РїСЂРѕС„РµСЃСЃРѕСЂ", "РґРѕС†РµРЅС‚", "РїСЂРµРїРѕРґР°РІР°С‚РµР»СЊ",
						"СЃС‚Р°СЂС€РёР№ РїСЂРµРїРѕРґР°РІР°С‚РµР»СЊ", "Р°СЃСЃРёСЃС‚РµРЅС‚", "-" };
				JComboBox<String> comboBoxD = new JComboBox<String>(degreeN);
				JComboBox<String> comboBoxDn = new JComboBox<String>(degreeT);
				JPanel myPanel1 = new JPanel();
				myPanel1.setLayout(new BoxLayout(myPanel1, BoxLayout.Y_AXIS));
				myPanel1.add(new JLabel("Р¤Р°РјРёР»РёСЏ:"));
				myPanel1.add(nameField);
				myPanel1.add(new JLabel("Р�РјСЏ"));
				myPanel1.add(surnameField);
				myPanel1.add(new JLabel("РћС‚С‡РµСЃС‚РІРѕ:"));
				myPanel1.add(secondNameField);
				myPanel1.add(new JLabel("Р¤Р°РєСѓР»СЊС‚РµС‚:"));
				myPanel1.add(faculty);
				myPanel1.add(new JLabel("РљР°С„РµРґСЂР°:"));
				myPanel1.add(department);
				myPanel1.add(new JLabel("РЈС‡РµРЅРѕРµ Р·РІР°РЅРёРµ:"));
				myPanel1.add(comboBoxDn);
				myPanel1.add(new JLabel("РЈС‡РµРЅР°СЏ СЃС‚РµРїРµРЅСЊ:"));
				myPanel1.add(comboBoxD);
				myPanel1.add(new JLabel("РЎС‚Р°Р¶ СЂР°Р±РѕС‚С‹:"));
				myPanel1.add(yearField);

				int result1 = JOptionPane.showConfirmDialog(null, myPanel1,
						"Р’РІРµРґРёС‚Рµ РёРЅС„РѕСЂРјР°С†РёСЋ Рѕ РЅРѕРІРѕРј РїСЂРµРїРѕРґР°РІР°С‚РµР»Рµ",
						JOptionPane.OK_CANCEL_OPTION);
				if (result1 == JOptionPane.OK_OPTION) {
					// server part of adding new lecturer
					/*
					 * currentUniversity = new Uni("New Uni"); Faculty fac = new
					 * Faculty(faculty.getText()); Department dep = new
					 * Department(department.getText()); Lecturer lect = new
					 * Lecturer(nameField.getText(), surnameField.getText(),
					 * secondNameField.getText(), (String) comboBoxDn.getSelectedItem(), (String)
					 * comboBoxD.getSelectedItem(), yearField.getText());
					 * currentUniversity.addFaculty(fac); fac.addDepartment(dep);
					 * dep.addLecturer(lect);
					 * System.out.println(currentUniversity.getFaculty(0).getTitle()); try {
					 * DOMExample dom = new DOMExample(currentUniversity, FileName); } catch
					 * (ParserConfigurationException | TransformerException e1) {
					 * e1.printStackTrace(); }
					 */

				/*}
				try {
					paintGUI();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/

			}
		};
		menu.addActionListener(actionListener);

	}

	public void openFile(JMenuItem menu) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// send to server request for array of Uni in string form

				
				
				try {
					paintGUI();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
		menu.addActionListener(actionListener);
	}

	public void listenerAdd(JButton button) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				JTextField nameField = new JTextField(20);
				JTextField surnameField = new JTextField(10);
				JTextField secondNameField = new JTextField(10);
				JTextField yearField = new JTextField(10);
				UniversityController uniContr = null;
				try {
					uniContr = new UniversityController();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String[] faculties = uniContr.getFaculties().toArray(new String[0]);
				String[] departments = uniContr.getDepartments().toArray(new String[0]);
				String[] degreeTitles = uniContr.getDegrees().toArray(new String[0]);
				String[] degreeSciences = uniContr.getDegreesSc().toArray(new String[0]);

				JComboBox<String> comboBoxFacultes = new JComboBox<String>(faculties);
				JComboBox<String> comboBoxDepartments = new JComboBox<String>(departments);
				JComboBox<String> comboBoxdegreeTitle = new JComboBox<String>(degreeTitles);
				JComboBox<String> comboBoxDegreeScience = new JComboBox<String>(degreeSciences);

				JPanel myPanel = new JPanel();
				myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
				myPanel.add(new JLabel("Р¤Р°РјРёР»РёСЏ:"));
				myPanel.add(nameField);
				myPanel.add(new JLabel("Р�РјСЏ"));
				myPanel.add(surnameField);
				myPanel.add(new JLabel("РћС‚С‡РµСЃС‚РІРѕ:"));
				myPanel.add(secondNameField);
				myPanel.add(new JLabel("Р¤Р°РєСѓР»СЊС‚РµС‚:"));
				myPanel.add(comboBoxFacultes);
				myPanel.add(new JLabel("РљР°С„РµРґСЂР°:"));
				myPanel.add(comboBoxDepartments);
				myPanel.add(new JLabel("РЈС‡РµРЅРѕРµ Р·РІР°РЅРёРµ:"));
				myPanel.add(comboBoxDegreeScience);
				myPanel.add(new JLabel("РЈС‡РµРЅР°СЏ СЃС‚РµРїРµРЅСЊ:"));
				myPanel.add(comboBoxdegreeTitle);
				myPanel.add(new JLabel("РЎС‚Р°Р¶ СЂР°Р±РѕС‚С‹:"));
				myPanel.add(yearField);

				int result = JOptionPane.showConfirmDialog(null, myPanel,
						"Р’РІРµРґРёС‚Рµ РёРЅС„РѕСЂРјР°С†РёСЋ Рѕ РЅРѕРІРѕРј РїСЂРµРїРѕРґР°РІР°С‚РµР»Рµ",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					/*
					 * if (currentUniversity.getFacultyByName((String)
					 * comboBoxFacultes.getSelectedItem()) .getDepartmentByName((String)
					 * comboBoxDepartments.getSelectedItem()) .getLectureByInfo(nameField.getText(),
					 * surnameField.getText(), secondNameField.getText(), (String)
					 * comboBoxDegreeScience.getSelectedItem(), (String)
					 * comboBoxdegreeTitle.getSelectedItem(), yearField.getText()) == null) {
					 * Lecturer lecturer = new Lecturer(nameField.getText(), surnameField.getText(),
					 * secondNameField.getText(), (String) comboBoxDegreeScience.getSelectedItem(),
					 * (String) comboBoxdegreeTitle.getSelectedItem(), yearField.getText());
					 * currentUniversity.getFacultyByName((String)
					 * comboBoxFacultes.getSelectedItem()) .getDepartmentByName((String)
					 * comboBoxDepartments.getSelectedItem()) .addLecturer(lecturer); try {
					 * DOMExample dom = new DOMExample(currentUniversity, FileName);
					 * currentTableWithLecturers.updateTable(currentUniversity); } catch
					 * (ParserConfigurationException | TransformerException e1) {
					 * e1.printStackTrace(); } }
					 */
					// adding new lecturer in server part
				}

			}
		};
		// mytable.updateTable(uni);
		button.addActionListener(actionListener);
	}

	public void listenerAdd(JMenuItem menu) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				JTextField nameField = new JTextField(20);
				JTextField surnameField = new JTextField(10);
				JTextField secondNameField = new JTextField(10);
				JTextField yearField = new JTextField(10);
				UniversityController uniContr = null;
				try {
					uniContr = new UniversityController();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String[] faculties = uniContr.getFaculties().toArray(new String[0]);
				String[] departments = uniContr.getDepartments().toArray(new String[0]);
				String[] degreeTitles = uniContr.getDegrees().toArray(new String[0]);
				String[] degreeSciences = uniContr.getDegreesSc().toArray(new String[0]);

				JComboBox<String> comboBoxFacultes = new JComboBox<String>(faculties);
				JComboBox<String> comboBoxDepartments = new JComboBox<String>(departments);
				JComboBox<String> comboBoxdegreeTitle = new JComboBox<String>(degreeTitles);
				JComboBox<String> comboBoxDegreeScience = new JComboBox<String>(degreeSciences);

				JPanel myPanel = new JPanel();
				myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
				myPanel.add(new JLabel("Р¤Р°РјРёР»РёСЏ:"));
				myPanel.add(nameField);
				myPanel.add(new JLabel("Р�РјСЏ"));
				myPanel.add(surnameField);
				myPanel.add(new JLabel("РћС‚С‡РµСЃС‚РІРѕ:"));
				myPanel.add(secondNameField);
				myPanel.add(new JLabel("Р¤Р°РєСѓР»СЊС‚РµС‚:"));
				myPanel.add(comboBoxFacultes);
				myPanel.add(new JLabel("РљР°С„РµРґСЂР°:"));
				myPanel.add(comboBoxDepartments);
				myPanel.add(new JLabel("РЈС‡РµРЅРѕРµ Р·РІР°РЅРёРµ:"));
				myPanel.add(comboBoxDegreeScience);
				myPanel.add(new JLabel("РЈС‡РµРЅР°СЏ СЃС‚РµРїРµРЅСЊ:"));
				myPanel.add(comboBoxdegreeTitle);
				myPanel.add(new JLabel("РЎС‚Р°Р¶ СЂР°Р±РѕС‚С‹:"));
				myPanel.add(yearField);

				int result = JOptionPane.showConfirmDialog(null, myPanel,
						"Р’РІРµРґРёС‚Рµ РёРЅС„РѕСЂРјР°С†РёСЋ Рѕ РЅРѕРІРѕРј РїСЂРµРїРѕРґР°РІР°С‚РµР»Рµ",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					/*
					 * if (currentUniversity.getFacultyByName((String)
					 * comboBoxFacultes.getSelectedItem()) .getDepartmentByName((String)
					 * comboBoxDepartments.getSelectedItem()) .getLectureByInfo(nameField.getText(),
					 * surnameField.getText(), secondNameField.getText(), (String)
					 * comboBoxDegreeScience.getSelectedItem(), (String)
					 * comboBoxdegreeTitle.getSelectedItem(), yearField.getText()) == null) {
					 * Lecturer lecturer = new Lecturer(nameField.getText(), surnameField.getText(),
					 * secondNameField.getText(), (String) comboBoxDegreeScience.getSelectedItem(),
					 * (String) comboBoxdegreeTitle.getSelectedItem(), yearField.getText());
					 * currentUniversity.getFacultyByName((String)
					 * comboBoxFacultes.getSelectedItem()) .getDepartmentByName((String)
					 * comboBoxDepartments.getSelectedItem()) .addLecturer(lecturer); try {
					 * DOMExample dom = new DOMExample(currentUniversity, FileName);
					 * currentTableWithLecturers.updateTable(currentUniversity); } catch
					 * (ParserConfigurationException | TransformerException e1) {
					 * e1.printStackTrace(); } }
					 */
					// server new lecturer adding
				}

			}
		};
		menu.addActionListener(actionListener);
	}

}