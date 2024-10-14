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
	
	private int[][] spielfeld;
	
	private boolean falscheZahl(int zahl, int min, int max) {
		return zahl < min || zahl > max;
	}
	
	/**
	 * Das Spielfeld wird initialisiert, indem die Zellen auf 0
	 * gesetzt werden.
	 */
	public SpielfeldImpl() {
		spielfeld = new int[9][9];
	}

	@Override
	public void setze(int zeilenNr, int spaltenNr, int wert) throws FalscheZahl, FalscherZustand, FalscherWert {
		if (falscheZahl(zeilenNr, 0, 8)) throw new FalscheZahl(zeilenNr, 0, 8);
		if (falscheZahl(spaltenNr, 0, 8)) throw new FalscheZahl(spaltenNr, 0, 8);
		if (falscheZahl(wert, 1, 9)) throw new FalscheZahl(wert, 1, 9);
		if (gesetzt(zeilenNr, spaltenNr)) throw new FalscherZustand(zeilenNr, spaltenNr);
		if (!moeglicheWerte(zeilenNr, spaltenNr).contains(wert)) throw new FalscherWert(zeilenNr, spaltenNr, wert); 
		spielfeld[zeilenNr][spaltenNr] = wert;
	}

	@Override
	public int wert(int zeilenNr, int spaltenNr) throws FalscheZahl {
		if (falscheZahl(zeilenNr, 0, 8)) throw new FalscheZahl(zeilenNr, 0, 8);
		if (falscheZahl(spaltenNr, 0, 8)) throw new FalscheZahl(spaltenNr, 0, 8);
		return spielfeld[zeilenNr][spaltenNr];
	}

	@Override
	public Set<Integer> moeglicheWerte(int zeilenNr, int spaltenNr) throws FalscheZahl {
		if (falscheZahl(zeilenNr, 0, 8)) throw new FalscheZahl(zeilenNr, 0, 8);
		if (falscheZahl(spaltenNr, 0, 8)) throw new FalscheZahl(spaltenNr, 0, 8);		 
		// Liefere null zurück, weil der Wert bereits feststeht
		if (gesetzt(zeilenNr, spaltenNr)) return null;
		Set<Integer> werte = new HashSet<Integer>(Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
		int z, s; // Laufvariablen für Zeilen und Spalten
		// Entferne Werte aus der gleichen Zeile
		for (s = 0; s <= 8; s++) 
			if (gesetzt(zeilenNr, s)) werte.remove(wert(zeilenNr, s));
		// Entferne Werte aus der gleichen Spalte
		for (z = 0; z <= 8; z++)
			if (gesetzt(z, spaltenNr)) werte.remove(wert(z, spaltenNr));
		// Entferne Werte aus dem gleichen Block
		int minZeile = zeilenNr - zeilenNr % 3;
		int minSpalte = spaltenNr - spaltenNr % 3;
		for (z = minZeile; z <= minZeile + 2; z++)
			for (s = minSpalte; s <= minSpalte + 2; s++)
				if (gesetzt(z, s)) werte.remove(wert(z, s));
		return werte;
	}

	@Override
	public boolean gesetzt(int zeilenNr, int spaltenNr) throws FalscheZahl {
		if (falscheZahl(zeilenNr, 0, 8)) throw new FalscheZahl(zeilenNr, 0, 8);
		if (falscheZahl(spaltenNr, 0, 8)) throw new FalscheZahl(spaltenNr, 0, 8);
		return spielfeld[zeilenNr][spaltenNr] > 0;
	}

}
