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
 */
class SpielfeldSchreiberImplTest {

	/**
	 * Schreiben des Rätsels 83_1 in eine Textdatei
	 * 
	 */
	@Test
	void testSchreibeSpielfeldRaetsel_83_1() {
		boolean erfolg = true;
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
		Spielfeld spielfeld1 = spielfeldHelfer.erzeugeSpielfeld(Testdaten.raetsel_83_1);
		SpielfeldSchreiber spielfeldSchreiber = new SpielfeldSchreiberImpl();
		try {
			spielfeldSchreiber.schreib(spielfeld1, "raetsel_83_1.txt");
		} catch (FileNotFoundException dateiNichtGefunden) {
			erfolg = false;
		}
		assertTrue(erfolg);
		SpielfeldLeser spielfeldLeser = new SpielfeldLeserImpl();
		Spielfeld spielfeld2;
		try {
			spielfeld2 = spielfeldLeser.lies("raetsel_83_1.txt");
		} catch (FileNotFoundException dateiNichtGefunden) {
			spielfeld2 = null;
			erfolg = false;
		}
		assertTrue(erfolg);
		assertTrue(spielfeldHelfer.gleich(spielfeld1, spielfeld2));
	}

}
