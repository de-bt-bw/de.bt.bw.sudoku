/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * Speicher-Kommando
 */
public class KommandoSpeichern extends Kommando {
	private final String dateiName;
	
	/**
	 * Konstruktor für das Kommando zum Laden
	 * 
	 * @param dateiName Name der zu speichernden Datei
	 */
	public KommandoSpeichern(String dateiName) {
		this.dateiName = dateiName;
	}
	
	/**
	 * Liefert den Namen der zu speichernden Datei zurück
	 * 
	 * @return der Dateiname
	 */
	public String gibDateiName() {
		return this.dateiName;
	}
}
