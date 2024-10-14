/**
 * 
 */
package de.bt.bw.sudoku;

import java.util.Set;

/**
 * Das Spielfeld ist eine 9x9-Matrix der Zahlen im Intervall [0..9].
 * Die Zeilen und Spalten werden durch Nummern im Intervall [0:8] adressiert.
 * Eine Zelle ist unbelegt, wenn sie den Wert 0 hat.
 * 
 */
public interface Spielfeld {
	/**
	 * Setzt die Zelle in der angegebenen Zeile und Spalte auf
	 * den angebenen Wert im Intervall [1:9], falls die Zelle noch
	 * nicht gesetzt wurde und der Wert in der 
	 * Menge der möglichen Werte enthalten ist. Im Erfolgsfall
	 * ist der Wert danach gesetzt und die Menge der möglichen
	 * Werte leer. Im Misserfolgsfall bleibt das Spielfeld unverändert.
	 * 
	 * @param zeilenNr Zeilennummer
	 * @param spaltenNr Spaltennummer
	 * @param wert Wert
	 * @throws FalscheZahl falls die Parameter nicht in ihren Intervallen liegen
	 * @throws FalscherZustand falls die Zelle bereits gesetzt wurde
	 * @throws FalscherWert falls der Wert im Konflikt zu Werten anderer Zellen steht
	 */
	void setze(int zeilenNr, int spaltenNr, int wert) throws FalscheZahl, FalscherZustand, FalscherWert;
	
	/**
	 * Liefert den Wert der Zelle zurück.
	 * Falls die Parameter nicht in ihren Intervallen liegen, wird eine
	 * Ausnahme ausgelöst.
	 *  
	 * @param zeilenNr Zeilennummer
	 * @param spaltenNr Spaltennummer
	 * @return Wert 
	 * @throws FalscheZahl falls die Parameter nicht in ihren Intervallen liegen
	 */
	int wert(int zeilenNr, int spaltenNr) throws FalscheZahl;
	
	/**
	 * Liefert die Menge aller möglichen Werte zurück, die in die Zelle eingetragen werden können,
	 * ohne die Sudoku-Bedingungen zu verletzen. 
	 * Falls die Parameter nicht in ihren Intervallen liegen, wird eine
	 * Ausnahme ausgelöst.<br>
	 * 
	 * @param zeilenNr Zeilennummer
	 * @param spaltenNr Spaltennummer
	 * @return Menge der möglichen Werte
	 * @throws FalscheZahl falls die Parameter nicht in ihren Intervallen liegen<br>
	 */
	Set<Integer> moeglicheWerte(int zeilenNr, int spaltenNr) throws FalscheZahl;
	
	/**
	 * Entscheidet, ob die Zelle gesetzt ist (Wert > 0).
	 * Falls die Parameter nicht in ihren Intervallen liegen, wird eine
	 * Ausnahme ausgelöst.
	 * 
	 * @param zeilenNr Zeilennummer
	 * @param spaltenNr Spaltennummer
	 * @return true, falls ein Wert > 0 gesetzt ist, false sonst
	 * @throws FalscheZahl falls die Parameter nicht in ihren Intervallen liegen
	 */
	boolean gesetzt(int zeilenNr, int spaltenNr) throws FalscheZahl;
}
