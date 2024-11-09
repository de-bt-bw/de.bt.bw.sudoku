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
class LoeserBasisImplTest {
	SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
	Loeser loeser = new LoeserBasisImpl();
	LoeserHelfer loeserHelfer = new LoeserHelferImpl();

	@Test
	void testLoeseRaetsel_83_1() {
		assertTrue(spielfeldHelfer.loesungVollstaendig(loeserHelfer.loese(loeser, Testdaten.raetsel_83_1)));
	}

	@Test
	void testLoeseRaetsel_FitImKopf() {
		assertTrue(loeserHelfer.loese(loeser, Testdaten.raetsel_FitImKopf) == null); // Vom Basislöser nicht lösbar
	}

}
