/**
 * 
 */
package de.bt.bw.sudoku;

import java.io.FileNotFoundException;

/**
 * 
 * Schreibt ein Spielfeld in eine Textdatei 
 * mit 9 Zeilen und 9 Spalten
 * 
 */
public interface SpielfeldSchreiber {

	/**
	 * Schreibt ein Spielfeld in eine Textdatei
	 * 
	 * @param spielfeld das Spielfeld
	 * @param dateiname der Name der Datei
	 * @throws IOException beim Schreiben ausgelöste Ausnahme
	 */
	public void schreib(Spielfeld spielfeld, String dateiname) throws FileNotFoundException;
	
}
