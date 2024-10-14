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
	 * 9 ganzzahlige Werte im Intervall [0:9]. 
	 * 
	 * @param dateiName der Name der Datei
	 * @return das Spielfeld
	 * @throws FileNotFoundException Datei wurde nicht gefunden
	 * @throws NumberFormatException Wert ist keine ganze Zahl
	 * @throws FalscheZeilenanzahl zu wenige oder zu viele Zeilen
	 * @throws FalscheZeilenlaenge Zeile zu lang oder zu kurz
	 * @throws FalscheZahl Wert liegt nicht im Intervall [0..9]>
	 */
	public Spielfeld lies(String dateiName) throws FileNotFoundException, NumberFormatException, 
		FalscheZeilenanzahl, FalscheZeilenlaenge, FalscheZahl, FalscherZustand, FalscherWert;
}
