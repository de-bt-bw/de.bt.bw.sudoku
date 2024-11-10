Beim Testen wird u.U. ein Fehler gemeldet. Dann unter Project das Kommando BuildAutomatically
deaktivieren, anschließend Clean und Build Project aktivieren. 

Alle Lösungen zu Rätseln aus Rätselbüchern sind eindeutig. Deshalb wird beim Testen
lediglich überprüft, ob eine (vollständige) Lösung gefunden wurde. Es wird
darauf verzichtet, die Gleichheit mit der Lösung im Rätselheft zu prüfen.

Die Lösung zum leeren Rätsel ist nicht eindeutig. Die Datei loesung_Leer.txt enthält
irgendeine Lösung; jeder Algorithmus kann eine andere Lösung erzeugen.

Das Rästel FitImKopf ist von der Basisimplementierung nicht lösbar, weil während
der Berechnung Mehrdeutigkeiten auftreten (obwohl die Lösung eindeutig ist).
Das leere Rätsel kann erst recht nicht von der Basisimplementierung gelöst werden.

Nur der Universallöser und die optimierte Tiefensuche können das leere Rätsel mit
akzeptabler Rechenzeit lösen. Die optimierte Tiefensuche ist etwas eleganter und
systematischer als der Universallöser, der als Erweiterung der Basisimplementierung
entstanden ist und ein- und mehrdeutige Zellen unterschiedlich behandelt.