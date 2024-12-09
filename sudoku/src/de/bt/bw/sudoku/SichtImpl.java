/**
 * 
 */
package de.bt.bw.sudoku;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Sicht für das Sudoku-Spiel
 */
public class SichtImpl implements Sicht {
    private Kontrolle kontrolle;
    private Modell modell;
    
    // GUI-Elemente
    private Rahmen rahmen;
    private Inhaltsflaeche inhaltsflaeche;
	private MenueZeile menueZeile;
	private static final Font standardFont = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
	private String aktuellerDateiName;
        
    public SichtImpl(Kontrolle kontrolle, Modell modell) {
    	
    	// MVC-Verbindungen initialisieren
        this.kontrolle = kontrolle;
        this.modell = modell;
        modell.registriere(this); 
        
        // Rahmen erzeugen
        this.rahmen = new Rahmen();
    }

	@Override
	public void aktualisiere() {
		inhaltsflaeche.aktualisiere();
	}
	
	private class Rahmen extends JFrame {
		public Rahmen() {
			super("Sudoku");
			menueZeile = new MenueZeile();
			this.setJMenuBar(menueZeile);
			inhaltsflaeche = new Inhaltsflaeche();
			this.getContentPane().add(inhaltsflaeche, BorderLayout.CENTER);
			this.pack();
			this.setVisible(true);
		}
	}
	
	private class Inhaltsflaeche extends JPanel implements ActionListener {
	    private Zelle[][] zellen;

		public Inhaltsflaeche() {
	        // Standard-Layout: Flow Layout
	        GridLayout layout = new GridLayout(9,9);
	        layout.setHgap(20);
	        layout.setVgap(20);
	    	this.setLayout(layout);
	    	this.setFont(standardFont);
	    	
	        // Spielfeld mit Combo-Boxen darstellen
	        this.zellen = new Zelle[9][9];
	        for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++) 
	        	for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
	        		Zelle aktuelleZelle = new Zelle(zeilenNr, spaltenNr, 0);
	        		zellen[zeilenNr][spaltenNr] = aktuelleZelle;
	        		aktuelleZelle.addActionListener(this);
	        		this.add(aktuelleZelle);        		
	        	}
	        
	        // Spielfeld an den Modellzustand anpassen
	        this.aktualisiere();

		}
		
		public void actionPerformed(ActionEvent e) {
			Zelle zelle = (Zelle)e.getSource();
			String selektion = (String)zelle.getSelectedItem();
			int wert = Zelle.wert(selektion);
			zelle.wert = wert;
			Kommando kommando = new KommandoSetzen(zelle.zeilenNr, zelle.spaltenNr, zelle.wert);
			boolean erfolg = kontrolle.behandleKommando(kommando);
			if (!erfolg) {
				String nachricht = "Der Wert " + zelle.wert + " in Zeile " + (zelle.zeilenNr + 1) + " und Spalte " + (zelle.spaltenNr + 1) + " ist nicht erlaubt";
				JOptionPane.showMessageDialog(rahmen, nachricht, "Falscher Wert", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		public void aktualisiere() {
	        for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++) 
	        	for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
	        		int wert = modell.wert(zeilenNr, spaltenNr);
	        		Zelle zelle = this.zellen[zeilenNr][spaltenNr];
	        		// Achtung: Hier soll nur die Sicht aktualisiert werden
	        		// Deshalb Action Listener deregestrieren und anschließend
	        		// wieder registrieren, sonst entsteht durch Selektieren
	        		// und Ereignisbehandlung eine Endlosschleife
	        		zelle.removeActionListener(this);
	        		zelle.setSelectedIndex(wert);
	        		zelle.addActionListener(this);
	        	}
	        this.repaint();
		}
	}
	
    private class Zelle extends JComboBox<String> {
    	// Achtung: JComboBox implementiert bereits das Interface ActionListener
    	// Die Methode actionPerformed() darf weder redefiniert noch aufgerufen werden
    	// Ereignisbehandlung muss also eine Ebene höher (in der Inhaltsflaeche) erfolgen
    	private final int zeilenNr, spaltenNr;
    	private int wert;
    	private static final String[] zahlen = {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    	
    	public Zelle(int zeilenNr, int spaltenNr, int wert) {
    		super(zahlen);
    		this.zeilenNr = zeilenNr;
    		this.spaltenNr = spaltenNr;
    		this.wert = wert;
    		this.setFont(standardFont);
    	}
    	
        private static int wert(String selektion) {
        	if (selektion.equals(" ")) 
        		return 0;
        	else
        		return Integer.parseInt(selektion);
        }
    }
	
    private class MenueZeile extends JMenuBar {
    	public MenueZeile() {
    		JMenu dateiMenu = new JMenu("Datei");
    		this.add(dateiMenu);
    		JMenuItem laden = new Laden();
    		dateiMenu.add(laden);
    		JMenuItem speichern = new Speichern();
    		dateiMenu.add(speichern);
    		JMenuItem beenden = new Beenden();
    		dateiMenu.add(beenden);
    	}
    }
    
    private class Laden extends JMenuItem implements ActionListener {
    	public Laden() {
    		super("Laden");
    		this.addActionListener(this);
    	}
    	
    	public void actionPerformed(ActionEvent e) {
    		String[] optionen = {"Speichern und Laden", "Nur Laden", "Abbrechen"};
    		String titel = "Laden";
    		String text = "Möchten Sie speichern und laden, nur speichern oder das Laden abbrechen?";
    		int n = JOptionPane.showOptionDialog(rahmen, text, titel,
    				JOptionPane.YES_NO_CANCEL_OPTION,
    				JOptionPane.QUESTION_MESSAGE,
    				null,
    				optionen,
    				optionen[2]);
    		if (n == 0) { // Speichern und laden
    			JOptionPane.showMessageDialog(rahmen, "Bitte wählen Sie zunächst Speichern im Menü", "Info", JOptionPane.INFORMATION_MESSAGE);
    		} else if (n == 1) { // Nur laden
    			String dateiName;
    			// Code für den Dialog zum Einlesen des Dateinamens
    			// dateiName = "raetsel_83_1.txt"; // Vorübergehend zu Testzwecken;
    			dateiName = JOptionPane.showInputDialog(rahmen, "Geben Sie den Namen der zu ladenden Datei ein");
    			if (dateiName != null) {
    				Kommando kommando = new KommandoLaden(dateiName);
    				boolean erfolg = kontrolle.behandleKommando(kommando);
    				if (erfolg) {
    					aktuellerDateiName = dateiName;
    					rahmen.setTitle(dateiName);
    				} else {
    					text = "Laden der Datei " + dateiName + " fehlgeschlagen";
    					titel = "Ladefehler";
    					JOptionPane.showMessageDialog(rahmen, text, titel, JOptionPane.ERROR_MESSAGE);
    				}
    			}
    		} // Keine Aktion beim Abbrechen
    	}
    }
    
    private class Speichern extends JMenuItem implements ActionListener {
    	public Speichern() {
    		super("Speichern");
    		this.addActionListener(this);
    	}
    	
    	public void actionPerformed(ActionEvent e) {
    		String[] optionen = {"Speichern", "Speichern unter", "Abbrechen"};
    		String titel = "Speichern";
    		String text = "Möchten Sie in der aktuellen Datei speichern,\n unter einer anderen Datei speichern oder abbrechen?";
    		int n = JOptionPane.showOptionDialog(rahmen, text, titel,
    				JOptionPane.YES_NO_CANCEL_OPTION,
    				JOptionPane.QUESTION_MESSAGE,
    				null,
    				optionen,
    				optionen[2]);
    		String dateiName = null;
    		boolean speichern = false;
    		if (n == 0) { // In der aktuellen Datei speichern
    			if (aktuellerDateiName == null) {
    				text = "Es wurde noch keine Datei geladen";
    				titel = "Speicherfehler";
    				JOptionPane.showMessageDialog(rahmen, text, titel, JOptionPane.ERROR_MESSAGE);
    			} else {
    				dateiName = aktuellerDateiName;
    				speichern = true;
    			}
    		} else if (n == 1) { // Unter einer (möglicherweise) anderen Datei speichern
    			dateiName = JOptionPane.showInputDialog(rahmen, "Geben Sie den Namen der zu speichernden Datei ein");
    			if (dateiName != null) {
    				speichern = true;
    			}
    		}
    		if (speichern) {
    			Kommando kommando = new KommandoSpeichern(dateiName);
				boolean erfolg = kontrolle.behandleKommando(kommando);
				if (erfolg) {
					aktuellerDateiName = dateiName;
					rahmen.setTitle(dateiName);
				} else {
					text = "Speichern der Datei " + dateiName + " fehlgeschlagen";
					titel = "Speicherfehler";
					JOptionPane.showMessageDialog(rahmen, text, titel, JOptionPane.ERROR_MESSAGE);
				}
    		}
    	}
    }
    
    private class Beenden extends JMenuItem implements ActionListener {
    	public Beenden() {
    		super("Beenden");
    		this.addActionListener(this);
    	}
    	
    	public void actionPerformed(ActionEvent e) {
    		String[] optionen = {"Speichern und Beenden", "Nur beenden", "Abbrechen"};
    		String titel = "Beenden";
    		String text = "Möchten Sie speichern und beenden, nur beenden oder das Beenden abbrechen?";
    		int n = JOptionPane.showOptionDialog(rahmen, text, titel,
    				JOptionPane.YES_NO_CANCEL_OPTION,
    				JOptionPane.QUESTION_MESSAGE,
    				null,
    				optionen,
    				optionen[2]);
    		if (n == 0) { // Speichern und beenden
    			JOptionPane.showMessageDialog(rahmen, "Bitte wählen Sie zunächst Speichern im Menü", "Info", JOptionPane.INFORMATION_MESSAGE);
    		} else if (n == 1) { // Nur beenden
        		Kommando kommando = new KommandoBeenden();
    			boolean erfolg = kontrolle.behandleKommando(kommando);
        		// Beenden ist immer erfolgreich
    		} // Keine Aktion beim Abbrechen
    		
    	}
    }

}
