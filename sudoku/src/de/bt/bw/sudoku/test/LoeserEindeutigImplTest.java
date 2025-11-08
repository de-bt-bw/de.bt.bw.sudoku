package de.bt.bw.sudoku.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.bt.bw.sudoku.Loeser;
import de.bt.bw.sudoku.LoeserEindeutigImpl;
import de.bt.bw.sudoku.LoeserHelfer;
import de.bt.bw.sudoku.LoeserHelferImpl;
import de.bt.bw.sudoku.SpielfeldHelfer;
import de.bt.bw.sudoku.SpielfeldHelferImpl;

class LoeserEindeutigImplTest {

	SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
	Loeser loeser = new LoeserEindeutigImpl();
	LoeserHelfer loeserHelfer = new LoeserHelferImpl();

	@Test
	void testLoeseRaetsel_83_1() {
		assertTrue(spielfeldHelfer.loesungVollstaendig(loeserHelfer.loese(loeser, Testdaten.raetsel_83_1)));
	}

	@Test
	void testLoeseRaetsel_FitImKopf() {
		assertTrue(loeserHelfer.loese(loeser, Testdaten.raetsel_FitImKopf) == null); // Vom Basislöser nicht lösbar
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
		assertTrue(loeserHelfer.loese(loeser, Testdaten.raetsel_Golden_309) == null); // Vom Basislöser nicht lösbar
	}
	
	@Test
	void testLoeseRaetsel_Golden_311() {
		assertTrue(loeserHelfer.loese(loeser, Testdaten.raetsel_Golden_311) == null); // Vom Basislöser nicht lösbar
	}
	
	@Test
	void testLoeseRaetsel_Golden_316() {
		assertTrue(loeserHelfer.loese(loeser, Testdaten.raetsel_Golden_316) == null); // Vom Basislöser nicht lösbar
	}


}
