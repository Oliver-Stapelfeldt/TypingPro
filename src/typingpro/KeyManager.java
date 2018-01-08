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
	TextManager randomtext;
	Timer timer;
	int textlength;
	long startingtime;
	boolean flashrunning;
	boolean firstwrong;
	
	// Konstruktor

	KeyManager(Frame frame, TextManager randomtext) {
		this.frame = frame;
		this.randomtext = randomtext;
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

		if (randomtext.start == true && randomtext.text.length() > 0) {
			randomtext.start = false;
			timer = new Timer(frame, randomtext);
			timer.start();
			startingtime = System.currentTimeMillis();
			if (k.getKeyChar() != randomtext.text.charAt(0)) {
				firstwrong = true;
			}
		}
		
		// Wird eine Übung abgeschlossen, so wird mit dem nächsten Tastendruck eine Neue erzeugt. Hierbei wird mit einem boolean überprüft, ob zuletzt die Fehlerliste
		// oder eine normale Übung verwendet wurde. Die Textlänge wird zur Ausgabe der Geschwindigkeit gespeichert.

		if (randomtext.text.length() < 1) {
			if (frame.usingtypopool == true) {
				randomtext.setTypolist();
			} else {
				randomtext.setRandomlist();
			}
			frame.speedunit.setVisible(false);
			frame.textlabel.setText((randomtext.setRandomtext()));
			textlength = randomtext.text.length();
		}
		
		// Wird während der Übung eine Taste korrekt gedrückt, so wird der erste Buchstabe entfernt. Die Geschwindigkeit wird berechnet und sämtliche Labels aktualisiert.
		// Falls nach der Entfernung des ersten Buchstabens, die Übung abgeschlossen ist, wird der Timer gestoppt. Die Berechnung der Geschwindigkeit soll erst ab dem
		// 2. Tastendruck erfolgen (der 1. Tastendruck startet den Timer). Hierfür wird der Boolean firstwrong verwendet.

		else if (k.getKeyChar() == randomtext.text.charAt(0)) {
			deletefirst();
			frame.progress.setValue((int) (((double) (textlength - randomtext.text.length()) / textlength) * 100));
			frame.keysleft.setText(""+randomtext.text.length());

			if (textlength - randomtext.text.length() > 1 || firstwrong) {
				firstwrong = false;
				double avg = (double) ((textlength - randomtext.text.length())
						/ ((double) (System.currentTimeMillis() - startingtime) / 1000));
				frame.speed.setText(String.format("%.2f", avg));
				frame.speedunit.setVisible(true);
			}

			if (randomtext.text.length() < 1) {
				frame.infolabel.setInfotext(frame.infolabel.anykeytorestart);
				frame.typolabel.setVisible(false);
				stoptimer();
			}
		}

		else {
			// Wird während der Übung eine Taste falsch gedrückt, so wird auch hier die Geschwindigkeit berechnet.
			// Wurde die erste Taste falsch gedrückt, so wird bei dem nächsten Tastendruck die Geschwindigkeit NaN ausgegeben. Dies wird mit einer Abfrage auf firstwrong korrigiert.
			// Sämtliche Labels werden aktualisiert und eine Thread wird ausgeführt, um die Schrift kurzzeitig rot zu färben.
			double avg = (double) ((textlength - randomtext.text.length())
					/ ((double) (System.currentTimeMillis() - startingtime) / 1000));
			frame.speed.setText(String.format("%.2f", avg));
			frame.speedunit.setVisible(true);
			if (firstwrong) {
				frame.speed.setText("0");
			}
			
			
			Typo.add(new Typo(frame, randomtext.text.charAt(0), k.getKeyChar()));
			frame.area.setText(Typo.printtypos());
			frame.typos.setText(""+Typo.typos);
			frame.infolabel.setInfotext(frame.infolabel.typoadded);
			frame.typolabel.setText("\""+randomtext.text.charAt(0)+"\"");
			frame.typolabel.setVisible(true);
			new Thread() {
				public void run() {
					flash();
				}
			}.start();
		}
	}
	/**
	 * Entfernt das erste Element des zufälligen Textes in der Klasse Randomtext und aktuallisiert das zugehörende Label
	 */
	public void deletefirst() {
		randomtext.text.deleteCharAt(0);
		frame.textlabel.setText(randomtext.text.toString());
	}
	
	/**
	 * Lässt den Übungstext für kurze Zeit die Farbe wechseln
	 */

	public void flash() {
		if (!flashrunning) {
			synchronized (this) {
				flashrunning = true;
				frame.textlabel.setForeground(new Color(255, 0, 0));
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				frame.textlabel.setForeground(new Color(30, 30, 30));
				flashrunning = false;
			}
		}
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
