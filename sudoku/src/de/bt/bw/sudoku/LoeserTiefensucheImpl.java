/**
 * 
 */
package de.bt.bw.sudoku;

import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

import de.bt.bw.sudoku.LoeserUniversalImpl.Zug;

/**
 * Lösung durch Tiefensuche. Jede Zelle wird mit einem möglichen Wert gefüllt.
 * Bei Erreichen einer Sackgasse wird Backtracking ausgelöst. Es wird entweder
 * eine vollständige Lösung zurückgeliefert, oder null, falls der Suchraum 
 * erschöpft wird und die Lösung nicht vollständig ist.
 * Vorsicht: extrem ineffiziente Lösung, daher einige Testfälle auskommentiert
 */
public class LoeserTiefensucheImpl implements Loeser {

	protected class Zug {
		int zeilenNr, spaltenNr, wert;
		Set<Integer> alternativeWerte; // Werte, die noch nicht ausprobiert wurden
		Zug(int zeilenNr, int spaltenNr, int wert, Set<Integer> alternativeWerte) {
			this.zeilenNr = zeilenNr;
			this.spaltenNr = spaltenNr;
			this.wert = wert;
			this.alternativeWerte = alternativeWerte;
		}
	}
	
	/**
	 * Tiefensuche ohne Optimierungen. Bei einem Zug wird jeweils ein möglicher Wert
	 * ausgewählt, ohne zu überprüfen, ob die Belegung der Zelle bereits eindeutig
	 * in der Zeile, der Spalte oder dem Block fixiert ist.
	 */
	@Override
	public Spielfeld loese(Spielfeld raetsel) {
		// Initialisierung
		SpielfeldHelfer helfer = new SpielfeldHelferImpl();
		Spielfeld loesung = helfer.kopiere(raetsel);
		Stack<Zug> zugStapel = new Stack<Zug>();
		int zeilenNr = 0, spaltenNr = 0;
		boolean erfolg = true; // Wird auf false gesetzt, wenn der Suchraum erschöpft ist
		
		// Die Lösung wird in einer zyklischen Schleife über die Zellen des Spielfelds gesucht
		while (erfolg && !helfer.loesungVollstaendig(loesung)) {
			int wert = loesung.wert(zeilenNr, spaltenNr);
			if (wert == 0) { // Belegte Felder überspringen
				if (loesung.moeglicheWerte(zeilenNr, spaltenNr).isEmpty()) {
					erfolg = neuerVersuch(loesung, zugStapel);
				} else {
					naechsterZug(loesung, zugStapel, zeilenNr, spaltenNr, loesung.moeglicheWerte(zeilenNr, spaltenNr));
				}
			}
			// Nächste Zelle ermitteln
			if (erfolg) {
				spaltenNr = (spaltenNr + 1) % 9;
				if (spaltenNr == 0) {
					zeilenNr = (zeilenNr + 1) % 9;
				}
			}			
		}
		
		// Abschlussbehandlung
		if (erfolg)
			return loesung;
		else
			return null;
	}
	
	/**
	 * Nimmt Züge zurück, bis ein alternativer Zug gefunden wird, der dann ausgeführt wird.
	 * 
	 * @return true, falls ein neuer Zug gefunden werden konnte, false sonst
	 */
	protected boolean neuerVersuch(Spielfeld loesung, Stack<Zug> zugStapel) {
		while (!zugStapel.empty()) {
			Zug zug = zugStapel.pop();
			// Zug zurücknehmen
			try {
				loesung.setze(zug.zeilenNr, zug.spaltenNr, 0);
			} catch (FalscherWert e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!zug.alternativeWerte.isEmpty()) {
				// Alternativen Zug ausführen
				naechsterZug(loesung, zugStapel, zug.zeilenNr, zug.spaltenNr, zug.alternativeWerte);
				return true;
			}
		}
		return false;			
	}
	
	/**
	 * Belegt die Zelle mit dem kleinsten möglichen Wert.
	 * Vorbedingung: Die Menge der möglichen Werte ist nicht leer.
	 */
	protected void naechsterZug(Spielfeld loesung, Stack<Zug> zugStapel, int zeilenNr, int spaltenNr, Set<Integer> moeglicheWerte) {
		int neuerWert = minimum(moeglicheWerte);
		moeglicheWerte.remove(neuerWert);
		try {
			loesung.setze(zeilenNr, spaltenNr, neuerWert);
		} catch (FalscherWert e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Zug zug = new Zug(zeilenNr, spaltenNr, neuerWert, moeglicheWerte);
		zugStapel.push(zug);
	}
	
	/**
	 * Bestimmt das Minimum einer nichtleeren Menge ganzer Zahlen
	 * 
	 * @param menge die nichtleere Menge ganzer Zahlen
	 * @return die kleinste Zahl in dieser Menge
	 */
	protected int minimum(Set<Integer> menge) {
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
