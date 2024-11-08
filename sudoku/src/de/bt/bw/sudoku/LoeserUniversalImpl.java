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
public class LoeserUniversalImpl implements Loeser {

	private class Zug {
		int zeilenNr, spaltenNr, wert;
		boolean eindeutig;
		Zug(int zeilenNr, int spaltenNr, int wert, boolean eindeutig) {
			this.zeilenNr = zeilenNr;
			this.spaltenNr = spaltenNr;
			this.wert = wert;
			this.eindeutig = eindeutig;
		}
	}
	
	
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
		if (raetsel == null) return null;
		// Initialisierung
		SpielfeldHelfer helfer = new SpielfeldHelferImpl();
		Spielfeld loesung = helfer.kopiere(raetsel);
		Stack<Zug> zugStapel = new Stack<Zug>(); // Stapel der bereits ausgeführten Züge
		boolean weiter = true; // Soll weiter gesucht werden?
		boolean erfolg = false; // Wurde bereits eine Lösung gefunden?
		while (weiter) {
			// 1. Phase: Eindeutig bestimmte Werte setzen
			boolean wertGesetzt; // Wurde in der Spielfeldschleife ein Wert gesetzt?
			boolean konsistent = true; // Ist die Teillösung noch konsistent?
			do {
				wertGesetzt = false;
				SpielfeldSchleife:
				for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++)
					for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
						// 1. Fall: Wert ist schon gesetzt => überspringen
						int wert = loesung.wert(zeilenNr, spaltenNr);
						if (wert != 0)
							continue;
						// 2. Fall: Wertemenge leer => Spielfeldschleife abbrechen
						Set<Integer> eingeschraenkteMoeglicheWerte = helfer.eingeschraenkteMoeglicheWerte(loesung, zeilenNr, spaltenNr);
						if (eingeschraenkteMoeglicheWerte.isEmpty()) {
							konsistent = false;
							break SpielfeldSchleife;
						}
						// 3. Fall: Wertemenge einelementig => eindeutig bestimmten Wert setzen
						if (eingeschraenkteMoeglicheWerte.size() == 1) {
							Iterator<Integer> iterator = eingeschraenkteMoeglicheWerte.iterator();
							wert = iterator.next(); // Der einzige Wert in der Menge
							try {
								loesung.setze(zeilenNr, spaltenNr, wert);
							} catch (FalscherWert e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							zugStapel.push(new Zug(zeilenNr, spaltenNr, wert, true)); // Zug auf dem Stapel ablegen
							wertGesetzt = true;
						}
						// Bei nicht eindeutigem Wert nichts tun
					} 
			} while (wertGesetzt && konsistent);
			// Die do-Schleife terminiert, wenn in der Spielfeldschleife kein Wert gesetzt werden konnte
			// oder eine Inkonsistenz gefunden wurde
			if (helfer.loesungVollstaendig(loesung)) { // Eine Lösung wurde gefunden
				erfolg = true;
				weiter = false;
			} else if (!konsistent) { // Sackgasse 
				weiter = neuerVersuch(loesung, zugStapel); // Backtracking, falls möglich
			} else { // Lösung konnte nicht eindeutig vervollständigt werden
				rate(loesung, zugStapel); // Es wird ein Wert geraten
				weiter = true; // Redundant, nur zum besseren Verständnis
			}
		}
		if (erfolg)
			return loesung;
		else 
			return null;
	}
	
	/**
	 * Nimmt Züge zurück, bis ein alternativer Zug gefunden wird, der dann ausgeführt wird.
	 * 
	 * @param loesung die bisher konstruierte Teillösung
	 * @param zugStapel der Stapel bisher ausgeführter Züge
	 * @return true, falls ein neuer Zug gefunden und ausgeführt werden konnte, false sonst
	 */
	private boolean neuerVersuch(Spielfeld loesung, Stack<Zug> zugStapel) {
		boolean erfolg = false;
		while (!(erfolg || zugStapel.empty())) {
			try {
				Zug zug = zugStapel.pop();
				// Zug zurücknehmen
				loesung.setze(zug.zeilenNr, zug.spaltenNr, 0);
				if (!zug.eindeutig) {
					// Falls noch vorhanden, alternativen Wert setzen
					// Dazu minimalen noch nicht verbrauchten Wert suchen
					Set<Integer> moeglicheWerte = loesung.moeglicheWerte(zug.zeilenNr, zug.spaltenNr);
					int neuerWert = 10;
					Iterator<Integer> iterator = moeglicheWerte.iterator();
					while (iterator.hasNext()) {
						int naechsterWert = iterator.next();
						if (zug.wert < naechsterWert) {
							erfolg = true; // Alternativer Wert vorhanden
							if (naechsterWert < neuerWert)
								neuerWert = naechsterWert;
						}
					}
					if (erfolg) { // Neuen Zug machen
						loesung.setze(zug.zeilenNr, zug.spaltenNr, neuerWert);
						zugStapel.push(new Zug(zug.zeilenNr, zug.spaltenNr, neuerWert, false));
					}
				}
			} catch (FalscherWert e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return erfolg;
	}

	/**
	 * Vorbedingung: Lösung ist unvollständig, und es gibt keinen eindeutig bestimmbaren Wert.
	 * Dann wird ein Wert geraten. Dazu wird eine Zelle mit einer minimalen Menge möglicher 
	 * Werte gesucht, um die Wahrscheinlichkeit einer Fehlentscheidung zu minimieren.
	 * Es wird der kleinste mögliche Wert gesetzt; beim Backtracking werden die möglichen Werte
	 * in aufsteigender Folge probiert.
	 * 
	 * @param loesung die bisher konstruierte Teillösung
	 * @param zugStapel Stapel der bisher ausgeführten Züge
	 */
	private void rate(Spielfeld loesung, Stack<Zug> zugStapel) {
		// Bestimme Zelle mit minimaler Zahl möglicher Werte
		int minZeilenNr = -1, minSpaltenNr = -1, minKardinalitaet = 10;
		// Da mindestens eine Zelle unbelegt ist, werden die Werte obiger Variablen
		// mindestens einmal in der Schleife neu gesetzt
		for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++)
			for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++)
				if (loesung.wert(zeilenNr, spaltenNr) == 0) {
					int kardinalitaet = loesung.moeglicheWerte(zeilenNr, spaltenNr).size();
					if (kardinalitaet < minKardinalitaet) {
						minZeilenNr = zeilenNr;
						minSpaltenNr = spaltenNr;
						minKardinalitaet = kardinalitaet;
					}
				}
		// Setze diese Zelle auf den minimalen möglichen Wert
		int minWert = minimum(loesung.moeglicheWerte(minZeilenNr, minSpaltenNr));
		try {
			loesung.setze(minZeilenNr, minSpaltenNr, minWert);
		} catch (FalscherWert e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Zug zug = new Zug(minZeilenNr, minSpaltenNr, minWert, false);
		zugStapel.push(zug);
	}
	
	/**
	 * Bestimmt das Minimum einer nichtleeren Menge ganzer Zahlen
	 * 
	 * @param menge die nichtleere Menge ganzer Zahlen
	 * @return die kleinste Zahl in dieser Menge
	 */
	private int minimum(Set<Integer> menge) {
		Iterator<Integer> iterator = menge.iterator();
		int minimum = iterator.next();
		while (iterator.hasNext()) {
			int naechsteZahl = iterator.next();
			if (naechsteZahl < minimum)
				minimum = naechsteZahl;
		}
		return minimum;
	}

}
