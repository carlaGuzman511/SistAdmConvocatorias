package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Person;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class LabelTeachingInput implements Serializable {

    private Person person;
    private long career, announcement;
    private List<Long> auxiliary;

}
