/**
 * 
 */
package de.bt.bw.sudoku;

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
		for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++)
			for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++)
				if (!spielfeld.belegt(zeilenNr, spaltenNr))
					return false;
		return true;
	}

}
