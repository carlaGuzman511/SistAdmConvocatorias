package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LogBookInput implements Serializable {

    @JsonFormat(pattern = "HH:mm",timezone = "America/La_Paz")
    private Date deliveryHour;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;

    private int documentQuantity;
    private String observation;
    private long label;

}
