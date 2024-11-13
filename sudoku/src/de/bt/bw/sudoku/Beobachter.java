/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * Schnittstelle für Beobachter des Modells
 */
public interface Beobachter {
	
	/**
	 * Wird nach Änderungen des Modells aufgerufen.
	 * Der Beobachter holt sich die Informationen
	 * über die Zustandsänderungen selbst (Pull-Modell). 
	 * 
	 */
	void aktualisiere();
}
