/**
 * 
 */
package de.bt.bw.sudoku;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Standardimplementierung des Spielfelds<br>
 * 
 */
public class SpielfeldImpl implements Spielfeld {
	
	private int[][] spielfeld = new int[9][9]; // Anfangs sind alle Zellen unbelegt.
	
	@Override
	public void setze(int zeilenNr, int spaltenNr, int wert) throws FalscherWert {
		if (wert == 0)
			spielfeld[zeilenNr][spaltenNr] = 0; // Keine Überprüfung nötig
		else {
			int alterWert = spielfeld[zeilenNr][spaltenNr];
			// Falls die Zelle belegt ist, wird der Wert auf 0 gesetzt,
			// damit der Aufruf von moeglicheWerte() korrekt funktioniert.
			if (alterWert != 0)
				spielfeld[zeilenNr][spaltenNr] = 0;
			if (moeglicheWerte(zeilenNr, spaltenNr).contains(wert))
				spielfeld[zeilenNr][spaltenNr] = wert; // Neuen Wert setzen
			else {
				spielfeld[zeilenNr][spaltenNr] = alterWert; // Alten Wert wiederherstellen
				throw new FalscherWert(zeilenNr, spaltenNr, wert); // Ausnahme auslösen
			}
		}
	}

	@Override
	public int wert(int zeilenNr, int spaltenNr) {
		return spielfeld[zeilenNr][spaltenNr];
	}

	@Override
	public Set<Integer> moeglicheWerte(int zeilenNr, int spaltenNr) {
		// Liefere leere Menge zurück, weil der Wert bereits feststeht
		if (belegt(zeilenNr, spaltenNr)) return new HashSet<Integer>();
		Set<Integer> werte = new HashSet<Integer>(Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
		int z, s; // Laufvariablen für Zeilen und Spalten
		// Entferne Werte aus der gleichen Zeile
		for (s = 0; s <= 8; s++) 
			if (belegt(zeilenNr, s)) werte.remove(wert(zeilenNr, s));
		// Entferne Werte aus der gleichen Spalte
		for (z = 0; z <= 8; z++)
			if (belegt(z, spaltenNr)) werte.remove(wert(z, spaltenNr));
		// Entferne Werte aus dem gleichen Block
		int minZeile = zeilenNr - zeilenNr % 3;
		int minSpalte = spaltenNr - spaltenNr % 3;
		for (z = minZeile; z <= minZeile + 2; z++)
			for (s = minSpalte; s <= minSpalte + 2; s++)
				if (belegt(z, s)) werte.remove(wert(z, s));
		return werte;
	}

	@Override
	public boolean belegt(int zeilenNr, int spaltenNr) {
		return spielfeld[zeilenNr][spaltenNr] > 0;
	}

}
