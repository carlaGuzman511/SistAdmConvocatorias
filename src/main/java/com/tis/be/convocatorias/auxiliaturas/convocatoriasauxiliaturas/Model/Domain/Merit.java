package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Merit")
public class Merit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idmerit")
    private long idmerit;
    @Column(length = 500)
    private String description;
    @Column
    private int baseScore;
    @Column
    private int finalScore;
    @Column(length = 500)
    private String note;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idannouncement", referencedColumnName = "idannouncement")
    private Announcement announcement;

}
