package typingpro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

/**
 * Diese Klasse ist für den Aufbau der Benutzteroberfläche zuständig.
 * 
 * @author Oliver Stapelfeldt
 */
public class Frame extends JFrame {

	// Attribute

	private static final long serialVersionUID = 1L;

	JPanel mainpanel, toppanel, textpanel, progresspanel, buttonpanel, topwest, topwests, topcenter, topsouth, topeast,
			radio, timepanel, speedpanel, typopanel, keypanel, exlistbuttons, exlistpanel, exlistnorthpanel,
			namemainpanel, namenorthpanel, namebuttonpanel, nametextpanel;

	Infolabel infolabel;

	JLabel typolabel, textlabel, blink, timelabel, time, keysleftlabel, keysleft, typoslabel, typos, speedlabel, speed,
			speedunit, exdiatitle, exdialabel, enternamelabel;

	JButton newtextexcercise, newtypo, refresh, newrandomtext, quit, loadex, cancelex, deleteex, choosename, cancelname;

	JMenuBar bar;

	JMenu filemenu, textexmenu, typomenu, optionmenu, colormenu;

	JMenuItem randomitem, quititem, loadexitem, importexitem, typoexitem, typoviewitem, typoclearitem;
	
	List<JCheckBoxMenuItem> coloritems;

	JRadioButton letters, numbers, basicsigns, manysigns, umlauts, uppercases;

	Color background;

	JDialog typodialog, importdialog, textexdialog;

	JTextArea typoarea;
	
	JTextField namefield;

	JScrollPane scroll, exscroll;

	JComboBox<String> lengthbox, languagebox;

	JProgressBar progress;

	LanguageManager languagem;

	TextManager textmanager;

	JList<String> list;

	Using using = Using.RANDOM;

	// Konstruktor

	public Frame() {

		// Frame wird wird eingerichtet.

		super("Typing Pro");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(500, 400, 950, 500);
		Dimension framelimit = new Dimension(950, 450);
		setMinimumSize(framelimit);

		// Panels werden erzeugt.

		mainpanel = new JPanel();
		toppanel = new JPanel();
		textpanel = new JPanel();
		progresspanel = new JPanel();
		buttonpanel = new JPanel();
		topwest = new JPanel();
		topcenter = new JPanel();
		topsouth = new JPanel();
		topeast = new JPanel();
		radio = new JPanel();
		topwests = new JPanel();
		timepanel = new JPanel();
		speedpanel = new JPanel();
		typopanel = new JPanel();
		keypanel = new JPanel();

		// Panels werden eingerichtet.

		mainpanel.setLayout(new GridLayout(2, 1, 0, 0));
		add(mainpanel, BorderLayout.CENTER);
		add(buttonpanel, BorderLayout.SOUTH);
		mainpanel.add(toppanel);
		mainpanel.add(textpanel);
		toppanel.setLayout(new BorderLayout());
		toppanel.add(topwest, BorderLayout.WEST);
		toppanel.add(topcenter, BorderLayout.CENTER);
		toppanel.add(topsouth, BorderLayout.SOUTH);
		topwest.setLayout(new BorderLayout());
		topwest.add(radio, BorderLayout.CENTER);
		topwests.setLayout(new GridLayout(0, 2, 0, 0));
		topwest.add(topwests, BorderLayout.SOUTH);
		toppanel.add(topeast, BorderLayout.EAST);
		radio.setLayout(new GridLayout(0, 1, 0, 0));
		topcenter.setLayout(new GridLayout(0, 2, 5, 0));
		topcenter.add(timepanel);
		topcenter.add(speedpanel);
		topcenter.add(typopanel);
		topcenter.add(keypanel);
		textpanel.setLayout(new BorderLayout());
		textpanel.add(progresspanel, BorderLayout.NORTH);

		// Objekte anderer Klassen werden erzeugt und der Languagemanager registriert
		// alle notwenigen Komponenten des Dialoges.

		textmanager = new TextManager(this);
		KeyManager keymanager = new KeyManager(this, textmanager);
		FileManager filemanager = new FileManager(this);
		FileManager.Windowcloser closer = filemanager.windowcloser;
		ColorManager colormanager = new ColorManager(this);
		addWindowListener(closer);

		languagem = new LanguageManager(this);
		languagem.registerComponent(Typo.called, "called");
		languagem.registerComponent(Typo.pressed, "pressed");
		languagem.registerComponent(Typo.emptytypolist, "emptytypolist");
		languagem.registerComponent(Typo.dialogtitle, "dialogtitle");
		addKeyListener(keymanager);

		// Top Bar
		// Menü wird erstellt und eingerichtet

		bar = new JMenuBar();
		setJMenuBar(bar);
		filemenu = new JMenu();

		filemenu.setOpaque(true);
		filemenu.getPopupMenu().setBorder(null);
		languagem.registerComponent(filemenu, "filemenu");
		bar.add(filemenu);
		
		randomitem = new JMenuItem();
		languagem.registerComponent(randomitem, "newitem");
		randomitem.addActionListener(e -> {
			using = Using.RANDOM;
			using.prepareExcercise(textmanager, keymanager, textlabel, typolabel);
		});
		filemenu.add(randomitem);
		
		typomenu = new JMenu();
		languagem.registerComponent(typomenu, "typomenu");
		typomenu.setOpaque(true);
		typomenu.getPopupMenu().setBorder(null);
		filemenu.add(typomenu);

		typoexitem = new JMenuItem();
		languagem.registerComponent(typoexitem, "typoexitem");
			typoexitem.addActionListener(e -> {
			using = Using.TYPOS;
			using.prepareExcercise(textmanager, keymanager, textlabel, typolabel);
		});
		typomenu.add(typoexitem);
	
		typoviewitem = new JMenuItem();
		languagem.registerComponent(typoviewitem, "typoviewitem");
		typoviewitem.addActionListener(e -> typodialog.setVisible(true));
		typomenu.add(typoviewitem);
		
		typoclearitem = new JMenuItem();
		languagem.registerComponent(typoclearitem, "typoclearitem");
		typoclearitem.addActionListener(e -> {
			Typo.typolist = new ArrayList<>();
			typoarea.setText(Typo.emptytypolist.getText());
		});
		typomenu.add(typoclearitem);

		textexmenu = new JMenu();
		languagem.registerComponent(textexmenu, "textexmenu");
		textexmenu.setOpaque(true);
		textexmenu.getPopupMenu().setBorder(null);
		filemenu.add(textexmenu);

		loadexitem = new JMenuItem();
		languagem.registerComponent(loadexitem, "loadexitem");
			loadexitem.addActionListener(e -> {
			list.setListData(filemanager.readFilenames());
			textexdialog.setVisible(true);
		});
		textexmenu.add(loadexitem);

		importexitem = new JMenuItem();
		languagem.registerComponent(importexitem, "importexitem");
		importexitem.addActionListener(e -> filemanager.chooseFile());
		textexmenu.add(importexitem);
		
		quititem = new JMenuItem();
		languagem.registerComponent(quititem, "quititem");
		quititem.addActionListener(e -> {
			filemanager.setConfig();
			System.exit(0);
		});
		filemenu.add(quititem);
		
		optionmenu = new JMenu();
		optionmenu.setOpaque(true);
		optionmenu.getPopupMenu().setBorder(null);
		languagem.registerComponent(optionmenu, "optionmenu");
		bar.add(optionmenu);
		
		colormenu = new JMenu();
		colormenu.setOpaque(true);
		colormenu.getPopupMenu().setBorder(null);
		languagem.registerComponent(colormenu, "colormenu");
		optionmenu.add(colormenu);
		
		coloritems = new ArrayList<>();
		
		for (int j=0; j< colormanager.colororder.length;j++) {
			int index =j;
			coloritems.add(new JCheckBoxMenuItem());
			coloritems.get(j).addActionListener(e -> {for (JCheckBoxMenuItem item : coloritems) 
				if( item != coloritems.get(index))item.setSelected(false); colormanager.setColors();});
			colormenu.add(coloritems.get(j));
		}
		
		languagem.registerComponent(coloritems.get(0), "beachitem");
		languagem.registerComponent(coloritems.get(1), "appleitem");
		languagem.registerComponent(coloritems.get(2), "wooditem");
		languagem.registerComponent(coloritems.get(3), "deepseaitem");
		languagem.registerComponent(coloritems.get(4), "greekitem");
		languagem.registerComponent(coloritems.get(5), "mountainitem");
		languagem.registerComponent(coloritems.get(6), "sushiitem");
		languagem.registerComponent(coloritems.get(7), "strawberryitem");
		languagem.registerComponent(coloritems.get(8), "kitchenitem");
		languagem.registerComponent(coloritems.get(9), "poppiesitem");
	
		
				
		
		// TopPanel West

		// RadioButtons und Combobox werden erstellt und eingerichtet

		Font radiof = new Font("Arial", Font.BOLD, 15);
		letters = new JRadioButton();
		languagem.registerComponent(letters, "lettersradio");
		letters.setFont(radiof);
		numbers = new JRadioButton();
		languagem.registerComponent(numbers, "numbersradio");
		numbers.setFont(radiof);
		basicsigns = new JRadioButton();
		languagem.registerComponent(basicsigns, "basicsignsradio");
		basicsigns.setFont(radiof);
		manysigns = new JRadioButton();
		languagem.registerComponent(manysigns, "manysignsradio");
		manysigns.setFont(radiof);
		umlauts = new JRadioButton();
		languagem.registerComponent(umlauts, "umlautsradio");
		umlauts.setFont(radiof);
		uppercases = new JRadioButton();
		languagem.registerComponent(uppercases, "uppercasesradio");
		uppercases.setFont(radiof);
	
		letters.setFocusable(false);
		numbers.setFocusable(false);
		basicsigns.setFocusable(false);
		manysigns.setFocusable(false);
		umlauts.setFocusable(false);
		uppercases.setFocusable(false);

		radio.add(letters);
		radio.add(umlauts);
		radio.add(basicsigns);
		radio.add(manysigns);
		radio.add(numbers);
		radio.add(uppercases);

		String[] lengthb = { "kurz", "mittel", "lang" };
		lengthbox = new JComboBox<String>(lengthb);
		languagem.registerComponent(lengthbox, "lengthbox");
		lengthbox.setFocusable(false);
		lengthbox.setSelectedIndex(0);
		topwests.add(lengthbox);

		// TopPanel Center

		// Labels werden erstellt und eingerichtet.
		Font labelfont = new Font("Arial Black", Font.PLAIN, 17);

		timelabel = new JLabel();
		languagem.registerComponent(timelabel, "timelabel");
		timelabel.setFont(labelfont);
		timepanel.add(timelabel);
		time = new JLabel();
		time.setFont(labelfont);
		timepanel.add(time);
		speedlabel = new JLabel();
		languagem.registerComponent(speedlabel, "speedlabel");
		speedlabel.setFont(labelfont);
		speedpanel.add(speedlabel);
		speed = new JLabel();
		speed.setFont(labelfont);
		speedpanel.add(speed);
		speedunit = new JLabel();
		speedunit.setVisible(false);
		languagem.registerComponent(speedunit, "speedunit");
		speedunit.setFont(labelfont);
		speedpanel.add(speedunit);
		typoslabel = new JLabel();
		languagem.registerComponent(typoslabel, "typolabel");
		typoslabel.setFont(labelfont);
		typopanel.add(typoslabel);
		typos = new JLabel("" + Typo.typos);
		typos.setFont(labelfont);
		typopanel.add(typos);
		keysleftlabel = new JLabel();
		languagem.registerComponent(keysleftlabel, "keyslabel");
		keysleftlabel.setFont(labelfont);
		keypanel.add(keysleftlabel);
		keysleft = new JLabel();
		keysleft.setFont(labelfont);
		keypanel.add(keysleft);

		// Language Panel

		// Combobox wird erstellt und eingerichtet.

		String[] langs = { "de", "en" };
		languagebox = new JComboBox<>(langs);
		languagem.registerComponent(languagebox, "languagebox");
		languagebox.setFocusable(false);
		languagebox.setSelectedIndex(0);
		languagebox.addActionListener(e -> languagem.setlang());
		topeast.add(languagebox);

		// InfoPanel

		// Label wird erstellt und eingerichtet.

		typolabel = new JLabel();
		typolabel.setFont(new Font("Arial Black", Font.BOLD, 23));
		topsouth.add(typolabel, BorderLayout.SOUTH);
		infolabel = new Infolabel();
		infolabel.setFont(new Font("Arial Black", Font.BOLD, 23));
		topsouth.add(infolabel, BorderLayout.SOUTH);

		// Progress Panel

		// ProgressBar wird erstellt und eingerichtet.

		progress = new JProgressBar(0, 100);
		progress.setValue(0);
		progress.setStringPainted(true);
		progresspanel.add(progress);

		// TextPanel

		// Label werden erstellt und eingerichtet. Thread wird gestartet.

		textlabel = new JLabel("");
		textlabel.setFont(new Font("Arial", Font.BOLD, 50));
		blink = new JLabel("         |");
		blink.setFont(new Font("Arial", Font.BOLD, 50));
		textpanel.add(blink, BorderLayout.WEST);
		textpanel.add(textlabel, BorderLayout.CENTER);
		Thread blinker = new Blinker(this);
		blinker.start();

		// ButtonPanel

		// Buttons werden erstellt und eingerichtet.


		Font bfont = new Font("Arial", Font.BOLD, 18);
		refresh = new JButton();
		languagem.registerComponent(refresh, "refreshbutton");
		refresh.setFont(bfont);
		refresh.addActionListener(e -> {
			using.prepareExcercise(textmanager, keymanager, textlabel, typolabel);
		});
		refresh.setFocusable(false);
	
		newrandomtext = new JButton();
		languagem.registerComponent(newrandomtext, "newrandomtextbutton");
		newrandomtext.setFont(bfont);
		newrandomtext.addActionListener(e -> {
			using = Using.RANDOM;
			using.prepareExcercise(textmanager, keymanager, textlabel, typolabel);
		});
		newrandomtext.setFocusable(false);


		newtextexcercise = new JButton();
		languagem.registerComponent(newtextexcercise, "newtextexcercisebutton");
		newtextexcercise.setFont(bfont);
		newtextexcercise.addActionListener(e -> {
			list.setListData(filemanager.readFilenames());
			textexdialog.setVisible(true);
		});
		newtextexcercise.setFocusable(false);

		quit = new JButton();
		languagem.registerComponent(quit, "quitbutton");
		quit.setFont(bfont);
		quit.addActionListener(e -> {
			filemanager.setConfig();
			System.exit(0);
		});
		quit.setFocusable(false);

		newtypo = new JButton();
		languagem.registerComponent(newtypo, "newtypobutton");
		newtypo.setFont(bfont);
		newtypo.addActionListener(e -> {
			using = Using.TYPOS;
			using.prepareExcercise(textmanager, keymanager, textlabel, typolabel);
		});
		newtypo.setFocusable(false);

		buttonpanel.add(refresh);
		buttonpanel.add(newrandomtext);
		buttonpanel.add(newtypo);
		buttonpanel.add(newtextexcercise);
		buttonpanel.add(quit);

		// Dialoge

		// Dialog für Tippfehler wird erstellt und eingerichtet.

		typodialog = new JDialog();
		typodialog.setBounds(150, 400, 300, 300);
		typoarea = new JTextArea();
		typoarea.setFont(new Font("Arial", Font.BOLD, 20));
		typoarea.setEditable(false);
		scroll = new JScrollPane(typoarea);
		typodialog.add(scroll);

		// Dialog für Imports wird erstellt und eingerichtet

		Font bfont2 = new Font("Arial", Font.BOLD, 15);

		importdialog = new JDialog();
		namemainpanel = new JPanel();
		importdialog.setBounds(400, 400, 300, 200);
		importdialog.add(namemainpanel);
		namemainpanel.setLayout(new BorderLayout());
		nametextpanel = new JPanel();
		nametextpanel.setLayout(new GridLayout(3, 1));

		enternamelabel = new JLabel("Enter a name");
		enternamelabel.setFont(new Font("Arial Black", Font.PLAIN, 17));
		languagem.registerComponent(enternamelabel, "enternamelabel");
		namenorthpanel = new JPanel();
		nametextpanel.add(namenorthpanel);
		namenorthpanel.add(enternamelabel);
		namemainpanel.add(nametextpanel, BorderLayout.CENTER);
		namebuttonpanel = new JPanel();
		namemainpanel.add(namebuttonpanel, BorderLayout.SOUTH);
		namefield = new JTextField();
		namefield.addActionListener(e -> {
			String exname = namefield.getText().trim();
			if (exname.length() > 0) {
				filemanager.importFile(exname);
				importdialog.setVisible(false);
				namefield.setText("");
			}
		});
		namefield.setFont(radiof);
		nametextpanel.add(namefield, BorderLayout.CENTER);
		choosename = new JButton();
		choosename.setFont(bfont2);
		choosename.setFocusable(false);
		languagem.registerComponent(choosename, "choosenameenter");
		choosename.addActionListener(e -> {
			String exname = namefield.getText().trim();
			if (exname.length() > 0) {
				filemanager.importFile(exname);
				importdialog.setVisible(false);
				namefield.setText("");
			}
		});
		namebuttonpanel.add(choosename, BorderLayout.SOUTH);
		cancelname = new JButton();
		cancelname.setFont(bfont2);
		cancelname.setFocusable(false);
		languagem.registerComponent(cancelname, "choosenamecancel");
		cancelname.addActionListener(e -> importdialog.setVisible(false));
		namebuttonpanel.add(cancelname, BorderLayout.SOUTH);

		// Dialog für die Auswahl der Textübungen wird erstellt und eingerichtet

		Font listfont = new Font("Arial", Font.BOLD, 15);
		textexdialog = new JDialog();
		textexdialog.setLocationRelativeTo(null);
		textexdialog.setSize(350, 300);
		textexdialog.setMinimumSize(new Dimension(320,300));

		exlistpanel = new JPanel();
		exlistpanel.setLayout(new BorderLayout());
		textexdialog.add(exlistpanel, BorderLayout.CENTER);

		exlistbuttons = new JPanel();
		textexdialog.add(exlistbuttons, BorderLayout.SOUTH);

		exdiatitle = new JLabel();
		languagem.registerComponent(exdiatitle, "loadex2");

		exlistnorthpanel = new JPanel();
		exlistpanel.add(exlistnorthpanel, BorderLayout.NORTH);

		exdialabel = new JLabel();
		languagem.registerComponent(exdialabel, "exdialabel");
		exdialabel.setFont(labelfont);
		exlistnorthpanel.add(exdialabel);

		loadex = new JButton();
		languagem.registerComponent(loadex, "loadex");
		loadex.setFont(bfont2);
		loadex.addActionListener(e -> {
			using = Using.TEXT;
			textmanager.textexcercise = filemanager.readTextfilefromname(list.getSelectedValue());
			using.prepareExcercise(textmanager, keymanager, textlabel, typolabel);
			textexdialog.setVisible(false);
		});
		exlistbuttons.add(loadex);

		deleteex = new JButton();
		languagem.registerComponent(deleteex, "deleteex");
		deleteex.setFont(bfont2);
		deleteex.addActionListener(e -> {
			filemanager.deleteTextfilefromname(list.getSelectedValue());
			list.setListData(filemanager.readFilenames());
		});
		exlistbuttons.add(deleteex);

		cancelex = new JButton();
		languagem.registerComponent(cancelex, "cancelex");
		cancelex.setFont(bfont2);
		cancelex.addActionListener(e -> textexdialog.setVisible(false));
		exlistbuttons.add(cancelex);

		list = new JList<>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(background);
		list.setFont(listfont);

		exscroll = new JScrollPane(list);
		exlistpanel.add(exscroll, BorderLayout.CENTER);

		// TestButton

//		 JButton test = new JButton();
//		 test.setText("test");
//		 test.addActionListener(e-> System.out.println(filemanager.firsttime));
//		 test.setFocusable(false);
//		 buttonpanel.add(test);

		colormanager.registerAllComponents();
		filemanager.loadConfig();
		colormanager.setColors();
		languagem.setlang();
		setVisible(true);

	}

	/**
	 * Dieses Label enthält selbst verschiedene JLabels, die im LanguageManager
	 * (welcher nur JComponents annimmt) registriert werden. Jedes dieser Labels ist
	 * für die Ausgabe einer Nachricht zuständig. Texte werden mit Hilfe dieser
	 * Labels gesetzt.
	 * 
	 * @author Oliver Stapelfeldt
	 */

	class Infolabel extends JLabel {
		private static final long serialVersionUID = 1L;

		JLabel reminder, emptylabel, anykeytostart, anykeytorestart, chooseanykeys, typopoolempty, typoadded;

		public Infolabel() {

			emptylabel = new JLabel();
			anykeytostart = new JLabel();
			anykeytorestart = new JLabel();
			chooseanykeys = new JLabel();
			typopoolempty = new JLabel();
			typoadded = new JLabel();
			languagem.registerComponent(emptylabel, "emptylabel");
			languagem.registerComponent(anykeytostart, "anykeytostart");
			languagem.registerComponent(anykeytorestart, "anykeytorestart");
			languagem.registerComponent(chooseanykeys, "chooseanykeys");
			languagem.registerComponent(typopoolempty, "typopoolempty");
			languagem.registerComponent(typoadded, "addedtotypopool");
			setInfotext(anykeytostart);
			reminder = anykeytostart;
		}

		/**
		 * Diese Methode dient als Text-setter. Das Label reminder merkt sich den
		 * zuletzt gesetzten Text, damit der LanguageManager darauf zugreifen kann.
		 * 
		 * @param JLabel
		 */
		public void setInfotext(JLabel label) {
			reminder = label;
			setText(label.getText());
		}
	}

	public enum Using {
		RANDOM {
			@Override
			public void prepareExcercise(TextManager textmanager, KeyManager keymanager, JLabel textlabel,
					JLabel typolabel) {
				keymanager.stoptimer();
				textmanager.setRandomlist();
				textlabel.setText((textmanager.setRandomtext()));
				keymanager.textlength = textmanager.text.length();
				typolabel.setVisible(false);
			}
		},
		TYPOS {
			@Override
			public void prepareExcercise(TextManager textmanager, KeyManager keymanager, JLabel textlabel,
					JLabel typolabel) {
				keymanager.stoptimer();
				textmanager.setTypolist();
				textlabel.setText((textmanager.setRandomtext()));
				keymanager.textlength = textmanager.text.length();
				typolabel.setVisible(false);
			}
		},
		TEXT {
			@Override
			public void prepareExcercise(TextManager textmanager, KeyManager keymanager, JLabel textlabel,
					JLabel typolabel) {
				keymanager.stoptimer();
				textmanager.text = new StringBuilder(textmanager.textexcercise);
				textlabel.setText(textmanager.text.toString());
				textmanager.resetComp();
				keymanager.textlength = textmanager.text.length();
				typolabel.setVisible(false);

			}
		};
		public abstract void prepareExcercise(TextManager textmanager, KeyManager keymanager, JLabel textlabel,
				JLabel typolabel);
	}

	public static void main(String[] args) {
		new Frame();
	}
}
