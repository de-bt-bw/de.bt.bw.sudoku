/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * Setze-Kommando
 */
public class KommandoSetzen extends Kommando {
	private final int zeilenNr, spaltenNr, wert;
	
	/**
	 * Konstruktor für das Kommando zum Setzen
	 * 
	 * @param zeilenNr Zeile der Zelle
	 * @param spaltenNr Spalte der Zelle
	 * @param wert zu setzender Wert
	 */
	public KommandoSetzen(int zeilenNr, int spaltenNr, int wert) {
		this.zeilenNr = zeilenNr;
		this.spaltenNr = spaltenNr;
		this.wert = wert;
	}

	/**
	 * Liefert die Zeilennummer der gesetzten Zelle zurück.
	 * 
	 * @return die Zeilennummer
	 */
	public int gibZeilenNr() {
		return this.zeilenNr;
	}

	/**
	 * Liefert die Spaltennummer der gesetzten Zelle zurück.
	 * 
	 * @return die Spaltennummer
	 */
	public int gibSpaltenNr() {
		return this.spaltenNr;
	}

	/**
	 * Liefert den gesetzten Wert zurück.
	 * 
	 * @return der gesetzte Wert
	 */
	public int gibWert() {
		return this.wert;
	}
}
