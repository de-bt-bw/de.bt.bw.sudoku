/**
 * 
 */
package de.bt.bw.sudoku;

import java.util.Iterator;
import java.util.Set;

/**
 * Basisimplementierung eines Lösers. Solange Werte eindeutig bestimmt werden können,
 * wird die Lösung vervollständigt. Sobald eine ungesetzte Zelle gefunden wird,
 * für die kein Wert mehr möglich ist, bricht die Lösungssuche mit einer Ausnahme ab.
 * Falls keine vollständige Lösung gefunden wird, wird ebenfalls eine Ausnahme ausgelöst.
 * 
 */
public class LoeserBasisImpl implements Loeser {

	/*
	 * Überprüft, ob der mögliche Wert genau einmal in der Zeile vorkommt
	 */
	private boolean eindeutigInZeile(Spielfeld spielfeld, int zeilenNr, int wert) {
		int haeufigkeit = 0;
		for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++)
			try {
				if (spielfeld.moeglicheWerte(zeilenNr, spaltenNr).contains(wert))
					haeufigkeit++;
			} catch (FalscheZahl e) {
			}
		return haeufigkeit == 1;
	}
	
	
	/*
	 * Überprüft, ob der mögliche Wert genau einmal in der Spalte vorkommt
	 */
	private boolean eindeutigInSpalte(Spielfeld spielfeld, int spaltenNr, int wert) {
		int haeufigkeit = 0;
		for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++)
			try {
				if (spielfeld.moeglicheWerte(zeilenNr, spaltenNr).contains(wert))
					haeufigkeit++;
			} catch (FalscheZahl e) {
			}
		return haeufigkeit == 1;
	}
	
	/*
	 * Überprüft, ob der mögliche Wert genau einmal in dem Block vorkommt,
	 * der die durch Zeilen- und Spaltennummer identifizierte Zelle enthält
	 */
	private boolean eindeutigInBlock(Spielfeld spielfeld, int zeilenNr, int spaltenNr, int wert) {
		int haeufigkeit = 0;
		int minZeile = zeilenNr - zeilenNr % 3;
		int minSpalte = spaltenNr - spaltenNr % 3;
		for (int z = minZeile; z <= minZeile + 2; z++)
			for (int s = minSpalte; s <= minSpalte + 2; s++)
				try {
					if (spielfeld.moeglicheWerte(z, s).contains(wert))
						haeufigkeit++;
				} catch (FalscheZahl e) {
				}
		return haeufigkeit == 1;
	}
	
	@Override
	public Spielfeld loese(Spielfeld raetsel) throws LoesungUnvollstaendig, KeinMoeglicherWert {
		// Kopiere Rätsel in Lösung
		SpielfeldHelfer helfer = new SpielfeldHelferImpl();
		Spielfeld loesung = helfer.kopiere(raetsel);
		// Konstruiere Lösung, bis kein Wert mehr gesetzt werden kann
		// oder eine ungesetzte Zelle gefunden wird, für die kein Wert mehr möglich ist
		boolean wertGesetzt = false;
		do {
			for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++)
				for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
					try {
						// 1. Fall: Wert ist schon gesetzt => überspringen
						int wert = loesung.wert(zeilenNr, spaltenNr);
						if (wert != 0)
							continue;
						// 2. Fall: Wertemenge leer => Ausnahme auslösen
						Set<Integer> moeglicheWerte = loesung.moeglicheWerte(zeilenNr, spaltenNr);
						if (moeglicheWerte.isEmpty())
							throw new KeinMoeglicherWert(zeilenNr, spaltenNr);
						// 3. Fall: Wertemenge einelementig => Wert setzen, wertGesetzt = true
						Iterator<Integer> iterator = moeglicheWerte.iterator();
						int moeglicherWert = iterator.next();
						if (!iterator.hasNext()) {
							// Nur ein Element
							loesung.setze(zeilenNr, spaltenNr, moeglicherWert);
							wertGesetzt = true;
							continue;
						}
						// 4. Fall: Iteriere über die möglichen Werte,
						// beginnend mit dem bereits ermittelten ersten Wert
						boolean weiter = true;
						do {
							// Wert eindeutig in Zeile oder Spalte oder Block => Wert setzen, wertGesetzt = true
							if (eindeutigInZeile(loesung, zeilenNr, moeglicherWert) ||
								eindeutigInSpalte(loesung, spaltenNr, moeglicherWert) ||
								eindeutigInBlock(loesung, zeilenNr, spaltenNr, moeglicherWert)) {
								loesung.setze(zeilenNr, spaltenNr, moeglicherWert);
								wertGesetzt = true;
								weiter = false;
							} else
								// Nächsten Wert ermitteln, falls noch vorhanden
								if (iterator.hasNext())
									moeglicherWert = iterator.next();
								else
									weiter = false;
						} while (weiter);
					} catch (FalscheZahl falscheZahl) {
					} catch (FalscherZustand falscherZustand) {
					} catch (FalscherWert falscherWert) {
					}
				}
		} while (wertGesetzt);
		// Überprüfe, ob die Lösung vollständig ist
		if (!helfer.loesungVollstaendig(loesung))
			throw new LoesungUnvollstaendig("Lösung unvollständig");
		return loesung;
	}

}
