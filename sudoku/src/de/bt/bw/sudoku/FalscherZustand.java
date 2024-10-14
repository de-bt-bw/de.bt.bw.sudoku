/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * 
 *  Falscher Zustand einer Zelle 
 *  
 */
public class FalscherZustand extends Exception {
	
	/**
	 * Konstruktor für die Ausnahme
	 * 
	 * @param zeilenNr die Zeilennummer der Zelle
	 * @param spaltenNr die Spaltennummer der Zelle
	 */
	public FalscherZustand(int zeilenNr, int spaltenNr) {
		super("Falscher Zustand der Zelle [" + zeilenNr + ": " + spaltenNr + "]");
	}

}
