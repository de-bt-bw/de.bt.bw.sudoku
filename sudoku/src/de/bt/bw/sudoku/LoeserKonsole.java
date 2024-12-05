/**
 * 
 */
package de.bt.bw.sudoku;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Konsole zum Aufruf eines Lösers. Das Rätsel wird aus einer Datei gelesen,
 * und die Lösung wird in eine Datei geschrieben.
 * 
 */
public class LoeserKonsole {

	/**
	 * Dialog zum Festlegen und Einlesen der Rätseldatei, der Auswahl und dem Aufruf des
	 * Lösers und dem Festlegen und Schreiben der Lösungsdatei 
	 * 
	 * @param args nicht benutzt
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Lösen eines Sudoku-Rätsels");
		
		// Rätsel einlesen
		System.out.println("Rätseldatei: ");
		String raetselDatei = scanner.nextLine();
		SpielfeldLeser spielfeldLeser = new SpielfeldLeserImpl();
		Spielfeld raetsel;
		try {
			raetsel = spielfeldLeser.lies(raetselDatei);
		} catch (FileNotFoundException e) {
			System.out.println("Datei " + raetselDatei + " nicht gefunden");
			scanner.close();
			return;
		}
		if (raetsel == null) {
			System.out.println("Rätsel falsch");
			scanner.close();
			return;
		}
		
		// Löser festlegen
		System.out.println("Auswahl des Lösers:");
		System.out.println("(1) Basislöser");
		System.out.println("(2) Universallöser");
		System.out.println("(3) Tiefensuche");
		System.out.println("(4) Optimierte Tiefensuche");
		System.out.println("Geben Sie eine Zahl von 1 bis 4 ein: ");
		int nummer = scanner.nextInt();
		scanner.nextLine(); // Zeile abschließen
		Loeser loeser;
		switch (nummer) {
			case 1 -> loeser = new LoeserBasisImpl();
			case 2 -> loeser = new LoeserUniversalImpl();
			case 3 -> loeser = new LoeserTiefensucheImpl();
			case 4 -> loeser = new LoeserTiefensucheOptImpl();
			default -> {System.out.println("Falsche Zahl"); scanner.close(); return;}
		}
		
		// Löser aufrufen
		Spielfeld loesung = loeser.loese(raetsel);
		if (loesung == null) {
			System.out.println("Das Rätsel konnte vom Löser nicht gelöst werden");
			scanner.close();
			return;
		} else {
			System.out.println("Der Löser hat das Rätsel gelöst");
		}
		
		// Ausgabedatei schreiben
		System.out.println("Lösungsdatei: ");
		String loesungsDatei = scanner.nextLine();
		SpielfeldSchreiber spielfeldSchreiber = new SpielfeldSchreiberImpl();
		try {
			spielfeldSchreiber.schreib(loesung, loesungsDatei);
		} catch (FileNotFoundException e) {
			System.out.println("Die Lösung konnte nicht in die Datei " + loesungsDatei + " geschrieben werden");
		}
		System.out.println("Die Lösung wurde in die Datei " + loesungsDatei + " geschrieben");
		scanner.close();
	}

}
