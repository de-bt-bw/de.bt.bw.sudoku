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
/**
 * 
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
	 * Felder werden durch Zeilen- und Spaltennummern identifiziert.
	 */
	private class Feld {
		int zeile, spalte;
		Feld (int zeile, int spalte) {
			this.zeile = zeile;
			this.spalte = spalte;
		}
	}
	
	/**
	 * Initialisiert die Lösung mit einer Kopie des Rätsels
	 * 
	 * @param raetsel das zu lösende Rätsel
	 */
	private void init(Spielfeld raetsel) {
		this.helfer = new SpielfeldHelferImpl();
		this.loesung = helfer.kopiere(raetsel);
		this.moeglicheWerte = new Set[9][9]; // Instantiierung nicht mit einem generischen Typ möglich
		for (int z = 0; z < 9; z++) {
			for (int s = 0; s < 9; s++) {
				this.moeglicheWerte[z][s] = loesung.moeglicheWerte(z, s);
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
				if (this.moeglicheWerte[z][s].contains(wert)) {
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
		boolean wertGesetzt = false;
		boolean erfolgInIteration;
		// Fixpunktiteration des Setzens
		do {
			erfolgInIteration = false;
			for (int zeile = 0; zeile < 9; zeile++) {
				for (int spalte = 0; spalte < 9; spalte++) {
					if (!this.loesung.belegt(zeile, spalte)) { // Nichts tun, wenn das Feld schon belegt ist
						int zuSetzenderWert = 0; // Der ggf. zu setzende Wert
						Iterator<Integer> iterator = this.moeglicheWerte[zeile][spalte].iterator(); // Mindestens ein Wert möglich
						if (this.eindeutig(zeile, spalte)) { // Nimm den Wert, wenn er eindeutig ist
							zuSetzenderWert = iterator.next();
						} else {
							while (iterator.hasNext()) {
								int wert = iterator.next();
								if (this.eindeutigInZeile(zeile, wert) ||
										this.eindeutigInSpalte(spalte, wert) ||
										this.eindeutigInBlock(zeile, spalte, wert)) {
									zuSetzenderWert = wert; // Wert wurde gefunden
									break; // Suchschleife abbrechen
								}
							}
						}
						if (zuSetzenderWert != 0) { // Suche nach einem eindeutigen Wert erfolgreich
							this.setze(zeile, spalte, zuSetzenderWert);
							wertGesetzt = true;
							erfolgInIteration = true;
						}
					}
				}
			}
		} while (erfolgInIteration); // Terminiert, sobald in einem Durchlauf kein Wert mehr gesetzt werden konnte
		return wertGesetzt; // Mindestens ein Wert gesetzt
	}
	
	/**
	 * Es werden Werte zusätzlich eingeschränkt (d.h. zusätzlich zu den 
	 * Einschränkungen, die direkt aus den Spielregeln folgen), solange dies möglich ist.<br>
	 * Die Einschränkungen sind notwendig, wenn die Spielregeln keine
	 * eindeutige Fortsetzung erlauben. Dazu wird
	 * die Matrix moeglicheWerte benutzt und eingeschränkt.
	 * Zum Einschränken werden Regeln benutzt, die unvollständige
	 * Informationen auswerten.
	 * 
	 * @return true falls mindestens eine Wertemenge auf einem Feld eingeschränkt wurde
	 */
	private boolean werteEinschraenken() {
		boolean wertEingeschraenkt = this.zeilenSpaltenEinschraenken() || this.clusterEinschraenken();
		if (wertEingeschraenkt) {
			while (this.zeilenSpaltenEinschraenken() || this.clusterEinschraenken()) {
				// Fixpunktiteration
			}
		}
		return wertEingeschraenkt;
	}
	
	/**
	 * Prüft in der Matrix möglicher Werte für jeden Block, ob die Zeile bzw. Spalte für einen Wert eindeutig bestimmt ist,
	 * und entfernt ggf. diesen Wert im jeweiligen Kontext.
	 * 
	 * @return true falls mindestens eine Zeilen- oder Spalteneinschränkung erfolgt ist
	 */
	private boolean zeilenSpaltenEinschraenken() {
		return this.zeilenEinschraenken() || this.spaltenEinschraenken(); 
	}
	
	/**
	 * Prüft Zeileneinschränkungen und führt sie ggf. aus, indem die Matrix moeglicheWerte eingeschränkt wird.<br>
	 * Dabei sind zwei Fälle zu unterscheiden:<br>
	 * 1. Wert w ist in Zeile z in Block b möglich, aber in den anderen Blöcken nicht möglich. Dann muss w in
	 * Block b in Zeile z stehen. w wird dann aus den anderen Zeilen des gleichen Blocks entfernt.<br>
	 * 2. Wert w ist in Block b nur in Zeile z möglich. Dann muss w in Block b in Zeile z stehen. w wird
	 * dann in der gleichen Zeile aus den anderen Blöcken entfernt.
	 * 
	 * @return true falls mindestens eine Zeileneinschränkung in einem Feld der Matrix moeglicheWerte erfolgt ist
	 */
	private boolean zeilenEinschraenken() {
		boolean eingeschraenkt = false;
		int w, h, z, az, b, ab, s; // Laufvariablen
		for (w = 1; w < 10; w++) { // Für alle Werte
			for (h = 0; h < 3; h++) { // Für alle horizontalen Blockbereiche
				boolean[][] moeglich = new boolean[3][3]; // Zeilenindex: Zeilen z, Spaltenindex: Blöcke b
				// Mögliche Zeilen und Blöcke aus Matrix moeglicheWerte bestimmen
				for (b = 0; b < 3; b++) { // Für alle Blöcke des Blockbereichs
					for (z = 0; z < 3; z++) { // Für alle Zeilen im Block
						for (s = 0; s < 3; s++) { // Für alle Spalten im Block
							if (this.moeglicheWerte[z + h*3][s + b*3].contains(w)) {
								moeglich[z][b] = true; // Wert w ist in Zeile z und Block b möglich
							}
						}
					}
				}
				// Zeileneinschränkungen durchführen
				for (b = 0; b < 3; b++) { // Für alle Blöcke des Blockbereichs
					for (z = 0; z < 3; z++) { // Für alle Zeilen im Block
						if (moeglich[z][b] && !moeglich[z][(b + 1) % 3] && !moeglich[z][(b + 2) % 3]) {
							// Fall 1: Wert w muss in Block b in Zeile z stehen, da w in den anderen Blöcken in z nicht möglich ist
							// Wert w in den anderen Zeilen von Block b ausschließen
							for (az = (z + 1) % 3; az != z; az = (az + 1) % 3) { // Für die beiden anderen Zeilen
								if (moeglich[az][b]) { // Sonst ist nichts zu tun, da w in az nicht möglich
									moeglich[az][b] = false; // Wert w in dieser Zeile nicht möglich
									for (s = 0; s < 3; s++) { // Für alle Spalten in der Zeile az
										if (this.moeglicheWerte[az + h*3][s + b*3].remove(w)) {
											// Entfernung von w war erfolgreich
											eingeschraenkt = true;
										}
									}
								}								
							}
						}
						if (moeglich[z][b] && !moeglich[(z +1)%3][b] && !moeglich[(z +2)%3][b]) {
							// Fall 2: Wert w muss in Block b in Zeile z stehen, da w in den anderen Zeilen von Block b nicht möglich ist
							// Wert w in den anderen Blöcken in Zeile z ausschließen
							for (ab = (b + 1) % 3; ab != b; ab = (ab + 1) % 3) { // Für die beiden anderen Blöcke
								if (moeglich[z][ab]) { // Sonst ist nichts zu tun, da w in ab in Zeile z nicht möglich
									moeglich[z][ab] = false; // Wert w in diesem Block in Zeile z nicht möglich
									for (s = 0; s < 3; s++) { // Für alle Spalten im Block ab
										if (this.moeglicheWerte[z + h*3][s + ab*3].remove(w)) {
											// Entfernung von w war erfolgreich
											eingeschraenkt = true;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return eingeschraenkt;
	}
	
	/**
	 * Prüft Spalteneinschränkungen und führt sie ggf. aus, indem die Matrix moeglicheWerte eingeschränkt wird.<br>
	 * Dabei sind zwei Fälle zu unterscheiden:<br>
	 * 1. Wert w ist in Spalte s in Block b möglich, aber in den anderen Blöcken nicht möglich. Dann muss w in
	 * Block b in Spalte s stehen. w wird dann aus den anderen Spalten des gleichen Blocks entfernt.<br>
	 * 2. Wert w ist in Block b nur in Spalte s möglich. Dann muss w in Block b in Spalte s stehen. w wird
	 * dann in der gleichen Spalte aus den anderen Blöcken entfernt.
	 * 
	 * 
	 * @return true falls mindestens eine Spalteneinschraenkung erfolgt ist
	 */
	private boolean spaltenEinschraenken() {
		boolean eingeschraenkt = false;
		int w, v, s, as, b, ab, z; // Laufvariablen
		for (w = 1; w < 10; w++) { // Für alle Werte
			for (v = 0; v < 3; v++) { // Für alle vertikalen Blockbereiche
				boolean[][] moeglich = new boolean[3][3]; // Zeilenindex: Blöcke b, Spaltenindex: Spalten s
				// Mögliche Blöcke und Spalten aus Matrix moeglicheWerte bestimmen
				for (b = 0; b < 3; b++) { // Für alle Blöcke des Blockbereichs
					for (s = 0; s < 3; s++) { // Für alle Spalten im Block
						for (z = 0; z < 3; z++) { // Für alle Zeilen im Block
							if (this.moeglicheWerte[z + b*3][s + v*3].contains(w)) {
								moeglich[b][s] = true; // Wert w ist in Block b und Spalte s möglich
							}
						}
					}
				}
				// Spalteneinschränkungen durchführen
				for (b = 0; b < 3; b++) { // Für alle Blöcke des Blockbereichs
					for (s = 0; s < 3; s++) { // Für alle Spalten im Block
						if (moeglich[b][s] && !moeglich[(b + 1) % 3][s] && !moeglich[(b + 2) % 3][s]) {
							// Fall 1: Wert w muss in Block b in Spalte s stehen, da w in den anderen Blöcken in Spalte s nicht möglich ist
							// Andere Spalten in Block b ausschließen
							for (as = (s + 1) % 3; as != s; as = (as + 1) % 3) { // Für die beiden anderen Spalten
								if (moeglich[b][as]) { // Sonst ist nichts zu tun
									moeglich[b][as] = false; // Wert w in dieser Spalte nicht möglich
									for (z = 0; z < 3; z++) { // Für alle Zeilen im Block
										if (this.moeglicheWerte[z + b*3][as + v*3].remove(w)) {
											// Entfernung von w war erfolgreich
											eingeschraenkt = true;
										}
									}
								}
							}
						}
						if (moeglich[b][s] && !moeglich[b][(s + 1) % 3] && !moeglich[b][(s + 2) % 3]) {
							// Fall 2: Wert w muss in Block b in Spalte s stehen, da w in anderen Spalten von b nicht möglich ist
							// Wert w in den anderen Blöcken in Spalte s ausschließen
							for (ab = (b + 1) % 3; ab != b; ab = (ab + 1) % 3) { // Für die beiden anderen Blöcke
								if (moeglich[ab][s]) { // Sonst ist nichts zu tun, da w in ab in Spalte s nicht möglich
									moeglich[ab][s] = false; // Wert w in diesem Block in Spalte s nicht möglich
									for (z = 0; z < 3; z++) { // Für alle Zeilen im Block
										if (this.moeglicheWerte[z + ab*3][s + v*3].remove(w)) {
											// Entfernung von w war erfolgreich
											eingeschraenkt = true;
										}
									}

								}
								
							}
						}
					}
				}
			}
		}
		return eingeschraenkt;
	}
	
	/**
	 * Cluster sind Mengen von Feldern innerhalb einer Einheit (Zeile, Spalte oder Block),
	 * die es ermöglichen, Werte zu eliminieren (entweder innerhalb des Clusters oder
	 * in den übrigen Feldern der Einheit). Die Werte werden durch Propagationsregeln
	 * eingeschränkt.<br> 
	 * Für die Einschränkungen werden nur Cluster der Größen 2 und 3 betrachtet,
	 * weil zum Lösen der Rätsel die Behandlung größerer Cluster nie erforderlich war.<br>
	 * Für jede Clustergröße wird ein einziger Durchlauf über alle Zeilen, Spalten
	 * und Blöcke durchgeführt.
	 * 
	 * @return true falls mindestens eine Wertemenge auf einem Feld eingeschränkt wurde
	 */
	private boolean clusterEinschraenken() {
		Set<Feld> einheit;
		int z, s, h, v; // Laufvariablen für Zeilen, Spalten und Blöcke
		boolean eingeschraenkt = false;
		for (int n = 2; n < 4; n++) { // Nur Cluster der Größen 2 und 3 betrachten
			// Alle Zeilen behandeln			
			for (z = 0; z < 9; z++) { // Für alle Zeilen
				einheit = new HashSet<Feld>();
				for (s = 0; s < 9; s++) {
					einheit.add(new Feld(z, s));
				} // Zeile in Menge konvertieren
				if (this.clusterEinschraenken(n, einheit)) {
					eingeschraenkt = true;
				}
			}
			for (s = 0; s < 9; s++) { // Für alle Spalten
				einheit = new HashSet<Feld>();
				for (z = 0; z < 9; z++) {
					einheit.add(new Feld(z, s));
				} // Spalte in Menge konvertieren
				if (this.clusterEinschraenken(n, einheit)) {
					eingeschraenkt = true;
				}
			}
			for (h = 0; h < 3; h++) { // Für alle horizontalen Blockbereiche
				for (v = 0; v < 3; v++) { // Für alle Blöcke
					einheit = new HashSet<Feld>();
					for (z = 3 * h; z < 3 * (h + 1); z++) { // Für alle Zeilen des Blocks
						for (s = 3 * v; s < 3 * (v + 1); s++) { // Für alle Spalten in der Zeile
							einheit.add(new Feld(z, s));
						}
					} // Block in Menge konvertieren
					if (this.clusterEinschraenken(n, einheit)) {
						eingeschraenkt = true;
					}
				}
			}
		}
		return eingeschraenkt;
	}
	
	/**
	 * Sucht Cluster der Größe n in einer Einheit und schränkt ggf. die möglichen 
	 * Werte ein.
	 * 
	 * @param n Größe des Clusters (2 <= n <= 9)
	 * @param einheit Menge von Feldern, die eine Einheit bilden (Zeile, Spalte oder Block)
	 * @return true falls mindestens eine Wertemenge auf einem Feld der Einheit eingeschränkt wurde
	 */
	private boolean clusterEinschraenken(int n, Set<Feld> einheit) {
		boolean eingeschraenkt = false;
		// Nur Felder betrachten, die frei sind
		Set<Feld> freieFelder = new HashSet<Feld>();
		Iterator<Feld> feldIterator = einheit.iterator();
		while (feldIterator.hasNext()) {
			Feld feld = feldIterator.next();
			if (!this.loesung.belegt(feld.zeile, feld.spalte)) {
				freieFelder.add(feld);
			}
		}
		if (freieFelder.size() >= n) {
			// Die Menge muss groß genug sein, um ein Cluster der Größe n bilden zu können
			// Alle Teilmengen der Größe n bestimmen und über diese Teilmengen iterieren
			Set<Set<Feld>> kandidaten = this.teilmengen(n, freieFelder);
			Iterator<Set<Feld>> kandidatenIterator = kandidaten.iterator();
			while (kandidatenIterator.hasNext()) {
				Set<Feld> kandidat = kandidatenIterator.next(); // Wähle Clusterkandidaten aus
				// Berechne Komplement (freie Felder, die nicht zum Clusterkandidaten gehören)
				Set<Feld> komplement = new HashSet<Feld>();
				Iterator<Feld> freieFelderIterator = freieFelder.iterator();
				while(freieFelderIterator.hasNext()) {
					Feld freiesFeld = freieFelderIterator.next();
					if (!kandidat.contains(freiesFeld)) {
						komplement.add(freiesFeld);
					}
				}
				// Falls der Kandidat ein Cluster ist, ggf. Werte einschränken
				if (this.clusterInternEinschraenken(kandidat, komplement) || 
						this.clusterExternEinschraenken(kandidat, komplement)) {
					eingeschraenkt = true;
				}
			}			
		}
		return eingeschraenkt;
	}
	
	/**
	 * Generische Methode, die für eine Menge des Eintragstyps T alle Teilmengen
	 * der Größe n liefert
	 * 
	 * @param <T> generischer Parameter für den Eintragstyp
	 * @param n Größe der Teilmengen (0 <= n <= menge.size())
	 * @param menge Menge von T-Objekten mit mindestens n Elementen
	 * @return
	 */
	private <T> Set<Set<T>> teilmengen(int n, Set<T> menge) {
		Set<Set<T>> teilmengen = new HashSet<Set<T>>();
		if (n == 0) { // Die leere Menge ist die einzige Teilmenge
			teilmengen.add(new HashSet<T>());
		} else if (menge.size() == n) { // Die gesamte Menge ist die einzige Teilmenge
			teilmengen.add(menge);
		} else { // 0 < n < menge.size()
			Iterator<T> iterator = menge.iterator();
			T element = iterator.next(); // Liefert das erste Element 
			Set<T> neueMenge = new HashSet<T>();
			// Die neue Menge für die rekursiven Aufrufe enthält alle Elemente außer dem ersten
			while (iterator.hasNext()) {
				neueMenge.add(iterator.next());
			}
			// Zunächst die Teilmengen berechnen, die das erste Element nicht enthalten
			teilmengen = teilmengen(n, neueMenge);
			// Nun die Teilmengen berechnen, die das erste Element enthalten
			Set<Set<T>> teilmengenMitELement = teilmengen(n - 1, neueMenge); // Zunächst ohne erstes Element
			Iterator<Set<T>> teilmengenIterator = teilmengenMitELement.iterator();
			while (teilmengenIterator.hasNext()) { // Zu jeder Teilmenge das erste Element hinzufügen
				Set<T> teilmenge = teilmengenIterator.next();
				teilmenge.add(element);
				teilmengen.add(teilmenge); // Teilmenge zur Antwortmenge hinzufügen
			}			
		}
		return teilmengen;
	}
		
	/**
	 * Behandelt einen Kandidaten für ein internes Cluster.<br>
	 * Eine Menge von Feldern innerhalb einer Einheit bildet ein internes Cluster der
	 * Größe n, falls auf ihnen insgesamt genau n Werte möglich sind, die auf keinem
	 * anderen Feld der Einheit möglich sind. In diesem Fall werden alle anderen
	 * Werte innerhalb des Clusters ausgeschlossen.<br>
	 * Der Methode werden der Clusterkandidat und sein Komplement innerhalb einer
	 * Einheit übergeben. 
	 * 
	 * @param kandidat der Clusterkandidat
	 * @param komplement das Komplement (die restlichen freien Felder) der Einheit
	 * @return true falls mindestens eine Wertemenge eingeschränkt wurde
	 */
	private boolean clusterInternEinschraenken(Set<Feld> kandidat, Set<Feld> komplement) {
		boolean eingeschraenkt = false;
		int n = kandidat.size();
		// Alle Werte auf den Kandidaten- und Komplementfeldern ermitteln
		Set<Integer> alleKandidatenWerte = this.alleWerte(kandidat);
		Set<Integer> alleKomplementWerte = this.alleWerte(komplement);
		// Nur im Clusterkandidaten vorkommende Werte ermitteln
		Set<Integer> werteNurImKandidaten = new HashSet<Integer>();
		Iterator<Integer> kandidatenWertIterator = alleKandidatenWerte.iterator();
		while (kandidatenWertIterator.hasNext()) {
			Integer wert = kandidatenWertIterator.next();
			if (!alleKomplementWerte.contains(wert)) {
				werteNurImKandidaten.add(wert);
			}
		}
		if (werteNurImKandidaten.size() == n && alleKandidatenWerte.size() > n) { // Kandidat ist ein internes Cluster
			// Innerhalb des Clusters alle Werte eliminieren, die auch im Komplement vorkommen
			Iterator<Feld> kandidatenIterator = kandidat.iterator();
			while (kandidatenIterator.hasNext()) {
				Feld kandidatenFeld = kandidatenIterator.next();
				Set<Integer> feldWerte = this.moeglicheWerte[kandidatenFeld.zeile][kandidatenFeld.spalte];
				Set<Integer> eingeschraenkteFeldWerte = new HashSet<Integer>();
				// Menge neu aufbauen, um Iterator auf zu verändernder Menge zu vermeiden
				Iterator<Integer> feldWertIterator = feldWerte.iterator();
				while(feldWertIterator.hasNext()) {
					Integer wert = feldWertIterator.next();
					if (werteNurImKandidaten.contains(wert)) {
						eingeschraenkteFeldWerte.add(wert);
					} else {
						// Wert wird nicht mehr berücksichtigt
						eingeschraenkt = true;
					}
				}
				// Mögliche Werte neu setzen
				if (!feldWerte.equals(eingeschraenkteFeldWerte)) {
					this.moeglicheWerte[kandidatenFeld.zeile][kandidatenFeld.spalte] = eingeschraenkteFeldWerte;
				}
			}
		}
		return eingeschraenkt;
	}
	
	/**
	 * Berechnet die Vereinigungsmenge aller Werte, die auf den Feldern einer vorgegebenen
	 * Menge möglich sind.
	 * 
	 * @param feldMenge die Menge der Felder
	 * @return alle Werte, die auf den Feldern der Menge noch möglich sind
	 */
	private Set<Integer> alleWerte(Set<Feld> feldMenge) {
		Set<Integer> alleWerte = new HashSet<Integer>();
		Iterator<Feld> feldIterator = feldMenge.iterator();
		while (feldIterator.hasNext()) {
			Feld feld = feldIterator.next();
			Set<Integer> feldWerte = this.moeglicheWerte[feld.zeile][feld.spalte];
			Iterator<Integer> wertIterator = feldWerte.iterator();
			while (wertIterator.hasNext()) {
				Integer wert = wertIterator.next();
				alleWerte.add(wert);
			}
		}
		return alleWerte;
	}
	
	/**
	 * Behandelt einen Kandidaten für ein externes Cluster.<br>
	 * Eine Menge von Feldern innerhalb einer Einheit bildet ein externes Cluster der
	 * Größe n, falls auf ihnen insgesamt genau n Werte möglich sind. In diesem Fall
	 * werden diese Werte auf allen anderen Feldern der Einheit ausgeschlossen.<br>
	 * Der Methode werden der Clusterkandidat und sein Komplement innerhalb einer
	 * Einheit übergeben. 
	 * 
	 * @param kandidat der Clusterkandidat
	 * @param komplement das Komplement (die restlichen freien Felder) der Einheit
	 * @return true falls mindestens eine Wertemenge eingeschränkt wurde
	 */
	private boolean clusterExternEinschraenken(Set<Feld> kandidat, Set<Feld> komplement) {
		boolean eingeschraenkt = false;
		int n = kandidat.size();
		// Alle Werte auf den Kandidatenfeldern ermitteln
		Set<Integer> alleWerte = this.alleWerte(kandidat);
		if (alleWerte.size() == n) { // Kandidat ist ein externes Cluster
			// Die Werte des Clusters aus dem Komplement entfernen
			Iterator<Feld> komplementIterator = komplement.iterator();
			while (komplementIterator.hasNext()) {
				Feld komplementFeld = komplementIterator.next();
				Set<Integer> feldWerte = this.moeglicheWerte[komplementFeld.zeile][komplementFeld.spalte];
				Set<Integer> eingeschraenkteFeldWerte = new HashSet<Integer>();
				// Menge neu aufbauen, um Iterator auf zu verändernder Menge zu vermeiden
				Iterator<Integer> feldWertIterator = feldWerte.iterator();
				while(feldWertIterator.hasNext()) {
					Integer wert = feldWertIterator.next();
					if (alleWerte.contains(wert)) {
						// Wert wird nicht mehr berücksichtigt
						eingeschraenkt = true;							
					} else {
						eingeschraenkteFeldWerte.add(wert);
					}
				}
				// Mögliche Werte ggf. neu setzen
				if (!feldWerte.equals(eingeschraenkteFeldWerte)) {
					this.moeglicheWerte[komplementFeld.zeile][komplementFeld.spalte] = eingeschraenkteFeldWerte;
				}				
			}
		}
		return eingeschraenkt;
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
			// Solange die Lösung unvollständig ist, Werte eingeschränkt werden können und
			// anschließend Werte gesetzt werden können, wird die Schleife ausgeführt.
		}
		if (helfer.loesungVollstaendig(this.loesung)) {
			return this.loesung;
		}
		else {
			return null;
		}
	}

}
