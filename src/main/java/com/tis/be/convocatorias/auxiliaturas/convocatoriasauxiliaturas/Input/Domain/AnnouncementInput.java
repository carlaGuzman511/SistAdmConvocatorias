package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AnnouncementInput implements Serializable {

    private String title, description, courtsDescription, appointment;
    private long area, management, academicUnit, faculty;
    private boolean pack, state;

//    @Temporal(TemporalType.DATE)
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private Date publicationDate, dueDate;
//    @Temporal(TemporalType.TIME)
//    @JsonFormat(pattern = "HH:mm")
//    private Date publicationHour, dueHour;

    private List<Long> auxiliary, postulant;

}
