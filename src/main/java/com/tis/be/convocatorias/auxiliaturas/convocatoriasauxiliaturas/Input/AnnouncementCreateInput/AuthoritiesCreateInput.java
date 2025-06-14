package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.AnnouncementCreateInput;


import lombok.Data;

import java.io.Serializable;

@Data
public class AuthoritiesCreateInput implements Serializable {

    private String name, position;
}
