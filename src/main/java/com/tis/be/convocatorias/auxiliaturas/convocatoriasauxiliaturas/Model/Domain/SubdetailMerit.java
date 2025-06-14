package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "SubdetailMerit")
public class SubdetailMerit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idsubdetailmerit")
    private long idsubdetailmerit;
    @Column
    private String description;
    @Column
    private int percentage;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "iddetailmerit", referencedColumnName = "iddetailmerit")
    private DetailMerit detailMerit;


}
