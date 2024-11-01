/**
 * 
 */
package de.bt.bw.sudoku;

import java.util.Iterator;
import java.util.Set;


/**
 * Basisimplementierung eines Lösers. Solange Werte eindeutig bestimmbar sind,
 * wird die Lösung vervollständigt. Es wird nicht garantiert, dass eine Lösung
 * gefunden wird, falls sie existiert.
 */
public class LoeserBasisImpl implements Loeser {

	/*
	 * Überprüft, ob der mögliche Wert genau einmal in der Zeile vorkommt
	 */
	protected boolean eindeutigInZeile(Spielfeld spielfeld, int zeilenNr, int wert) {
		int haeufigkeit = 0;
		for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++)
			if (spielfeld.moeglicheWerte(zeilenNr, spaltenNr).contains(wert))
				haeufigkeit++;
		return haeufigkeit == 1;
	}
	
	
	/*
	 * Überprüft, ob der mögliche Wert genau einmal in der Spalte vorkommt
	 */
	protected boolean eindeutigInSpalte(Spielfeld spielfeld, int spaltenNr, int wert) {
		int haeufigkeit = 0;
		for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++)
			if (spielfeld.moeglicheWerte(zeilenNr, spaltenNr).contains(wert))
				haeufigkeit++;
		return haeufigkeit == 1;
	}
	
	/*
	 * Überprüft, ob der mögliche Wert genau einmal in dem Block vorkommt,
	 * der die durch Zeilen- und Spaltennummer identifizierte Zelle enthält
	 */
	protected boolean eindeutigInBlock(Spielfeld spielfeld, int zeilenNr, int spaltenNr, int wert) {
		int haeufigkeit = 0;
		int minZeile = zeilenNr - zeilenNr % 3;
		int minSpalte = spaltenNr - spaltenNr % 3;
		for (int z = minZeile; z <= minZeile + 2; z++)
			for (int s = minSpalte; s <= minSpalte + 2; s++)
				if (spielfeld.moeglicheWerte(z, s).contains(wert))
					haeufigkeit++;
		return haeufigkeit == 1;
	}
	
	/**
	 * Basisimplementierung eines Lösers. Solange Werte eindeutig bestimmt werden können,
	 * wird die Lösung vervollständigt. Die Lösungssuche bricht ab, wenn kein eindeutig
	 * bestimmter Wert oder eine unbelegte Zelle mit einer leeren Menge möglicher
	 * Werte gefunden wird.
	 * Ein möglicher Wert ist eindeutig, wenn er
	 * - der einzige mögliche Wert für eine Zelle ist oder
	 * - innerhalb einer Zeile, einer Spalte oder eines Blocks genau eine 
	 *   Zelle existiert, in der dieser Wert möglich ist
	 */
	@Override
	public Spielfeld loese(Spielfeld raetsel) {
		// Kopiere Rätsel in Lösung
		SpielfeldHelfer helfer = new SpielfeldHelferImpl();
		Spielfeld loesung = helfer.kopiere(raetsel);
		// Konstruiere Lösung, bis kein Wert mehr gesetzt werden kann
		// oder eine ungesetzte Zelle gefunden wird, für die kein Wert mehr möglich ist
		boolean wertGesetzt; // Ist in einem Durchlauf durch die Matrix ein Wert gesetzt worden?
		// Setze Werte, solange sie eindeutig sind
		do {
			wertGesetzt = false;
			for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++)
				for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
					try {
						// 1. Fall: Wert ist schon gesetzt => überspringen
						int wert = loesung.wert(zeilenNr, spaltenNr);
						if (wert != 0)
							continue;
						// 2. Fall: Wertemenge leer => null zurückliefern
						Set<Integer> moeglicheWerte = loesung.moeglicheWerte(zeilenNr, spaltenNr);
						if (moeglicheWerte.isEmpty())
							return null;
						// 3. Fall: Wertemenge nicht leer => nach eindeutigem Wert suchen und setzen
						Iterator<Integer> iterator = moeglicheWerte.iterator();
						while (iterator.hasNext()) {
							int moeglicherWert = iterator.next();
							if (moeglicheWerte.size() == 1 ||
								eindeutigInZeile(loesung, zeilenNr, moeglicherWert) ||
								eindeutigInSpalte(loesung, spaltenNr, moeglicherWert) ||
								eindeutigInBlock(loesung, zeilenNr, spaltenNr, moeglicherWert)) {
								loesung.setze(zeilenNr, spaltenNr, moeglicherWert);
								wertGesetzt = true;
								break;
							}
						}
					} catch (FalscherWert falscherWert) {
					}
				}
		} while (wertGesetzt);
		// Überprüfe, ob die Lösung vollständig ist
		if (!helfer.loesungVollstaendig(loesung))
			return null;
		return loesung;
	}
}
