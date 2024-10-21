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
		boolean erfolg = true;
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
		Spielfeld raetsel_83_1 = null, loesung_83_1 = null;
		// Die Erzeugung wird als korrekt vorausgesetzt
		try {
			raetsel_83_1 = spielfeldHelfer.erzeugeSpielfeld(Testdaten.raetsel_83_1);
		} catch (Exception e) {
		}
		Loeser loeser = new LoeserBasisImpl();
		SpielfeldSchreiber spielfeldSchreiber = new SpielfeldSchreiberImpl();
		try {
			loesung_83_1 = loeser.loese(raetsel_83_1);
		} catch (LoesungUnvollstaendig loesungUnvollstaendig) {
			erfolg = false;
			System.err.println(loesungUnvollstaendig.getMessage());			
			try {
				spielfeldSchreiber.schreib(loesungUnvollstaendig.spielfeld, "err/loesung_83_1.txt");
			} catch (IOException e) {
			}
		} catch (KeinMoeglicherWert keinMoeglicherWert) {
			erfolg = false;
			System.err.println(keinMoeglicherWert.getMessage());
			try {
				spielfeldSchreiber.schreib(keinMoeglicherWert.spielfeld, "err/loesung_83_1.txt");
			} catch (IOException e) {
			}		
		}
		if (erfolg)
			try {
				Spielfeld korrekte_loesung_83_1 = spielfeldHelfer.erzeugeSpielfeld(Testdaten.loesung_83_1);
				erfolg = spielfeldHelfer.gleich(loesung_83_1, korrekte_loesung_83_1);
			} catch (Exception e) {
			}
		assertTrue(erfolg);
	}

}
