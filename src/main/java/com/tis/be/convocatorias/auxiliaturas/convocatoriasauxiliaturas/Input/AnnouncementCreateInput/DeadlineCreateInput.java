package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.AnnouncementCreateInput;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
public class DeadlineCreateInput implements Serializable {

    private String description, place;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/La_Paz")
    private Date date;

    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH:mm", timezone = "America/La_Paz")
    private Date time;
}
