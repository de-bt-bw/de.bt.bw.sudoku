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
		int zeilenNr = 0;
		try {	
			while (scanner.hasNextLine()) {
				String zeile = scanner.nextLine();
				if (!zeile.trim().isEmpty()) { // Leerzeilen überspringen
					if (zeilenNr > 8) 
						throw new FalscheZeilenanzahl(zeilenNr + 1); // +1, weil Zeilennummern bei 0 beginnen					
					String[] zahlen = zeile.split("\\s+"); // Zahlen durch nichtleere Folgen von Leerzeichen getrennt
					int zeilenLaenge = zahlen.length;
					if (zeilenLaenge != 9) 
						throw new FalscheZeilenlaenge(zeilenNr, zeilenLaenge);
					for (int spaltenNr = 0; spaltenNr <= 8; spaltenNr++) {
						int wert = Integer.parseInt(zahlen[spaltenNr]); 
						if (wert != 0) 
							// Kein Setzen, falls Wert = 0
							spielfeld.setze(zeilenNr, spaltenNr, wert);
					}						
				}
				zeilenNr++;
			}
			if (zeilenNr < 8)
				throw new FalscheZeilenanzahl(zeilenNr + 1); // +1, weil Zeilennummern bei 0 beginnen
		}
		finally {
			scanner.close();
		}				
		return spielfeld;
	}

}
