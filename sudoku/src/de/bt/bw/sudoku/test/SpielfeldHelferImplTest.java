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
class SpielfeldHelferImplTest {

	/**
	 * Testet, ob ein korrektes, als Matrix gegebenes Rätsel in ein
	 * Spielfeld konvertiert werden kann.
	 */
	@Test
	void testErzeugeSpielfeldRaetsel_83_1() {
		boolean erfolg = true;
		Spielfeld spielfeld;
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
		try {
			spielfeld = spielfeldHelfer.erzeugeSpielfeld(Testdaten.raetsel_83_1);
		} catch (Exception e) {
			erfolg = false;
		}
		assertTrue(erfolg);
	}
	
	/**
	 * Testet, ob eine korrekte, als Matrix gegebene Lösung in ein
	 * Spielfeld konvertiert werden kann.
	 */
	@Test
	void testErzeugeSpielfeldLoesung_83_1() {
		boolean erfolg = true;
		Spielfeld spielfeld;
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
		try {
			spielfeld = spielfeldHelfer.erzeugeSpielfeld(Testdaten.loesung_83_1);
		} catch (Exception e) {
			erfolg = false;
		}
		assertTrue(erfolg);
	}
	
	/**
	 * Testet, ob zwei Spielfelder, die aus derselben Matrix erzeugt wurden,
	 * gleich sind. 
	 * Voraussetzung: Die Matrix ist korrekt.
	 */
	@Test
	void testGleich() {
		boolean erfolg;
		Spielfeld spielfeld1, spielfeld2;
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
		try {
			spielfeld1 = spielfeldHelfer.erzeugeSpielfeld(Testdaten.raetsel_83_1);
			spielfeld2 = spielfeldHelfer.erzeugeSpielfeld(Testdaten.raetsel_83_1);
			erfolg = spielfeldHelfer.gleich(spielfeld1, spielfeld2);
		} catch (Exception e) {
			erfolg = false;
		}
		assertTrue(erfolg);
	}

	/**
	 * Testet das Kopieren eines Spielfelds unter Benutzung von
	 * erzeugeSpielfeld() und gleich().
	 */
	@Test
	void testKopieren() {
		boolean erfolg;
		Spielfeld spielfeld1, spielfeld2;
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
		try {
			spielfeld1 = spielfeldHelfer.erzeugeSpielfeld(Testdaten.raetsel_83_1);
			spielfeld2 = spielfeldHelfer.kopiere(spielfeld1);
			erfolg = spielfeldHelfer.gleich(spielfeld1, spielfeld2);
		} catch (Exception e) {
			erfolg = false;
		}
		assertTrue(erfolg);
	}
	
	/**
	 * Test die Methode loesungVollstaendig() mit einem unvollständigen 
	 * und einem vollständigem Spielfeld.
	 */
	@Test
	void testLoesungVollstaendig() {
		boolean erfolg;
		Spielfeld spielfeld1, spielfeld2;
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
		try {
			spielfeld1 = spielfeldHelfer.erzeugeSpielfeld(Testdaten.raetsel_83_1);
			if (spielfeldHelfer.loesungVollstaendig(spielfeld1))
				erfolg = false;
			else {
				spielfeld2 = spielfeldHelfer.erzeugeSpielfeld(Testdaten.loesung_83_1);
				erfolg = spielfeldHelfer.loesungVollstaendig(spielfeld2);
			}
		} catch (Exception e) {
			erfolg = false;
		}
		assertTrue(erfolg);
	}
}
