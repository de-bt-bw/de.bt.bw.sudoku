package de.bt.bw.sudoku;

import java.util.ArrayList;

/**
 * Ergänzt die Implementierung des Spielfelds um eine Implementierung der Subjekt-Schnittstelle
 */
public class ModellImpl extends SpielfeldImpl implements Modell {	
	
    /**
     * Konstruktor für das Modell, stellt den initialen Spielstand her.
     */
    public ModellImpl()
    {
        // Initialisierung des Modells
    }

	/**
	 * Ruft nach dem Setzen die Benachrichtigung auf
	 */
	@Override
	public void setze(int zeilenNr, int spaltenNr, int wert) throws FalscherWert {
		super.setze(zeilenNr, spaltenNr, wert);
		this.benachrichtige();
	}

    private ArrayList<Beobachter> alleBeobachter = new ArrayList<Beobachter>();
	
	@Override
	public void registriere(Beobachter beobachter) {
		alleBeobachter.add(beobachter);
	}

	@Override
	public void deregistriere(Beobachter beobachter) {
		alleBeobachter.add(beobachter);
	}

	@Override
	public void benachrichtige() {
		for (Beobachter beobachter : alleBeobachter) beobachter.aktualisiere();
	}

}
