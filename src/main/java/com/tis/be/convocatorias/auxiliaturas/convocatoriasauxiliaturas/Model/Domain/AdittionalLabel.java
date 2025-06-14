package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;//package com.tis.convocatorias.postulant.service.Career;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "AdittionalLabel")
public class AdittionalLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idadittionallabel")
    private long idadittionallabel;

    @Column
    private String field;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idlabel", referencedColumnName = "idlabel")
    private Label label;


}
