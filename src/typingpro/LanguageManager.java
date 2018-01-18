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
	 * Komponente wird mit einem Schl�ssel im LanguageManager registriert
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
				frame.typoarea.setText(Typo.emptytypolist.getText());
				}
			else {
				frame.typoarea.setText(Typo.printtypos());
			}
			
			frame.typodialog.setTitle(Typo.dialogtitle.getText());
			frame.textexdialog.setTitle(frame.exdiatitle.getText());
			frame.infolabel.setInfotext(frame.infolabel.reminder);
			
		}
	}
	
	/**
	 * �berpr�ft die Sprachauswahl und aktualisiert s�mtliche Labels
	 */
	public synchronized void setLang() {
		
		int langindex = getSelectedLanguageIndex();
		switch(langindex) {
		case 0: loc = Locale.ENGLISH;break;
		case 1: loc = Locale.GERMAN;break;
		
		}
		bundle = ResourceBundle.getBundle("typingpro/LabelsBundle", loc);
		update();
		
	}
	
	public int getSelectedLanguageIndex() {
		int index = 0;
		for (int i = 0; i < 2; i++) {
			if (frame.languageitems.get(i).isSelected())
				index = i;
		}
		return index;
	}
	
}
