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
	   ergeben (Werte in einer Zeile, Spalte oder in einem Block müssen eindeutig sein).
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
		this.moeglicheWerte = (Set<Integer>[][]) new Object[9][9]; // Instantiierung nicht mit einem generischen Typ möglich
		for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++) {
			for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
				this.moeglicheWerte[zeilenNr][spaltenNr] = loesung.moeglicheWerte(zeilenNr, spaltenNr);
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
			if (s != spaltenNr) {
				this.moeglicheWerte[zeilenNr][s].remove(wert);	
			}			
		}
		// Entferne Werte aus der gleichen Spalte
		for (z = 0; z < 9; z++) {
			if (z != zeilenNr) {
				this.moeglicheWerte[z][spaltenNr].remove(wert);
			}
		}
		// Entferne Werte aus dem gleichen Block
		int minZeile = zeilenNr - zeilenNr % 3;
		int minSpalte = spaltenNr - spaltenNr % 3;
		for (z = minZeile; z <= minZeile + 2; z++) {
			for (s = minSpalte; s <= minSpalte + 2; s++) {
				if (!(z == zeilenNr && s == spaltenNr)) {
					this.moeglicheWerte[z][s].remove(wert);
				}
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
		return loesung.wert(zeilenNr, spaltenNr) == 0 && this.moeglicheWerte[zeilenNr][spaltenNr].size() == 1;
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
		for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
			if (this.moeglicheWerte[zeilenNr][spaltenNr].contains(wert)) {
				haeufigkeit++;
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
		for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++)
			if (this.moeglicheWerte[zeilenNr][spaltenNr].contains(wert))
				haeufigkeit++;
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
		for (int z = minZeile; z <= minZeile + 2; z++)
			for (int s = minSpalte; s <= minSpalte + 2; s++)
				if (this.moeglicheWerte[zeilenNr][spaltenNr].contains(wert))
					haeufigkeit++;
		return haeufigkeit == 1;
	}

	
	/**
	 * Es werden Werte gesetzt, solange dies eindeutig möglich ist. Dies wird<br>
	 * mit Hilfe der Matrix moeglicheWerte festgestellt.<br>
	 * Ein Wert für ein unbelegtes Feld ist eindeutig, wenn<br>
	 * - für das Feld nur noch ein Wert möglich ist oder<br>
	 * - der Wert eindeutig in der Zeile, Spalte oder im Block ist.<br>
	 * 
	 * @return true falls mindestens ein Wert gesetzt wurde
	 */
	private boolean werteSetzen() {
		boolean wertInMethodeGesetzt = false; // Wurde in der Methode mindestens ein Wert gesetzt?
		boolean wertInSchleifeGesetzt; // Wurde im aktuellen Durchlauf der do-Schleife mindestens ein Wert gesetzt?
		do {
			wertInSchleifeGesetzt = false;
			for (int zeilenNr = 0; zeilenNr < 9; zeilenNr++) {
				for (int spaltenNr = 0; spaltenNr < 9; spaltenNr++) {
					int wert = this.loesung.wert(zeilenNr, spaltenNr);
					if (wert == 0) 	{ // Bei gesetztem Wert /= 0 ist nichts zu tun
						int neuerWert = 0; // Variable für ggf. zu setzenden neuen Wert
						Iterator<Integer> iterator = this.moeglicheWerte[zeilenNr][spaltenNr].iterator(); // Mindestens ein Wert möglich
						if (this.eindeutig(zeilenNr, spaltenNr)) {
							// Nimm den einzigen Wert, den der Iterator liefern kann
							neuerWert = iterator.next();
						} else {
							// Suchschleife über mögliche Werte
							while (iterator.hasNext()) {
								wert = iterator.next();
								if (this.eindeutigInZeile(zeilenNr, wert) ||
										this.eindeutigInSpalte(spaltenNr, wert) ||
										this.eindeutigInBlock(zeilenNr, spaltenNr, wert)) {
									neuerWert = wert; // Nimm diesen Wert
									break; // Suchschleife abbrechen
								}
							}
						}
						if (neuerWert != 0) {
							// Suche erfolgreich, setze diesen Wert
							this.setze(zeilenNr, spaltenNr, neuerWert);
							wertInMethodeGesetzt = true;
							wertInSchleifeGesetzt = true;
						}
					}
				}
			}
		} while (wertInSchleifeGesetzt);
		return wertInMethodeGesetzt;
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
		this.werteSetzen();
		while (!helfer.loesungVollstaendig(this.loesung) && this.werteEinschraenken() && this.werteSetzen()) {
			// Es werden nacheinander die Prüfungen und Aktionen in der Schleifenbedingung ausgeführt,
			// bis irgendeiner dieser Schritte fehlschlägt
		}
		if (helfer.loesungVollstaendig(this.loesung))
			return this.loesung;
		else
			return null;
	}

}
