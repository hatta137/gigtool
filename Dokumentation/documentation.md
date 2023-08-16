# Wird von Postman im Anschluss hier rein kopiert oder direkt erstellt

## Größere Änderungen im Vergleich zum Java 1 Projekt
### Calc Klasse
Diese Utility-Class wurde entfernt, da wir festgestellt haben, dass alle Funktionen auf entsprechende Services aufgeteilt werden konnten.
### WeightClass und WeightClassList
Diese Klassen waren gedacht um das Equipment, anhand des Gewichts zu Kategorisieren. 
Diese Kategorien (WeightClass) waren aufsteigend in der WeightClassList gespeichert.
Das alles sollte dazu dienen, Aussagen über das Gewicht/Gesamtgewicht zu geben.
Um die Klassenstruktur zu vereinfachen wurden diese Klassen gestrichen. 
Aussagen über das Gewicht/Gesamtgewicht können trotzdem getroffen werden, da das Eigengewicht in der
Entität Equipment gespeichert wird.
### EquipmentList
Diese Hilfsklasse wurde entfernt und durch das direkte Verknüpfen von Equipments in Happenings und Band realisiert.
### Happening
Diese klasse ist nun nicht mehr Abstract, da eine Datenbank nun genutzt wird.
### Rental und Gig
Diese Klassen erben nun nicht mehr von Happening. Sie verweisen jedoch auf ihr Happening.
### Dimension
Diese Klasse wurde entfernt, weil der Sinn und Zweck erhalten bleibt, wenn wir Länge, Breite, Höhe direkt in der Entität 
Equipment gespeichert wird.

----------

