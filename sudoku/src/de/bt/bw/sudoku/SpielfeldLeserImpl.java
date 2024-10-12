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
	public Spielfeld lies(String name)
			throws FileNotFoundException, NumberFormatException, FalscheZeilenanzahl, FalscheZeilenlaenge, FalscheZahl {
		Scanner scanner = new Scanner(new File(name));
		Spielfeld spielfeld = new SpielfeldImpl();
		int zeilenIndex = 0;
		while (scanner.hasNextLine()) {
			String zeile = scanner.nextLine();
			if (!zeile.trim().isEmpty()) { // Leerzeilen überspringen
				zeilenIndex++;
				if (zeilenIndex > 9) {
					scanner.close();
					throw new FalscheZeilenanzahl(zeilenIndex);
				}					
				String[] zahlen = zeile.split("\\s+"); // Zahlen durch nichtleere Folgen von Leerzeichen getrennt
				int zeilenLaenge = zahlen.length;
				if (zeilenLaenge != 9) {
					scanner.close();
					throw new FalscheZeilenlaenge(zeilenIndex, zeilenLaenge);
				}					
				for (int spaltenindex = 1; spaltenindex <= 9; spaltenindex++) {
					int wert = Integer.parseInt(zahlen[spaltenindex-1]); // Achtung: Arrayindizes beginnen bei 0
					if (0 <= wert && wert <= 9) 
						spielfeld.setze(zeilenIndex, spaltenindex, wert);
					else {
						scanner.close();
						throw new FalscheZahl(wert, 0, 9);
					}						
				}	
			}			
		}
		scanner.close();
		if (zeilenIndex < 9)
			throw new FalscheZeilenanzahl(zeilenIndex);		
		return spielfeld;
	}

}
