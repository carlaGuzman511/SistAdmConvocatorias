package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.MeritComplexInput;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.DetailMerit;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Merit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MeritComplexInput implements Serializable {

    private String description, note;
    private int baseScore, finalScore;
    private long idannouncement;
    private List<DetailmeritcomplexInput> merits;
}
