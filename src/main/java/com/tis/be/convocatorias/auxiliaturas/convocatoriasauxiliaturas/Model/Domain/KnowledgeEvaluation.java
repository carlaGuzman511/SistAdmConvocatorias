package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "KnowledgeEvaluation")
public class KnowledgeEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idknowledgeevaluation")
    private long idknowledgeevaluation;
    @Column
    private String description;
    @Column
    private int percentage;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idknowledge", referencedColumnName = "idknowledge")
    private Knowledge knowledge;

}
