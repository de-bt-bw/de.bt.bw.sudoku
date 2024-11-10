/**
 * 
 */
package de.bt.bw.sudoku;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * Implementierung des Spielfeld-Helfers
 * 
 */
public class SpielfeldHelferImpl implements SpielfeldHelfer {

	@Override
	public boolean gleich(Spielfeld spielfeld1, Spielfeld spielfeld2) {
		if (spielfeld1 == spielfeld2)
			return true;
		if (spielfeld1 == null || spielfeld2 == null)
			return false;
		for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++)
			for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++)
				if (spielfeld1.wert(zeilenNr, spaltenNr) != spielfeld2.wert(zeilenNr, spaltenNr)) 
					return false;
		return true;
	}

	@Override
	public Spielfeld erzeugeSpielfeld(int[][] spielfeldArray) {
		if (spielfeldArray == null || spielfeldArray.length != 9)
			return null;
		Spielfeld spielfeld = new SpielfeldImpl();
		for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++) {
			if (spielfeldArray[zeilenNr] == null || spielfeldArray[zeilenNr].length != 9)
				return null;
			for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
				try {
					spielfeld.setze(zeilenNr, spaltenNr, spielfeldArray[zeilenNr][spaltenNr]);
				} catch (FalscherWert falscherWert) {
					return null;
				}
			}
		}
		return spielfeld;
	}

	@Override
	public Spielfeld kopiere(Spielfeld spielfeld) {
		if (spielfeld == null) 
			return null;
		Spielfeld kopie = new SpielfeldImpl();
		for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++)
			for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++)
				try {
					kopie.setze(zeilenNr, spaltenNr, spielfeld.wert(zeilenNr, spaltenNr));
				} catch (FalscherWert falscherWert) {
				}
		return kopie;
	}

	@Override
	public boolean loesungVollstaendig(Spielfeld spielfeld) {
		if (spielfeld == null)
			return false;
		for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++)
			for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++)
				if (!spielfeld.belegt(zeilenNr, spaltenNr))
					return false;
		return true;
	}

	@Override
	public Set<Integer> eingeschraenkteMoeglicheWerte(Spielfeld spielfeld, int zeilenNr, int spaltenNr) {
		if (spielfeld == null)
			return null;
		Set<Integer> eingeschraenkteMoeglicheWerte = spielfeld.moeglicheWerte(zeilenNr, spaltenNr);
		Iterator<Integer> iterator = eingeschraenkteMoeglicheWerte.iterator();
		while (iterator.hasNext()) {
			int wert = iterator.next();
			if (eindeutigInZeile(spielfeld, zeilenNr, wert) ||
				eindeutigInSpalte(spielfeld, spaltenNr, wert) ||
				eindeutigInBlock(spielfeld, zeilenNr, spaltenNr, wert)) {
				eingeschraenkteMoeglicheWerte = new HashSet<Integer>(Set.of(wert));
				break;
			}
		}
		return eingeschraenkteMoeglicheWerte;
	}
	
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

}
