package org.gigtool.gigtool.storage;

import org.gigtool.gigtool.storage.model.*;
import org.gigtool.gigtool.storage.services.*;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalTime;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BeforeAllTests {
    public static WeightClass wClass1 = new WeightClass("leicht", "kleine Teile", 0, 2);
    public static WeightClass wClass2 = new WeightClass("mittel", "mittlere Teile", 2, 8);
    public static WeightClass wClass3 = new WeightClass("groß", "große Teile", 11, 20);
    public static WeightClass wClass4 = new WeightClass("ganz groß", "ganz große Teile", 32, 1000);
    public static WeightClassList weightClassList = new WeightClassList().addWeightClass(wClass1).addWeightClass(wClass2).addWeightClass(wClass3).addWeightClass(wClass4);

    public static Address address1 = new Address("Hauptstrasse", "Erfurt", "99086", "Deutschland", 10);
    public static Address address2 = new Address("Andere-Straße", "Erfurt", "99086", "Deutschland", 20);
    public static Address address3 = new Address("Neue-Straße", "Erfurt", "99086", "Deutschland", 30);
    public static Address address4 = new Address("Musterstrasse", "Erfurt", "99086", "Deutschland", 40);

    public static TypeOfLocation typeOfLocation1 = new TypeOfLocation("Lager-EF", "Garage in Erfurt");
    public static TypeOfLocation typeOfLocation2 = new TypeOfLocation("Buero-EF", "Buero in Erfurt");
    public static TypeOfLocation typeOfLocation3 = new TypeOfLocation("Proberaum-WM", "Proberaum Band1 in Weimar");
    public static TypeOfLocation typeOfLocation4 = new TypeOfLocation("Lager-WM", "Lagerraum in Weimar");
    public static TypeOfLocation typeOfLocation5 = new TypeOfLocation("Auftritt", "Gig");

    public static Location location1 = new Location(address1, typeOfLocation1);
    public static Location location2 = new Location(address2, typeOfLocation2);
    public static Location location3 = new Location(address3, typeOfLocation3);
    public static Location location4 = new Location(address4, typeOfLocation4);
    public static Location location5 = new Location(address3, typeOfLocation5);

    public static TypeOfEquipment typeGuitar6     = new TypeOfEquipment("Gitarre-6", "Gitarre mit 6 Saiten");
    public static TypeOfEquipment typeGuitar12    = new TypeOfEquipment("Gitarre-12", "Gitarre mit 12 Saiten");
    public static TypeOfEquipment typeBassGuitar4 = new TypeOfEquipment("Bass-4", "Bass mit 4 Saiten");
    public static TypeOfEquipment typeBassGuitar5 = new TypeOfEquipment("Bass-5", "Bass mit 5 Saiten");
    public static TypeOfEquipment typeDrumset2    = new TypeOfEquipment("Schlagzeug-2", "Schlagzeug mit 2 Toms");

    public static Dimension dimension1 = new Dimension(110, 40, 20);
    public static Dimension dimension2 = new Dimension(140, 40, 20);
    public static Dimension dimension3 = new Dimension(250, 150, 100);

    public static Equipment equipment1 = new Equipment("Gibson Les Paul", "Gitarre LP", typeGuitar6, 6, dimension1, LocalDate.now(), location1,2000.0f, weightClassList);
    public static Equipment equipment2 = new Equipment("Fender-Bass", "Bass von Fender", typeBassGuitar4, 7, dimension2, LocalDate.now(), location2, 1050.0f, weightClassList);
    public static Equipment equipment3 = new Equipment("Fender - Gitarre", "Fender Gitarre 12 Saiter", typeGuitar12, 5, dimension1, LocalDate.now(), location3, 1000.0f, weightClassList);
    public static Equipment equipment4 = new Equipment("Schlagzeug DW", "Kleines Set fur Cockteil-Gigs", typeDrumset2, 70, dimension3, LocalDate.now(), location3, 2300.0f, weightClassList);
    public static Equipment equipment5 = new Equipment("Fender - Bass 5", "Fender Bass 5 Saiter", typeBassGuitar5, 7, dimension2, LocalDate.now(), location1, 1000.0f, weightClassList);

    public static Genre genreJazz     = new Genre("Jazz", "komische Musik");
    public static Genre genreRock     = new Genre("Rock", "RocknRoll");
    public static Genre genrePop      = new Genre("Pop",  " = moderner Schlager");
    public static Genre genreFunkRock = new Genre("Funk-Rock",  "sowas wie die Red Hot Chillipeppers");

    public static RoleInTheBand roleFrontSinger       = new RoleInTheBand("Front-Sänger", "Hauptgesang");
    public static RoleInTheBand roleBackgroundSinger  = new RoleInTheBand("Background-Sänger", "Begleitender Gesang");
    public static RoleInTheBand roleLeadGuitarist     = new RoleInTheBand("Gitarrist", "Haupt-Gitarrist");
    public static RoleInTheBand roleDrummer           = new RoleInTheBand("Schlagzeuger", "Person die trommelt");
    public static RoleInTheBand roleBass              = new RoleInTheBand("Bassist", "wäre gern Gitarrist");

    public static TypeOfGig typeOpenAir   =   new TypeOfGig("OpenAir", "Bühne im Freien");
    public static TypeOfGig typeTent      =   new TypeOfGig("Festzelt", "Bühne im Festzelt");
    public static TypeOfGig typePrivate   =   new TypeOfGig("Privatveranstaltung", "-");
    public static TypeOfGig typeBirthday  =   new TypeOfGig("Geburtstagsfeier", "Jemand wurde mal geboren");

    public static Timetable myTimetable;

    public static EquipmentList equipmentList1 = new EquipmentList().addEquipment(equipment1).addEquipment(equipment2).addEquipment(equipment3);

    public static Timeslot myTimeslot = new Timeslot(LocalTime.of(14,00), LocalTime.of(16,00), LocalDate.of(2022,10,19));
    public static Timeslot myTimeslot2 = new Timeslot(LocalTime.of(17,00), LocalTime.of(20,00), LocalDate.of(2022,10,15));
    public static Timeslot myTimeslot3 = new Timeslot(LocalTime.of(17,00), LocalTime.of(20,00), LocalDate.of(2022,10,19));
    public static TypeOfGig myTypeOfGig = new TypeOfGig("Konzert","musik genre");
    public static TypeOfGig myTypeOfGig2 = new TypeOfGig("Festival","musik genre2");
    public static Band myBand = new Band("Birgitt",genreJazz, roleFrontSinger);
    public static Band myBand2 = new Band("ACDC",genreRock, roleLeadGuitarist);
    public static Gig myGig = new Gig("Auftritt 1", "Ester Auftritt", address3,myTimeslot,myTypeOfGig,myBand2);
    public static Gig myGig2 = new Gig("Auftritt 2", "Zweiter Auftritt", address4,myTimeslot2,myTypeOfGig2,myBand);
    public static Gig myGig3 = new Gig("Auftritt 3", "Dritter Auftritt", address4,myTimeslot3,myTypeOfGig2,myBand);

    public static Band band1 = new Band("The Beatles", genrePop, roleDrummer);
    public static Band band2 = new Band();
}
