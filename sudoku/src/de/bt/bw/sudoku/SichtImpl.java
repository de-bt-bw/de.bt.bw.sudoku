/**
 * 
 */
package de.bt.bw.sudoku;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * Sicht für das Sudoku-Spiel
 */
public class SichtImpl implements Sicht {
    private Kontrolle kontrolle;
    private Modell modell;
    private Rahmen rahmen;
        
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

	}
	
	private class Rahmen extends JFrame {
		private Inhaltsflaeche inhaltsflaeche;
		private MenueZeile menueZeile;
		
		private Rahmen() {
			super("Sudoku");
			menueZeile = new MenueZeile();
			this.setJMenuBar(menueZeile);
			inhaltsflaeche = new Inhaltsflaeche();
			this.getContentPane().add(inhaltsflaeche);
			this.pack();
			this.setVisible(true);
		}
	}
	
	private class Inhaltsflaeche extends JPanel {
	    private Zelle[][] zellen;

		private Inhaltsflaeche() {
	        // Standard-Layout: Flow Layout
	        GridLayout layout = new GridLayout(9,9);
	        layout.setHgap(20);
	        layout.setVgap(20);
	    	this.setLayout(layout);
	    	
	        // Spielfeld mit Combo-Boxen darstellen
	        this.zellen = new Zelle[9][9];
	        for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++) 
	        	for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
	        		Zelle aktuelleZelle = new Zelle(zeilenNr, spaltenNr, 0);
	        		zellen[zeilenNr][spaltenNr] = aktuelleZelle;
	        		// aktuelleZelle.addActionListener(this);
	        		this.add(aktuelleZelle);        		
	        	}

		}
		
		
	    private class Zelle extends JComboBox<String> {
	    	private final int zeilenNr, spaltenNr;
	    	private int wert;
	    	private static final String[] zahlen = {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	    	
	    	private Zelle(int zeilenNr, int spaltenNr, int wert) {
	    		super(zahlen);
	    		this.zeilenNr = zeilenNr;
	    		this.spaltenNr = spaltenNr;
	    		this.wert = wert;
	    		this.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
	    	}
	    }
	}
	
    private class MenueZeile extends JMenuBar {
    	private MenueZeile() {
    		JMenu dateiMenu = new JMenu("Datei");
    		this.add(dateiMenu);
    		JMenuItem laden = new JMenuItem("Laden");
    		dateiMenu.add(laden);
    		JMenuItem speichern = new JMenuItem("Speichern");
    		dateiMenu.add(speichern);
    		JMenuItem beenden = new JMenuItem("Beenden");
    		dateiMenu.add(beenden);
    	}
    }

}
