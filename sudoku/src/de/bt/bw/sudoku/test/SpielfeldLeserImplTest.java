
/**
 * 
 */
package de.bt.bw.sudoku.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

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
		SpielfeldLeser spielfeldLeser = new SpielfeldLeserImpl();
		Spielfeld spielfeld;
		try {
			spielfeld = spielfeldLeser.lies("raetsel_83_1.txt");
		} catch (FileNotFoundException dateiNichtGefunden) {
			spielfeld = null;
		}
		assertTrue(spielfeld != null);
	}


}
