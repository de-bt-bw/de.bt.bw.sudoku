/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * Implementierung des Setze-Kommandos
 */
public class KommandoSetzenImpl implements KommandoSetzen {
	private final int zeilenNr, spaltenNr, wert;
	
	public KommandoSetzenImpl(int zeilenNr, int spaltenNr, int wert) {
		this.zeilenNr = zeilenNr;
		this.spaltenNr = spaltenNr;
		this.wert = wert;
	}

	@Override
	public int gibZeilenNr() {
		return this.zeilenNr;
	}

	@Override
	public int gibSpaltenNr() {
		return this.spaltenNr;
	}

	@Override
	public int gibWert() {
		return this.wert;
	}

}
