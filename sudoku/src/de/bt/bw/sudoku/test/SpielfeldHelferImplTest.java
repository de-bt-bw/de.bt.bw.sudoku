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
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
		Spielfeld spielfeld = spielfeldHelfer.erzeugeSpielfeld(Testdaten.raetsel_83_1);
		assertTrue(spielfeld != null);
	}
	
	/**
	 * Testet, ob eine korrekte, als Matrix gegebene Lösung in ein
	 * Spielfeld konvertiert werden kann.
	 */
	@Test
	void testErzeugeSpielfeldLoesung_83_1() {
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
		Spielfeld spielfeld = spielfeldHelfer.erzeugeSpielfeld(Testdaten.loesung_83_1);
		assertTrue(spielfeld != null);
	}
	
	/**
	 * Testet, ob zwei Spielfelder, die aus derselben Matrix erzeugt wurden,
	 * gleich sind, unter Benutzung von erzeugeSpielfeld(). 
	 * Voraussetzung: Die Matrix ist korrekt.
	 */
	@Test
	void testGleich() {
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
        Spielfeld spielfeld1 = spielfeldHelfer.erzeugeSpielfeld(Testdaten.raetsel_83_1);
		Spielfeld spielfeld2 = spielfeldHelfer.erzeugeSpielfeld(Testdaten.raetsel_83_1);
		assertTrue(spielfeldHelfer.gleich(spielfeld1, spielfeld2));
	}

	/**
	 * Testet das Kopieren eines Spielfelds unter Benutzung von
	 * erzeugeSpielfeld() und gleich().
	 */
	@Test
	void testKopieren() {
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
		Spielfeld spielfeld1 = spielfeldHelfer.erzeugeSpielfeld(Testdaten.raetsel_83_1);
		Spielfeld spielfeld2 = spielfeldHelfer.kopiere(spielfeld1);
		assertTrue(spielfeldHelfer.gleich(spielfeld1, spielfeld2));
	}
	
	/**
	 * Testet die Methode loesungVollstaendig() mit einem unvollständigen 
	 * Spielfeld.
	 */
	@Test
	void testLoesungVollstaendigFalsch() {
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
		Spielfeld spielfeld = spielfeldHelfer.erzeugeSpielfeld(Testdaten.raetsel_83_1);
		assertFalse(spielfeldHelfer.loesungVollstaendig(spielfeld));
	}
	
	/**
	 * Testet die Methode loesungVollstaendig() mit einem unvollständigen 
	 * Spielfeld.
	 */
	@Test
	void testLoesungVollstaendigWahr() {
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
		Spielfeld spielfeld = spielfeldHelfer.erzeugeSpielfeld(Testdaten.loesung_83_1);
		assertTrue(spielfeldHelfer.loesungVollstaendig(spielfeld));
	}
}
