package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
public class DeadlineInput implements Serializable {

    private String deliveryPlace, description;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;

    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH:mm", timezone = "America/La_Paz")
    private Date deliveryTime;

    private long announcement;
}
