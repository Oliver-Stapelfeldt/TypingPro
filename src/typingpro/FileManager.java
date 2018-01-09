package typingpro;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileManager {

	// Attribute

	Frame frame;

	Windowcloser windowcloser;

	Path datapath;

	Path expath;

	String extext;

	// Konstruktor

	public FileManager(Frame frame) {
		this.frame = frame;
		this.windowcloser = new Windowcloser();
		datapath = Paths.get(System.getProperty("user.home")).toAbsolutePath().resolve("Typing Pro data").normalize();
		expath = datapath.toAbsolutePath().resolve("Excercises").normalize();
	}

	// fachliche Methoden

	/**
	 * Speichert sämtliche ausgewählte Einstellungen in die config.txt.
	 */

	public void setConfig() {
		try {
			if (!Files.isDirectory(datapath))
				Files.createDirectory(datapath);

			Path config = datapath.resolve("config.txt");
			if (!Files.exists(config))
				Files.createFile(config);

			FileWriter writer = new FileWriter(config.toString());

			PrintWriter printer = new PrintWriter(writer);

			printer.println("letters=" + frame.letters.isSelected());
			printer.println("numbers=" + frame.numbers.isSelected());
			printer.println("basicsigns=" + frame.basicsigns.isSelected());
			printer.println("manysigns=" + frame.manysigns.isSelected());
			printer.println("umlauts=" + frame.umlauts.isSelected());
			printer.println("uppercases=" + frame.uppercases.isSelected());
			printer.println("length=" + frame.lengthbox.getSelectedIndex());
			printer.println("language=" + frame.languagebox.getSelectedIndex());
			printer.close();
		} catch (Exception e) {
		}
	}

	/**
	 * Lädt ausgewählte Einstellungen aus der config.txt und setzt Diese.
	 */

	public void loadConfig() {
		try {

			Path path2 = datapath.resolve("config.txt");

			FileReader fr = new FileReader(path2.toString());
			BufferedReader br = new BufferedReader(fr);
			String[] temparr;
			String temp;
			Properties prop = new Properties();
			while ((temp = br.readLine()) != null) {
				temparr = temp.split("=");
				prop.setProperty(temparr[0], temparr[1]);
			}

			br.close();
			frame.letters.setSelected(Boolean.parseBoolean(prop.getProperty("letters")));
			frame.numbers.setSelected(Boolean.parseBoolean(prop.getProperty("numbers")));
			frame.basicsigns.setSelected(Boolean.parseBoolean(prop.getProperty("basicsigns")));
			frame.manysigns.setSelected(Boolean.parseBoolean(prop.getProperty("manysigns")));
			frame.umlauts.setSelected(Boolean.parseBoolean(prop.getProperty("umlauts")));
			frame.uppercases.setSelected(Boolean.parseBoolean(prop.getProperty("uppercases")));
			frame.lengthbox.setSelectedIndex(Integer.parseInt(prop.getProperty("length")));
			frame.languagebox.setSelectedIndex(Integer.parseInt(prop.getProperty("language")));

		} catch (Exception e) {
			System.out.println("config.txt konnte nicht geladen werden.");
		}

	}

	public void chooseFile() {
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt");
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(filter);
		int returnVal = chooser.showDialog(null, "import");
		File file = chooser.getSelectedFile();
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			readTextfile(file);
			frame.importdialog.setVisible(true);}
	}
	
	
	
	public String readTextfile(File file) {
		StringBuilder builder = new StringBuilder();
		try {
			if (file.getName().endsWith(".txt")) {
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String temp;
				while ((temp = br.readLine()) != null)
					builder.append(temp);
				br.close();
				extext = builder.toString();
			}
		} catch (IOException e) {}
		return builder.toString();
	}

	public void importFile(String userentry) {
		try {

			if (!Files.isDirectory(expath))
				Files.createDirectory(expath);
			Path exc = expath.resolve(userentry + ".txt");
			if (!Files.exists(exc))
				Files.createFile(exc);
			FileWriter fw = new FileWriter(exc.toString());
			PrintWriter pw = new PrintWriter(fw);
			pw.print(extext);
			pw.close();
		} catch (Exception e) {
		}
	}

	public String[] readFilenames() {
		try {
			if (!Files.isDirectory(expath))
				Files.createDirectory(expath);
		} catch (Exception e) {
		}

		File folder = new File(expath.toString());
		File[] files = folder.listFiles();
		String[] filenames = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			int pos =  files[i].getName().lastIndexOf(".");
			filenames[i] = files[i].getName().substring(0, pos);
		}	
			return filenames;
	}
	
	

	class Windowcloser extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent arg0) {
			setConfig();
		}
	}

}
