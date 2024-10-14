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
				try {
					if (spielfeld1.wert(zeilenNr, spaltenNr) != spielfeld2.wert(zeilenNr, spaltenNr)) {
						erfolg = false;
						break Suche;
					}
				} catch (FalscheZahl e) {
				}
		return erfolg;
	}

	@Override
	public Spielfeld erzeugeSpielfeld(int[][] spielfeldArray)
			throws FalscheZahl, FalscherWert, FalscheZeilenanzahl, FalscheZeilenlaenge {
		if (spielfeldArray == null)
			return null;
		Spielfeld spielfeld = new SpielfeldImpl();
		int zeilenanzahl = spielfeldArray.length, zeilenlaenge;
		if (zeilenanzahl != 9)
			throw new FalscheZeilenanzahl(zeilenanzahl);
		for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++) {
			zeilenlaenge = spielfeldArray[zeilenNr].length;
			if (zeilenlaenge != 9)
				throw new FalscheZeilenlaenge(zeilenNr, zeilenlaenge);
			for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++)
				try {
					int wert = spielfeldArray[zeilenNr][spaltenNr];
					if (wert != 0)
						spielfeld.setze(zeilenNr, spaltenNr, wert);
				} catch (FalscherZustand e) {
				}
		}
		return spielfeld;
	}

}
