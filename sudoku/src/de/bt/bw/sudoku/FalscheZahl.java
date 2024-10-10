/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * 
 * Ausnahme für Zahlen, die außerhalb eines spezifizierten Bereichs liegen<br>
 * 
 */
public class FalscheZahl extends Exception {

	/**
	 * Konstruktor für die Ausnahme
	 * 
	 * @param zahl falsche Zahl
	 * @param min  untere Grenze des gewünschten Intervalls
	 * @param max  obere Grenze des gewünschten Intervalls
	 */
	public FalscheZahl(int zahl, int min, int max) {
		super("Zahl " + zahl + "liegt außerhalb von [" + min + ":" + max + "]");
	}

}
