/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * Setze-Kommando
 */
public interface KommandoSetzen extends Kommando {

	/**
	 * Liefert die Zeilennummer der gesetzten Zelle zurück.
	 * 
	 * @return die Zeilennummer
	 */
	public int gibZeilenNr();
	
	/**
	 * Liefert die Spaltennummer der gesetzten Zelle zurück.
	 * 
	 * @return die Spaltennummer
	 */
	public int gibSpaltenNr();
	
	/**
	 * Liefert den gesetzten Wert zurück.
	 * 
	 * @return der gesetzte Wert
	 */
	public int gibWert();
}
