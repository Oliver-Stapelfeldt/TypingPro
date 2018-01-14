package typingpro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
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
 * Diese Klasse ist f�r den Aufbau der Benutzteroberfl�che zust�ndig.
 * 
 * @author Oliver Stapelfeldt
 */
public class Frame extends JFrame {

	// Attribute

	private static final long serialVersionUID = 1L;

	JPanel mainpanel, toppanel, textpanel, progresspanel, buttonpanel, topwest, topwests, topcenter, topsouth, topeast,
			radio, timepanel, speedpanel, typopanel, keypanel, exlistbuttons, exlistpanel, exlistnorthpanel,
			namemainpanel, namenorthpanel, namebuttonpanel;

	Infolabel infolabel;

	JLabel typolabel, textlabel, blink, timelabel, time, keysleftlabel, keysleft, typoslabel, typos, speedlabel, speed,
			speedunit, exdiatitle, exdialabel;

	JButton newtextexcercise, newtypo, refresh, newrandomtext, quit, loadex, cancelex, deleteex;

	JMenuBar bar;

	JMenu filemenu, textexmenu, typomenu;

	JMenuItem randomitem, quititem, loadexitem, importexitem, typoexitem, typoviewitem, typoclearitem;

	JRadioButton letters, numbers, basicsigns, manysigns, umlauts, uppercases;

	Color background, background2, background3, background4;

	LineBorder border, menuborder;

	JDialog typodialog, importdialog, textexdialog;

	JTextArea area;

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

		// Hintergrundfarben werden festfelegt und R�nder hinzugef�gt.

		background = new Color(243, 205, 5);
		background2 = new Color(244, 159, 5);
		background3 = new Color(208, 112, 3);
		background4 = new Color(54, 104, 141);

		mainpanel.setBackground(background3);
		toppanel.setBackground(background3);
		textpanel.setBackground(background);
		progresspanel.setBackground(background);
		topwest.setBackground(background2);
		topwests.setBackground(background2);
		topcenter.setBackground(background2);
		topsouth.setBackground(background2);
		topeast.setBackground(background2);
		radio.setBackground(background2);
		buttonpanel.setBackground(background4);
		timepanel.setBackground(background2);
		speedpanel.setBackground(background2);
		typopanel.setBackground(background2);
		keypanel.setBackground(background2);

		border = new LineBorder(background3, 5);
		topwest.setBorder(border);
		topcenter.setBorder(border);
		textpanel.setBorder(border);
		topsouth.setBorder(border);
		topeast.setBorder(border);

		// Objekte anderer Klassen werden erzeugt und der Languagemanager registriert
		// alle notwenigen Komponenten des Dialoges.

		textmanager = new TextManager(this);
		KeyManager keymanager = new KeyManager(this, textmanager);
		FileManager filemanager = new FileManager(this);
		FileManager.Windowcloser closer = filemanager.windowcloser;
		addWindowListener(closer);

		languagem = new LanguageManager(this);
		languagem.registerComponent(Typo.called, "called");
		languagem.registerComponent(Typo.pressed, "pressed");
		languagem.registerComponent(Typo.emptytypolist, "emptytypolist");
		languagem.registerComponent(Typo.dialogtitle, "dialogtitle");
		addKeyListener(keymanager);

		// Top Bar
		// Men� wird erstellt und eingerichtet

		Color menucolor = background;
		menuborder = new LineBorder(background, 2);
		bar = new JMenuBar();
		bar.setBackground(menucolor);

		bar.setBorder(menuborder);
		setJMenuBar(bar);
		filemenu = new JMenu();
		filemenu.setBackground(menucolor);
		filemenu.setOpaque(true);
		filemenu.getPopupMenu().setBorder(null);

		languagem.registerComponent(filemenu, "filemenu");
		bar.add(filemenu);
		randomitem = new JMenuItem();
		languagem.registerComponent(randomitem, "newitem");
		randomitem.setBackground(menucolor);
		randomitem.setBorder(menuborder);

		randomitem.addActionListener(e -> {
			using = Using.RANDOM;
			using.prepareExcercise(textmanager, keymanager, textlabel, typolabel);
		});
		filemenu.add(randomitem);
		
		typomenu = new JMenu();
		languagem.registerComponent(typomenu, "typomenu");
		typomenu.setOpaque(true);
		typomenu.setBackground(menucolor);
		typomenu.getPopupMenu().setBorder(null);
		typomenu.setBorder(menuborder);
		filemenu.add(typomenu);
		typoexitem = new JMenuItem();
		languagem.registerComponent(typoexitem, "typoexitem");
		typoexitem.setBackground(menucolor);
		typoexitem.setBorder(menuborder);
		typoexitem.addActionListener(e -> {
			using = Using.TYPOS;
			using.prepareExcercise(textmanager, keymanager, textlabel, typolabel);
		});
		typomenu.add(typoexitem);
		typoviewitem = new JMenuItem();
		languagem.registerComponent(typoviewitem, "typoviewitem");
		typoviewitem.setBackground(menucolor);
		typoviewitem.setBorder(menuborder);
		typoviewitem.addActionListener(e -> typodialog.setVisible(true));
		typomenu.add(typoviewitem);
		typoclearitem = new JMenuItem();
		languagem.registerComponent(typoclearitem, "typoclearitem");
		typoclearitem.setBackground(menucolor);
		typoclearitem.setBorder(menuborder);
		typoclearitem.addActionListener(e -> {
			Typo.typolist = new ArrayList<>();
			area.setText(Typo.emptytypolist.getText());
		});
		typomenu.add(typoclearitem);

		textexmenu = new JMenu();
		languagem.registerComponent(textexmenu, "textexmenu");
		textexmenu.setOpaque(true);
		textexmenu.setBackground(menucolor);
		textexmenu.getPopupMenu().setBorder(null);
		textexmenu.setBorder(menuborder);
		filemenu.add(textexmenu);
		loadexitem = new JMenuItem();
		languagem.registerComponent(loadexitem, "loadexitem");
		loadexitem.setBackground(menucolor);
		loadexitem.addActionListener(e -> {
			list.setListData(filemanager.readFilenames());
			textexdialog.setVisible(true);
		});
		loadexitem.setBorder(menuborder);
		textexmenu.add(loadexitem);
		importexitem = new JMenuItem();
		languagem.registerComponent(importexitem, "importexitem");
		importexitem.setBackground(menucolor);
		importexitem.setBorder(menuborder);
		importexitem.addActionListener(e -> filemanager.chooseFile());
		textexmenu.add(importexitem);
		
		quititem = new JMenuItem();
		languagem.registerComponent(quititem, "quititem");
		quititem.setBackground(menucolor);
		quititem.setBorder(menuborder);
		quititem.addActionListener(e -> {
			filemanager.setConfig();
			System.exit(0);
		});
		filemenu.add(quititem);

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
		letters.setBackground(background2);
		letters.setFocusable(false);
		letters.setSelected(true);
		numbers.setBackground(background2);
		numbers.setFocusable(false);
		basicsigns.setBackground(background2);
		basicsigns.setFocusable(false);
		basicsigns.setSelected(true);
		manysigns.setBackground(background2);
		manysigns.setFocusable(false);
		umlauts.setBackground(background2);
		umlauts.setFocusable(false);
		umlauts.setSelected(true);
		uppercases.setBackground(background2);
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
		typolabel.setForeground(new Color(30, 30, 30));
		typolabel.setFont(new Font("Arial Black", Font.BOLD, 23));
		topsouth.add(typolabel, BorderLayout.SOUTH);
		infolabel = new Infolabel();
		infolabel.setForeground(new Color(30, 30, 30));
		infolabel.setFont(new Font("Arial Black", Font.BOLD, 23));
		topsouth.add(infolabel, BorderLayout.SOUTH);

		// Progress Panel

		// ProgressBar wird erstellt und eingerichtet.

		progress = new JProgressBar(0, 100);
		progress.setValue(0);
		progress.setStringPainted(true);
		progress.setForeground(background4);
		progress.setBorder(border);
		progresspanel.add(progress);

		// TextPanel

		// Label werden erstellt und eingerichtet. Thread wird gestartet.

		textlabel = new JLabel("");
		textlabel.setForeground(new Color(30, 30, 30));
		textlabel.setFont(new Font("Arial", Font.BOLD, 50));
		blink = new JLabel("         |");
		blink.setForeground(new Color(30, 30, 30));
		blink.setFont(new Font("Arial", Font.BOLD, 50));
		textpanel.add(blink, BorderLayout.WEST);
		textpanel.add(textlabel, BorderLayout.CENTER);
		Thread blinker = new Blinker(this);
		blinker.start();

		// ButtonPanel

		// Buttons werden erstellt und eingerichtet.

		Color buttonforeground = new Color(30, 30, 30);
		Font bfont = new Font("Arial", Font.BOLD, 18);
		refresh = new JButton();
		languagem.registerComponent(refresh, "refreshbutton");
		refresh.setFont(bfont);
		refresh.addActionListener(e -> {
			using.prepareExcercise(textmanager, keymanager, textlabel, typolabel);
		});
		refresh.setFocusable(false);
		refresh.setForeground(buttonforeground);
		refresh.setBackground(background2);

		newrandomtext = new JButton();
		languagem.registerComponent(newrandomtext, "newrandomtextbutton");
		newrandomtext.setFont(bfont);
		newrandomtext.addActionListener(e -> {
			using = Using.RANDOM;
			using.prepareExcercise(textmanager, keymanager, textlabel, typolabel);
		});
		newrandomtext.setFocusable(false);
		newrandomtext.setForeground(buttonforeground);
		newrandomtext.setBackground(background2);

		newtextexcercise = new JButton();
		languagem.registerComponent(newtextexcercise, "newtextexcercisebutton");
		newtextexcercise.setFont(bfont);
		newtextexcercise.addActionListener(e -> {
			list.setListData(filemanager.readFilenames());
			textexdialog.setVisible(true);
		});
		newtextexcercise.setFocusable(false);
		newtextexcercise.setForeground(buttonforeground);
		newtextexcercise.setBackground(background2);

		quit = new JButton();
		languagem.registerComponent(quit, "quitbutton");
		quit.setFont(bfont);
		quit.addActionListener(e -> {
			filemanager.setConfig();
			System.exit(0);
		});
		quit.setFocusable(false);
		quit.setForeground(buttonforeground);
		quit.setBackground(background2);

		newtypo = new JButton();
		languagem.registerComponent(newtypo, "newtypobutton");
		newtypo.setFont(bfont);
		newtypo.addActionListener(e -> {
			using = Using.TYPOS;
			using.prepareExcercise(textmanager, keymanager, textlabel, typolabel);
		});
		newtypo.setFocusable(false);
		newtypo.setForeground(buttonforeground);
		newtypo.setBackground(background2);

		buttonpanel.add(refresh);
		buttonpanel.add(newrandomtext);
		buttonpanel.add(newtypo);
		buttonpanel.add(newtextexcercise);
		buttonpanel.add(quit);

		// Dialoge

		// Dialog f�r Tippfehler wird erstellt und eingerichtet.

		typodialog = new JDialog();
		typodialog.setBounds(150, 400, 300, 300);
		area = new JTextArea();
		area.setFont(new Font("Arial", Font.BOLD, 20));
		area.setBackground(background2);
		area.setEditable(false);
		scroll = new JScrollPane(area);
		typodialog.add(scroll);

		// Dialog f�r Imports wird erstellt und eingerichtet

		Font bfont2 = new Font("Arial", Font.BOLD, 15);

		importdialog = new JDialog();
		namemainpanel = new JPanel();
		importdialog.setBounds(400, 400, 300, 200);
		importdialog.add(namemainpanel);
		namemainpanel.setLayout(new BorderLayout());
		namemainpanel.setBackground(background2);
		namemainpanel.setBorder(border);
		JPanel textpanel = new JPanel();
		textpanel.setBackground(background2);
		textpanel.setLayout(new GridLayout(3, 1));

		JLabel enternamelabel = new JLabel("Enter a name");
		enternamelabel.setFont(new Font("Arial Black", Font.PLAIN, 17));
		languagem.registerComponent(enternamelabel, "enternamelabel");
		namenorthpanel = new JPanel();
		namenorthpanel.setBackground(background2);
		textpanel.add(namenorthpanel);
		namenorthpanel.add(enternamelabel);
		namemainpanel.add(textpanel, BorderLayout.CENTER);
		namebuttonpanel = new JPanel();
		namebuttonpanel.setBackground(background4);
		namemainpanel.add(namebuttonpanel, BorderLayout.SOUTH);
		JTextField namefield = new JTextField();
		namefield.addActionListener(e -> {
			String exname = namefield.getText().trim();
			if (exname.length() > 0) {
				filemanager.importFile(exname);
				importdialog.setVisible(false);
				namefield.setText("");
			}
		});
		namefield.setFont(radiof);
		textpanel.add(namefield, BorderLayout.CENTER);
		JButton choosename = new JButton();
		choosename.setFont(bfont2);
		choosename.setFocusable(false);
		choosename.setForeground(buttonforeground);
		choosename.setBackground(background2);
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
		JButton cancelname = new JButton();
		cancelname.setFont(bfont2);
		cancelname.setFocusable(false);
		cancelname.setForeground(buttonforeground);
		cancelname.setBackground(background2);
		languagem.registerComponent(cancelname, "choosenamecancel");
		cancelname.addActionListener(e -> importdialog.setVisible(false));
		namebuttonpanel.add(cancelname, BorderLayout.SOUTH);

		// Dialog f�r die Auswahl der Text�bungen wird erstellt und eingerichtet

		Font listfont = new Font("Arial", Font.BOLD, 15);
		textexdialog = new JDialog();
		textexdialog.setLocationRelativeTo(null);
		textexdialog.setSize(350, 300);
		textexdialog.setMinimumSize(new Dimension(320,300));
		textexdialog.setBackground(background2);

		exlistpanel = new JPanel();
		exlistpanel.setBackground(background2);
		exlistpanel.setLayout(new BorderLayout());
		textexdialog.add(exlistpanel, BorderLayout.CENTER);

		exlistbuttons = new JPanel();
		exlistbuttons.setBackground(background4);
		textexdialog.add(exlistbuttons, BorderLayout.SOUTH);

		exdiatitle = new JLabel();
		languagem.registerComponent(exdiatitle, "loadex2");

		exlistnorthpanel = new JPanel();
		exlistnorthpanel.setBackground(background2);
		exlistpanel.setBorder(border);
		exlistpanel.add(exlistnorthpanel, BorderLayout.NORTH);

		exdialabel = new JLabel();
		languagem.registerComponent(exdialabel, "exdialabel");
		exdialabel.setFont(labelfont);
		exlistnorthpanel.add(exdialabel);

		loadex = new JButton();
		languagem.registerComponent(loadex, "loadex");
		loadex.setFont(bfont2);
		loadex.setBackground(background2);
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
		deleteex.setBackground(background2);
		deleteex.addActionListener(e -> {
			filemanager.deleteTextfilefromname(list.getSelectedValue());
			list.setListData(filemanager.readFilenames());
		});

		exlistbuttons.add(deleteex);

		cancelex = new JButton();
		languagem.registerComponent(cancelex, "cancelex");
		cancelex.setFont(bfont2);
		cancelex.setBackground(background2);
		cancelex.addActionListener(e -> textexdialog.setVisible(false));
		exlistbuttons.add(cancelex);

		list = new JList<>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(background);
		list.setFont(listfont);

		exscroll = new JScrollPane(list);
		exlistpanel.add(exscroll, BorderLayout.CENTER);

		// TestButton

		// JButton test = new JButton();
		// test.setText("test");
		// test.addActionListener(e-> filemanager.readFilenames());
		// test.setFocusable(false);
		// buttonpanel.add(test);

		filemanager.loadConfig();
		languagem.setlang();
		setVisible(true);

	}

	/**
	 * Dieses Label enth�lt selbst verschiedene JLabels, die im LanguageManager
	 * (welcher nur JComponents annimmt) registriert werden. Jedes dieser Labels ist
	 * f�r die Ausgabe einer Nachricht zust�ndig. Texte werden mit Hilfe dieser
	 * Labels gesetzt.
	 * 
	 * @author Oliver Stapelfeldt
	 */

	class Infolabel extends JLabel {
		private static final long serialVersionUID = 1L;

		JLabel reminder;
		JLabel emptylabel;
		JLabel anykeytostart;
		JLabel anykeytorestart;
		JLabel chooseanykeys;
		JLabel typopoolempty;
		JLabel typoadded;

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
