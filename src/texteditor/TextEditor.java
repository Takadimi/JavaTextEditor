package texteditor;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextEditor extends JFrame implements ActionListener {

	private FileOperations fo = new FileOperations();

	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenu editMenu = new JMenu("Edit");
	private JMenuItem[] menuBarButtons = new JMenuItem[4];

	private JTextArea textArea = new JTextArea(33, 50);
	private JScrollPane sp = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	public TextEditor() {

		super("Java Text Editor");
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(610, 567);
		setResizable(true);
		setLocationRelativeTo(null);

		menuBarButtons[0] = new JMenuItem("Save");
		menuBarButtons[1] = new JMenuItem("Open");
		menuBarButtons[2] = new JMenuItem("Copy");
		menuBarButtons[3] = new JMenuItem("Paste");
		
		addListenersToMenuBarItems();
		
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
		
		String selectedText = "";
		
		if (evt.getSource() == menuBarButtons[0]) {
			JFileChooser fileChoose = new JFileChooser();

			int returnVal = fileChoose.showSaveDialog(this);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("SAVING FILE");
				fo.saveFile(fileChoose.getSelectedFile(), textArea.getText());
			}

			System.out.println("SAVE");
		} else if (evt.getSource() == menuBarButtons[1]) {
			
			// TODO Create fileChooser that opens up files to be read and printed to the textArea
			
			JFileChooser fileChoose = new JFileChooser();
			
			int returnVal = fileChoose.showOpenDialog(this);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("OPENING FILE");
				
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
			
			selectedText = textArea.getSelectedText();
			
			System.out.println("COPY");
		} else if (evt.getSource() == menuBarButtons[3]) {
			
			textArea.append(selectedText);
			
			System.out.println("PASTE");
		}

	}

}
