package org.gigtool.gigtool.storage;

import org.gigtool.gigtool.storage.model.Location;
import org.gigtool.gigtool.storage.services.*;
import org.gigtool.gigtool.storage.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Timetable.class)
public class TimetableTest extends BeforeAllTests{

    @Test
    void testAddHappening(){
        myTimetable = new Timetable();
        assertTrue(myTimetable.addHappening(myGig));
        assertFalse(myTimetable.addHappening(myGig));
        assertTrue(myTimetable.addHappening(myGig2));
        assertTrue(myTimetable.addHappening(myGig3));
    }

    @Test
    void testGetHappeningFromBand(){
        testAddHappening();
        ArrayList<Happening> expHappenings = new ArrayList<>(); //nur Happenings mit myBand in die List
        expHappenings.add(myGig2);
        expHappenings.add(myGig3);
        //getHappening from Band Test
        ArrayList<Happening> gettedHappenings = myTimetable.getHappening(myBand);
        for ( int i = 0; i < gettedHappenings.size(); i++){
            assertEquals(expHappenings.get(i), gettedHappenings.get(i));
        }
    }

    @Test
    void testGetHappeningFromTypeOfGig(){
        testAddHappening();
        ArrayList<Happening> expHappenings = new ArrayList<>(); //nur Happenings mit myBand in die List
        expHappenings.add(myGig2);
        expHappenings.add(myGig3);
        //getHappening from Band Test
        ArrayList<Happening> gettedHappenings = myTimetable.getHappening(myTypeOfGig2);
        for ( int i = 0; i < gettedHappenings.size(); i++){
            assertEquals(expHappenings.get(i), gettedHappenings.get(i));
        }
    }

    @Test
    void testGetHappeningFromDate(){
        testAddHappening();
        //nur Happenings mit datum 19.10.2022
        ArrayList<Happening> expHappenings = new ArrayList<>();
        expHappenings.add(myGig);
        expHappenings.add(myGig3);
        //getHappening FromDate Test
        ArrayList<Happening> gettedHappenings = myTimetable.getHappening(LocalDate.of(2022,10,19));
        for ( int i = 0; i < gettedHappenings.size(); i++){
            assertEquals(expHappenings.get(i), gettedHappenings.get(i));
        }
    }

    @Test
    void testGetHappeningFromLocation(){
        testAddHappening();
        //nur Happenings mit location myaddr2
        ArrayList<Happening> expHappenings = new ArrayList<>();
        expHappenings.add(myGig2);
        expHappenings.add(myGig3);
        //getHappening FromAddr Test
        Location gps = new Location(address4, null);
        ArrayList<Happening> gettedHappenings = myTimetable.getHappening(gps);
        for ( int i = 0; i < gettedHappenings.size(); i++){
            assertEquals(expHappenings.get(i), gettedHappenings.get(i));
        }
    }

    @Test
    void testGetHappeningFromName(){
        testAddHappening();
        //getHappening FromName Test
        ArrayList<Happening> expHappenings = new ArrayList<>();
        expHappenings.add(myGig2);
        assertEquals(expHappenings, myTimetable.getHappeningByName("Auftritt 2"));
    }

    @Test
    void testDeleteHappening(){
        testAddHappening();
        assertNotNull(myTimetable.getListOfHappening().get(2));
        ArrayList<Happening> expHappenings = new ArrayList<>();
        expHappenings.add(myGig3);
        assertEquals(expHappenings,myTimetable.getHappeningByName("Auftritt 3"));
        expHappenings = new ArrayList<>();
        assertTrue(myTimetable.deleteHappening(myGig3));
        assertEquals(expHappenings,myTimetable.getHappeningByName("Auftritt 3"));
        assertFalse(myTimetable.deleteHappening(myGig3));
    }
}


