package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.*;
import org.gigtool.gigtool.storage.repositories.*;
import org.gigtool.gigtool.storage.services.model.BandCreate;
import org.gigtool.gigtool.storage.services.model.BandResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing band operations in the application.
 * @author Dario
 */
@Service
public class BandService {

    private final BandRepository bandRepository;
    private final GenreRepository genreRepository;
    private final RoleInTheBandRepository roleInTheBandRepository;
    private final EquipmentRepository equipmentRepository;
    private final HappeningRepository happeningRepository;
    private final GigRepository gigRepository;

    public BandService( BandRepository bandRepository,
                        GenreRepository genreRepository,
                        RoleInTheBandRepository roleInTheBandRepository,
                        EquipmentRepository equipmentRepository,
                        HappeningRepository happeningRepository,
                        GigRepository gigRepository ) {
        this.bandRepository = bandRepository;
        this.genreRepository = genreRepository;
        this.roleInTheBandRepository = roleInTheBandRepository;
        this.equipmentRepository = equipmentRepository;
        this.happeningRepository = happeningRepository;
        this.gigRepository = gigRepository;
    }

    /**
     * Checks if the specified equipment is used by the band during the same time as its gigs.
     *
     * @param band The band for which equipment usage is being checked.
     * @param equipment The equipment to be checked for simultaneous usage.
     * @return True if the equipment is used during the same time as any of the band's gigs, false otherwise.
     */
    private boolean equipmentIsUseSameTimeLikeBandGigs( Band band, Equipment equipment ){

        List<Gig> bandGigs = gigRepository.findGigsByBand( band );

        for (Gig bandGig: bandGigs) {

            List<Happening> overlappingHappenings = happeningRepository.findOverlappingHappeningsWithEquipment( bandGig.getStartTime(), bandGig.getEndTime(), equipment );

            if(!overlappingHappenings.isEmpty()){

                if(!(overlappingHappenings.size() == 1 && overlappingHappenings.contains( bandGig )))
                    return true;

            }
        }
        return false;
    }

    /**
     * Add a new band to the system.
     * @param bandCreate An object containing the details needed to create a new band.
     * @return A ResponseEntity containing a BandResponse object.
     */
    public ResponseEntity<BandResponse> addBand( BandCreate bandCreate ) {

        if (bandCreate.getName() == null ||
                bandCreate.getGenre() == null ||
                bandCreate.getMainRoleInTheBand() == null) {
            return ResponseEntity.badRequest().build();
        }

        Band band = new Band();

        Optional<Genre> genre = genreRepository.findById(bandCreate.getGenre());
        Optional<RoleInTheBand> role = roleInTheBandRepository.findById( bandCreate.getMainRoleInTheBand() );

        if(genre.isEmpty() || role.isEmpty())
            return ResponseEntity.notFound().build();


        Genre existingGenre = genre.get();

        RoleInTheBand existingRole = role.get();

        List<RoleInTheBand> roleInTheBandList = new ArrayList<>();

        roleInTheBandList.add( existingRole );

        band.setGenre( existingGenre );
        band.setListOfRole( roleInTheBandList );
        band.setName( bandCreate.getName() );
        band.setEquipmentList( new ArrayList<>() );

        Band savedBand = bandRepository.saveAndFlush( band );

        return ResponseEntity.ok( new BandResponse( savedBand ) );
    }

    /**
     * Retrieves a list of all bands in the system.
     * @return A response entity containing a list of band responses
     */
    public ResponseEntity<List<BandResponse>> getAllBands() {

        List<Band> bandList = bandRepository.findAll();

        List<BandResponse> responseList = bandList.stream()
                .map( BandResponse::new )
                .toList();

        return ResponseEntity.ok( responseList );
    }

    /**
     * Retrieves a specific band by its unique identifier.
     * @param bandId The unique identifier of the band to retrieve information for.
     * @return A response entity containing the retrieved band response.
     */
    public ResponseEntity<BandResponse> getBandById( UUID bandId ) {

        Optional<Band> bandOptional = bandRepository.findById( bandId );

        return bandOptional.map(band -> ResponseEntity.ok(new BandResponse( band )))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Adds equipment to a band's equipment list
     * @param bandId The unique identifier of the band to which equipment will be added.
     * @param equipmentId The unique identifier of the equipment to be added.
     * @return A response entity containing a band's response object constructed from the saved band.
     */
    public ResponseEntity<BandResponse> addEquipmentToBand( UUID bandId, UUID equipmentId ) {

        if ((equipmentId == null) || (bandId == null))
            return ResponseEntity.badRequest().build();

        Optional<Band> existingBand = bandRepository.findById( bandId );
        Optional<Equipment> existingEquipment = equipmentRepository.findById( equipmentId );

        if(existingEquipment.isEmpty() || existingBand.isEmpty())
            return ResponseEntity.notFound().build();


        Band band = existingBand.get();
        Equipment equipment = existingEquipment.get();

        if (band.getEquipmentList().contains(equipment) ||
                this.equipmentIsUseSameTimeLikeBandGigs(band, equipment)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Gig> bandGigs = gigRepository.findGigsByBand( band );

        for (Gig bandGig: bandGigs) {

            if(bandGig.getEquipmentList().contains( equipment ))
                bandGig.getEquipmentList().remove( equipment );

        }

        band.getEquipmentList().add( equipment );

        Band savedBand = bandRepository.saveAndFlush( band );

        return ResponseEntity.ok(new BandResponse( savedBand ));
    }

    /**
     * Updates an existing Band with new information.
     * @param bandId The unique identifier of the band to be updated.
     * @param bandRequest An object containing the band's updated information.
     * @return A response entity containing the updated band response.
     */
    public ResponseEntity<BandResponse> updateBand( UUID bandId, BandCreate bandRequest ) {

        if ((bandId == null))
            return ResponseEntity.badRequest().build();

        Optional<Band> existingBand = bandRepository.findById( bandId );

        if (existingBand.isEmpty())
            return ResponseEntity.notFound().build();

        Band updatedBand = existingBand.get();

        if (bandRequest.getName() != null)
            updatedBand.setName(bandRequest.getName());


        if (bandRequest.getGenre() != null) {

            Optional<Genre> existingGenre = genreRepository.findById( bandRequest.getGenre() );

            if (existingGenre.isEmpty())
                return ResponseEntity.notFound().build();

            updatedBand.setGenre( existingGenre.get() );
        }

        Band savedBand = bandRepository.saveAndFlush( updatedBand );

        return ResponseEntity.ok( new BandResponse( savedBand ) );
    }

    /**
     * Removes a specific equipment from a band's equipment list.
     *
     * @param bandId The unique identifier of the band to remove equipment from.
     * @param equipmentId The unique identifier of the equipment to be removed.
     * @return A response entity indicating the success of the equipment removal operation.
     */

    public ResponseEntity<BandResponse> deleteEquipmentFromBand( UUID bandId, UUID equipmentId ) {

        if ((equipmentId == null) || (bandId == null))
            return ResponseEntity.badRequest().build();

        Optional<Band> existingBand = bandRepository.findById( bandId );

        Optional<Equipment> existingEquipment = equipmentRepository.findById( equipmentId );

        if(existingEquipment.isEmpty() || existingBand.isEmpty())
            return ResponseEntity.notFound().build();

        Band band = existingBand.get();

        Equipment equipment = existingEquipment.get();

        if (!band.getEquipmentList().contains(equipment))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        band.getEquipmentList().remove( equipment );

        Band savedBand = bandRepository.saveAndFlush( band );

        return ResponseEntity.ok(new BandResponse( savedBand ));
    }

    /**
     * Adds a new role to an existing band.
     * @param bandId The unique identifier of the band to which the role will be added.
     * @param roleId The unique identifier of the role to be added.
     * @return A response entity indicating the success or failure of the operation.
     */
    public ResponseEntity<BandResponse> addRoleToBand( UUID bandId, UUID roleId ) {

        if ((roleId == null) || (bandId == null))
            return ResponseEntity.badRequest().build();

        Optional<Band> existingBand = bandRepository.findById( bandId );

        Optional<RoleInTheBand> existingRole = roleInTheBandRepository.findById( roleId );

        if(existingRole.isEmpty() || existingBand.isEmpty())
            return ResponseEntity.notFound().build();

        Band band = existingBand.get();
        RoleInTheBand role = existingRole.get();

        if (band.getListOfRole().contains( role ))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        band.getListOfRole().add( role );

        Band savedBand = bandRepository.saveAndFlush( band );

        return ResponseEntity.ok(new BandResponse( savedBand ));
    }

    /**
     * Removes a specific role from a band's list of roles.
     *
     * @param bandId The unique identifier of the band from which the role will be removed.
     * @param roleId The unique identifier of the role to be removed.
     * @return A response entity indicating the success of the role removal operation.
     */
    public ResponseEntity<BandResponse> deleteRoleFromBand( UUID bandId, UUID roleId ) {

        if ((roleId == null) || (bandId == null))
            return ResponseEntity.badRequest().build();

        Optional<Band> existingBand = bandRepository.findById( bandId );

        Optional<RoleInTheBand> existingRole = roleInTheBandRepository.findById( roleId );

        if(existingRole.isEmpty() || existingBand.isEmpty())
            return ResponseEntity.notFound().build();

        Band band = existingBand.get();

        RoleInTheBand role = existingRole.get();

        if (!band.getListOfRole().contains( role ))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        band.getListOfRole().remove( role );

        Band savedBand = bandRepository.saveAndFlush( band );

        return ResponseEntity.ok( new BandResponse( savedBand ) );
    }

    /**
     * Deletes an existing band from the system.
     * @return A response entity indicating the success or failure of the deletion operation.
     */
    public ResponseEntity<String> deleteBand( UUID bandId ) {

        if (bandId == null)
            return ResponseEntity.badRequest().body("No ID");

        Optional<Band> existingBand = bandRepository.findById( bandId );

        if (existingBand.isEmpty())
            return ResponseEntity.notFound().build();

        int gigsForBand = bandRepository.countGigsForBand( bandId );

        if (gigsForBand > 0)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Band has a relation to Gigs. You cannot delete this Band!");

        bandRepository.delete( existingBand.get() );

        return ResponseEntity.ok("Band is deleted");
    }
}
