package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Update;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UpdateAnnouncementInput implements Serializable {

    private String title, description, courtsDescription, appointment;
    private long idannouncement, area, management, academicUnit, faculty;
    private boolean pack, state;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publicationDate, dueDate;
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH:mm")
    private Date publicationHour, dueHour;

    private List<Long> auxiliarys, postulants;
}
