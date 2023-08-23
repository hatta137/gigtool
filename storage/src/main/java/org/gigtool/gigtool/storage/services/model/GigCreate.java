package org.gigtool.gigtool.storage.services.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gigtool.gigtool.storage.model.Band;
import org.gigtool.gigtool.storage.model.TypeOfGig;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class GigCreate extends HappeningCreate{
    private UUID typeOfGig;
    private UUID band;
}