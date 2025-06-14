package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.ScheduleCreateInput;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
public class EventCreateInput implements Serializable {

    private String eventName, place;
    private Date date;

    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH:mm", timezone = "America/La_Paz")
    private Date time;

}
