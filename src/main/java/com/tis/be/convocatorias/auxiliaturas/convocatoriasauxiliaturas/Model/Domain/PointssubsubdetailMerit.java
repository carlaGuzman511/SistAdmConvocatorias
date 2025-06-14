package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "PointssubsubdetailMerit")
public class PointssubsubdetailMerit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idpointssubsubdetailmerit")
    private long idpointssubsubdetailmerit;
    @Column
    private String description;
    @Column
    private double points;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idsubsubdetailmerit", referencedColumnName = "idsubsubdetailmerit")
    private SubsubdetailMerit subsubdetailMerit;

}
