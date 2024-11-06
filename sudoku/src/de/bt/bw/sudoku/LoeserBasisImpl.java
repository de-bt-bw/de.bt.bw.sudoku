/**
 * 
 */
package de.bt.bw.sudoku;

import java.util.Iterator;
import java.util.Set;


/**
 * Basisimplementierung eines Lösers. Solange Werte eindeutig bestimmbar sind,
 * wird die Lösung vervollständigt. Es wird nicht garantiert, dass eine Lösung
 * gefunden wird, falls sie existiert.
 */
public class LoeserBasisImpl implements Loeser {

	
	/**
	 * Basisimplementierung eines Lösers. Solange Werte eindeutig bestimmt werden können,
	 * wird die Lösung vervollständigt. Die Lösungssuche bricht ab, wenn kein eindeutig
	 * bestimmter Wert oder eine unbelegte Zelle mit einer leeren Menge möglicher
	 * Werte gefunden wird.
	 * Ein möglicher Wert ist eindeutig, wenn er
	 * - der einzige mögliche Wert für eine Zelle ist oder
	 * - innerhalb einer Zeile, einer Spalte oder eines Blocks genau eine 
	 *   Zelle existiert, in der dieser Wert möglich ist
	 */
	@Override
	public Spielfeld loese(Spielfeld raetsel) {
		// Kopiere Rätsel in Lösung
		SpielfeldHelfer helfer = new SpielfeldHelferImpl();
		Spielfeld loesung = helfer.kopiere(raetsel);
		// Konstruiere Lösung, bis kein Wert mehr gesetzt werden kann
		// oder eine ungesetzte Zelle gefunden wird, für die kein Wert mehr möglich ist
		boolean wertGesetzt; // Ist in einem Durchlauf durch die Matrix ein Wert gesetzt worden?
		// Setze Werte, solange sie eindeutig sind
		do {
			wertGesetzt = false;
			for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++)
				for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
					// 1. Fall: Wert ist schon gesetzt => überspringen
					int wert = loesung.wert(zeilenNr, spaltenNr);
					if (wert != 0)
						continue;
					// 2. Fall: Wertemenge leer => null zurückliefern
					Set<Integer> eingeschraenkteMoeglicheWerte = helfer.eingeschraenkteMoeglicheWerte(loesung, zeilenNr, spaltenNr);
					if (eingeschraenkteMoeglicheWerte.isEmpty())
						return null;
					// 3. Fall: Wertemenge einelementig => eindeutig bestimmten Wert setzen
					if (eingeschraenkteMoeglicheWerte.size() == 1) {
						Iterator<Integer> iterator = eingeschraenkteMoeglicheWerte.iterator();
						wert = iterator.next(); // Der einzige Wert in der Menge
						try {
							loesung.setze(zeilenNr, spaltenNr, wert);
						} catch (FalscherWert e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						wertGesetzt = true;
					}
					// Bei nicht eindeutigem Wert nichts tun
				}
		} while (wertGesetzt);
		// Überprüfe, ob die Lösung vollständig ist
		if (!helfer.loesungVollstaendig(loesung))
			return null;
		return loesung;
	}
}
