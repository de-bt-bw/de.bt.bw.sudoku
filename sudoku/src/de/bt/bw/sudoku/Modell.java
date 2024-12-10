package de.bt.bw.sudoku;

/**
 * Das Modell im MVC-Muster verkapselt das Spielfeld und 
 * implementiert die Subjekt-Schnittstelle. 
 * Außerdem stellt es Methoden zum Laden und Speichern
 * von Spielfeldern zur Verfügung.
 * Alle Methoden, die das Spielfeld verändern, benachrichtigen
 * die Beobachter des Modells.
 */
public interface Modell extends Spielfeld, Subjekt {

	/**
	 * Lädt ein Spielfeld aus einer Datei.
	 * Falls das Laden fehlschlägt, bleibt das alte
	 * Spielfeld erhalten.
	 * 
	 * @param dateiName der Name der Datei
	 * @return true, falls das Laden erfolgreich war, false sonst
	 */
	public boolean laden(String dateiName);
	
	/**
	 * Speichert das Spielfeld in eine Datei.
	 * 
	 * @param dateiName der Name der Datei
	 * @return true, falls das Speichern erfolgreich war, false sonst
	 */
	public boolean speichern(String dateiName);
	
	/**
	 * Vervollständigt das aktuelle Spielfeld zu einer Lösung.
	 * 
	 * @return true, falls eine Lösung gefunden wurde, false sonst
	 */
	public boolean loesen();
}
