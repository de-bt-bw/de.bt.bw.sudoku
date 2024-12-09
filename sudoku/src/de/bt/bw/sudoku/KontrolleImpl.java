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
    		return this.behandleSetzen((KommandoSetzen) kommando);
    	} else if (kommando instanceof KommandoLaden) {
    		return this.behandleLaden((KommandoLaden) kommando);
    	} else if (kommando instanceof KommandoSpeichern) {
    		return this.behandleSpeichern((KommandoSpeichern) kommando);
    	} else if (kommando instanceof KommandoBeenden) {
    		return this.behandleBeenden((KommandoBeenden) kommando);
    	}
    	return true; // Code nicht erreichbar, wenn alle Kommandotypen behandelt werden
	}
	
	private boolean behandleLaden(KommandoLaden kommando) {
		String dateiName = kommando.gibDateiName();
		boolean erfolg = modell.laden(dateiName);
		return erfolg;
	}
	
	private boolean behandleSpeichern(KommandoSpeichern kommando) {
		String dateiName = kommando.gibDateiName();
		boolean erfolg = modell.speichern(dateiName);
		return erfolg;
	}
	
	private boolean behandleSetzen(KommandoSetzen kommando) {
		int zeilenNr = kommando.gibZeilenNr();
		int spaltenNr = kommando.gibSpaltenNr();
		int wert = kommando.gibWert();
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
		}
		return true;
	}
	
	private boolean behandleBeenden(KommandoBeenden kommando) {
		System.exit(0);
		return true; // Code nicht erreichbar, aber notwendig für Compiler
	}
}
