package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "DetailMerit")
public class DetailMerit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "iddetailmerit")
    private long iddetailmerit;
    @Column
    private String description;
    @Column
    private int percentage;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idmerit", referencedColumnName = "idmerit")
    private Merit merit;

}
