/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * 
 * Für eine Zelle ist kein Wert mehr möglich.
 * 
 */
public class KeinMoeglicherWert extends Exception {
	public Spielfeld spielfeld;
	public int zeilenNr, spaltenNr;
	
	/**
	 * Diese Ausnahme wird ausgelöst, wenn für eine ungesetzte Zelle kein möglicher Wert
	 * gefunden wird.
	 * 
	 * @param spielfeld die bisher gefundene Teillösung
	 * @param zeilenNr die Zeilennummer der Zelle
	 * @param spaltenNr die Spaltennummer der Zelle
	 */
	public KeinMoeglicherWert(Spielfeld spielfeld, int zeilenNr, int spaltenNr) {
		super("Für die ungesetzte Zelle [" + zeilenNr + ", " + spaltenNr + "] ist kein Wert mehr möglich");
		this.spielfeld = spielfeld;
		this.zeilenNr = zeilenNr;
		this.spaltenNr = spaltenNr;
	}
}
