package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;//package com.tis.convocatorias.postulant.service.Career;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "AcademicUnit")
public class AcademicUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idacademicunit")
    private long idacademicunit;

    @Column
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idfaculty", referencedColumnName = "idfaculty")
    private Faculty faculty;

}
