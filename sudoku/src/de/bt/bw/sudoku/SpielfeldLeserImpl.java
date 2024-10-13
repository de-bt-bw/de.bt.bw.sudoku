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
			throws FileNotFoundException, NumberFormatException, FalscheZeilenanzahl, FalscheZeilenlaenge, FalscheZahl {
		Scanner scanner = new Scanner(new File(dateiName));
		Spielfeld spielfeld = new SpielfeldImpl();
		int zeilenNr = 0;
		try {	
			while (scanner.hasNextLine()) {
				String zeile = scanner.nextLine();
				if (!zeile.trim().isEmpty()) { // Leerzeilen überspringen
					zeilenNr++;
					if (zeilenNr > 9) 
						throw new FalscheZeilenanzahl(zeilenNr);					
					String[] zahlen = zeile.split("\\s+"); // Zahlen durch nichtleere Folgen von Leerzeichen getrennt
					int zeilenLaenge = zahlen.length;
					if (zeilenLaenge != 9) 
						throw new FalscheZeilenlaenge(zeilenNr, zeilenLaenge);
					for (int spaltenNr = 1; spaltenNr <= 9; spaltenNr++) {
						int wert = Integer.parseInt(zahlen[spaltenNr-1]); // Achtung: Arrayindizes beginnen bei 0
						if (0 <= wert && wert <= 9) 
							spielfeld.setze(zeilenNr, spaltenNr, wert);
						else
							throw new FalscheZahl(wert, 0, 9);
					}						
				}	
			}
			if (zeilenNr < 9)
				throw new FalscheZeilenanzahl(zeilenNr);
		}
		finally {
			scanner.close();
		}				
		return spielfeld;
	}

}
