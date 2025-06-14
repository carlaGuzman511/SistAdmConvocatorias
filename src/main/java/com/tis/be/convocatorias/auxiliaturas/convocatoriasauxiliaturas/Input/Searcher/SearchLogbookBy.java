package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Searcher;


import lombok.Data;

import java.io.Serializable;

@Data
public class SearchLogbookBy implements Serializable {


    private String text;
    private boolean status;
    private long idannouncement;
}
