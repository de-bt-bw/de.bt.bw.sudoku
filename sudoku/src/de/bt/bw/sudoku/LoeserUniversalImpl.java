/**
 * 
 */
package de.bt.bw.sudoku;

import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

/**
 * Falls zu einem gegebenen Rätsel eine Lösung existiert, wird sie gefunden
 * und zurückgegeben. Sonst wird null zurückgegeben.
 */
public class LoeserUniversalImpl extends LoeserBasisImpl {

	protected class Zug {
		int zeilenNr, spaltenNr, wert;
		boolean eindeutig;
		Zug(int zeilenNr, int spaltenNr, int wert, boolean eindeutig) {
			this.zeilenNr = zeilenNr;
			this.spaltenNr = spaltenNr;
			this.wert = wert;
			this.eindeutig = eindeutig;
		}
	}
	
	protected Stack<Zug> zugStapel;
	
	protected Spielfeld loesung;
	
	/**
	 * Findet eine Lösung durch Tiefensuche. Dazu wird ein
	 * Stapel von Zügen verwaltet. Jeder Zug wird auf dem 
	 * Stapel abgelegt. Dabei werden eindeutige Züge durchgeführt,
	 * bis eine der folgenden Bedingungen eintritt:
	 * 1. Die Lösung ist vollständig, und der Algorithmus terminiert.
	 * 2. Die Lösung ist unvollständig. Dann wird ein nicht eindeutiger
	 *    Zug ausgeführt und das eindeutige Setzen fortgesetzt.
	 * 3. Es wird eine Sackgasse erreicht (d.h. eine unbelegte Zelle
	 *    mit einer leeren Menge möglicher Werte). Dann wird versucht,
	 *    zurückzusetzen, bis ein Zug gefunden wird, der noch revidiert
	 *    werden kann. Dieser Zug wird ausgeführt und das eindeutige
	 *    Setzen fortgesetzt.
	 * Falls eine Lösung zum Rätsel existiert, wird eine Lösung gefunden.
	 * Falls keine Lösung gefunden wird, ist das Rätsel inkonsistent,
	 * und es wird null zurückgegeben.
	 */
	@Override
	public Spielfeld loese(Spielfeld raetsel) {
		// Initialisierung
		SpielfeldHelfer helfer = new SpielfeldHelferImpl();
		loesung = helfer.kopiere(raetsel);
		zugStapel = new Stack<Zug>();
		boolean weiter = true;
		boolean erfolg = false;
		while (weiter) {
			// 1. Phase: Eindeutig bestimmte Werte setzen
			boolean wertGesetzt = false;
			boolean konsistent = true;
			do {
				MatrixSchleife:
				for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++)
					for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++)
						try {
							// 1. Fall: Wert ist schon gesetzt => überspringen
							int wert = loesung.wert(zeilenNr, spaltenNr);
							if (wert != 0)
								continue;
							// 2. Fall: Wertemenge leer => Schleife abbrechen
							Set<Integer> moeglicheWerte = loesung.moeglicheWerte(zeilenNr, spaltenNr);
							if (moeglicheWerte.isEmpty()) {
								konsistent = false;
								break MatrixSchleife;
							}
							// 3. Fall: Wertemenge nicht leer => nach eindeutigem Wert suchen und setzen
							Iterator<Integer> iterator = moeglicheWerte.iterator();
							while (iterator.hasNext()) {
								int moeglicherWert = iterator.next();
								if (moeglicheWerte.size() == 1 ||
									eindeutigInZeile(loesung, zeilenNr, moeglicherWert) ||
									eindeutigInSpalte(loesung, spaltenNr, moeglicherWert) ||
									eindeutigInBlock(loesung, zeilenNr, spaltenNr, moeglicherWert)) {
									loesung.setze(zeilenNr, spaltenNr, moeglicherWert);
									zugStapel.push(new Zug(zeilenNr, spaltenNr, moeglicherWert, true));
									wertGesetzt = true;
									break;
								}
							}
						} catch (FalscherWert falscherWert) {
						}
			} while (wertGesetzt && konsistent);
			// Die do-Schleife terminiert, wenn in der Matrixschleife kein Wert gesetzt werden konnte
			// oder eine Inkonsistenz gefunden wurde
			if (helfer.loesungVollstaendig(loesung)) { // Eine Lösung wurde gefunden
				erfolg = true;
				weiter = false;
			} else if (!konsistent) { // Sackgasse 
				weiter = neuerVersuch(); // Backtracking
			} else { // Lösung konnte nicht eindeutig vervollständigt werden
				rate(); // Es wird ein Wert geraten
				weiter = true;
			}
		}
		if (erfolg)
			return loesung;
		else 
			return null;
	}
	
	/**
	 * Nimmt Züge zurück, bis ein alternativer Zug gefunden wird, der dann ausgeführt wird.
	 * @return true, falls ein neuer Zug gefunden werden konnte, false sonst
	 */
	protected boolean neuerVersuch() {
		boolean erfolg = false;
		return erfolg;
	}
	
	/**
	 * Vorbedingung: Lösung ist unvollständig, und es gibt keinen eindeutig bestimmbaren Wert.
	 * Dann wird ein Wert geraten.
	 */
	protected void rate() {
		
	}

}
