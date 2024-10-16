/**
 * 
 */
package de.bt.bw.sudoku.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.bt.bw.sudoku.*;

/**
 * 
 * Test des Lesen eines Spielfelds aus einer Textdatei
 * 
 */
class SpielfeldLeserImplTest {
	
	/**
	 * Testet, ob ein korrektes, als Textdatei gegebenes Rätsel in ein
	 * Spielfeld konvertiert werden kann.
	 */
	@Test
	void testLiesSpielfeldRaetsel_83_1() {
		boolean erfolg = true;
		Spielfeld spielfeld;
		SpielfeldLeser spielfeldLeser = new SpielfeldLeserImpl();
		try {
			spielfeld = spielfeldLeser.lies("raetsel_83_1.txt");
		} catch (Exception e) {
			erfolg = false;
		}
		assertTrue(erfolg);
	}


}
