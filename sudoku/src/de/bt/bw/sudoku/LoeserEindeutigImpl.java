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
	 * Es werden Werte zusätzlich eingeschränkt (d.h. zusätzlich zu den 
	 * Einschränkungen, die direkt aus den Spielregeln folgen). Diese
	 * Einschränkungen sind notwendig, wenn die Spielregeln keine
	 * eindeutige Fortsetzung erlauben. Dazu wird
	 * die Matrix moeglicheWerte benutzt und eingeschränkt.
	 * Zum Einschränken werden Regeln benutzt, die unvollständige
	 * Informationen auswerten.
	 * 
	 * @return true falls mindestens eine Wertemenge eingeschränkt wurde
	 */
	private boolean werteEinschraenken() {
		return this.zeilenSpaltenEinschraenken();
	}
	
	/**
	 * Prüft in der Matrix möglicher Werte für jeden Block, ob die Zeile bzw. Spalte für einen Wert eindeutig bestimmt ist,
	 * und entfernt ggf. diesen Wert aus den anderen Zeilen bzw. Spalten dieses Blocks.
	 * 
	 * @return true falls mindestens eine Zeilen- oder Spalteneinschränkung erfolgt ist
	 */
	private boolean zeilenSpaltenEinschraenken() {
		return this.zeilenEinschraenken() | this.spaltenEinschraenken(); // Striktes Oder verwenden!
	}
	
	/**
	 * Prüft Zeileneinschränkungen und führt sie ggf. aus
	 * 
	 * @return true falls mindestens eine Zeileneinschränkung in einem Feld der Matrix moeglicheWerte erfolgt ist
	 */
	private boolean zeilenEinschraenken() {
		boolean eingeschraenkt = false;
		int w, h, z, az, b, s; // Laufvariablen
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
				// Zeileneinschraenkungen durchführen
				for (b = 0; b < 3; b++) { // Für alle Blöcke des Blockbereichs
					for (z = 0; z < 3; z++) { // Für alle Zeilen im Block
						if (moeglich[z][b] && !moeglich[z][(b + 1) % 3] && !moeglich[z][(b + 2) % 3]) {
							// Wert w muss in Block b in Zeile z stehen
							// Andere Zeilen in Block b ausschließen
							for (az = (z +1) % 3; az != z; az = (az + 1) % 3) { // Für die beiden anderen Zeilen
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
				}
			}
		}
		return eingeschraenkt;
	}
	
	/**
	 * Prüft Spalteneinschränkungen und führt sie ggf. aus
	 * 
	 * @return true falls mindestens eine Spalteneinschraenkung erfolgt ist
	 */
	private boolean spaltenEinschraenken() {
		boolean eingeschraenkt = false;
		int w, v, s, as, b, z; // Laufvariablen
		for (w = 1; w < 10; w++) { // Für alle Werte
			for (v = 0; v < 3; v++) { // Für alle vertikalen Blockbereiche
				boolean[][] moeglich = new boolean[3][3]; // Zeilenindex: Blöcke b, Spaltenindex: Spalten s
				// Mögliche Zeilen und Blöcke aus Matrix moeglicheWerte bestimmen
				for (b = 0; b < 3; b++) { // Für alle Blöcke des Blockbereichs
					for (s = 0; s < 3; s++) { // Für alle Spalten im Block
						for (z = 0; z < 3; z++) { // Für alle Zeilen im Block
							if (this.moeglicheWerte[z + b*3][s + v*3].contains(w)) {
								moeglich[b][s] = true; // Wert w ist in Block b und Spalte s möglich
							}
						}
					}
				}
				// Spalteneinschraenkungen durchführen
				for (b = 0; b < 3; b++) { // Für alle Blöcke des Blockbereichs
					for (s = 0; s < 3; s++) { // Für alle Spalten im Block
						if (moeglich[b][s] && !moeglich[(b + 1) % 3][s] && !moeglich[(b + 2) % 3][s]) {
							// Wert w muss in Block b in Spalte s stehen
							// Andere Spalten in Block b ausschließen
							for (as = (s +1) % 3; as != s; as = (as + 1) % 3) { // Für die beiden anderen Spalten
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
	 * weil zum Lösen der Rätsel die Behandlung größerer Cluster nie erforderlich war.
	 * 
	 * @return true falls mindestens eine Wertemenge eingeschränkt wurde
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
				for (v = 0; v < 3; v++) { // Für alle vertikalen Blöcke
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
	 * @param n Größe des Clusters
	 * @param einheit Menge von Feldern, die eine Einheit bilden (Zeile, Spalte oder Block)
	 * @return true falls mindestens eine Wertemenge eingeschränkt wurde
	 */
	private boolean clusterEinschraenken(int n, Set<Feld> einheit) {
		boolean eingeschraenkt = false;
		// Nur Felder betrachten, die frei sind und deren Wert noch nicht eindeutig bestimmt ist
		Set<Feld> freieFelder = new HashSet<Feld>();
		Iterator<Feld> iterator = einheit.iterator();
		while (iterator.hasNext()) {
			Feld f = iterator.next();
			int zeile = f.zeile;
			int spalte = f.spalte;
			if (this.moeglicheWerte[zeile][spalte].size() >= 2) {
				freieFelder.add(f);
			}
		}
		if (freieFelder.size() >= n) {
			// Die Menge muss groß genug sein, um ein Cluster der Größe n bilden zu können
			// Alle Teilmengen der Größe n bestimmen und über diese Teilmengen iterieren
		}
		return eingeschraenkt;
	}
	
	/**
	 * Generische Methode, die für eine Menge des Eintragstyps T alle Teilmengen
	 * der Größe n liefert
	 * 
	 * @param <T> generischer Parameter für den Eintragstyp
	 * @param n Größe der Teilmengen
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
			T e = iterator.next(); // Liefert das erste Element 
			Set<T> neueMenge = new HashSet<T>();
			// Die neue Menge für die rekursiven Aufrufe enthält alle Elemente außer dem ersten
			while (iterator.hasNext()) {
				neueMenge.add(iterator.next());
			}
			// Zunächst die Teilmengen berechnen, die das erste Element nicht enthalten
			teilmengen = teilmengen(n, neueMenge);
			// Nun die Teilmengen berechnen, die das erste Element enthalten.
			Set<Set<T>> teilmengenMitE = teilmengen(n - 1, neueMenge);
			Iterator<Set<T>> teilmengenIterator = teilmengenMitE.iterator();
			while (teilmengenIterator.hasNext()) { // Zu jeder Teilmenge das erste Element hinzufügen
				Set<T> teilmenge = teilmengenIterator.next();
				teilmenge.add(e);
				teilmengen.add(teilmenge); // Teilmenge zur Antwortmenge hinzufügen
			}
			
		}
		return teilmengen;
	}
	
	
	/**
	 * Behandelt interne Cluster der Größe innerhalb einer Einheit.
	 * Eine Menge von Feldern innerhalb einer Einheit bildet ein internes Cluster der
	 * Größe, falls auf ihnen insgesamt genau n Werte möglich sind, die auf keinem
	 * anderen Feld der Einheit möglich sind. In diesem Fall werden alle anderen
	 * Werte innerhalb des Clusters ausgeschlossen.
	 * 
	 * @param n Größe der Cluster
	 * @param einheit Array von Feldern, die eine Einheit bilden
	 * @return true falls mindestens eine Wertemenge eingeschränkt wurde
	 */
	private boolean clusterInternEinschraenken(int n, Feld[] einheit) {
		return false;
	}
	
	/**
	 * Behandelt externe Cluster der Größe innerhalb einer Einheit.
	 * Eine Menge von Feldern innerhalb einer Einheit bildet ein externes Cluster der
	 * Größe n, falls auf ihnen insgesamt genau n Werte möglich sind. In diesem Fall
	 * werden diese Werte auf allen anderen Feldern der Einheit ausgeschlossen.
	 * 
	 * @param n Größe der Cluster
	 * @param einheit Array von Feldern, die eine Einheit bilden
	 * @return true falls mindestens eine Wertemenge eingeschränkt wurde
	 */
	private boolean clusterExternEinschraenken(int n, Feld[] einheit) {
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
		boolean erfolg = true;
		while (erfolg && !helfer.loesungVollstaendig(this.loesung)) { 
			int zaehler = 0;
			do {
				erfolg = this.werteEinschraenken();
				if (erfolg) {
					zaehler++;
				}
			} while (erfolg); // Einschränkung von Werten so lange wie möglich iterieren
			if (zaehler > 0) { // Mindestens eine Einschränkung
				erfolg = this.werteSetzen(); // Falls kein Wert gesetzt wurde, wird die Schleife terminieren
			}
		}
		if (helfer.loesungVollstaendig(this.loesung)) {
			return this.loesung;
		}
		else {
			return null;
		}
	}

}
