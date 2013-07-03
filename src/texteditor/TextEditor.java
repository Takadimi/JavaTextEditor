package texteditor;

import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import java.io.*;

public class TextEditor extends JFrame implements ActionListener, KeyListener {

	private FileOperations fo = new FileOperations();

	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenu editMenu = new JMenu("Edit");
	private JMenuItem[] menuBarButtons = new JMenuItem[5];

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

		menuBarButtons[0] = new JMenuItem("Save As");
		menuBarButtons[1] = new JMenuItem("Open");
		menuBarButtons[2] = new JMenuItem("Copy");
		menuBarButtons[3] = new JMenuItem("Paste");
		menuBarButtons[4] = new JMenuItem("Save");
		
		menuBarButtons[4].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.META_DOWN_MASK));
		
		addListenersToMenuBarItems();
		
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);

		JPanel pane1 = new JPanel();
		
		addMenuBarItems();
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		
		pane1.add(sp);
		
		add(pane1);
		
		setJMenuBar(menuBar);
		
		setVisible(true);

	}

	private void addMenuBarItems() {
		
		for (int i = 0; i < menuBarButtons.length; i++) {
			
			if (i < 2) {
				fileMenu.add(menuBarButtons[i]);
			} else {
				editMenu.add(menuBarButtons[i]);
			}
			
			
		}
		
	}

	private void addListenersToMenuBarItems() {
		
		for (int i = 0; i < menuBarButtons.length; i++) {

			menuBarButtons[i].addActionListener(this);

		}
		
	}

	public void actionPerformed(ActionEvent evt) {
		
		if (evt.getSource() == menuBarButtons[0]) {
			JFileChooser fileChoose = new JFileChooser();

			int returnVal = fileChoose.showSaveDialog(this);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("SAVING FILE");
				fo.saveFile(fileChoose.getSelectedFile(), textArea.getText());
				currentFile = fileChoose.getSelectedFile();
				this.setTitle(currentFile.getName());
			}

			System.out.println("SAVE AS");
		} else if (evt.getSource() == menuBarButtons[1]) {
			
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
		} else if (evt.getSource() == menuBarButtons[2]) {
			
			textArea.copy();
			
			System.out.println("COPY");
		} else if (evt.getSource() == menuBarButtons[3]) {
			
			textArea.paste();
			
			System.out.println("PASTE");
		} else if (evt.getSource() == menuBarButtons[4]) {
			
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
		}

	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		
		
		
	}

	public void keyReleased(KeyEvent e) {
		
	}

}
