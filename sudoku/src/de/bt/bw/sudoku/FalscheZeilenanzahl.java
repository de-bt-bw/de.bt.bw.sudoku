/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * 
 * Falsche Anzahl von Zeilen in einem Spielfeld<br>
 * 
 */
public class FalscheZeilenanzahl extends Exception {
	
	/**
	 * Konstruktor für die Ausnahme.<br>
	 * 
	 * @param zeilen tatsächliche Zahl der Zeilen
	 */
	public FalscheZeilenanzahl(int zeilen) {
		super(zeilen + " statt 9 Zeilen");
	}

}
