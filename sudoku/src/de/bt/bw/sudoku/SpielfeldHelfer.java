/**
 * 
 */
package de.bt.bw.sudoku;

import java.util.Set;

/**
 * Sammlung von Hilfsoperationen auf Spielfeldern
 */
public interface SpielfeldHelfer {
	
	/**
	 * Vergleicht zwei Spielfelder zellenweise
	 * 
	 * @param spielfeld1 das erste Spielfeld
	 * @param spielfeld2 das zweite Spielfeld
	 * @return true, falls beide Parameter null oder zellenweise gleich sind
	 */
	public boolean gleich(Spielfeld spielfeld1, Spielfeld spielfeld2);
	

	/**
	 * Erzeugt aus einer 9x9-Matrix ein Spielfeld.
	 * 
	 * @param spielfeldArray das zweidimensionale Array
	 * @return das Spielfeld, falls das Array eine 9x9-Matrix ist,<br>
	 * die die Spielregeln erfüllt, und null sonst
	 */
	public Spielfeld erzeugeSpielfeld(int[][] spielfeldArray);
	
	/**
	 * Erzeugt eine Kopie eines Spielfelds bzw. liefert null
	 * zurück, wenn das Argument den Wert null hat
	 * 
	 * @param spielfeld das zu kopierende Spielfeld
	 * @return die Kopie bzw. null
	 */
	public Spielfeld kopiere(Spielfeld spielfeld);
	
	/**
	 * Prüft, ob das Spielfeld eine vollständige Lösung ist,
	 * d.h. ob alle Zellen gesetzt sind
	 * 
	 * @param spielfeld das Spielfeld
	 * @return true, falls alle Zellen gesetzt sind, false sonst
	 */
	public boolean loesungVollstaendig(Spielfeld spielfeld);
	
	/**
	 * Für eine Zelle wird zunächst die Zahl der möglichen Werte bestimmt (s. Interface
	 * Spielfeld). Anschließend wird in dieser Menge ein eindeutiger Wert gesucht.
	 * Ein Wert ist eindeutig, wenn er in keiner anderen Zelle derselben Zeile,
	 * derselben Spalte oder desselben Blocks gesetzt werden kann. Wenn ein eindeutiger
	 * Wert gefunden, wird die entsprechende einelementige Menge zurückgeliefert,
	 * sonst die Menge möglicher Werte.
	 * 
	 * @param spielfeld das Spielfeld
	 * @param zeilenNr die Zeile der Zelle
	 * @param spaltenNr die Spalte der Zelle
	 * @return die eingeschränkte Wertemenge
	 */
	Set<Integer> eingeschraenkteMoeglicheWerte(Spielfeld spielfeld, int zeilenNr, int spaltenNr);
	
}
