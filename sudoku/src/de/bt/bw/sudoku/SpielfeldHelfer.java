/**
 * 
 */
package de.bt.bw.sudoku;

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
	 * @return das Spielfeld 
	 * @throws FalscheZahl Wert für eine Zelle liegt nicht im Intervall [0:9]
	 * @throws FalscherWert Wert ist wegen der Sudoku-Regeln im Spielfeld nicht mehr zulässig
	 * @throws FalscheZeilenanzahl zu viele oder zu wenige Zeilen
	 * @throws FalscheZeilenlaenge zu kurze oder zu lange Zeile
	 */
	public Spielfeld erzeugeSpielfeld(int[][] spielfeldArray) throws FalscheZahl, FalscherWert, FalscheZeilenanzahl, FalscheZeilenlaenge;
	
	/**
	 * Erzeugt eine Kopie eines Spielfelds
	 * 
	 * @param spielfeld das zu kopierende Spielfeld
	 * @return die Kopie
	 */
	public Spielfeld kopiere(Spielfeld spielfeld);
	
	/**
	 * Prüft, ob das Spielfeld eine vollständige Lösung hat,
	 * d.h. ob alle Zellen gesetzt sind
	 * 
	 * @param spielfeld das Spielfeld
	 * @return true, falls alle Zellen gesetzt sind, false sonst
	 */
	public boolean loesungVollstaendig(Spielfeld spielfeld);
	
}
