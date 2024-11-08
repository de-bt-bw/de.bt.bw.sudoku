/**
 * 
 */
package de.bt.bw.sudoku;

import java.util.Iterator;
import java.util.Set;
import java.util.Stack;


/**
 * Lösung durch Tiefensuche. Die Zellen werden zyklisch durchlaufen.
 * Jede Zelle wird mit einem möglichen Wert gefüllt.
 * Bei Erreichen einer Sackgasse wird Backtracking ausgelöst. Es wird entweder
 * eine vollständige Lösung zurückgeliefert, oder null, falls der Suchraum 
 * erschöpft wird und die Lösung nicht vollständig ist.
 */
public class LoeserTiefensucheImpl implements Loeser {

	private class Zug {
		int zeilenNr, spaltenNr;
		Set<Integer> alternativeWerte; // Werte, die noch nicht ausprobiert wurden
		Zug(int zeilenNr, int spaltenNr, Set<Integer> alternativeWerte) {
			this.zeilenNr = zeilenNr;
			this.spaltenNr = spaltenNr;
			this.alternativeWerte = alternativeWerte;
		}
	}
	
	/**
	 * Tiefensuche. Bei einem Zug wird jeweils ein eingeschränkter möglicher Wert
	 * ausgewählt (Definition s. Interface von Helfer).
	 */
	@Override
	public Spielfeld loese(Spielfeld raetsel) {
		if (raetsel == null) return null;
		// Initialisierung
		SpielfeldHelfer helfer = new SpielfeldHelferImpl();
		Spielfeld loesung = helfer.kopiere(raetsel);
		Stack<Zug> zugStapel = new Stack<Zug>();
		int zeilenNr = 8, spaltenNr = 8;
		boolean erfolg = true; // Wird auf false gesetzt, wenn der Suchraum erschöpft ist
		
		// Die Lösung wird in einer zyklischen Schleife über die Zellen des Spielfelds gesucht
		while (erfolg && !helfer.loesungVollstaendig(loesung)) {
			// Nächste Zelle ermitteln
			spaltenNr = (spaltenNr + 1) % 9;
			if (spaltenNr == 0) {
				zeilenNr = (zeilenNr + 1) % 9;
			}
			int wert = loesung.wert(zeilenNr, spaltenNr);
			if (wert == 0) { // Belegte Felder überspringen
				Set<Integer> eingeschraenkteMoeglicheWerte = helfer.eingeschraenkteMoeglicheWerte(loesung, zeilenNr, spaltenNr);
				if (eingeschraenkteMoeglicheWerte.isEmpty()) {
					erfolg = this.neuerVersuch(loesung, zugStapel); // Backtracking
				} else {
					this.naechsterZug(loesung, zugStapel, new Zug(zeilenNr, spaltenNr, eingeschraenkteMoeglicheWerte)); // Nächster Zug
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
	 * @param loesung die bisher konstruierte Teillösung
	 * @param zugStapel
	 * @return true, falls ein neuer Zug gefunden werden konnte, false sonst
	 */
	private boolean neuerVersuch(Spielfeld loesung, Stack<Zug> zugStapel) {
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
				naechsterZug(loesung, zugStapel, zug);
				return true;
			}
		}
		return false;			
	}
	
	/**
	 * Führt den nächsten Zug aus. 
	 * Vorbedingungen:
	 * - Zeile, Spalte und alternative Werte des Zugs sind bereits fixiert.
	 * - Die Menge der alternativen Werte ist nicht leer.
	 * Nachbedingung:
	 * - Der Zug ist mit einem der alternativen Werte ausgeführt worden.
	 */
	/**
	 * @param loesung die bisher konstruierte Teillösung
	 * @param zugStapel der Stapel der bisher ausgeführten Züge
	 * @param zug der auszuführende Zug (bis auf den zu setzenden Wert bereits fixiert)
	 */
	private void naechsterZug(Spielfeld loesung, Stack<Zug> zugStapel, Zug zug) {
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

}
