package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "LaboratoryEvaluation")
public class LaboratoryEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idlaboratoryevaluation")
    private long idlaboratoryevaluation;
    @Column
    private int percentage;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idauxiliary", referencedColumnName = "idauxiliary")
    private Auxiliary auxiliary;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idthematic", referencedColumnName = "idthematic")
    private Thematic thematic;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idknowledge", referencedColumnName = "idknowledge")
    private Knowledge knowledge;


}
