package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ResultSubsubdetailMeritEvaluation")
public class ResultSubsubdetailMeritEvaluation {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "idresultsubsubdetailmeritevaluation")
    private long idresultsubsubdetailmeritevaluation;

    @Column
    private float score;

    @Column(length = 2500, columnDefinition = "varchar(2500) default 'no observations'")
    private String observations;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idsubsubdetailmerit", referencedColumnName = "idsubsubdetailmerit")
    private SubsubdetailMerit subsubdetailMerit;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idresultsubdetailmeritevaluation", referencedColumnName = "idresultsubdetailmeritevaluation")
    private ResultSubdetailMeritEvaluation resultSubdetailMeritEvaluation;

}
