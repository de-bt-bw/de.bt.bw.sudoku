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

	@Test
	void testLoeseRaetsel_FitImKopf() {
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
		Spielfeld raetsel_FitImKopf = spielfeldHelfer.erzeugeSpielfeld(Testdaten.raetsel_FitImKopf);
		Loeser loeser = new LoeserBasisImpl();
		Spielfeld loesung_FitImKopf = loeser.loese(raetsel_FitImKopf);
		assertTrue(loesung_FitImKopf == null); // Vom Basislöser nicht lösbar
	}

}
