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

	// Hier werden Eingaben kontrolliert und mit dem �bungstext verglichen
	
	@Override
	public void keyTyped(KeyEvent k) {
		
		
		
		// Wurde eine neue �bung gestartet, so wird der Boolean "start" in der Klasse Randomtext auf true gesetzt, um Dies zu kennzeichen.
		// Ist eine neue �bung erfolgreich gestartet worden und der Text gesetzt, so wird der Thread Timer gestartet und eine Startzeit
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
		
		// Wird eine �bung abgeschlossen, so wird mit dem n�chsten Tastendruck eine Neue erzeugt. Hierbei wird mit einem boolean �berpr�ft, ob zuletzt die Fehlerliste
		// oder eine normale �bung verwendet wurde. Die Textl�nge wird zur Ausgabe der Geschwindigkeit gespeichert.

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
		
		// Wird w�hrend der �bung eine Taste korrekt gedr�ckt, so wird der erste Buchstabe entfernt. Die Geschwindigkeit wird berechnet und s�mtliche Labels aktualisiert.
		// Falls nach der Entfernung des ersten Buchstabens, die �bung abgeschlossen ist, wird der Timer gestoppt. Die Berechnung der Geschwindigkeit soll erst ab dem
		// 2. Tastendruck erfolgen (der 1. Tastendruck startet den Timer). Hierf�r wird der Boolean firstwrong verwendet.

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
			// Wird w�hrend der �bung eine Taste falsch gedr�ckt, so wird auch hier die Geschwindigkeit berechnet.
			// Wurde die erste Taste falsch gedr�ckt, so wird bei dem n�chsten Tastendruck die Geschwindigkeit NaN ausgegeben. Dies wird mit einer Abfrage auf firstwrong korrigiert.
			// S�mtliche Labels werden aktualisiert und eine Thread wird ausgef�hrt, um die Schrift kurzzeitig rot zu f�rben.
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
	 * Entfernt das erste Element des zuf�lligen Textes in der Klasse Randomtext und aktuallisiert das zugeh�rende Label
	 */
	public void deletefirst() {
		randomtext.text.deleteCharAt(0);
		frame.textlabel.setText(randomtext.text.toString());
	}
	
	/**
	 * L�sst den �bungstext f�r kurze Zeit die Farbe wechseln
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
