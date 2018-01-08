package typingpro;
/**
 * Dieser Thread ist f�r die Ausgabe der vergangenen Zeit zust�ndig.
 * 
 * @author Oliver Stapelfeldt
 *
 */
public class Timer extends Thread {

	// Attribute
	
	static boolean running;
	Frame frame;
	TextManager randomtext;
	
	// Konstruktor

	Timer(Frame frame, TextManager randomtext) {
		this.frame = frame;
	}

	/**
	 *  Nach einer jeder Sekunde Schlaf aktualisiert dieser Thread das Label f�r die vergangene Zeit.
	 */
	public void run() {
		running = true;
		int t = 0;
		while (running) {
			frame.time.setText(t++ + " s");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
}
