package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ResultSubdetailMeritEvaluation")
public class ResultSubdetailMeritEvaluation {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "idresultsubdetailmeritevaluation")
    private long idresultsubdetailmeritevaluation;

    @Column
    private float score;

    @Column(length = 2500, columnDefinition = "varchar(2500) default 'no observations'")
    private String observations;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idsubdetailmerit", referencedColumnName = "idsubdetailmerit")
    private SubdetailMerit subdetailMerit;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idlabel", referencedColumnName = "idlabel")
    private Label label;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idresultdetailmeritevaluation", referencedColumnName = "idresultdetailmeritevaluation")
    private ResultDetailMeritEvaluation resultDetailMeritEvaluation;

}
