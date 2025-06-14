package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
public class EventInput implements Serializable {

    private String name, place;
    private Date timeEvent;

    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH:mm", timezone = "America/La_Paz")
    private Date dateEvent;

    private long schedule;
}
