package typingpro;

/**
 * Dieser Thread sorgt f�r ein blinkendes "|" vor dem �bungstext.
 * Die Hintergrundfarbe �ndert sich jede halbe Sekunde.
 * 
 * @author Oliver Stapelfeldt
 *
 */
public class Blinker extends Thread {

	Frame frame;

	
	public Blinker(Frame frame) {
		this.frame = frame;
	}
	
	
	public void run(){
		while (true) {
			frame.blink.setForeground(frame.foreground);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			frame.blink.setForeground(frame.background);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
