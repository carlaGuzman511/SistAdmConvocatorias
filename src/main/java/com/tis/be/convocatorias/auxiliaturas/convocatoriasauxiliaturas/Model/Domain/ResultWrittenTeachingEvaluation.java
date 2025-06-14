package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ResultWrittenTeachingEvaluation")
public class ResultWrittenTeachingEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idresultwrittenteachingevaluation")
    private long idresultwrittenteachingevaluation;
    @Column
    private double score;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idknowledgeevaluation", referencedColumnName = "idknowledgeevaluation")
    private KnowledgeEvaluation knowledgeEvaluation;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idpostulant", referencedColumnName = "idpostulant")
    private Postulant postulantes;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idauxiliary", referencedColumnName = "idauxiliary")
    private Auxiliary auxiliary;

}
