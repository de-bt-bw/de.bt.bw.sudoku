package de.bt.bw.sudoku;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Set;

/**
 * Implementiert die Modellschnittstelle.
 * Dazu wird Komposition und Delegation statt Vererbung benutzt.
 */
public class ModellImpl implements Modell {	
	
	private Spielfeld spielfeld;
	private SpielfeldLeser spielfeldLeser;
	private SpielfeldSchreiber spielfeldSchreiber;
	
    /**
     * Konstruktor für das Modell, stellt den initialen Spielstand her.
     */
    public ModellImpl()
    {
        spielfeld = new SpielfeldImpl();
        spielfeldLeser = new SpielfeldLeserImpl();
        spielfeldSchreiber = new SpielfeldSchreiberImpl();
    }
    
	@Override
	public int wert(int zeilenNr, int spaltenNr) {
		return spielfeld.wert(zeilenNr, spaltenNr);
	}
	
	@Override
	public void setze(int zeilenNr, int spaltenNr, int wert) throws FalscherWert {
		spielfeld.setze(zeilenNr, spaltenNr, wert);
		this.benachrichtige();
	}

	@Override
	public Set<Integer> moeglicheWerte(int zeilenNr, int spaltenNr) {
		return spielfeld.moeglicheWerte(zeilenNr, spaltenNr);
	}

	@Override
	public boolean belegt(int zeilenNr, int spaltenNr) {
		return spielfeld.belegt(zeilenNr, spaltenNr);
	}

	@Override
	public boolean laden(String dateiName) {
		Spielfeld neuesSpielfeld;
		try {
			neuesSpielfeld = spielfeldLeser.lies(dateiName);
		} catch (FileNotFoundException e) {
			neuesSpielfeld = null;
		}
		if (neuesSpielfeld == null) {
			return false;
		} else {
			spielfeld = neuesSpielfeld;
			this.benachrichtige();
			return true;
		}
	}

	@Override
	public boolean speichern(String dateiName) {
		try {
			spielfeldSchreiber.schreib(spielfeld, dateiName);
		} catch (FileNotFoundException e) {
			return false;
		}
		return true;
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
