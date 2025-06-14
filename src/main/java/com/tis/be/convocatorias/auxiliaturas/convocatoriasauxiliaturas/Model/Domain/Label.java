package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Label")
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idlabel")
    private long idlabel;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idpostulant", referencedColumnName = "idpostulant")
    private Postulant postulantes;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idauxiliary", referencedColumnName = "idauxiliary")
    private Auxiliary auxiliary;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idannouncement", referencedColumnName = "idannouncement")
    private Announcement announcement;


}
