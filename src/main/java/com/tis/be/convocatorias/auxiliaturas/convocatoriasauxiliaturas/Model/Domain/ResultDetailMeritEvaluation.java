package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ResultDetailMeritEvaluation")
public class ResultDetailMeritEvaluation {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "idresultdetailmeritevaluation")
    private long idresultdetailmeritevaluation;

    @Column
    private float score;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "iddetailmerit", referencedColumnName = "iddetailmerit")
    private DetailMerit detailMerit;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idlabel", referencedColumnName = "idlabel")
    private Label label;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idresultmeritevaluation", referencedColumnName = "idresultmeritevaluation")
    private ResultMeritEvaluation resultMeritEvaluation;

}
