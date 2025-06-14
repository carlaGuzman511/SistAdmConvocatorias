package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Update.RequestPostulantUpdateInput;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ListRequestPostulantUpdateStatusInput implements Serializable {

    List<RequestPostulantUpdateInput> requestPostulantUpdateInputs = new ArrayList<>();
}
