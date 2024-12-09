/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * Lade-Kommando
 */
public class KommandoLaden extends Kommando {
	private final String dateiName;
	
	/**
	 * Konstruktor für das Kommando zum Laden
	 * 
	 * @param dateiName Name der zu ladenden Datei
	 */
	public KommandoLaden(String dateiName) {
		this.dateiName = dateiName;
	}
	
	/**
	 * Liefert den Namen der zu ladenden Datei zurück
	 * 
	 * @return der Dateiname
	 */
	public String gibDateiName() {
		return this.dateiName;
	}
}
