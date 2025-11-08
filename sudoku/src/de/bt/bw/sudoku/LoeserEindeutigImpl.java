package de.bt.bw.sudoku;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Löser für eindeutige Rätsel (alle Rätsel aus den Rätselheften haben eine eindeutige Lösung).
 * Es ist nicht garantiert, dass eine Lösung gefunden wird. Dieser Löser implementiert jedoch
 * einige zusätzliche Regeln, mit denen der Lösungsraum eingeschränkt werden kann:<br>
 * 1. Cluster: Cluster sind Mengen von Feldern in einer Einheit (Zeile, Spalte oder Block).
 *    Die Kardinalität der Menge der möglichen Werte dieser Felder muss mit der Kardinalität
 *    des Clusters übereinstimmen. Dann können diese Werte aus allen weiteren Feldern derselben
 *    Einheit gestrichen werden.<br>
 * 2. Zeilen- oder Spalteneinschränkung: Bei der Zeileneinschränkung werden Blöcke horizontal untersucht.
 *    Für jeden Block, in dem ein Wert noch nicht gesetzt wird, wird ermittelt, in welchen Zeilen der 
 *    Wert vorkommen kann. Dazu werden die horizontal benachbarten Blöcke betrachtet. Ggf. wird der
 *    Wert aus allen Zeilen gestrichen, in denen er nicht vorkommen kann.<br> 
 */
public class LoeserEindeutigImpl implements Loeser {
	// In der ersten Version ist die Implementierung funktional äquivalent zur Basisimplementierung.
	// Sie wird später um die zusätzlichen Regeln erweitert.
	
	/**
	 * Verwaltet für jedes Feld die noch möglichen Werte. Die Menge ist leer, wenn das Feld bereits 
	   gesetzt wurde. Sonst enthält sie eine Teilmenge der Werte, die sich durch die Spielregeln
	   ergeben (Werte in einer Zeile, Spalte oder in einem Block müssen eindeutig sein). Bei der
	   Berechnung der Teilmenge werden Cluster sowie Zeilen- oder Spalteneinschränkungen
	   berücksichtigt.
	 */
	private Set<Integer>[][] moeglicheWerte; 
	
	private Spielfeld loesung;
	
	private SpielfeldHelfer helfer;
	
	/**
	 * Initialisiert die Lösung mit einer Kopie des Rätsels
	 * 
	 * @param raetsel das zu lösende Rätsel
	 */
	private void init(Spielfeld raetsel) {
		this.helfer = new SpielfeldHelferImpl();
		this.loesung = helfer.kopiere(raetsel);
		this.moeglicheWerte = new Set[9][9]; // Instantiierung nicht mit einem generischen Typ möglich
		for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++) {
			for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
				this.moeglicheWerte[zeilenNr][spaltenNr] = loesung.moeglicheWerte(zeilenNr, spaltenNr);
				// Hierbei werden Cluster sowie Zeilen- oder Spalteneinschränkungen zunächst nicht berücksichtigt
			}
		}
	}
	
	/**
	 * Setzt ein Feld auf einen Wert.
	 * Der Wert wird in der Lösung gesetzt, und das Array moeglicheWerte wird eingeschränkt
	 * (der Wert wird für alle Felder in der gleichen Zeile oder Spalte oder im gleichen Block entfernt).<br>
	 * Vorbedingungen (werden nicht überprüft):<br>
	 * - Das Feld ist bisher unbelegt.<br>
	 * - Der Wert ist > 0.<br>
	 * - Der Wert ist zulässig.<br>
	 * 
	 * @param zeilenNr Zeilennummer
	 * @param spaltenNr Spaltennummer
	 * @param wert Wert > 0
	 */
	private void setze(int zeilenNr, int spaltenNr, int wert) {
		try {
			this.loesung.setze(zeilenNr, spaltenNr, wert);
		} catch (FalscherWert e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.moeglicheWerte[zeilenNr][spaltenNr] = new HashSet<Integer>(); // Wert steht schon fest
		int z, s; // Laufvariablen für Zeilen und Spalten
		// Entferne Werte aus der gleichen Zeile
		for (s = 0; s < 9; s++) {
				this.moeglicheWerte[zeilenNr][s].remove(wert);	
		}
		// Entferne Werte aus der gleichen Spalte
		for (z = 0; z < 9; z++) {
				this.moeglicheWerte[z][spaltenNr].remove(wert);
		}
		// Entferne Werte aus dem gleichen Block
		int minZeile = zeilenNr - zeilenNr % 3;
		int minSpalte = spaltenNr - spaltenNr % 3;
		for (z = minZeile; z < minZeile + 3; z++) {
			for (s = minSpalte; s < minSpalte + 3; s++) {
					this.moeglicheWerte[z][s].remove(wert);
			}
		}		
	}
	
	/**
	 * Überprüft, ob das durch Zeilen- und Spaltennummer identifizierte Feld noch unbelegt ist
	 * und nur noch ein Werte in Frage kommt.
	 * 
	 * @param zeilenNr die Nummer der Zeile
	 * @param spaltenNr die Nummer der Spalte
	 * @return true, falls das Feld unbelegt und der Wert eindeutig ist, false sonst
	 */
	private boolean eindeutig(int zeilenNr, int spaltenNr) {
		return this.moeglicheWerte[zeilenNr][spaltenNr].size() == 1;
	}
	

	/** 
	 * Überprüft, ob der Wert in der Zeile eindeutig ist.
	 * 
	 * @param zeilenNr die Nummer der Zeile
	 * @param wert der gesuchte Wert
	 * @return true, falls der Wert eindeutig ist, false sonst
	 */
	private boolean eindeutigInZeile(int zeilenNr, int wert) {
		int haeufigkeit = 0;
		for (int s = 0; s < 9; s++) {
			if (this.moeglicheWerte[zeilenNr][s].contains(wert)) {
				haeufigkeit++;
				if (haeufigkeit > 1) {
					break;
				}
			}
		}
		return haeufigkeit == 1;
	}
	
	

	/** 
	 * Überprüft, ob der Wert in der Spalte eindeutig ist.
	 * 
	 * @param spaltenNr die Nummer der Spalte
	 * @param wert der gesuchte Wert
	 * @return true, falls der Wert eindeutig ist, false sonst
	 */
	private boolean eindeutigInSpalte(int spaltenNr, int wert) {
		int haeufigkeit = 0;
		for (int z = 0; z < 9; z++) {
			if (this.moeglicheWerte[z][spaltenNr].contains(wert)) {
				haeufigkeit++;
				if (haeufigkeit > 1) {
					break;
				}
			}
		}
		return haeufigkeit == 1;
	}
	

	/**
	 * Überprüft, ob der mögliche Wert genau einmal in dem Block vorkommt,<br>
	 * der die durch Zeilen- und Spaltennummer identifizierte Zelle enthält.
	 * 
	 * @param zeilenNr Nummer der Zeile
	 * @param spaltenNr Nummer der Spalte
	 * @param wert der gesuchte Wert
	 * @return true, falls der Wert eindeutig ist, false sonst
	 */
	private boolean eindeutigInBlock(int zeilenNr, int spaltenNr, int wert) {
		int haeufigkeit = 0;
		int minZeile = zeilenNr - zeilenNr % 3;
		int minSpalte = spaltenNr - spaltenNr % 3;
		for (int z = minZeile; z < minZeile + 3; z++) {
			for (int s = minSpalte; s < minSpalte + 3; s++) {
				if (this.moeglicheWerte[zeilenNr][spaltenNr].contains(wert)) {
					haeufigkeit++;
					if (haeufigkeit > 1) {
						break;
					}					
				}
			}
		}
		return haeufigkeit == 1;
	}

	
	/**
	 * Es werden Werte gesetzt, solange dies eindeutig möglich ist. Dies wird
	 * mit Hilfe der Matrix moeglicheWerte festgestellt.
	 * Ein Wert für ein unbelegtes Feld ist eindeutig, wenn
	 * - für das Feld nur noch ein Wert möglich ist oder
	 * - der Wert eindeutig in der Zeile, Spalte oder im Block ist.
	 * 
	 * @return true falls mindestens ein Wert gesetzt wurde
	 */
	private boolean werteSetzen() {
		int schleifenZaehler = 0; // Zählt die Durchläufe der do-Schleife
		boolean wertInSchleifeGesetzt; // Wurde im aktuellen Durchlauf der do-Schleife mindestens ein Wert gesetzt?
		do {
			schleifenZaehler++;
			wertInSchleifeGesetzt = false;
			for (int z = 0; z < 9; z++) {
				for (int s = 0; s < 9; s++) {
					if (!this.loesung.belegt(z, s)) { // Nichts tun, wenn das Feld schon belegt ist
						int wert = 0; // Der ggf. zu setzende Wert
						Iterator<Integer> iterator = this.moeglicheWerte[z][s].iterator(); // Mindestens ein Wert möglich
						if (this.eindeutig(z, s)) { // Nimm den Wert, wenn er eindeutig ist
							wert = iterator.next();
						} else {
							int w; // Variable für die zu betrachtenden Werte
							while (iterator.hasNext()) {
								w = iterator.next();
								if (this.eindeutigInZeile(z, w) ||
										this.eindeutigInSpalte(s, w) ||
										this.eindeutigInBlock(z, s, w)) {
									wert = w; // Wert wurde gefunden
									break; // Suchschleife abbrechen
								}
							}
						}
						if (wert != 0) { // Suche nach einem eindeutigen Wert erfolgreich
							this.setze(z, s, wert);
							wertInSchleifeGesetzt = true;
						}
					}
				}
			}
		} while (wertInSchleifeGesetzt); // Terminiert, sobald in einem Durchlauf kein Wert mehr gesetzt werden konnte
		return schleifenZaehler > 1; // Mindestens 1 erfolgreicher Schleifendurchlauf
	}
	
	/**
	 * Es werden Werte eingeschränkt, solange dies möglich ist. Dazu wird
	 * die Matrix moeglicheWerte benutzt und eingeschränkt.
	 * Zum Einschränken werden Regeln benutzt, die unvollständige
	 * Informationen auswerten (z.B. Clusteranalyse).
	 * 
	 * @return true falls mindestens eine Wertemenge eingeschränkt wurde
	 */
	private boolean werteEinschraenken() {
		return false;
	}
	
	/**
	 * Zunächst wird die Lösung mit dem Rätsel initialisiert. Dann
	 * werden alternierend Werte gesetzt und eingeschränkt, solange
	 * dies möglich ist.
	 * 
	 * @param raetsel das zu lösende Rätsel
	 * @return die Lösung, falls das Rätsel gelöst werden konnte, null sonst
	 */
	@Override
	public Spielfeld loese(Spielfeld raetsel) {
		if (raetsel == null) 
			return null;
		this.init(raetsel);
		this.werteSetzen(); // Terminiert, wenn keine Werte mehr gesetzt werden können
		while (!helfer.loesungVollstaendig(this.loesung) && this.werteEinschraenken() && this.werteSetzen()) {
			// Es werden nacheinander die Prüfungen und Aktionen in der Schleifenbedingung ausgeführt,
			// bis irgendeiner dieser Schritte fehlschlägt
		}
		if (helfer.loesungVollstaendig(this.loesung)) {
			return this.loesung;
		}
		else {
			return null;
		}
	}

}
