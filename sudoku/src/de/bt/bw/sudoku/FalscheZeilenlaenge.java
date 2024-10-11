/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * 
 * Falsche Zeilenlänge in einem Spielfeld<br>
 * 
 */
public class FalscheZeilenlaenge extends Exception {
	
	/**
	 * Konstruktor für die Ausnahme.<br>
	 * 
	 * @param zeile Zeile, in der der Fehler aufgetreten ist
	 * @param laenge tatsächliche Länge der Zeile
	 */
	public FalscheZeilenlaenge(int zeile, int laenge) {
		super(laenge + "statt 9 Werte in der Zeile " + zeile);
	}
}
