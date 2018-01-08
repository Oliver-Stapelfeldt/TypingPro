package typingpro;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *Diese Klasse ist f�r die Erstellung von zuf�lligen Zeichenketten zust�ndig.
 *
 * @author Oliver Stapelfeldt
 *
 */
public class TextManager {
	
	// Attribute

	Frame frame;

	StringBuilder text = new StringBuilder();

	List<Character> randomlist = new ArrayList<Character>();

	boolean start;
	
	Random random;
	
	// Konstruktor

	TextManager(Frame frame) {
		this.frame = frame;
		random = new Random();
	} 
	
/**
 *  Wurde eine neue �bung gestartet, so wird eine Liste mit Zeichen erstellt. Welche Zeichen hinzugef�gt werden, h�ngt von der Auswahl der RadioButtons ab.
 *  Ausserdem bestimmt die Auswahl der L�nge �ber eine Combox, wie h�ufig dies geschieht (Die L�nge der Liste bestimmt die L�nge der �bung).
 **/
	public void setRandomlist() {

		randomlist = new ArrayList<Character>();
		for(int i = 0; i<(frame.lengthbox.getSelectedIndex() + 1) * 2;i++) {
		if (frame.letters.isSelected())
			for (char c = 'a'; c < 123; c++) {
				randomlist.add(c);
			}
			
		if (frame.numbers.isSelected())
			for (char c = '0'; c < '0' + 9; c++) {
				randomlist.add(c);
			}
		if (frame.basicsigns.isSelected()) {
			randomlist.add(',');
			randomlist.add('.');
			randomlist.add('-');
		}

		if (frame.manysigns.isSelected()) {
			randomlist.add(';');
			randomlist.add(':');
			randomlist.add('_');
			randomlist.add('#');
			randomlist.add('+');
			randomlist.add('*');
			randomlist.add('<');
			randomlist.add('>');
			randomlist.add('"');
			randomlist.add('!');
			randomlist.add('?');
		}

		if (frame.umlauts.isSelected()) {
			randomlist.add('�');
			randomlist.add('�');
			randomlist.add('�');
		}
		}

		if (randomlist.size() < 1)
			frame.infolabel.setInfotext(frame.infolabel.chooseanykeys);
	}
	
	/**
	 *  Wird eine �bung �ber die FehlerListe aufgerufen, so werden zuf�llige Zeichen aus der Liste in die ZeichenListe hinzugef�gt.
 	 *  Die L�nge der Liste h�ngt wieder von der Auswahl der Combobox ab.
	 */
	
	
	public void setTypolist() {
		randomlist = new ArrayList<Character>();

		List<Character> chars = Typo.typolist.stream().map(t -> t.right).filter(c -> c != ' ')
				.collect(Collectors.toList());

		
		if(chars.size()>0)
		while (randomlist.size() < (frame.lengthbox.getSelectedIndex() + 1)*30) {
			randomlist.add(chars.get(random.nextInt(chars.size())));
		}
		
		if (randomlist.size() < 1)
			frame.infolabel.setInfotext(frame.infolabel.typopoolempty);
	}

	/**
	 * Aus einer Character-Liste wird ein zuf�lliger Text erstellt.
	 * @return String
	 */
	public String setRandomtext() {
		StringBuilder textbuilder = new StringBuilder("");
		if (randomlist.size() > 0) {

			// Die Zeichen werden zuf�llig angeordnet zu einer Zeichenkette geformt.
	
			for (char c : randomlist) {
				textbuilder.insert(random.nextInt(textbuilder.length() + 1), c);	
				}
			// Die Leerzeichen werden mit einem gewissen Grad an Zufall eingef�gt. Die m�gliche Wortl�nge betr�gt 4-6 Zeichen.

			int space = 0;
			while (space < textbuilder.length()) {
				space += 5 + random.nextInt(3);
				if (space < textbuilder.length() - 1)
					textbuilder.insert(space, " ");
			}
			
			// Wurde Gro�- und Kleinschreibung ausgew�hlt, so wird jeder Anfang eines Wortes zum Gro�buchstaben, falls es sich um einen Buchstaben handelt.

			if (frame.uppercases.isSelected()) {
				textbuilder.replace(0, 1, "" + Character.toUpperCase(textbuilder.charAt(0)));
				for (int i = 0; i < textbuilder.length() - 1; i++)
					if (textbuilder.charAt(i) == ' ')
						textbuilder.replace(i + 1, i + 2, "" + Character.toUpperCase(textbuilder.charAt(i + 1)));
			}
			
			// Da diese Methode beim Starten einer �bung ausgef�hrt wird, werden hier s�mtliche Variablen und Labels zur�ckgesetzt.
			
			Typo.typos = 0;
			start = true;
			frame.progress.setValue(0);
			frame.infolabel.setText(" ");
			frame.typos.setText("0");
			frame.time.setText("0 s");
			frame.keysleft.setText(""+textbuilder.length());
			frame.speed.setText("");
		}
		text = textbuilder;
		return textbuilder.toString();
		
	}

}