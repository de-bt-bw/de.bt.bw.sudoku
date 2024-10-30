/**
 * 
 */
package de.bt.bw.sudoku;

import java.io.FileNotFoundException;

/**
 * Liest ein Spielfeld aus einer Datei, die
 * 9 Zeilen mit jeweils 9 Spalten enthält.
 * 
 */
public interface SpielfeldLeser {
	
	/**
	 * Liest ein Spielfeld aus einer Datei,
	 * die 9 Zeilen enthält. Jede Zeile enthält 
	 * 9 ganzzahlige Werte im Intervall [0:9],
	 * die durch Leerzeichen voneinander getrennt sind.
	 * Die Werte müssen die Sudoku-Regeln erfüllen.
	 *  
	 * @param dateiName
	 * @return das Spielfeld im Erfolgsfall und null im Misserfolgsfall
	 * @throws FileNotFoundException die zu lesende Datei wurde nicht gefunden.
	 */
	public Spielfeld lies(String dateiName) throws FileNotFoundException;
}
