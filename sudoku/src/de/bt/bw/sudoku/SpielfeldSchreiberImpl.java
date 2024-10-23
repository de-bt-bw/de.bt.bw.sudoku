/**
 * 
 */
package de.bt.bw.sudoku;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

/**
 * 
 */
public class SpielfeldSchreiberImpl implements SpielfeldSchreiber {

	@Override
	public void schreib(Spielfeld spielfeld, String dateiname) throws IOException {
		PrintWriter schreiber = new PrintWriter(new File(dateiname));
		for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++) {
			for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
				schreiber.print(spielfeld.wert(zeilenNr, spaltenNr));
				if (spaltenNr < 8) schreiber.print(' ');
			}
			if (zeilenNr < 8) 
				schreiber.println();
		}
		schreiber.flush();
		schreiber.close();
	}

}
