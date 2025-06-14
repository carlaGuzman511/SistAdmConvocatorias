package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ResultLaboratoryEvaluation")
public class ResultLaboratoryEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idresultlaboratoryevaluation")
    private long idresultlaboratoryevaluation;
    @Column
    private double score;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idlaboratoryevaluation", referencedColumnName = "idlaboratoryevaluation")
    private LaboratoryEvaluation laboratoryEvaluation;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idpostulant", referencedColumnName = "idpostulant")
    private Postulant postulantes;


}
