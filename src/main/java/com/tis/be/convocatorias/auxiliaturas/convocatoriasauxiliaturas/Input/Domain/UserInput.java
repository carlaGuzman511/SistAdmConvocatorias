package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Person;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class UserInput implements Serializable {

    private String ci, password;
    private long role;
    private Person person;
    private boolean enable;

    @Temporal(TemporalType.DATE)
    private Date startDate, endDate;

    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH:mm")
    private Date startHour, endHour;

    private List<Long> announcements, auxiliarys, thematics;

}
