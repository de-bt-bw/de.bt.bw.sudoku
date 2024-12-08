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
	private JLabel statusZeile;
	private static final Font standardFont = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
        
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
			statusZeile = new JLabel("Viel Spaß beim Spiel!");
			statusZeile.setFont(standardFont);
			this.getContentPane().add(statusZeile, BorderLayout.SOUTH);
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
			Kommando kommando = new KommandoSetzenImpl(zelle.zeilenNr, zelle.spaltenNr, zelle.wert);
			boolean erfolg = kontrolle.behandleKommando(kommando);
			if (erfolg) {
				statusZeile.setText("Setzen erfolgreich!");
			} else {
				statusZeile.setText("Setzen fehlgeschlagen!");
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
    		JMenuItem laden = new JMenuItem("Laden");
    		dateiMenu.add(laden);
    		JMenuItem speichern = new JMenuItem("Speichern");
    		dateiMenu.add(speichern);
    		JMenuItem beenden = new Beenden();
    		// beenden.addActionListener((ActionEvent e) -> {System.exit(0);});
    		dateiMenu.add(beenden);
    	}
    }
    
    private class Beenden extends JMenuItem implements ActionListener {
    	public Beenden() {
    		super("Beenden");
    		this.addActionListener(this);
    	}
    	
    	public void actionPerformed(ActionEvent e) {
    		Kommando kommando = new KommandoBeendenImpl();
    		boolean erfolg = kontrolle.behandleKommando(kommando);
    		// Beenden ist immer erfolgreich
    	}
    }

}
