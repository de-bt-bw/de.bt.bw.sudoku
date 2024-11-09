/**
 * 
 */
package de.bt.bw.sudoku;

import java.io.FileNotFoundException;

/**
 * 
 * Hilfsoperationen für Löser
 * 
 */
public interface LoeserHelfer {
	
	/**
	 * Konvertiert die Matrix in ein Spielfeld und ruft den Löser auf
	 * 
	 * @param loeser der zu verwendende Löser
	 * @param raetselArray 9x9-Matrix von Zahlen im Intervall [0:9]
	 * @return die Lösung (oder null, falls keine Lösung berechnet werden konnte)
	 */
	public Spielfeld loese(Loeser loeser, int[][] raetselArray);
	
	/**
	 * Liest das Rätsel aus einer Datei und ruft den Löser auf dem Spielfeld auf
	 * 
	 * @param loeser der zu verwendende Löser
	 * @param dateiName Name der Rätseldatei
	 * @return die Lösung (oder null, falls keine Lösung berechnet werden konnte)
	 * @throws FileNotFoundException falls die Datei nicht gefunden wurde
	 */
	public Spielfeld loese(Loeser loeser, String dateiName) throws FileNotFoundException;

}
