package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Person;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserSingleInput implements Serializable {

    private String ci, password;
    private long role;
    private Person person;
    private boolean enable;

    @Temporal(TemporalType.DATE)
    private Date startDate, endDate;

    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH:mm")
    private Date startHour, endHour;
}
