package typingpro;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Diese Klasse reagiert auf Tasteneingaben und kontrolliert, ob die Eingabe korrekt ist, eine Übung startet oder endet.
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

	// Hier werden Eingaben kontrolliert und mit dem Übungstext verglichen
	
	@Override
	public void keyTyped(KeyEvent k) {
		
		
		
		// Wurde eine neue Übung gestartet, so wird der Boolean "start" in der Klasse Randomtext auf true gesetzt, um Dies zu kennzeichen.
		// Ist eine neue Übung erfolgreich gestartet worden und der Text gesetzt, so wird der Thread Timer gestartet und eine Startzeit
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
		
		// Wird eine Übung abgeschlossen, so wird mit dem nächsten Tastendruck eine Neue erzeugt. Hierbei wird mit einem boolean überprüft, ob zuletzt die Fehlerliste
		// oder eine normale Übung verwendet wurde. Die Textlänge wird zur Ausgabe der Geschwindigkeit gespeichert.

		if (textmanager.text.length() < 1) {
			frame.using.prepareExcercise(textmanager, this, frame.textlabel, frame.typolabel);
			frame.speedunit.setVisible(false);
			
			
		}
		
		// Wird während der Übung eine Taste korrekt gedrückt, so wird der erste Buchstabe entfernt. Die Geschwindigkeit wird berechnet und sämtliche Labels aktualisiert.
		// Falls nach der Entfernung des ersten Buchstabens, die Übung abgeschlossen ist, wird der Timer gestoppt. Die Berechnung der Geschwindigkeit soll erst ab dem
		// 2. Tastendruck erfolgen (der 1. Tastendruck startet den Timer). Hierfür wird der Boolean firstwrong verwendet.

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
			// Wird während der Übung eine Taste falsch gedrückt, so wird auch hier die Geschwindigkeit berechnet.
			// Wurde die erste Taste falsch gedrückt, so wird bei dem nächsten Tastendruck die Geschwindigkeit NaN ausgegeben. Dies wird mit einer Abfrage auf firstwrong korrigiert.
			// Sämtliche Labels werden aktualisiert und eine Thread wird ausgeführt, um die Schrift kurzzeitig rot zu färben.
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
	 * Entfernt das erste Element des zufälligen Textes in der Klasse Randomtext und aktuallisiert das zugehörende Label
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
