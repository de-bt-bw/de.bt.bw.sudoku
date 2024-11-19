/**
 * 
 */
package de.bt.bw.sudoku;

/**
 * Verbindet Sicht und Modell. Modellbezogene Kommandos werden ausgeführt.
 * Die Sicht wird dann als Beobachter des Modells aktualisiert (nicht
 * von der Kontrolle aus).
 */
public class Kontrolle {
    private Modell modell;
    private Sicht sicht; // Redundant, solange es keine sichtbezogenen Kommandos gibt
    
    /**
     * Konstruktor zur Initialisierung des Spiels. Zu diesem Zweck werden 
     * Sicht und Modell instantiiert und gekoppelt. Nach Ausführung des
     * Konstruktors ist das Spielbrett angezeigt, und das Spiel kann beginnen.
     */
    public Kontrolle(Modell modell) {
        this.modell = modell;
        this.sicht = new Sicht(this, modell);
    }
    
    /**
     * Behandelt ein Kommando.
     * 
     * @param kommando das Kommando
     */
    public void behandleKommando(Kommando kommando) {
    	if (kommando instanceof KommandoSetzen) {
    		KommandoSetzen kommandoSetzen = (KommandoSetzen) kommando;
    		int zeilenNr = kommandoSetzen.gibZeilenNr();
    		int spaltenNr = kommandoSetzen.gibSpaltenNr();
    		int wert = kommandoSetzen.gibWert();
   			try {
				modell.setze(zeilenNr, spaltenNr, wert);
			} catch (FalscherWert e) {
				// Testausgabe
				System.out.println("Der Wert " + wert + " kann in Zeile " + zeilenNr + " und Spalte " + spaltenNr + " nicht gesetzt werden");
			}
    	}
    }
}
