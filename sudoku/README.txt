Init startet das interaktive Werkzeug (fensterbasiert).
LoeserKonsole startet das automatische Werkzeug (Konsole).

Beim Testen oder Ausführen wird u.U. ein Fehler gemeldet. 
Dann unter Project das Kommando BuildAutomatically
deaktivieren, anschließend Clean und Build Project aktivieren.

Alle Lösungen zu Rätseln aus Rätselbüchern sind eindeutig. Deshalb wird beim Testen
lediglich überprüft, ob eine (vollständige) Lösung gefunden wurde. Es wird
darauf verzichtet, die Gleichheit mit der Lösung im Rätselheft zu prüfen.

Die Lösung zum leeren Rätsel ist nicht eindeutig. Die Datei loesung_Leer.txt enthält
irgendeine Lösung; jeder Algorithmus kann eine andere Lösung erzeugen.

Folgende Löser wurden implementiert:

Basisimplementierung
====================

Hier werden nur die Sudoku-Regeln ausgenutzt. Der Löser sucht nach Werten, die
eindeutig in einer Zeile, einer Spalte, einem Block oder einem Feld sind. Falls 
ein solcher Wert gefunden wird, wird er in das jeweilige Feld eingetragen und
der nächste eindeutige Wert gesucht. Schließlich ist entweder das Rätsel 
vollständig gelöst, oder der Löser terminiert ohne eine Lösung.

Die Basisimplementierung ist der schwächste Löser hinsichtlich der Menge der
lösbaren Rätsel. Beim leeren Rätsel terminiert er sofort ohne Lösung.

Tiefensuche
===========

Eine Lösung wird durch Tiefensuche mit Backtracking ermittelt. Dabei werden die 
Felder zyklisch durchlaufen. Ein nicht belegtes Feld wird entweder mit irgendeinem
möglichen Wert belegt, oder - falls die Menge der noch möglichen Werte leer ist -
es wird Backtracking ausgelöst. Ist kein Backtracking mehr möglich, terminiert
der Löser ohne Lösung.

Dieser Löser setzt nicht voraus, dass ein Rätsel eindeutig ist. Ist es mehrdeutig,
so findet der Löser irgendeine Lösung. Allerdings ist der Löser so ineffizient,
dass er u.U. in vertretbarer Zeit keine Lösung findet: Beim leeren Rätsel wurde
der Versuch, eine Lösung zu finden, nach ca. 1 Stunde abgebrochen. In anderen
Testfällen braucht die reine Tiefensuche erheblich länger als andere Löser,
um eine Lösung zu finden.

Optimierte Tiefensuche
======================

Auch hier wird eine Lösung gefunden, falls eine existiert - unabhängig davon, ob
das Rätsel ein- oder mehrdeutig ist. Die Optimierung besteht darin, dass man
in jedem Schritt nach einem Feld mit einer minimalen Anzahl noch anwendbarer
Alternativwerte sucht. Im Gegensatz zur einfachen Tiefensuche findet die
optimierten Tiefensuche in vertretbarer Zeit eine Lösung für das leere Rätsel.

Universallöser
==============

Es handelt sich um eine Symbiose aus Basisimplementierung und Tiefensuche. 
Solange dies möglich ist, werden eindeutig bestimmte Werte gesetzt. Falls 
kein Feld mehr eindeutig belegt werden, wird einer der noch möglichen alternativen
Werte gesetzt und anschließend wie in der Basisimplementierung weiter 
verfahren. Im Falle einer Sackgasse wird Backtracking ausgelöst.

Der Universallöser verhält sich ähnlich wie die optimierte Tiefensuche. Er findet
ebenfalls in vertretbarer Zeit eine Lösung des leeren Rätsels.

Eindeutiger Löser
=================

Im Gegensatz zu den Tiefensuchalgorithmen wird hier vorausgesetzt, dass die Lösung
eindeutig ist. Falls der Algorithmus auf eine Mehrdeutigkeit stößt, die er nicht
auflösen kann, bricht er ohne Lösung ab. Deshalb findet er keine Lösung für das 
leere Rätsel. Von der Basisimplementierung unterscheidet sich der eindeutige Löser
dadurch, dass er weitere Regeln angewendet, mit denen sich die Menge möglicher
Werte weiter eingrenzen lässt:

- Cluster: Eine Menge von Feldern in einer Einheit (Zeile, Spalte oder Block)
bildet ein Cluster, wenn die Zahl der Felder mit der Zahl aller möglichen Werte
übereinstimmt. Diese Werte können dann aus allen nicht belegten Felder der
gleichen Einheit gestrichen werden.

- Zeilen- oder Spalteneinschränkungen: Hier wird für jeden Block in einem
horizontalen bzw. vertikalen Blockbereich jeweils bestimmt, in welchen Zeilen
bzw. Spalten ein noch nicht gesetzter Wert auftreten kann. Falls die Zeile 
bzw. Spalte in einem Block eindeutig ist, kann man diesen Wert in allen anderen
Zeilen bzw. Spalten des gleichen Blockbereichs ausschließen.

Die Regeln des eindeutigen Lösers sollten ausreichend sein, um zu jedem Rätsel
aus einem Rätselheft eine (eindeutige) Lösung zu finden. Bisher wurde noch kein
Gegenbeispiel gefunden, es gibt aber auch keinen Beweis für die allgemeine
Gültigkeit dieser Aussage.

----------------------------
Vergleich der Testergebnisse
----------------------------

Menge der lösbaren Rätsel:

- Alle Tiefensuchimplementierungen bestehen alle Testfälle.
- Der eindeutige Löser löst alle eindeutigen Rätsel, aber nicht das leere Rätsel.
- Die Basisimplementierung scheitert nicht nur am leeren Rätsel, sondern auch
an einigen eindeutigen Rätseln.

Effizienz:

- Die Basisimplementierung ist am schnellsten, gefolgt vom eindeutigen Löser.
- Alle Tiefensuchimplementierungen sind langsamer als die deterministischen 
Implementierungen.
- Die einfache Tiefensuche ist mit Abstand am langsamsten (und liefert keine
Lösung in akzeptabler Zeit für das leere Rätsel).
- Die optimierte Tiefensuche und der Universallöser sind etwa gleich schnell. 

