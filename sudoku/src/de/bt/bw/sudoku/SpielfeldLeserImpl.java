/**
 * 
 */
package de.bt.bw.sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * Implementierung der Schnittstelle SpielfeldLeser
 * 
 */
public class SpielfeldLeserImpl implements SpielfeldLeser {

	@Override
	public Spielfeld lies(String dateiName) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(dateiName));
		Spielfeld spielfeld = new SpielfeldImpl();
		try {
			for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++) {
				String zeile = scanner.nextLine();
				String[] zahlen = zeile.split("\\s+"); // Zahlen durch nichtleere Folgen von Leerzeichen getrennt
				for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) 
					spielfeld.setze(zeilenNr, spaltenNr, Integer.parseInt(zahlen[spaltenNr]));	
			}
		} catch (Exception e) {
			spielfeld = null;
		}
		scanner.close();
		return spielfeld;		
	}

}
