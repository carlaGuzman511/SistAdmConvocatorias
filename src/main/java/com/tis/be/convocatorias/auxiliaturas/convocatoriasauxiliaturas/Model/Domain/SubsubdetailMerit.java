package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "SubsubdetailMerit")
public class SubsubdetailMerit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idsubsubdetailmerit")
    private long idsubsubdetailmerit;
    @Column
    private String description;
    @Column
    private int percentage;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idsubdetailmerit", referencedColumnName = "idsubdetailmerit")
    private SubdetailMerit subdetailMerit;


}
