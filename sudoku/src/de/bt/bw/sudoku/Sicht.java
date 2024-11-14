/**
 * 
 */
package de.bt.bw.sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

/**
 * Sicht für das Sudoku-Spiel
 */
public class Sicht extends JPanel implements Beobachter, ActionListener {
    private Kontrolle kontrolle;
    private Modell modell;
    
    public Sicht(Kontrolle kontrolle, Modell modell) {
        this.kontrolle = kontrolle;
        this.modell = modell;
        
        // Initialisierung der Sicht
        
        // Registrierung als Beobachter beim Modell
        modell.registriere(this);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void aktualisiere() {
		// TODO Auto-generated method stub

	}

}
