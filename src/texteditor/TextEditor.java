package texteditor;

import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import java.io.*;

public class TextEditor extends JFrame implements ActionListener {

	private FileOperations fo = new FileOperations();
	private TextManipulation tm = new TextManipulation();

	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenu editMenu = new JMenu("Edit");
	private JMenuItem[] menuBarButtons = new JMenuItem[5];
	private HashMap<String, JMenuItem> menuBarItems = new HashMap<String, JMenuItem>();
	
	private JTextArea textArea = new JTextArea(33, 50);
	private JScrollPane sp = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	private File currentFile;

	public TextEditor() {

		super("Java Text Editor");
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(610, 567);
		setResizable(true);
		setLocationRelativeTo(null);
		
		JMenuItem save = new JMenuItem("Save");
		JMenuItem saveAs = new JMenuItem("Save As");
		JMenuItem open = new JMenuItem("Open");
		JMenuItem copy = new JMenuItem("Copy");
		JMenuItem paste = new JMenuItem("Paste");
		JMenuItem wordCount = new JMenuItem("Word Count");
		
		menuBarItems.put("Save", save);
		menuBarItems.put("Save As", saveAs);
		menuBarItems.put("Open", open);
		menuBarItems.put("Copy", copy);
		menuBarItems.put("Paste", paste);
		menuBarItems.put("Word Count", wordCount);
			
		menuBarButtons[0] = new JMenuItem("Save As");
		menuBarButtons[1] = new JMenuItem("Open");
		menuBarButtons[2] = new JMenuItem("Copy");
		menuBarButtons[3] = new JMenuItem("Paste");
		menuBarButtons[4] = new JMenuItem("Save");
		
		menuBarItems.get("Open").setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.META_DOWN_MASK));
		menuBarItems.get("Copy").setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.META_DOWN_MASK));
		menuBarItems.get("Paste").setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.META_DOWN_MASK));
		menuBarItems.get("Save").setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.META_DOWN_MASK));
		
		addListenersToMenuBarItems();
		
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);

		JPanel pane1 = new JPanel();
		
		addMenuBarItemsToMenus();
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		
		pane1.add(sp);
		
		add(pane1);
		
		setJMenuBar(menuBar);
		
		setVisible(true);

	}

	private void addMenuBarItemsToMenus() {
		
		fileMenu.add(menuBarItems.get("Save"));
		fileMenu.add(menuBarItems.get("Save As"));
		fileMenu.add(menuBarItems.get("Open"));
		
		editMenu.add(menuBarItems.get("Copy"));
		editMenu.add(menuBarItems.get("Paste"));
		editMenu.add(menuBarItems.get("Word Count"));
		
	}

	private void addListenersToMenuBarItems() {
		
		menuBarItems.get("Save").addActionListener(this);
		menuBarItems.get("Save As").addActionListener(this);
		menuBarItems.get("Open").addActionListener(this);
		menuBarItems.get("Copy").addActionListener(this);
		menuBarItems.get("Paste").addActionListener(this);
		menuBarItems.get("Word Count").addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent evt) {
		
		if (evt.getSource() == menuBarItems.get("Save As")) {
			
			JFileChooser fileChoose = new JFileChooser();

			int returnVal = fileChoose.showSaveDialog(this);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("SAVING FILE");
				fo.saveFile(fileChoose.getSelectedFile(), textArea.getText());
				currentFile = fileChoose.getSelectedFile();
				this.setTitle(currentFile.getName());
			}

			System.out.println("SAVE AS");
			
		} else if (evt.getSource() == menuBarItems.get("Open")) {
			
			JFileChooser fileChoose = new JFileChooser();
			
			int returnVal = fileChoose.showOpenDialog(this);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("OPENING FILE");
				
				currentFile = fileChoose.getSelectedFile();
				
				this.setTitle(currentFile.getName());
				
				textArea.setText("");
				
				ArrayList<String> fileText = fo.openFile(fileChoose.getSelectedFile());
				
				for (int i = 0; i < fileText.size(); i++) {
					textArea.append(fileText.get(i));
					
					if (i != fileText.size() - 1) {
						textArea.append("\n");
					}
					
				}
				
			}
			
			System.out.println("OPEN");
		} else if (evt.getSource() == menuBarItems.get("Copy")) {
			
			textArea.copy();
			
			System.out.println("COPY");
		} else if (evt.getSource() == menuBarItems.get("Paste")) {
			
			textArea.paste();
			
			System.out.println("PASTE");
		} else if (evt.getSource() == menuBarItems.get("Save")) {
			
			if (currentFile == null) {
				
				JFileChooser fileChoose = new JFileChooser();
				
				int returnVal = fileChoose.showSaveDialog(this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fo.saveFile(fileChoose.getSelectedFile(), textArea.getText());
					currentFile = fileChoose.getSelectedFile();
					this.setTitle(currentFile.getName());
				} else {
					try {
						fo.saveFile(currentFile, textArea.getText());
					} catch (NullPointerException npe) {
						System.out.println("This didn't work!");
					}
				}
				
			}
			
			System.out.println("SAVE");
		} else if (evt.getSource() == menuBarItems.get("Word Count")) {
			JOptionPane.showMessageDialog(this, "Word Count: " + tm.getWordCount(textArea.getText()));
		}

	}

}
