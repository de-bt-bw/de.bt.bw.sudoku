/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * Löser für ein Rätsel
 */
public interface Loeser {

	/**
	 * Legt eine Kopie des Rätsels an und vervollständigt sie zu einer Lösung.
	 * Falls keine Lösung berechnet werden kann, wird null zurückgeliefert
	 * 
	 * @param raetsel das Rätsel
	 * @return die Lösung oder null, falls keine Lösung gefunden wurde
	 */
	public Spielfeld loese(Spielfeld raetsel);

}
