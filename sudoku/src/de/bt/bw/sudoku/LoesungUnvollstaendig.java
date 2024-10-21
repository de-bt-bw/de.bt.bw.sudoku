/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * Eine Teillösung kann nicht vervollständigt werden. 
 */
public class LoesungUnvollstaendig extends Exception {
	public Spielfeld spielfeld;
	
	/**
	 * Diese Ausnahme wird ausgelöst, wenn es nicht gelingt,
	 * eine Teillösung zu vervollständigen.
	 * 
	 * @param spielfeld die bisher gefundene Teillösung
	 */
	public LoesungUnvollstaendig(Spielfeld spielfeld) {
		super("Lösung unvollständig");
		this.spielfeld = spielfeld;
	}
}
