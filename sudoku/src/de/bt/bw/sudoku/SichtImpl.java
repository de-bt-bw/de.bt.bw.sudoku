/**
 * 
 */
package de.bt.bw.sudoku;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Sicht für das Sudoku-Spiel
 */
public class SichtImpl extends JPanel implements Sicht, ActionListener {
    private Kontrolle kontrolle;
    private Modell modell;
    
    private static final String[] zahlen = {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    
    private static int wert(String selektion) {
    	if (selektion.equals(" ")) 
    		return 0;
    	else
    		return Integer.parseInt(selektion);
    }
    
    private class Zelle extends JComboBox {
    	final int zeilenNr, spaltenNr;
    	int wert;
    	Zelle(String[] zahlen, int zeilenNr, int spaltenNr, int wert) {
    		super(zahlen);
    		this.zeilenNr = zeilenNr;
    		this.spaltenNr = spaltenNr;
    		this.wert = wert;
    		this.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
    	}
    }
    
    private class NachrichtenFenster extends JPanel {
    	final String nachricht;
    	
    	NachrichtenFenster(String nachricht) {
    		this.nachricht = nachricht;
    		JFrame frame = new JFrame();
    		frame.getContentPane().add(this);
    		frame.setSize(600, 150);
    		frame.setVisible(true);
    	}
    	
    	public void paintComponent(Graphics g) {
    		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
    		g.drawString(nachricht, 50, 50);
    	}
    }
    
    Zelle[][] zellen;
    
    public SichtImpl(Kontrolle kontrolle, Modell modell) {
    	
    	// MVC-Verbindungen initialisieren
        this.kontrolle = kontrolle;
        this.modell = modell;
        modell.registriere(this);
        
        // Standard-Layout: Flow Layout
        GridLayout layout = new GridLayout(9,9);
        layout.setHgap(20);
        layout.setVgap(20);
    	this.setLayout(layout);
    	
        // Spielfeld mit Combo-Boxen darstellen
        zellen = new Zelle[9][9];
        for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++) 
        	for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
        		Zelle aktuelleZelle = new Zelle(zahlen, zeilenNr, spaltenNr, 0);
        		zellen[zeilenNr][spaltenNr] = aktuelleZelle;
        		aktuelleZelle.addActionListener(this);
        		this.add(aktuelleZelle);        		
        	}
        
        // Spielfeld an den Modellzustand anpassen
        this.aktualisiere();
        
        // Rahmen erzeugen und anzeigen
        JFrame frame = new JFrame("Sudoku");
        frame.getContentPane().add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.setVisible(true);        
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		Kommando kommando = null;
		if (e.getSource() instanceof Zelle) {
			// Combo-Box
			Zelle zelle = (Zelle)e.getSource();
			String selektion = (String)zelle.getSelectedItem();
			int wert = this.wert(selektion);
			zelle.wert = wert;
			// Ausgabe zu Testzwecken
			// System.out.println("Setzen von Zeile: " + zelle.zeilenNr + " Spalte: " + zelle.spaltenNr + " Wert: " + wert);
			// Erzeugung des Kommandos
			kommando = new KommandoSetzenImpl(zelle.zeilenNr, zelle.spaltenNr, zelle.wert);
			boolean erfolg = kontrolle.behandleKommando(kommando);
			if (!erfolg) {
				String nachricht = "Der Wert " + zelle.wert + " kann in Zeile " + zelle.zeilenNr + " und Spalte " + zelle.spaltenNr + " nicht gesetzt werden";
				NachrichtenFenster nachrichtenFenster = new NachrichtenFenster(nachricht);
			}
		} // Andere Kommandos fehlen noch
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Alle Zellen neu zeichnen
		// Horizontale und vertikale Linien zur Unterteilung
		// in Blöcke zeichnen
		g.drawLine(1, 250, 800, 250);
		g.drawLine(1, 508, 800, 508);
		g.drawLine(258, 1, 258, 800);
		g.drawLine(525, 1, 525, 800);
	}

	@Override
	public void aktualisiere() {
        for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++) 
        	for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
        		int wert = modell.wert(zeilenNr, spaltenNr);
        		Zelle zelle = this.zellen[zeilenNr][spaltenNr];
        		// Achtung: Hier soll nur die Sicht aktualisiert werden
        		// Deshalb Action Listener deregestrieren und anschließend
        		// wieder registrieren
        		zelle.removeActionListener(this);
        		zelle.setSelectedIndex(wert);
        		zelle.addActionListener(this);
        	}
        this.repaint();
	}

}
