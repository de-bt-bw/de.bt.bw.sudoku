/**
 * 
 */
package de.bt.bw.sudoku;

import java.io.FileNotFoundException;

/**
 * 
 */
public class LoeserHelferImpl implements LoeserHelfer {

	@Override
	public Spielfeld loese(Loeser loeser, int[][] raetselArray) {
		SpielfeldHelfer spielfeldHelfer = new SpielfeldHelferImpl();
		Spielfeld spielfeld = spielfeldHelfer.erzeugeSpielfeld(raetselArray);
		return loeser.loese(spielfeld);
	}

	@Override
	public Spielfeld loese(Loeser loeser, String dateiName) throws FileNotFoundException {
		SpielfeldLeser spielfeldLeser = new SpielfeldLeserImpl();
		Spielfeld spielfeld = spielfeldLeser.lies(dateiName);
		return loeser.loese(spielfeld);
	}

}
