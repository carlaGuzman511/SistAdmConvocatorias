package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUserInput implements Serializable {


        private String ci, password;


}
