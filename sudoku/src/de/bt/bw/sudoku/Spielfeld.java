/**
 * 
 */
package de.bt.bw.sudoku;

import java.util.Set;

/**
 * Das Spielfeld ist eine 9x9-Matrix der Zahlen 0..9.<br>
 * Die Zeilen und Spalten werden durch Indizes im Bereich 1..9 adressiert.<br>
 * Eine Zelle ist unbelegt, wenn sie den Wert 0 hat.<br>
 */
public interface Spielfeld {
	/**
	 * Setzt die Zelle in der angegebenen Zeile und Spalte auf
	 * den angebenen Wert, falls die Zelle noch
	 * nicht gesetzt wurde und der Wert in der 
	 * Menge der möglichen Werte enthalten ist. Im Erfolgsfall
	 * ist der Wert danach gesetzt und die Menge der möglichen
	 * Werte leer. Im Misserfolgsfall bleibt das Spielfeld unverändert.
	 * Falls die Parameter nicht im Bereich 1..9 liegen, wird eine
	 * Ausnahme ausgelöst.<br>
	 * 
	 * @param zeile im Bereich 1..9<br>
	 * @param spalte im Bereich 1..9<br>
	 * @param wert im Bereich 1..9<br>
	 * @return true, falls die Operation erfolgreich war, false sonst<br>
	 * @throws FalscheZahl falls die Parameter nicht in ihren Bereichen liegen<br>
	 */
	boolean setze(int zeile, int spalte, int wert) throws FalscheZahl;
	
	/**
	 * Liefert den Wert der Zelle im Bereich 0..9 zurück.
	 * Falls die Parameter nicht im Bereich 1..9 liegen, wird eine
	 * Ausnahme ausgelöst.<br>
	 *  
	 * @param zeile im Bereich 1..9<br>
	 * @param spalte im Bereich 1..9<br>
	 * @return Wert im Bereich 0..9<br>
	 * @throws FalscheZahl falls die Parameter nicht in ihren Bereichen liegen<br>
	 */
	int wert(int zeile, int spalte) throws FalscheZahl;
	
	/**
	 * Liefert die Menge aller möglichen Werte zurück, die in die Zelle eingetragen werden können,
	 * ohne die Sudoku-Bedingungen zu verletzen. Wenn die Zelle bereits gesetzt wurde, wird
	 * null zurückgeliefert.
	 * Falls die Parameter nicht im Bereich 1..9 liegen, wird eine
	 * Ausnahme ausgelöst.<br>
	 * 
	 * @param zeile im Bereich 1..9<br>
	 * @param spalte im Bereich 1..9<br>
	 * @return Menge der möglichen Werte<br>
	 * @throws FalscheZahl falls die Parameter nicht in ihren Bereichen liegen<br>
	 */
	Set<Integer> moeglicheWerte(int zeile, int spalte) throws FalscheZahl;
	
	/**
	 * Entscheidet, ob die Zelle gesetzt ist (Wert > 0).
	 * Falls die Parameter nicht im Bereich 1..9 liegen, wird eine
	 * Ausnahme ausgelöst.<br>
	 * @param zeile im Bereich 1..9<br>
	 * @param spalte im Bereich 1..9<br>
	 * @return true, falls ein Wert > 0 gesetzt ist, false sonst<br>
	 * @throws FalscheZahl falls die Parameter nicht in ihren Bereichen liegen<br>
	 */
	boolean gesetzt(int zeile, int spalte) throws FalscheZahl;
}
