package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.ScheduleCreateInput;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ScheduleCreateInput implements Serializable {

    private String description, note;
    private long idAnnouncement;
    private List<EventCreateInput> events;
}
