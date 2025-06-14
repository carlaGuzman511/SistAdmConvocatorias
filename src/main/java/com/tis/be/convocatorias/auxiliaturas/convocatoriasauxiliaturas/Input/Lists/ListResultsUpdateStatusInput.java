package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Update.ResultUpdateStatusInput;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ListResultsUpdateStatusInput implements Serializable {

    List<ResultUpdateStatusInput> resultUpdateStatusInputs = new ArrayList<>();

}
