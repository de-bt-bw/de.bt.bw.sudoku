/**
 * 
 */
package de.bt.bw.sudoku;

import java.util.Set;

/**
 * Das Spielfeld ist eine 9x9-Matrix von Zellen mit Werten im Intervall [0..9].
 * Die Zeilen und Spalten werden durch Nummern im Intervall [0:8] indiziert.
 * Es wird nicht überprüft, ob Argumente in den vorgeschriebenen Intervallen
 * liegen; ggf. kommt es dann zu einem Laufzeitfehler.
 * Eine Zelle ist unbelegt, wenn sie den Wert 0 hat.
 * 
 */
public interface Spielfeld {
	/**
	 * Setzt die Zelle in der angegebenen Zeile und Spalte auf
	 * den angegebenen Wert. Falls der Wert > 0 ist,
	 * muss er in der Menge der möglichen Werte liegen.
	 * Im Erfolgsfall ist der Wert danach gesetzt. 
	 * Im Misserfolgsfall bleibt das Spielfeld unverändert,
	 * und es wird eine Ausnahme ausgelöst.
	 * 
	 * @param zeilenNr Zeilennummer
	 * @param spaltenNr Spaltennummer
	 * @param wert Wert
	 * @throws FalscherWert falls der Wert > 0, aber nicht möglich ist
	 */
	void setze(int zeilenNr, int spaltenNr, int wert) throws FalscherWert;
	
	/**
	 * Liefert den Wert der Zelle zurück.
	 *  
	 * @param zeilenNr Zeilennummer
	 * @param spaltenNr Spaltennummer
	 * @return Wert
	 */
	int wert(int zeilenNr, int spaltenNr);
	
	/**
	 * Falls die Zelle unbelegt ist, wird die Menge der Werte > 0 zurückgeliefert,
	 * mit denen die Zelle gesetzt werden kann, ohne die Sudoku-Bedingungen
	 * zu verletzen. Ein Wert ist zulässig, wenn er nicht in der gleichen
	 * Zeile, der gleichen Spalte oder im gleichen Block vorkommt.
	 * Falls die Zelle belegt ist, wird die leere Menge zurückgeliefert. 
	 * 
	 * @param zeilenNr Zeilennummer
	 * @param spaltenNr Spaltennummer
	 * @return Menge der möglichen Werte (Kopie)
	 */
	Set<Integer> moeglicheWerte(int zeilenNr, int spaltenNr);
	
	/**
	 * Entscheidet, ob die Zelle belegt ist (Wert > 0).
	 * 
	 * @param zeilenNr Zeilennummer
	 * @param spaltenNr Spaltennummer
	 * @return true, falls ein Wert > 0 gesetzt ist, false sonst
	 */
	boolean belegt(int zeilenNr, int spaltenNr);
}
