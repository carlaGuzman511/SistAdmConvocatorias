package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;//package com.tis.convocatorias.postulant.service.Career;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Career")
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idcareer")
    private long idcareer;
    @Column
    private int codCareer;
    @Column
    private String name;

}
