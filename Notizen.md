# Analyse des Binärbaum-Modells

## Modellierungsansatz

Die Implementierung nutzt eine Kombination aus Composite Pattern und Null Object Pattern:

**Tree\<T>** ist das gemeinsame Interface

**Node\<T>** repräsentiert nicht-leere Knoten

**Empty\<T>** repräsentiert leere Knoten anstelle von null-Referenzen

### Vorteile dieser Modellierung

Typsicherheit: Vermeidet NullPointerExceptions durch Verwendung von Empty-Objekten
Polymorphismus: Einheitliche Behandlung von leeren und nicht-leeren Knoten
Immutabilität: Records garantieren unveränderliche Datenstrukturen
Funktionaler Ansatz: Operationen erzeugen neue Bäume statt bestehende zu verändern
Clean Code: Klare Trennung der Verantwortlichkeiten
Visitor-Pattern-Unterstützung: Ermöglicht flexible Baumtraversierung

### Nachteile dieser Modellierung

Speicherverbrauch: Empty-Objekte benötigen mehr Speicher als null-Referenzen
Komplexität: Etwas schwieriger zu verstehen für Entwickler ohne Pattern-Kenntnisse
Performance: Das Erstellen neuer Objekte bei Änderungen kann bei großen Bäumen ineffizient sein
Keine Elternreferenzen: Erschwert bestimmte Baumoperationen
Implementierung für Schleifen und Streams
Um die Bäume in Schleifen und Streams nutzen zu können, wurden implementiert:

## Für Schleifen (Iterable)

iterator() Methode in beiden Klassen, die einen TreeIterator zurückgibt
Implementierung des Iterator-Interfaces in TreeIterator

## Für Streams

spliterator() Methode, die einen Spliterator zurückgibt
Nutzung von Spliterators.spliteratorUnknownSize(iterator(), Spliterator.ORDERED)
Implementierung von forEach() für direkte Consumer-Operationen
Funktionsweise des TreeIterators
Der TreeIterator implementiert eine Inorder-Traversierung des Binärbaums:

**Initialisierung:**

Nutzt einen Stack zur Speicherung von Knoten
Methode pushAllLeftNodes() füllt den Stack mit dem Wurzelknoten und allen linken Nachfolgern
Traversierung:

hasNext(): Prüft, ob der Stack leer ist
next():
Nimmt obersten Knoten vom Stack
Fügt dessen rechten Nachfolger und alle dessen linken Nachfolger zum Stack hinzu
Gibt den Datenwert des entnommenen Knotens zurück
Diese Implementierung garantiert, dass die Elemente in aufsteigender Reihenfolge (bei einem binären Suchbaum) durchlaufen werden: linker Teilbaum → aktueller Knoten → rechter Teilbaum.
