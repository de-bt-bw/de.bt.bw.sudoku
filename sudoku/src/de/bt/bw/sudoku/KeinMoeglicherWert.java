/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * 
 * Für eine Zelle ist kein Wert mehr möglich.
 * 
 */
public class KeinMoeglicherWert extends Exception {
	public KeinMoeglicherWert(int zeilenNr, int spaltenNr) {
		super("Für die ungesetzte Zelle [" + zeilenNr + ", " + spaltenNr + "] ist kein Wert mehr möglich");
	}
}
