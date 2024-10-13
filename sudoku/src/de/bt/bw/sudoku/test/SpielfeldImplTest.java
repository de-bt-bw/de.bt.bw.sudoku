/**
 * 
 */
package de.bt.bw.sudoku.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.bt.bw.sudoku.*;

/**
 * 
 */
class SpielfeldImplTest {

	/**
	 * Lieblings-Sudoku 83/1 
	 */
	@Test
	void testeSetzen() {
		boolean erfolg;
		Spielfeld spielfeld = new SpielfeldImpl();
		while (true) {
			try {
				erfolg = spielfeld.setze(1, 4, 8); if (!erfolg) break;
				erfolg = spielfeld.setze(1, 7, 7); if (!erfolg) break;
				erfolg = spielfeld.setze(1, 9, 5); if (!erfolg) break;
				erfolg = spielfeld.setze(2, 2, 6); if (!erfolg) break;
				erfolg = spielfeld.setze(2, 4, 7); if (!erfolg) break;
				erfolg = spielfeld.setze(2, 5, 1); if (!erfolg) break;
				erfolg = spielfeld.setze(2, 6, 2); if (!erfolg) break;
				erfolg = spielfeld.setze(2, 7, 9); if (!erfolg) break;
				erfolg = spielfeld.setze(3, 2, 4); if (!erfolg) break;
				erfolg = spielfeld.setze(3, 6, 3); if (!erfolg) break;
				erfolg = spielfeld.setze(3, 9, 1); if (!erfolg) break;
				erfolg = spielfeld.setze(4, 5, 3); if (!erfolg) break;
				erfolg = spielfeld.setze(4, 6, 9); if (!erfolg) break;
				erfolg = spielfeld.setze(4, 7, 6); if (!erfolg) break;
				erfolg = spielfeld.setze(5, 1, 3); if (!erfolg) break;
				erfolg = spielfeld.setze(5, 4, 6); if (!erfolg) break;
				erfolg = spielfeld.setze(5, 6, 4); if (!erfolg) break;
				erfolg = spielfeld.setze(5, 9, 7); if (!erfolg) break;
				erfolg = spielfeld.setze(6, 3, 4); if (!erfolg) break;
				erfolg = spielfeld.setze(6, 4, 1); if (!erfolg) break;
				erfolg = spielfeld.setze(6, 5, 7); if (!erfolg) break;
				erfolg = spielfeld.setze(7, 1, 2); if (!erfolg) break;
				erfolg = spielfeld.setze(7, 4, 3); if (!erfolg) break;
				erfolg = spielfeld.setze(7, 8, 9); if (!erfolg) break;
				erfolg = spielfeld.setze(8, 3, 3); if (!erfolg) break;
				erfolg = spielfeld.setze(8, 4, 4); if (!erfolg) break;
				erfolg = spielfeld.setze(8, 5, 6); if (!erfolg) break;
				erfolg = spielfeld.setze(8, 6, 1); if (!erfolg) break;
				erfolg = spielfeld.setze(8, 8, 8); if (!erfolg) break;
				erfolg = spielfeld.setze(9, 1, 4); if (!erfolg) break;
				erfolg = spielfeld.setze(9, 3, 6); if (!erfolg) break;
				erfolg = spielfeld.setze(9, 6, 5); break;						
			} catch (FalscheZahl fz) {
				erfolg = false; break;
			}
		}
		assertTrue(erfolg);
	}

}
