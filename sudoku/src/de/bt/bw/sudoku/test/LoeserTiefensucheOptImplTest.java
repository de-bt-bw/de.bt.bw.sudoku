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
class LoeserTiefensucheOptImplTest {
	SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
	Loeser loeser = new LoeserTiefensucheOptImpl();
	LoeserHelfer loeserHelfer = new LoeserHelferImpl();

	@Test
	void testLoeseRaetsel_83_1() {
		assertTrue(spielfeldHelfer.loesungVollstaendig(loeserHelfer.loese(loeser, Testdaten.raetsel_83_1)));
	}

	@Test
	void testLoeseRaetsel_FitImKopf() {
		assertTrue(spielfeldHelfer.loesungVollstaendig(loeserHelfer.loese(loeser, Testdaten.raetsel_FitImKopf)));
	}

	@Test
	void testLoeseRaetsel_Leer() {
		assertTrue(spielfeldHelfer.loesungVollstaendig(loeserHelfer.loese(loeser, Testdaten.raetsel_Leer)));
	}
	
	@Test
	void testLoeseRaetsel_83_51() {
		assertTrue(spielfeldHelfer.loesungVollstaendig(loeserHelfer.loese(loeser, Testdaten.raetsel_83_51)));
	}
	
	@Test
	void testLoeseRaetsel_Golden_1() {
		assertTrue(spielfeldHelfer.loesungVollstaendig(loeserHelfer.loese(loeser, Testdaten.raetsel_Golden_1)));
	}
	
	@Test
	void testLoeseRaetsel_Golden_161() {
		assertTrue(spielfeldHelfer.loesungVollstaendig(loeserHelfer.loese(loeser, Testdaten.raetsel_Golden_161)));
	}
	
	@Test
	void testLoeseRaetsel_Golden_309() {
		assertTrue(spielfeldHelfer.loesungVollstaendig(loeserHelfer.loese(loeser, Testdaten.raetsel_Golden_309)));
	}
}
