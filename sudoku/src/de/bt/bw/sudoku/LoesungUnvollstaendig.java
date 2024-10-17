/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * Eine Teillösung kann nicht vervollständigt werden. 
 */
public class LoesungUnvollstaendig extends Exception {
	/**
	 * Diese Ausnahme wird ausgelöst, wenn es nicht gelingt,
	 * eine Teillösung zu vervollständigen.
	 * 
	 * @param meldung Fehlermeldung
	 */
	public LoesungUnvollstaendig(String meldung) {
		super(meldung);
	}
}
