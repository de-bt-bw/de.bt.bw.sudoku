/**
 * 
 */
package de.bt.bw.sudoku;

import java.io.FileNotFoundException;

/**
 * Liest ein Spielfeld aus einer Datei, die
 * 9 Zeilen mit jeweils 9 Spalten enthält.<br>
 * 
 */
public interface SpielfeldLeser {
	/**
	 * Liest ein Spielfeld aus einer Datei,
	 * die 9 Zeilen enthält. Jede Zeile enthält 
	 * 9 ganzzahlige Werte im Intervall [0:9].<br> 
	 * 
	 * @param name der Name der Datei<br>
	 * @return das Spielfeld<br>
	 * @throws FileNotFoundException Datei wurde nicht gefunden<br>
	 * @throws NumberFormatException Wert ist keine ganze Zahl<br>
	 * @throws FalscheZeilenanzahl zu wenige oder zu viele Zeilen<br>
	 * @throws FalscheZeilenlaenge Zeile zu lang oder zu kurz<br>
	 * @throws FalscheZahl Wert liegt nicht im Intervall [0..9]<br>
	 */
	public Spielfeld lies(String name) throws FileNotFoundException, NumberFormatException, FalscheZeilenanzahl, FalscheZeilenlaenge, FalscheZahl;
}
