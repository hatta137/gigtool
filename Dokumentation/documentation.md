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

Wie im Komponentendiagramm zu erkennen, enthält die api alle wichtigen Controller-Klassen.
Diese sind für das Routing der Anfragen zu den Endpoints zuständig.

Die storage Komponente ist unterteilt in verschiedene Module.

Das "model" ist für die Definition der Datenbank-Objekte vorgesehen und beschreibt, wie diese auszusehen haben.

Die repos im "repositories"-Modul, benötigen wir um überhaupt Datenbank-Operationen anwenden zu können. 
Sie sind bei Bedarf beliebig erweiterbar, falls die JPA-Funktionalitäten nicht ausreichen.

Das services-Modul ist nochmal unterteilt in service.models und services.
Die service.models definieren, wie die create- und response-Objekte auszusehen haben.
Services sind für die Trennung von Geschäftslogik und Datenbankzugriff zuständig.


Die Komponente "db" enthält unser Initialiserungsskript mit dem Namen der Datenbank und dem root-User zur Verwaltung.

Die docker-compose.yml ist unsere docker-Komponente im Projekt. Sie sorgt für die Containerisierung unserer PostgresSQL-DB und dem Verwaltungstool pgadmin.

Die Maven POM enthält alle wichtigen Abhängigkeiten für unser Spring Boot-Projekt und stellt sozusagen den Build-Planer dar.


### Interfaces

Im folgenden Schaubild zeigen wir euch, wie unser API-Interface funktioniert und welche Abläufe im backend angestoßen werden, wenn der User beispielsweise ein Equipment anlegen möchte.

![Interface](assets/EquipmentInterface.png "Interface Equipment")



