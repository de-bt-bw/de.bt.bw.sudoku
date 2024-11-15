/**
 * 
 */
package de.bt.bw.sudoku;

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
public class Sicht extends JPanel implements Beobachter, ActionListener {
    private Kontrolle kontrolle;
    private Modell modell;
    
    private static final String[] zahlen = {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    // private static final Integer[] zahlen = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    
    // JComboBox<Integer> beispiel;
    
    JButton laden, speichern, fertig;
    
    JComboBox[][] zellen;
    
    public Sicht(Kontrolle kontrolle, Modell modell) {
    	
    	// MVC-Verbindungen initialisieren
        this.kontrolle = kontrolle;
        this.modell = modell;
        modell.registriere(this);
        
        // Standard-Layout: Flow Layout
    	
    	// Buttons für Kommandos
    	laden = new JButton("Laden");
    	laden.addActionListener(this);
    	this.add(laden);
    	speichern = new JButton("Speichern");
    	speichern.addActionListener(this);
    	this.add(speichern);
    	fertig = new JButton("Fertig");
    	fertig.addActionListener(this);
    	this.add(fertig);
        
        // Spielfeld mit Combo-Boxen darstellen
    	// this.setLayout(new GridLayout(9,9));
        zellen = new JComboBox[9][9];
        for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++) 
        	for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
        		JComboBox aktuelleZelle = new JComboBox(zahlen);
        		zellen[zeilenNr][spaltenNr] = aktuelleZelle;
        		aktuelleZelle.addActionListener(this);
        		this.add(aktuelleZelle);        		
        	}
        
        // Rahmen erzeugen und anzeigen
        JFrame frame = new JFrame("Sudoku");
        frame.getContentPane().add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.setVisible(true);        
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// Bisher nur Testausgaben auf der Konsole
		if (e.getSource() instanceof JComboBox) {
			// Combo-Box
			JComboBox zelle = (JComboBox)e.getSource();
			String selektion = (String)zelle.getSelectedItem();
			System.out.println("Selektion: " + selektion);
		} else {
			// Buttons
			JButton button = (JButton)e.getSource();
			String kommando = e.getActionCommand();
			System.out.println("Kommando: " + kommando);
		}
	}

	@Override
	public void aktualisiere() {
		// TODO Auto-generated method stub

	}

}
