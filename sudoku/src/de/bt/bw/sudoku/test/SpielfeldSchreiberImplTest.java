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
class SpielfeldSchreiberImplTest {

	/**
	 * Schreiben des Rätsels 83_1 in eine Textdatei
	 * 
	 */
	@Test
	void testSchreibeSpielfeldRaetsel_83_1() {
		boolean erfolg = true;
		Spielfeld spielfeld;
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
		SpielfeldSchreiber spielfeldSchreiber = new SpielfeldSchreiberImpl();
		try {
			spielfeld = spielfeldHelfer.erzeugeSpielfeld(Testdaten.raetsel_83_1);
			spielfeldSchreiber.schreib(spielfeld, "raetsel_83_1.txt");
		} catch (Exception e) {
			erfolg = false;
		}
		assertTrue(erfolg);
	}

}
