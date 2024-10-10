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
		spielfeld = new int[10][10];
		/* 
		 * Die Zeilen und Spalten mit den Indizes 0 werden nicht benutzt,
		 * damit die Schnittstellenindizes unverändert verwendet werden können.
		 */
	}

	@Override
	public boolean setze(int zeile, int spalte, int wert) throws FalscheZahl {
		if (falscheZahl(zeile, 1, 9)) throw new FalscheZahl(zeile, 1, 9);
		if (falscheZahl(spalte, 1, 9)) throw new FalscheZahl(spalte, 1, 9);
		if (falscheZahl(wert, 1, 9)) throw new FalscheZahl(wert, 1, 9);
		if (!gesetzt(zeile, spalte) && moeglicheWerte(zeile, spalte).contains(wert)) {
			spielfeld[zeile][spalte] = wert;
			return true;
		}
		return false;
	}

	@Override
	public int wert(int zeile, int spalte) throws FalscheZahl {
		if (falscheZahl(zeile, 1, 9)) throw new FalscheZahl(zeile, 1, 9);
		if (falscheZahl(spalte, 1, 9)) throw new FalscheZahl(spalte, 1, 9);
		return spielfeld[zeile][spalte];
	}

	@Override
	public Set<Integer> moeglicheWerte(int zeile, int spalte) throws FalscheZahl {
		if (falscheZahl(zeile, 1, 9)) throw new FalscheZahl(zeile, 1, 9);
		if (falscheZahl(spalte, 1, 9)) throw new FalscheZahl(spalte, 1, 9);		 
		// Liefere null zurück, weil der Wert bereits feststeht
		if (gesetzt(zeile, spalte)) return null;
		Set<Integer> werte = new HashSet<Integer>(Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
		int z, s; // Laufvariablen für Zeilen und Spalten
		// Entferne Werte aus der gleichen Zeile
		for (s = 1; s <= 9; s++) 
			if (gesetzt(zeile, s)) werte.remove(wert(zeile, s));
		// Entferne Werte aus der gleichen Spalte
		for (z = 1; z <= 9; z++)
			if (gesetzt(z, spalte)) werte.remove(wert(z, spalte));
		// Entferne Werte aus dem gleichen Block
		int minZeile = zeile - ((zeile - 1) % 3);
		int minSpalte = spalte - ((spalte - 1) % 3);
		for (z = minZeile; z <= minZeile + 2; z++)
			for (s = minSpalte; s <= minSpalte + 2; s++)
				if (gesetzt(z, s)) werte.remove(wert(z, s));
		return werte;
	}

	@Override
	public boolean gesetzt(int zeile, int spalte) throws FalscheZahl {
		if (falscheZahl(zeile, 1, 9)) throw new FalscheZahl(zeile, 1, 9);
		if (falscheZahl(spalte, 1, 9)) throw new FalscheZahl(spalte, 1, 9);
		return spielfeld[zeile][spalte] > 0;
	}

}
