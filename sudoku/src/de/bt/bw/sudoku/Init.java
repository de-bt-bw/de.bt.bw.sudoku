/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * 
 */
public class Init {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Modell modell = new ModellImpl();
        Kontrolle kontrolle = new Kontrolle(modell);
	}

}
