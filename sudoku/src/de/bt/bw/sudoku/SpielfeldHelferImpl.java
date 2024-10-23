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
		boolean erfolg = true;
		Suche:
		for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++)
			for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++)
				if (spielfeld1.wert(zeilenNr, spaltenNr) != spielfeld2.wert(zeilenNr, spaltenNr)) {
					erfolg = false;
					break Suche;
				}
		return erfolg;
	}

	@Override
	public Spielfeld erzeugeSpielfeld(int[][] spielfeldArray)
			throws FalscheZahl, FalscherWert, FalscheZeilenanzahl, FalscheZeilenlaenge {
		// Array-Dimensionen überprüfen
		if (spielfeldArray.length != 9)
			throw new FalscheZeilenanzahl(spielfeldArray.length);
		int zeilenNr, spaltenNr;
		for (zeilenNr = 0; zeilenNr < 9; zeilenNr++)
			if (spielfeldArray[zeilenNr].length != 9)
				throw new FalscheZeilenlaenge(zeilenNr, spielfeldArray[zeilenNr].length);
		// Spielfeld erzeugen und Werte setzen
		Spielfeld spielfeld = new SpielfeldImpl();
		for (zeilenNr = 0; zeilenNr < 9; zeilenNr++) {
			for (spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
				int wert = spielfeldArray[zeilenNr][spaltenNr];
				if (wert != 0)
					spielfeld.setze(zeilenNr, spaltenNr, wert);
			}
		}
		return spielfeld;
	}

	@Override
	public Spielfeld kopiere(Spielfeld spielfeld) {
		Spielfeld kopie = new SpielfeldImpl();
		for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++)
			for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++)
				try {
					int wert = spielfeld.wert(zeilenNr, spaltenNr);
					if (wert != 0) 
						kopie.setze(zeilenNr, spaltenNr, wert);
				} catch (Exception e) {
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
