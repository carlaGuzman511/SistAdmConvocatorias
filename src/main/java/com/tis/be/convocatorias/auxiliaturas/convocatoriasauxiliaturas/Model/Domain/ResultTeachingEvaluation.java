package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ResultTeachingEvaluation")
public class ResultTeachingEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idresultteachingevaluation")
    private long idresultteachingevaluation;
    @Column
    private double score;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idknowledgeevaluation", referencedColumnName = "idknowledgeevaluation")
    private KnowledgeEvaluation knowledgeEvaluation;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idpostulant", referencedColumnName = "idpostulant")
    private Postulant postulantes;


}
