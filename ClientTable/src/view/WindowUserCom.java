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
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import controller.ChatMessage;
import controller.Client;
import controller.UniversityController;

public class WindowUserCom {

	List<String[]> uni;
	public JFrame mainFrame;
	public String FileName;
	public TableWithPages currentTableWithLecturers;
	public List<JMenuItem> itemsSearch;
	public List<JMenuItem> itemsDelete;
	public int defaultPort;
	String serverAdress;
	private boolean connected;
	private Client client;
	List<JMenuItem> itemsFile;
	public JPanel mainPanel;
	public JPanel activePanel;
	
	JButton addButton;
	JButton searchButton;
	JButton deleteButton; 
	

	public WindowUserCom(String host, int port)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		// this.uni = uniContr.getUniversity();
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		inputPanel.add(new JLabel("Enter your number"));
		JTextField serverField = new JTextField();
		inputPanel.add(serverField);
		JOptionPane.showConfirmDialog(null, inputPanel, "Введите информацию о новом преподавателе",
				JOptionPane.OK_CANCEL_OPTION);
		this.serverAdress = serverField.getText();
		System.out.println(serverAdress);
		this.defaultPort = port;
		mainPanel = new JPanel();
		mainFrame = new JFrame();
		mainFrame.add(mainPanel, BorderLayout.NORTH);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		addButton = new JButton("Add");
		searchButton = new JButton("Search");
		deleteButton = new JButton("Delete");
		
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
		
		List<JMenu> menusFile = new ArrayList<JMenu>();
		menusFile.add(new JMenu("File"));
		itemsFile = new ArrayList<JMenuItem>();
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

		createNewFile(itemsFile.get(0), this);
		listenerAdd(itemsAdd.get(0));
		openFile(itemsFile.get(1), this);
		activePanel = mainPanel;
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
		
		System.out.println("END");

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

	public void createNewFile(JMenuItem menu, WindowUserCom cg) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("New File Button begin");
				String username = "User";
				client = new Client(serverAdress, defaultPort, username, cg);
				// test if we can start the Client
				if (!client.start())
					return;
				
				connected = true;
				System.out.println("New Fiel");
				// client.sendMessage(new ChatMessage(ChatMessage.FILE, "Add File"));
				String fileSeparator = System.getProperty("file.separator");
				JTextField nameOfFile = new JTextField(10);

				JPanel myPanel = new JPanel();
				myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

				myPanel.add(new JLabel("РЎС‚Р°Р¶ СЂР°Р±РѕС‚С‹ СЃ:"));
				myPanel.add(nameOfFile);
				JPanel pan = new JPanel();
				int result = JOptionPane.showConfirmDialog(null, myPanel,
						"Р’РІРµРґРёС‚Рµ РґР°РЅРЅС‹Рµ РґР»СЏ РїРѕРёСЃРєР° Рё СѓРґР°Р»РµРЅРёСЏ",
						JOptionPane.OK_CANCEL_OPTION);
				String name = nameOfFile.getText() + ".xml";
				// server part for creating file

				JTextField nameField = new JTextField(20);
				JTextField surnameField = new JTextField(10);
				JTextField secondNameField = new JTextField(10);
				JTextField yearField = new JTextField(10);
				JTextField faculty = new JTextField(20);
				JTextField department = new JTextField(10);
				String[] degreeN = { "доктор физико-математических наук", "доктор технических наук",
						"доктор гуманитарных наук", "кандидат физико-математических наук", "кандидат технических наук",
						"кандидат гуманитарных наук", "магистр физико-математических наук" };
				String[] degreeT = { "профессор", "доцент", "преподаватель", "старший преподаватель", "ассистент",
						"-" };
				JComboBox<String> comboBoxD = new JComboBox<String>(degreeN);
				JComboBox<String> comboBoxDn = new JComboBox<String>(degreeT);
				JPanel myPanel1 = new JPanel();
				myPanel1.setLayout(new BoxLayout(myPanel1, BoxLayout.Y_AXIS));
				myPanel1.add(new JLabel("Фамилия:"));
				myPanel1.add(nameField);
				myPanel1.add(new JLabel("Имя"));
				myPanel1.add(surnameField);
				myPanel1.add(new JLabel("Отчество:"));
				myPanel1.add(secondNameField);
				myPanel1.add(new JLabel("Факультет:"));
				myPanel1.add(faculty);
				myPanel1.add(new JLabel("Кафедра:"));
				myPanel1.add(department);
				myPanel1.add(new JLabel("Ученое звание:"));
				myPanel1.add(comboBoxDn);
				myPanel1.add(new JLabel("Ученая степень:"));
				myPanel1.add(comboBoxD);
				myPanel1.add(new JLabel("Стаж работы:"));
				myPanel1.add(yearField);

				int result1 = JOptionPane.showConfirmDialog(null, myPanel1, "Введите информацию о новом преподавателе",
						JOptionPane.OK_CANCEL_OPTION);
				if (result1 == JOptionPane.OK_OPTION) {
					List<String> lecturer = new ArrayList<String>();
					lecturer.add(faculty.getText());
					lecturer.add(department.getText());
					lecturer.add(nameField.getText() + " " + surnameField.getText() + " " + secondNameField.getText());
					lecturer.add((String) comboBoxDn.getSelectedItem());
					lecturer.add((String) comboBoxD.getSelectedItem());
					
					
					lecturer.add(yearField.getText());
					client.sendMessage(new ChatMessage(ChatMessage.FILE_NEW, name, lecturer));

					uni = new ArrayList<String[]>();
					uni.add(lecturer.toArray(new String[0]));
					uni.get(0);
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
					
					listenerAdd(addButton, cg);
					drawTable(mainPanel, uni);
					// currentTableWithLecturers.updateTable(uni);
					
					mainFrame.setVisible(true);

				}
				System.out.println("New File Button ended");
			}
		};
		menu.addActionListener(actionListener);

	}
	
	public void drawTable(JPanel currPane, List<String[]> uni) {
		if (currentTableWithLecturers != null) {
			currentTableWithLecturers.delete();
		}
		this.uni = uni;
		currentTableWithLecturers = new TableWithPages(this, uni, currPane);
	}

	public void openFile(JMenuItem menu, WindowUserCom cg) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// send to server request for array of Uni in string form

				System.out.println("Open File Button begin");
				String username = "User";

				// try creating a new Client with GUI
				client = new Client(serverAdress, defaultPort, username, cg);
				// test if we can start the Client
				if (!client.start())
					return;
				/*
				 * tf.setText(""); label.setText("Enter your message below");
				 */

				//JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
				//fileChooser.showSaveDialog(null);
				//FileName = fileChooser.getSelectedFile().getAbsolutePath();
				// Open the save dialog
				JPanel pan1 = new JPanel();
				// pan.setLayout(null);
				JLabel ans = new JLabel("Choose your file");
				String[] files= {
						"BSUIR", "BSU"
				};
				JComboBox<String> file = new JComboBox<String>(files);
				pan1.add(file);
				UIManager.put("OptionPane.minimumSize", new Dimension(1800, 500));
				JOptionPane.showMessageDialog(null, pan1, "Hohoho", JOptionPane.OK_CANCEL_OPTION);
				FileName = (String) file.getSelectedItem();
				connected = true;
				System.out.println("Open Fiel");
				client.sendMessage(new ChatMessage(ChatMessage.FILE_OPEN, FileName));
				// System.out.println("Trying" + uni.get(0)[0]);
				/*
				 * try { paintGUI(); } catch (IOException e1) { // TODO Auto-generated catch
				 * block e1.printStackTrace(); }
				 */

				System.out.println("OOPS" + FileName);

				// currentTableWithLecturers = new TableWithPages(cg, uni, mainPanel);
				System.out.println("End of Open file Button proccessing");
			}
		};
		menu.addActionListener(actionListener);
	}

	public void listenerAdd(JButton button, WindowUserCom cg) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.println("Open File Button begin");
				String username = "User";

				// try creating a new Client with GUI
				client = new Client(serverAdress, defaultPort, username, cg);
				// test if we can start the Client
				if (!client.start())
					return;
				/*
				 * tf.setText(""); label.setText("Enter your message below");
				 */

				

				connected = true;
				
				JTextField nameField = new JTextField(20);
				JTextField surnameField = new JTextField(10);
				JTextField secondNameField = new JTextField(10);
				JTextField yearField = new JTextField(10);
				UniversityController uniContr = null;
				try {
					uniContr = new UniversityController(cg.uni);
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
				myPanel.add(new JLabel("Фамилия:"));
				myPanel.add(nameField);
				myPanel.add(new JLabel("Имя"));
				myPanel.add(surnameField);
				myPanel.add(new JLabel("Отчество:"));
				myPanel.add(secondNameField);
				myPanel.add(new JLabel("Факультет:"));
				myPanel.add(comboBoxFacultes);
				myPanel.add(new JLabel("Кафедра:"));
				myPanel.add(comboBoxDepartments);
				myPanel.add(new JLabel("Ученое звание:"));
				myPanel.add(comboBoxDegreeScience);
				myPanel.add(new JLabel("Ученая степень:"));
				myPanel.add(comboBoxdegreeTitle);
				myPanel.add(new JLabel("Стаж работы:"));
				myPanel.add(yearField);

				int result = JOptionPane.showConfirmDialog(null, myPanel,
						"Введите данные о новом работнике",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					System.out.println("Write new lecturer");
					List<String> lecturer = new ArrayList<String>();
					lecturer.add((String) comboBoxFacultes.getSelectedItem());
					lecturer.add((String) comboBoxDepartments.getSelectedItem());
					lecturer.add(nameField.getText() + " " + surnameField.getText() + " " + secondNameField.getText());
					lecturer.add((String) comboBoxdegreeTitle.getSelectedItem());
					lecturer.add((String) comboBoxDegreeScience.getSelectedItem());
					
					lecturer.add(yearField.getText());
					//uni = new ArrayList<String[]>();
					uni.add(lecturer.toArray(new String[0]));
					
					for(int i=0; i<uni.size(); i++) {
						System.out.println("trym" + uni.get(i)[0]);
					}
					//uni.get(0);
					client.sendMessage(new ChatMessage(ChatMessage.ADD_LECT, uni));
					System.out.println("End of putting");
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
					uniContr = new UniversityController(uni);
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

	public void connectionFailed() {
		/*
		 * addFileButton.setEnabled(true); newFielButton.setEnabled(false);
		 * whoIsIn.setEnabled(false); label.setText("Enter your username below");
		 * tf.setText("Anonymous"); // reset port number and host name as a construction
		 * time tfPort.setText("" + defaultPort); tfServer.setText(defaultHost); // let
		 * the user change them tfServer.setEditable(false); tfPort.setEditable(false);
		 * // don't react to a <CR> after the username tf.removeActionListener(this);
		 */
		connected = false;
	}

}