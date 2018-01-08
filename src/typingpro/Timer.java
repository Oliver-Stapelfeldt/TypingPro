package typingpro;
/**
 * Dieser Thread ist für die Ausgabe der vergangenen Zeit zuständig.
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
	 *  Nach einer jeder Sekunde Schlaf aktualisiert dieser Thread das Label für die vergangene Zeit.
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
