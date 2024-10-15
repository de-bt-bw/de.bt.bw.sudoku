/**
 * 
 */
package de.bt.bw.sudoku.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.bt.bw.sudoku.*;

/**
 * 
 */
class SpielfeldImplTest {

	/**
	 * Lieblings-Sudoku 83/1
	 * Es wird getestet, ob das Setzen korrekt funktioniert
	 * (Überprüfung auf Konfliktfreiheit der Werte). 
	 */
	@Test
	void testeSetzen() {
		boolean erfolg = true;
		Spielfeld spielfeld = new SpielfeldImpl();
		try {
			spielfeld.setze(0, 3, 8); 
			spielfeld.setze(0, 6, 7); 
			spielfeld.setze(0, 8, 5); 
			spielfeld.setze(1, 1, 6); 
			spielfeld.setze(1, 3, 7); 
			spielfeld.setze(1, 4, 1); 
			spielfeld.setze(1, 5, 2); 
			spielfeld.setze(1, 6, 9); 
			spielfeld.setze(2, 1, 4); 
			spielfeld.setze(2, 5, 3); 
			spielfeld.setze(2, 8, 1); 
			spielfeld.setze(3, 4, 3); 
			spielfeld.setze(3, 5, 9); 
			spielfeld.setze(3, 6, 6); 
			spielfeld.setze(4, 0, 3); 
			spielfeld.setze(4, 3, 6); 
			spielfeld.setze(4, 5, 4); 
			spielfeld.setze(4, 8, 7); 
			spielfeld.setze(5, 2, 4); 
			spielfeld.setze(5, 3, 1); 
			spielfeld.setze(5, 4, 7); 
			spielfeld.setze(6, 0, 2); 
			spielfeld.setze(6, 3, 3); 
			spielfeld.setze(6, 7, 9); 
			spielfeld.setze(7, 2, 3); 
			spielfeld.setze(7, 3, 4); 
			spielfeld.setze(7, 4, 6); 
			spielfeld.setze(7, 5, 1); 
			spielfeld.setze(7, 7, 8); 
			spielfeld.setze(8, 0, 4); 
			spielfeld.setze(8, 2, 6); 
			spielfeld.setze(8, 5, 5); 					
		} catch (Exception e) {
			erfolg = false;
		}
		assertTrue(erfolg);
	}

}
