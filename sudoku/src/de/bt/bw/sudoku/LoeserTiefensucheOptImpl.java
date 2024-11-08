/**
 * 
 */
package de.bt.bw.sudoku;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

/**
 * Lösung durch Tiefensuche. In jedem Schritt wird eine Zelle mit einer minimalen
 * Zahl an Alternativen ausgesucht.
 */
public class LoeserTiefensucheOptImpl implements Loeser {
	
	private class Zug implements Comparable<Zug> {
		public int zeilenNr, spaltenNr;
		public Set<Integer> alternativeWerte; // Werte, die noch nicht ausprobiert wurden
		public Zug(int zeilenNr, int spaltenNr, Set<Integer> alternativeWerte) {
			this.zeilenNr = zeilenNr;
			this.spaltenNr = spaltenNr;
			this.alternativeWerte = alternativeWerte;
		}
		public int compareTo(Zug anderer) {
			int meineKardinalitaet = this.alternativeWerte.size();
			int andereKardinalitaet = anderer.alternativeWerte.size();
			if (meineKardinalitaet < andereKardinalitaet)
				return -1;
			else if (meineKardinalitaet > andereKardinalitaet)
				return +1;
			else
				return 0;
		}
	}
	
	/**
	 * Bestimmt das Minimum einer nichtleeren Menge vergleichbarer Objekte
	 * 
	 * @param menge die nichtleere Menge
	 * @return das Minimum in dieser Menge
	 */
	private <T extends Comparable<T>> T minimum(Set<T> menge) {
		Iterator<T> iterator = menge.iterator();
		T minimum = iterator.next();
		while (iterator.hasNext()) {
			T naechstesElement = iterator.next();
			if (naechstesElement.compareTo(minimum) < 0) {
				minimum = naechstesElement;
			}
		}
		return minimum;
	}

	/**
	 * Nimmt Züge zurück, bis ein alternativer Zug gefunden wird, der dann ausgeführt wird.
	 * 
	 * @param loesung bisherige Lösung
	 * @param zugStapel Stapel der Züge
	 * @param arbeitsMenge noch nicht bearbeitete Züge
	 * @return true, falls ein neuer Zug gefunden werden konnte, false sonst
	 */
	private boolean neuerVersuch(Spielfeld loesung, Stack<Zug> zugStapel, Set<Zug> arbeitsMenge) {
		while (!zugStapel.empty()) {
			Zug zug = zugStapel.pop();
			arbeitsMenge.add(zug);
			// Zug zurücknehmen
			try {
				loesung.setze(zug.zeilenNr, zug.spaltenNr, 0);
			} catch (FalscherWert e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!zug.alternativeWerte.isEmpty()) {
				// Alternativen Zug ausführen
				this.naechsterZug(loesung, zugStapel, arbeitsMenge, zug);
				return true;
			}
		}
		return false;			
	}

	/**
	 * Belegt die Zelle mit einem Element aus der Menge alternativer Werte.
	 * Vorbedingung: Die Menge der alternativen Werte ist nicht leer.
	 * 
	 * @param loesung bisherige Lösung
	 * @param zugStapel Stapel der Züge, auf dem der auszuführende Zug abgelegt wird
	 * @param arbeitsMenge noch nicht bearbeitete Züge
	 * @param zug der auszuführende Zug aus der Arbeitsmenge
	 */
	private void naechsterZug(Spielfeld loesung, Stack<Zug> zugStapel, Set<Zug> arbeitsMenge, Zug zug) {
		arbeitsMenge.remove(zug);
		Iterator<Integer> iterator = zug.alternativeWerte.iterator();
		int neuerWert = iterator.next();
		try {
			loesung.setze(zug.zeilenNr, zug.spaltenNr, neuerWert);
		} catch (FalscherWert e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		zug.alternativeWerte.remove(neuerWert);
		zugStapel.push(zug);
	}



	/**
	 * Die Lösung wird durch Tiefensuche ermittelt. null wird nur zurückgeliefert,
	 * falls das Rätsel inkonsistent war.
	 * Für das Backtracking wird ein Stapel verwaltet, für die Auswahl des 
	 * nächsten Zugs eine Arbeitsmenge.
	 */
	@Override
	public Spielfeld loese(Spielfeld raetsel) {
		if (raetsel == null) return null;
		// Initialisierung
		SpielfeldHelfer helfer = new SpielfeldHelferImpl();
		Spielfeld loesung = helfer.kopiere(raetsel);
		Stack<Zug> zugStapel = new Stack<Zug>();
		Set<Zug> arbeitsMenge = new HashSet<Zug>(); // Enthält nur ungesetzte Zellen mit nichtleerer Menge alternativer Werte
		for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++) {
			for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
				if (loesung.wert(zeilenNr, spaltenNr) == 0) {
					// Zelle muss bearbeitet werden
					Set<Integer> alternativeWerte = helfer.eingeschraenkteMoeglicheWerte(loesung, zeilenNr, spaltenNr);
					if (alternativeWerte.isEmpty()) {
						// Fehler im Rätsel
						return null;
					} else {
						arbeitsMenge.add(new Zug(zeilenNr, spaltenNr, alternativeWerte));
					}					
				}
			}
		}
		boolean erfolg = true; // Wird auf false gesetzt, wenn der Suchraum erschöpft ist
		
		// Abarbeiten der Arbeitsmenge
		while (erfolg && !arbeitsMenge.isEmpty()) {
			// Wähle den Zug mit einer minimalen Zahl von Alternativen,
			// um das Backtracking zu begrenzen
			Zug naechsterZug = minimum(arbeitsMenge);
			if (naechsterZug.alternativeWerte.isEmpty()) {
				// Backtracking
				erfolg = this.neuerVersuch(loesung, zugStapel, arbeitsMenge);
			} else {
				// Zug ausführen
				this.naechsterZug(loesung, zugStapel, arbeitsMenge, naechsterZug);
			}
			// Alternative Werte in der gesamten Arbeitsmenge neu berechnen
			if (erfolg) {
				Iterator<Zug> iterator = arbeitsMenge.iterator();
				while (iterator.hasNext()) {
					Zug zug = iterator.next();
					zug.alternativeWerte = helfer.eingeschraenkteMoeglicheWerte(loesung, zug.zeilenNr, zug.spaltenNr);
				}
			}
		}

		// Abschlussbehandlung
		if (erfolg)
			return loesung;
		else
			return null;
	}
	

}
