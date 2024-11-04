/**
 * 
 */
package de.bt.bw.sudoku.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import de.bt.bw.sudoku.Loeser;
import de.bt.bw.sudoku.LoeserUniversalImpl;
import de.bt.bw.sudoku.Spielfeld;
import de.bt.bw.sudoku.SpielfeldHelfer;
import de.bt.bw.sudoku.SpielfeldHelferImpl;
import de.bt.bw.sudoku.SpielfeldSchreiber;
import de.bt.bw.sudoku.SpielfeldSchreiberImpl;

/**
 * 
 */
class LoeserUniversalImplTest {

	@Test
	void testLoeseRaetsel_83_1() {
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
		Spielfeld raetsel_83_1 = spielfeldHelfer.erzeugeSpielfeld(Testdaten.raetsel_83_1);
		Loeser loeser = new LoeserUniversalImpl();
		Spielfeld loesung_83_1 = loeser.loese(raetsel_83_1);
		assertTrue(loesung_83_1 != null);
		Spielfeld korrekte_loesung_83_1 = spielfeldHelfer.erzeugeSpielfeld(Testdaten.loesung_83_1);
		assertTrue(spielfeldHelfer.gleich(loesung_83_1, korrekte_loesung_83_1));
	}
	
	@Test
	void testLoeseRaetsel_FitImKopf() {
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
		Spielfeld raetsel_FitImKopf = spielfeldHelfer.erzeugeSpielfeld(Testdaten.raetsel_FitImKopf);
		Loeser loeser = new LoeserUniversalImpl();
		Spielfeld loesung_FitImKopf = loeser.loese(raetsel_FitImKopf);
		assertTrue(loesung_FitImKopf != null); // Vom Universallöser lösbar
		Spielfeld korrekte_loesung_FitImKopf = spielfeldHelfer.erzeugeSpielfeld(Testdaten.loesung_FitImKopf);
		assertTrue(spielfeldHelfer.gleich(loesung_FitImKopf, korrekte_loesung_FitImKopf));
	}

}
