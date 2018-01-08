package typingpro;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
/**
 *Diese Klasse fängt sämtliche Fehler auf, um diese im Dialog oder für eine Übung mit aus dem Fehlerpool verwenden zu können.
 * 
 * @author Oliver Stapelfeldt
 *
 */
public class Typo {
	
	// Attribute

	Frame frame;

	static int typos;

	static List<Typo> typolist = new ArrayList<>();

	char right;

	char wrong;
	
	static JLabel dialogtitle = new JLabel();
	
	static JLabel emptytypolist = new JLabel();
	
	static JLabel called = new JLabel();
	
	static JLabel pressed = new JLabel();
	
	// Konstruktor

	Typo(Frame frame, char right, char wrong) {
		this.right = right;
		this.wrong = wrong;
		this.frame = frame;
	}
	
	public String toString() {
			return String.format("%s: %c \t %s: %c\n",called.getText(), right, pressed.getText(), wrong);
	}
	
	/**
	 * Fügt einen neuen Tippfehler hinzu und aktuallisiert Parameter der Klasse 
	 * @param t
	 */
	
	public static void add(Typo t) {
		typolist.add(t);
		typos++;
	}
	
	/**
	 * Diese Methode dient zur Ausgabe des Dialoges für Tippfehler
	 * @return String
	 */
	public static String printtypos() {
		
		StringBuilder areatext = new StringBuilder();
		for(Typo typo : typolist) {
			areatext.append(typo);
		}
		return areatext.toString();	
	}
}
