package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ResultMeritEvaluation")
public class ResultMeritEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "idresultmeritevaluation")
    private long idresultmeritevaluation;

    @Column
    private float score;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idmerit", referencedColumnName = "idmerit")
    private Merit merit;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idlabel", referencedColumnName = "idlabel")
    private Label label;


}
