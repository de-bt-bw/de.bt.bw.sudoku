/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * Interface der Kontrolle im MVC-Muster.
 */
public interface Kontrolle {

	/**
	 * Behandelt ein Kommando
	 * 
	 * @param kommando das Kommando
	 * @return true, falls das Kommando ausgeführt werden konnte, false sonst
	 */
	public boolean behandleKommando(Kommando kommando);
}
