package org.gigtool.modules;


import org.gigtool.models.Gig;
import org.gigtool.models.Location;
import org.gigtool.models.TypeOfGig;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

/***
 * Author: Dario Daßler
 * The Timetable class represents a schedule of Happening objects, which can include Gig and other types of events.
 */
public class Timetable {

    private ArrayList<Happening> listOfHappening = new ArrayList<>();
    public Timetable() {
    }

    /**
     * Constructor with only one Parameter.
     * @param listOfHappening a ready list with Happenings
     */
    public Timetable(ArrayList<Happening > listOfHappening) {
        this.listOfHappening = listOfHappening;
    }

    public ArrayList<Happening> getListOfHappening() {
        return listOfHappening;
    }

    /***
     * Getter & Setter
     * Author: Dario Daßler
     */

    public void setListOfHappening(ArrayList<Happening> listOfHappening) {
        this.listOfHappening = listOfHappening;
    }

    /***
     * Author: Dario Daßler
     * Adds a new Happening to the list of Happenings if there is no time slot collision with any existing Happenings.
     * @param newHappening The Happening object to be added to the list.
     * @return True if the Happening was successfully added, false otherwise.
     */
    public boolean addHappening(Happening newHappening){

        if(newHappening != null) {

            if(this.listOfHappening != null) {

                for (Happening slot : this.listOfHappening) {

                    //Kollisonsprüfung
                    if (newHappening.getTimeslot().getDate() == slot.getTimeslot().getDate()) {

                        LocalTime slotStartTime = slot.getTimeslot().getStartTime();

                        LocalTime slotEndTime = slot.getTimeslot().getEndTime();

                        LocalTime parameterStartTime = newHappening.getTimeslot().getStartTime();

                        LocalTime parameterEndTime = newHappening.getTimeslot().getEndTime();

                        if ((parameterStartTime.isAfter(slotStartTime) && parameterStartTime.isBefore(slotEndTime)) ||
                                (parameterEndTime.isAfter(slotStartTime) && parameterEndTime.isBefore(slotEndTime)) || (parameterEndTime == slotEndTime) && (parameterStartTime == slotStartTime)) {

                            return false;

                        }

                    }

                }

            }

            this.listOfHappening.add(newHappening);

            return true;

        }

        return false;

    }


    /**
     * Author: Dario Daßler
     * Returns an ArrayList of Happenings that occur on the specified date.
     *
     * @param date The LocalDate object representing the date to filter the Happenings by.
     * @return An ArrayList of Happening objects that occur on the specified date.
     */

    public ArrayList<Happening> getHappening(LocalDate date){

        ArrayList<Happening> listOfHappeningByDate = new ArrayList<>();

        for( Happening Happening : this.listOfHappening){

            if(Happening.getTimeslot().getDate() == date){

                listOfHappeningByDate.add(Happening);

            }

        }

        return listOfHappeningByDate;

    }

    /**
     * Author: Dario Daßler
     * Returns an ArrayList of Gigs that feature the specified Band.
     *
     * @param band The Band object to filter the Gigs by.
     * @return An ArrayList of Gig objects that feature the specified Band.
     */
    public ArrayList<Happening> getHappening(Band band){

        ArrayList<Happening> listOfHappeningByBand = new ArrayList<>();

        for( Happening Happening : this.listOfHappening){

            if (Happening instanceof Gig gig) {

                if (gig.getBand() == band) {

                    listOfHappeningByBand.add(gig);

                }

            }

        }
        return listOfHappeningByBand;
    }

    /***
     * Author: Dario Daßler
     * This method searches for Happenings that have a specific location.
     @param location the Location object to search for
     @return an ArrayList of Happening objects that are associated with the specified Location
     */
    public ArrayList<Happening> getHappening(Location location){

        ArrayList<Happening> listOfHappeningByLocation = new ArrayList<>();

        for( Happening Happening : this.listOfHappening){

            if(Happening.getAddress() == location.getAddress()){

                listOfHappeningByLocation.add(Happening);

            }

        }

        return listOfHappeningByLocation;

    }

    /***
     * Author: Dario Daßler
     * This method retrieves a list of Happening objects of a specific TypeOfGig.
     * The code iterates through each Happening in the listOfHappening, and checks if it is an instance of Gig.
     * @param typeOfGig the type of gig to filter by
     * @return an ArrayList of Happening objects that have the specified type of gig
     */

   public ArrayList<Happening> getHappening(TypeOfGig typeOfGig){
        ArrayList<Happening> listOfHappeningByTypeOfGig = new ArrayList<>();

        for(Happening Happening : this.listOfHappening){

            if (Happening instanceof Gig gig) {

                if (gig.getTypeOfGig() == typeOfGig) {

                    listOfHappeningByTypeOfGig.add(Happening);
                }
            }
        }
        return listOfHappeningByTypeOfGig;
    }

    /***
     * Author: Dario Daßler
     * This method searches for Happenings that have a specific name
     * @param name the name of the Happenings to search for
     * @return an ArrayList of Happening objects with the given name, or an empty ArrayList if no matches are found
     */
    public ArrayList<Happening> getHappeningByName(String name){

        ArrayList<Happening> listOfHappeningByName = new ArrayList<>();

        for( Happening Happening : this.listOfHappening){

            if(Objects.equals(Happening.getName(), name)){

                listOfHappeningByName.add(Happening);

            }

        }

        return listOfHappeningByName;

    }

    /***
     * Author: Dario Daßler
     * This method is a straightforward implementation of deleting a Happening object from the list of Happening objects.
     * It checks whether the input Happening object is not null and then removes it from the list.
     * @param Happening  represents a happening or event
     * @return boolean
     */
    public boolean deleteHappening(Happening Happening){

        if(Happening != null){

            return this.listOfHappening.remove(Happening);

        }

        return false;
    }
}


