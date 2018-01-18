package typingpro;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class ColorManager {

	Frame frame;

	Color background, background2, background3, background4, foreground, foreground2;

	List<ColorComponent> colorcomponents = new ArrayList<ColorComponent>();

	Color[] beach = { new Color(243, 205, 5), new Color(244, 159, 5), new Color(208, 112, 3), new Color(54, 104, 141),
			new Color(30, 30, 30), new Color(54, 104, 141)};

	Color[] ornamental = { new Color(216,213,131), new Color(217,172,42), new Color(118,63,2),
			new Color(114,0,23), new Color(30, 30, 30), new Color(114,0,23)};

	Color[] sushi = { new Color(184, 210, 11), new Color(245, 108, 87), new Color(35, 27, 18), new Color(247, 118, 4),
			new Color(30, 30, 30), new Color(245, 108, 87) };

	Color[] deepsea = { new Color(196, 223, 230), new Color(102, 165, 173), new Color(7, 87, 91), new Color(0, 59, 70),
			new Color(30, 30, 30), new Color(102, 165, 173) };

	Color[] wood = { new Color(162, 197, 35), new Color(72, 107, 0), new Color(46, 66, 0), new Color(125, 68, 39),
			new Color(30, 30, 30), new Color(72, 107, 0) };

	Color[] mountain = { new Color(183, 184, 182), new Color(76, 181, 245), new Color(52, 103, 92),
			new Color(179, 193, 0), new Color(30, 30, 30), new Color(76, 181, 245) };

	Color[] greek = { new Color(244, 234, 222), new Color(237, 140, 114), new Color(47, 73, 110),
			new Color(41, 136, 188), new Color(30, 30, 30), new Color(41, 136, 188) };

	Color[] nightvision = { new Color(34,38,41), new Color(34,38,41), new Color(97,137,47),
			new Color(71,75,79), new Color(134,194,50), new Color(134,194,50) };

	Color[] kitchen = { new Color(253, 246, 246), new Color(179, 219, 192), new Color(254, 0, 0),
			new Color(103, 186, 202), new Color(30, 30, 30), new Color(179, 219, 192) };

	Color[] apples = { new Color(244, 236, 106), new Color(187, 207, 74), new Color(231, 63, 11),
			new Color(161, 31, 12), new Color(30, 30, 30), new Color(161, 31, 12)};

	Color[][] colororder = { beach, apples, wood, deepsea, greek, mountain, sushi, nightvision, kitchen, ornamental };
	
	boolean flashrunning;

	public ColorManager(Frame frame) {
		this.frame = frame;
	}

	public void setColors() {
		this.background = colororder[getSelectedColorIndex()][0];
		this.background2 = colororder[getSelectedColorIndex()][1];
		this.background3 = colororder[getSelectedColorIndex()][2];
		this.background4 = colororder[getSelectedColorIndex()][3];
		this.foreground = colororder[getSelectedColorIndex()][4];
		this.foreground2 = colororder[getSelectedColorIndex()][5];
		

		updateColors();
		updateBorders();
	}

	public int getSelectedColorIndex() {
		int index = 0;
		for (int i = 0; i < colororder.length; i++) {
			if (frame.coloritems.get(i).isSelected())
				index = i;
		}
		return index;
	}

	public void registerComponent(JComponent component, int foreground, int background) {
		colorcomponents.add(new ColorComponent(component, foreground, background));
	}

	public void updateBorders() {
		Border border = new LineBorder(background3, 5);
		Border menuborder = new LineBorder(background, 2);

		// MenuBorder

		frame.bar.setBorder(menuborder);
		frame.randomitem.setBorder(menuborder);
		frame.typomenu.setBorder(menuborder);
		frame.typoexitem.setBorder(menuborder);
		frame.typoviewitem.setBorder(menuborder);
		frame.typoclearitem.setBorder(menuborder);
		frame.textexmenu.setBorder(menuborder);
		frame.loadexitem.setBorder(menuborder);
		frame.importexitem.setBorder(menuborder);
		frame.quititem.setBorder(menuborder);
		frame.optionmenu.setBorder(menuborder);
		frame.colormenu.setBorder(menuborder);
		frame.randomoptionitem.setBorder(menuborder);

		for (JCheckBoxMenuItem item : frame.coloritems)
			item.setBorder(menuborder);
		
		for (JCheckBoxMenuItem item : frame.languageitems)
			item.setBorder(menuborder);

		// PanelBorder

		frame.exlistpanel.setBorder(border);
		frame.topcenter.setBorder(border);
		frame.textpanel.setBorder(border);
		frame.topsouth.setBorder(border);
		frame.namemainpanel.setBorder(border);
		frame.progress.setBorder(border);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateColors() {

		for (ColorComponent colorcomponent : colorcomponents) {
			JComponent component = colorcomponent.getComponent();
			Color background = colorcomponent.getBackground();
			Color foreground = colorcomponent.getForeground();

			if (component instanceof JPanel) {
				((JPanel) component).setBackground(background);
				((JPanel) component).setForeground(foreground);
			} else if (component instanceof JLabel) {
				((JLabel) component).setBackground(background);
				((JLabel) component).setForeground(foreground);
			} else if (component instanceof JButton) {
				((JButton) component).setBackground(background);
				((JButton) component).setForeground(foreground);
			} else if (component instanceof JRadioButton) {
				((JRadioButton) component).setBackground(background);
				((JRadioButton) component).setForeground(foreground);
			} else if (component instanceof JMenu) {
				((JMenu) component).setBackground(background);
				((JMenu) component).setForeground(foreground);
			} else if (component instanceof JMenuItem) {
				((JMenuItem) component).setBackground(background);
				((JMenuItem) component).setForeground(foreground);
			} else if (component instanceof JMenuBar) {
				((JMenuBar) component).setBackground(background);
				((JMenuBar) component).setForeground(foreground);
			} else if (component instanceof JTextArea) {
				((JTextArea) component).setBackground(background);
				((JTextArea) component).setForeground(foreground);
			} else if (component instanceof JComboBox) {
				((JComboBox<String>) component).setBackground(background);
				((JComboBox<String>) component).setForeground(foreground);
			} else if (component instanceof JProgressBar) {
				((JProgressBar) component).setBackground(background);
				((JProgressBar) component).setForeground(foreground);
			} else if (component instanceof JList) {
				((JList) component).setBackground(background);
				((JList) component).setForeground(foreground);
			}
		}
	}

	class ColorComponent {

		private int fore;

		private int back;

		private JComponent component;

		public ColorComponent(JComponent component, int foreground, int background) {

			this.component = component;

			fore = foreground;

			back = background;

		}

		public Color getForeground() {
			switch (fore) {

			case 1:
				return foreground;
			case 2:
				return foreground2;
			default:
				return foreground;
			}
		}

		public Color getBackground() {

			switch (back) {
			case 1:
				return background;
			case 2:
				return background2;
			case 3:
				return background3;
			case 4:
				return background4;
			default:
				return new Color(0, 0, 0);
			}

		}

		public JComponent getComponent() {
			return component;
		}

	}
	
	class Blinker extends Thread {

		public Blinker() {
			
		}
		
		
		public void run(){
			while (true) {
				frame.blink.setForeground(foreground);
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				frame.blink.setForeground(background);
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
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
				frame.textlabel.setForeground(foreground);
				flashrunning = false;
			}
		}
	}
	

	public void registerAllComponents() {

		// Panels

		registerComponent(frame.mainpanel, 1, 3);
		registerComponent(frame.toppanel, 1, 3);
		registerComponent(frame.textpanel, 1, 1);
		registerComponent(frame.progresspanel, 1, 1);
		registerComponent(frame.buttonpanel, 1, 4);
		registerComponent(frame.topcenter, 1, 2);
		registerComponent(frame.topsouth, 1, 2);
		registerComponent(frame.speedpanel, 1, 2);
		registerComponent(frame.radio, 1, 2);
		registerComponent(frame.timepanel, 1, 2);
		registerComponent(frame.typopanel, 1, 2);
		registerComponent(frame.keypanel, 1, 2);
		registerComponent(frame.exlistbuttons, 1, 4);
		registerComponent(frame.exlistpanel, 1, 2);
		registerComponent(frame.exlistnorthpanel, 1, 2);
		registerComponent(frame.namemainpanel, 1, 2);
		registerComponent(frame.namenorthpanel, 1, 2);
		registerComponent(frame.namebuttonpanel, 1, 4);
		registerComponent(frame.nametextpanel, 1, 2);

		// Labels

		registerComponent(frame.typolabel, 1, 1);
		registerComponent(frame.textlabel, 1, 1);
		registerComponent(frame.blink, 1, 1);
		registerComponent(frame.timelabel, 1, 1);
		registerComponent(frame.time, 1, 1);
		registerComponent(frame.keysleftlabel, 1, 1);
		registerComponent(frame.keysleftlabel, 1, 1);
		registerComponent(frame.keysleft, 1, 1);
		registerComponent(frame.typoslabel, 1, 1);
		registerComponent(frame.typos, 1, 1);
		registerComponent(frame.speedlabel, 1, 1);
		registerComponent(frame.speed, 1, 1);
		registerComponent(frame.speedunit, 1, 1);
		registerComponent(frame.exdiatitle, 1, 1);
		registerComponent(frame.exdialabel, 1, 1);
		registerComponent(frame.infolabel, 1, 1);

		// Buttons

		registerComponent(frame.newtextexcercise, 1, 1);
		registerComponent(frame.newtypo, 1, 1);
		registerComponent(frame.refresh, 1, 1);
		registerComponent(frame.newrandomtext, 1, 1);
		registerComponent(frame.quit, 1, 1);
		registerComponent(frame.loadex, 1, 1);
		registerComponent(frame.cancelex, 1, 1);
		registerComponent(frame.deleteex, 1, 1);
		registerComponent(frame.choosename, 1, 1);
		registerComponent(frame.cancelname, 1, 1);

		// RadioButtons

		registerComponent(frame.letters, 1, 2);
		registerComponent(frame.numbers, 1, 2);
		registerComponent(frame.basicsigns, 1, 2);
		registerComponent(frame.manysigns, 1, 2);
		registerComponent(frame.umlauts, 1, 2);
		registerComponent(frame.uppercases, 1, 2);

		// Menu

		registerComponent(frame.filemenu, 1, 1);
		registerComponent(frame.textexmenu, 1, 1);
		registerComponent(frame.typomenu, 1, 1);
		registerComponent(frame.optionmenu, 1, 1);
		registerComponent(frame.colormenu, 1, 1);
		registerComponent(frame.languagemenu, 1, 1);

		// MenuItems

		registerComponent(frame.randomitem, 1, 1);
		registerComponent(frame.quititem, 1, 1);
		registerComponent(frame.loadexitem, 1, 1);
		registerComponent(frame.importexitem, 1, 1);
		registerComponent(frame.typoexitem, 1, 1);
		registerComponent(frame.typoviewitem, 1, 1);
		registerComponent(frame.typoclearitem, 1, 1);
		registerComponent(frame.randomoptionitem, 1, 1);

		for (JCheckBoxMenuItem item : frame.coloritems)
			registerComponent(item, 1, 1);
		
		for (JCheckBoxMenuItem item : frame.languageitems)
			registerComponent(item, 1, 1);

		// MenuBar

		registerComponent(frame.bar, 1, 1);

		// TextArea

		registerComponent(frame.typoarea, 1, 2);

		// TextField

		registerComponent(frame.namefield, 1, 1);

		// List

		registerComponent(frame.list, 1, 1);

		// ComboBox

		registerComponent(frame.lengthbox, 1, 1);
		registerComponent(frame.languagebox, 1, 1);

		// ProgressBar

		registerComponent(frame.progress, 2, 1);

	}

}
