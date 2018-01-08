package typingpro;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
/**
 * 
 * Diese Klasse verwaltet die Textausgabe aller registrierten Komponenten
 * 
 * @author Oliver Stapelfeldt
 *
 */
public class LanguageManager {
	
	Locale loc = Locale.GERMAN;
	
	ResourceBundle bundle;
	
	HashMap<String, JComponent> compmap = new HashMap<>();
	
	Frame frame;
	
	public LanguageManager(Frame frame) {
		this.frame = frame;
	}
	
	/**
	 * Komponente wird mit einem Schlüssel im LanguageManager registriert
	 * @param JComponent comp
	 * @param String key
	 */
	
	public void registerComponent(JComponent comp, String key) {
		compmap.put(key, comp);
	}
	
	/**
	 * Alle registrierten Komponenten werden aktualisiert
	 */
	public synchronized void update() {
		for(Map.Entry<String, JComponent> entry : compmap.entrySet()) {
			if(entry.getValue() instanceof JButton)
				((JButton) entry.getValue()).setText(bundle.getString(entry.getKey()));
			else if(entry.getValue() instanceof JLabel)
				((JLabel) entry.getValue()).setText(bundle.getString(entry.getKey()));
			else if(entry.getValue() instanceof JRadioButton)
				((JRadioButton) entry.getValue()).setText(bundle.getString(entry.getKey()));
			else if(entry.getValue() instanceof JComboBox) {
				@SuppressWarnings("unchecked")
				JComboBox<String> box = (JComboBox<String>) entry.getValue();
				String entries = bundle.getString(entry.getKey());
				String[] boxentries = entries.split(",");
				int selected = box.getSelectedIndex();
				box.removeAllItems();
				for(String boxentry : boxentries)
				box.addItem(boxentry);
				box.setSelectedIndex(selected);
			}
			else if(entry.getValue() instanceof JMenu)
				((JMenu) entry.getValue()).setText(bundle.getString(entry.getKey()));
			else if(entry.getValue() instanceof JMenuItem)
				((JMenuItem) entry.getValue()).setText(bundle.getString(entry.getKey()));
			
			if(Typo.typolist.size()<1) {
				frame.area.setText(Typo.emptytypolist.getText());
				}
			else {
				frame.area.setText(Typo.printtypos());
			}
			
			frame.typodialog.setTitle(Typo.dialogtitle.getText());
			frame.infolabel.setInfotext(frame.infolabel.reminder);
		}
	}
	
	/**
	 * Überprüft die Sprachauswahl und aktualisiert sämtliche Labels
	 */
	public synchronized void setlang() {
		
		int langindex = frame.languagebox.getSelectedIndex();
		switch(langindex) {
		case 0: loc = Locale.GERMAN;break;
		case 1: loc = Locale.ENGLISH;break;
		
		}
		bundle = ResourceBundle.getBundle("typingpro/LabelsBundle", loc);
		update();
		
	}
}
