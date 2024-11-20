/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * Verbindet Sicht und Modell. Modellbezogene Kommandos werden ausgeführt.
 * Die Sicht wird dann als Beobachter des Modells aktualisiert (nicht
 * von der Kontrolle aus).
 */
public class KontrolleImpl implements Kontrolle {
    private Modell modell;
    private Sicht sicht; // Redundant, solange es keine sichtbezogenen Kommandos gibt
    
    /**
     * Konstruktor zur Initialisierung des Spiels. Zu diesem Zweck werden 
     * Sicht und Modell instantiiert und gekoppelt. Nach Ausführung des
     * Konstruktors ist das Spielbrett angezeigt, und das Spiel kann beginnen.
     */
    public KontrolleImpl(Modell modell) {
        this.modell = modell;
        this.sicht = new SichtImpl(this, modell);
        sicht.aktualisiere();
    }

	@Override
	public boolean behandleKommando(Kommando kommando) {
    	if (kommando instanceof KommandoSetzen) {
		KommandoSetzen kommandoSetzen = (KommandoSetzen) kommando;
		int zeilenNr = kommandoSetzen.gibZeilenNr();
		int spaltenNr = kommandoSetzen.gibSpaltenNr();
		int wert = kommandoSetzen.gibWert();
		int alterWert = modell.wert(zeilenNr, spaltenNr);
			try {
			modell.setze(zeilenNr, spaltenNr, wert);
		} catch (FalscherWert e) {
			// Alten Wert nochmals setzen, um Aktualisierung der Sicht zu erzwingen
			try {
				modell.setze(zeilenNr, spaltenNr, alterWert);
			} catch (FalscherWert e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
			// Testausgabe
			// System.out.println("Der Wert " + wert + " kann in Zeile " + zeilenNr + " und Spalte " + spaltenNr + " nicht gesetzt werden");
		}

	}
	return true;
	}
}
