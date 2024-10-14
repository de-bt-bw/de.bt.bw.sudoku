/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * Falscher Wert für eine Zelle (im aktuellen Zustand des Spielfelds nicht zulässig) 
 */
public class FalscherWert extends Exception {

	public FalscherWert(int zeilenNr, int spaltenNr, int wert) {
		super("Wert " + wert + "in Zelle ["  + zeilenNr + ": " + spaltenNr + "]" + "nicht zulässig");
	}
}
