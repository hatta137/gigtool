package org.gigtool.gigtool.storage.services;

import org.gigtool.gigtool.storage.model.WeightClass;
import org.gigtool.gigtool.storage.model.WeightClassList;
import org.gigtool.gigtool.storage.repositories.WeightClassListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class WeightClassListService {

    private final WeightClassListRepository weightClassListRepository;
    private final WeightClassList weightClassList;

    @Autowired
    public WeightClassListService(WeightClassListRepository weightClassListRepository) {
        this.weightClassListRepository = weightClassListRepository;
        this.weightClassList = getOrCreateWeightClassList();
    }

    public WeightClassList saveWeightClassList(WeightClassList weightClassList) {
        return weightClassListRepository.save(weightClassList);
    }

    private WeightClassList getOrCreateWeightClassList() {
        List<WeightClassList> existingLists = weightClassListRepository.findAll();

        if (!existingLists.isEmpty()) {
            return existingLists.get(0);
        } else {
            WeightClassList newWeightClassList = new WeightClassList();
            return weightClassListRepository.save(newWeightClassList);
        }
    }

    public void addWeightClassToWeightClassList(WeightClass weightClass) {
        if (weightClass == null) {
            throw new IllegalArgumentException("Weight class cannot be null.");
        }

        int endWeight = weightClass.getWeightStart() + weightClass.getDuration();
        if (endWeight > 2000) {

            endWeight = 2000;
            weightClass.setDuration(endWeight - weightClass.getWeightStart());
        }

        int actualMaxWeightInList = weightClassList.getMaxWeightInWeightClassList();

        if (weightClass.getWeightStart() >= actualMaxWeightInList) {

            weightClassList.getListOfWeightClass().add(weightClass);
            weightClassList.setMaxWeightInWeightClassList(endWeight);

        } else if (weightClass.getWeightStart() < actualMaxWeightInList && endWeight > actualMaxWeightInList) {

            WeightClass weightClassAutoGen = new WeightClass(
                    weightClass.getName(),
                    weightClass.getDescription(),
                    actualMaxWeightInList,
                    endWeight - actualMaxWeightInList
            );

            weightClassList.getListOfWeightClass().add(weightClassAutoGen);

            weightClassList.setMaxWeightInWeightClassList(endWeight);

            weightClassListRepository.save(weightClassList);
        }

        weightClassListRepository.saveAndFlush(weightClassList);

    }

    public Optional<WeightClass> getWeightClass(int index) {
        if (index < 0 || index >= weightClassList.getListOfWeightClass().size()) {
            return Optional.empty();
        }

        return Optional.of(weightClassList.getListOfWeightClass().get(index));
    }

    public WeightClassList deleteLastWeightClass() {
        if (weightClassList.getListOfWeightClass().isEmpty()) {
            throw new NoSuchElementException("Cannot delete weight class from empty list.");
        }

        int lastWeightClassDuration = weightClassList.getListOfWeightClass().get(weightClassList.getListOfWeightClass().size() - 1).getDuration();

        int newMaxWeight = weightClassList.getMaxWeightInWeightClassList() - lastWeightClassDuration;

        weightClassList.getListOfWeightClass().remove(weightClassList.getListOfWeightClass().size() - 1);

        weightClassList.setMaxWeightInWeightClassList(newMaxWeight);

        weightClassListRepository.save(weightClassList); // Speichern der Änderungen in der Datenbank

        return weightClassList;
    }

    public WeightClass getBiggestWeightClass() {
        if (weightClassList.getListOfWeightClass().isEmpty()) {
            throw new NoSuchElementException("Cannot get weight class from empty list.");
        }

        return weightClassList.getListOfWeightClass().get(weightClassList.getListOfWeightClass().size() - 1);
    }

    public ResponseEntity<WeightClassList> getWeightClassList() {
        return ResponseEntity.status(200).body( this.weightClassList );
    }

}
