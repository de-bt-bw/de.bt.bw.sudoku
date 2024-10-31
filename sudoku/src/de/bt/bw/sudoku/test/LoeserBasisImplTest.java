/**
 * 
 */
package de.bt.bw.sudoku.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import de.bt.bw.sudoku.*;


/**
 * 
 */
class LoeserBasisImplTest {

	@Test
	void testLoeseRaetsel_83_1() {
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
		Spielfeld raetsel_83_1 = spielfeldHelfer.erzeugeSpielfeld(Testdaten.raetsel_83_1);
		Loeser loeser = new LoeserBasisImpl();
		Spielfeld loesung_83_1 = loeser.loese(raetsel_83_1);
		assertTrue(loesung_83_1 != null);
		Spielfeld korrekte_loesung_83_1 = spielfeldHelfer.erzeugeSpielfeld(Testdaten.loesung_83_1);
		assertTrue(spielfeldHelfer.gleich(loesung_83_1, korrekte_loesung_83_1));
	}

}
