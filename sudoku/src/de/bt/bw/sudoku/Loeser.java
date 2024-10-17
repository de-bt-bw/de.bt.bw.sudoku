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
	 * Eine perfekte Implementierung löst keine Ausnahme aus und berechnet immer 
	 * eine vollständige Lösung. Es sind aber auch Implementierungen zulässig,
	 * bei denen eine Lösung nicht immer berechnet werden kann. Falls die 
	 * Berechnung fehlschlägt, wird eine der Ausnahmen ausgelöst.
	 * 
	 * @param raetsel das Rätsel
	 * @return die Lösung
	 * @throws LoesungUnvollstaendig die Lösung konnte nicht vollständig berechnet werden
	 * @throws KeinMoeglicherWert für eine ungesetzte Zelle gibt es keinen möglichen Wert
	 */
	public Spielfeld loese(Spielfeld raetsel) throws LoesungUnvollstaendig, KeinMoeglicherWert;

}
