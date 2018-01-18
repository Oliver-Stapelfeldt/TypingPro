package typingpro;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Diese Klasse reagiert auf Tasteneingaben und kontrolliert, ob die Eingabe korrekt ist, eine �bung startet oder endet.
 * 
 * @author Oliver Stapelfeldt
 *
 */
public class KeyManager implements KeyListener {
	
	// Attribute

	Frame frame;
	TextManager textmanager;
	Timer timer;
	int textlength;
	long startingtime;
	boolean firstwrong;
	
	// Konstruktor

	KeyManager(Frame frame, TextManager randomtext) {
		this.frame = frame;
		this.textmanager = randomtext;
	}

	@Override
	public void keyPressed(KeyEvent k) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	// Hier werden Eingaben kontrolliert und mit dem �bungstext verglichen
	
	@Override
	public void keyTyped(KeyEvent k) {
		
		
		
		// Wurde eine neue �bung gestartet, so wird der Boolean "start" in der Klasse Randomtext auf true gesetzt, um Dies zu kennzeichen.
		// Ist eine neue �bung erfolgreich gestartet worden und der Text gesetzt, so wird der Thread Timer gestartet und eine Startzeit
		// zur Berechnung der Geschwindigkeit bestimmt.

		if (textmanager.start == true && textmanager.text.length() > 0) {
			textmanager.start = false;
			timer = new Timer(frame, textmanager);
			timer.start();
			startingtime = System.currentTimeMillis();
			if (k.getKeyChar() != textmanager.text.charAt(0)) {
				firstwrong = true;
			}
		}
		
		// Wird eine �bung abgeschlossen, so wird mit dem n�chsten Tastendruck eine Neue erzeugt. Hierbei wird mit einem boolean �berpr�ft, ob zuletzt die Fehlerliste
		// oder eine normale �bung verwendet wurde. Die Textl�nge wird zur Ausgabe der Geschwindigkeit gespeichert.

		if (textmanager.text.length() < 1) {
			frame.using.prepareExcercise(textmanager, this, frame.textlabel, frame.typolabel);
			frame.speedunit.setVisible(false);
			
			
		}
		
		// Wird w�hrend der �bung eine Taste korrekt gedr�ckt, so wird der erste Buchstabe entfernt. Die Geschwindigkeit wird berechnet und s�mtliche Labels aktualisiert.
		// Falls nach der Entfernung des ersten Buchstabens, die �bung abgeschlossen ist, wird der Timer gestoppt. Die Berechnung der Geschwindigkeit soll erst ab dem
		// 2. Tastendruck erfolgen (der 1. Tastendruck startet den Timer). Hierf�r wird der Boolean firstwrong verwendet.

		else if (k.getKeyChar() == textmanager.text.charAt(0)) {
			deletefirst();
			frame.progress.setValue((int) (((double) (textlength - textmanager.text.length()) / textlength) * 100));
			frame.keysleft.setText(""+textmanager.text.length());

			if (textlength - textmanager.text.length() > 1 || firstwrong) {
				firstwrong = false;
				double avg = (double) ((textlength - textmanager.text.length())
						/ ((double) (System.currentTimeMillis() - startingtime) / 1000));
				frame.speed.setText(String.format("%.2f", avg));
				frame.speedunit.setVisible(true);
			}

			if (textmanager.text.length() < 1) {
				frame.infolabel.setInfotext(frame.infolabel.anykeytorestart);
				frame.typolabel.setVisible(false);
				stoptimer();
			}
		}

		else {
			// Wird w�hrend der �bung eine Taste falsch gedr�ckt, so wird auch hier die Geschwindigkeit berechnet.
			// Wurde die erste Taste falsch gedr�ckt, so wird bei dem n�chsten Tastendruck die Geschwindigkeit NaN ausgegeben. Dies wird mit einer Abfrage auf firstwrong korrigiert.
			// S�mtliche Labels werden aktualisiert und eine Thread wird ausgef�hrt, um die Schrift kurzzeitig rot zu f�rben.
			double avg = (double) ((textlength - textmanager.text.length())
					/ ((double) (System.currentTimeMillis() - startingtime) / 1000));
			frame.speed.setText(String.format("%.2f", avg));
			frame.speedunit.setVisible(true);
			if (firstwrong) {
				frame.speed.setText("0");
			}
			
			
			Typo.add(new Typo(frame, textmanager.text.charAt(0), k.getKeyChar()));
			frame.typoarea.setText(Typo.printtypos());
			frame.typos.setText(""+Typo.typos);
			frame.infolabel.setInfotext(frame.infolabel.typoadded);
			frame.typolabel.setText("\""+ textmanager.text.charAt(0)+"\"");
			frame.typolabel.setVisible(true);
			new Thread() {
				public void run() {
					frame.colormanager.flash();
				}
			}.start();
		}
	}
	/**
	 * Entfernt das erste Element des zuf�lligen Textes in der Klasse Randomtext und aktuallisiert das zugeh�rende Label
	 */
	public void deletefirst() {
		textmanager.text.deleteCharAt(0);
		frame.textlabel.setText(textmanager.text.toString());
	}
	

	
	/**
	 * Stoppt einen Thread des Typs Timer
	 */

	public void stoptimer() {
		try {
			Timer.running = false;
			timer.interrupt();
		} catch (Exception e) {
		}
	}
}
