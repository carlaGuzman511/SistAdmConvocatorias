package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Announcement;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AuxiliaryInput implements Serializable {

    private String name, code, academicHours;
    private long area, academinUnit;

}