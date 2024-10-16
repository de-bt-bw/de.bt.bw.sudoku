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
	public Spielfeld lies(String dateiName)
			throws FileNotFoundException, NumberFormatException, 
			FalscheZeilenanzahl, FalscheZeilenlaenge, FalscheZahl, FalscherZustand, FalscherWert {
		Scanner scanner = new Scanner(new File(dateiName));
		Spielfeld spielfeld = new SpielfeldImpl();
		try {
			for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++) {
				if (!scanner.hasNextLine())
					throw new FalscheZeilenanzahl(zeilenNr);
				String zeile = scanner.nextLine();
				if (zeile.trim().isEmpty())
					throw new FalscheZeilenlaenge(zeilenNr, 0);
				String[] zahlen = zeile.split("\\s+"); // Zahlen durch nichtleere Folgen von Leerzeichen getrennt
				if (zahlen.length != 9)
					throw new FalscheZeilenlaenge(zeilenNr, zahlen.length);
				for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
					int wert = Integer.parseInt(zahlen[spaltenNr]); 
					if (wert != 0) // Kein Setzen, falls Wert = 0
						spielfeld.setze(zeilenNr, spaltenNr, wert);
				}
			}
			if (scanner.hasNextLine()) // Mindestens 10 Zeilen
				throw new FalscheZeilenanzahl(10);
		} finally {
			scanner.close();
		}				
		return spielfeld;
	}

}
