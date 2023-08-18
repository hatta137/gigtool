# GigTool Dokumentation

Willkommen bei der Dokumentation unserer API-Applikation GigTool.

Ein Herzensprojekt von Robin Harris, Hendrik Lendeckel, Dario Daßler und Max Schelenz für die Inventarisierung und Organisation von Musikequipment.

Wir möchten Musikern dabei helfen ihr Equipment zu verwalten, um sich auf das eigentliche Wichtige - die Musik fokussieren zu können.

<pre> Hinweis: Unser Projekt wurde wie besprochen als Verbesserungsversuch für Java 1 angemeldet. </pre>

Zum Einstieg möchten wir euch erstmal zeigen, wie ihr unsere API-Applikation installieren könnt.

--- 

## Installationsanleitung



<pre>git clone https://git.ai.fh-erfurt.de/prgj2-23/gigtool.git
Hier noch ergänzen Maven etc mal Testweise auf Laptop alles durchklicken
docker compose up</pre>

Die [Postman-App](https://www.postman.com/downloads/) werden wir später in der Anwenderdokumentation nutzen, um unsere Routen und Controller zu testen.

## Architektur

Die GigTool Architektur umfasst mehrere Bereiche, die wir euch in den kommenden Abschnitt verständlich näher bringen wollen.


### Komponenten

![Komponentendiagramm](assets/Komponentendiagramm.drawio.v2.png "Komponentendiagramm GigTool")

#### api
Wie im Komponentendiagramm zu erkennen, enthält die api alle wichtigen Controller-Klassen.
Diese sind für das Routing der Anfragen zu den Endpoints zuständig.

#### storage
Die storage Komponente ist unterteilt in verschiedene Module.

Das "model" ist für die Definition der Datenbank-Objekte vorgesehen und beschreibt, wie diese auszusehen haben.

Die repos im "repositories"-Modul, benötigen wir um überhaupt Datenbank-Operationen anwenden zu können. 
Sie sind bei Bedarf beliebig erweiterbar, falls die JPA-Funktionalitäten nicht ausreichen.

Das services-Modul ist nochmal unterteilt in service.models und services.
Die service.models definieren, wie die create- und response-Objekte auszusehen haben.
Services sind für die Trennung von Geschäftslogik und Datenbankzugriff zuständig.

#### db
Die Komponente "db" enthält unser Initialiserungsskript mit dem Namen der Datenbank und dem root-User zur Verwaltung.

#### docker
Die docker-compose.yml ist unsere docker-Komponente im Projekt. Sie sorgt für die Containerisierung unserer PostgresSQL-DB und dem Verwaltungstool pgadmin.

#### maven
Die Maven POM enthält alle wichtigen Abhängigkeiten für unser Spring Boot-Projekt und stellt sozusagen den Build-Planer dar.


### Interfaces (wird noch überarbeitet)

Im folgenden Schaubild zeigen wir euch, wie unser API-Interface funktioniert und welche Abläufe im backend angestoßen werden, wenn der User beispielsweise ein Equipment anlegen möchte.

![Interface](assets/EquipmentInterface.png "Interface Equipment")



### Größere Architekturänderungen im Vergleich zu Java 1

Unser Projekt haben wir von Java 1 weitergeführt. Wie ihr sicherlich merkt, ist der Grad an Komplexität jedoch deutlich gestiegen und wir haben mehrere Umbauten vornehmen müssen.

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

Diese Klasse ist nun nicht mehr Abstract, da wir eine PostgresSQL-Datenbank nutzen.

### Rental und Gig

Diese Klassen erben nun nicht mehr von Happening. Sie verweisen jedoch auf ihr Happening.

### Dimension

Diese Klasse wurde entfernt, weil der Sinn und Zweck erhalten bleibt, wenn wir Länge, Breite, Höhe direkt in der Entität
Equipment speichern.

## Anwenderdokumentation

### Service Tests in Intellj

### Routen und Controller in Postman

### Auflistung Endpoints

## Besonderheiten im Projekt

testutils- randomgenerator


## Lessons Learned



