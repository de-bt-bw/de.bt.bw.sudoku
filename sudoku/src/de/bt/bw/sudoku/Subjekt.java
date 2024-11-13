/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * Schnittstelle für das beobachtete Modell
 */
public interface Subjekt {
	
	/**
	 * Registriert einen Beobachter.
	 * @param beobachter der Beobachter
	 */
	void registriere(Beobachter beobachter);
	
	/**
	 * Entfernt einen Beobachter.
	 * 
	 * @param beobachter der Beobachter
	 */
	void deregistriere(Beobachter beobachter);
	
	/**
	 * Wird nach einer Zustandsänderung des Modells aufgerufen.
	 * Alle Beobachter werden benachrichtigt.
	 */
	void benachrichtige();
}
