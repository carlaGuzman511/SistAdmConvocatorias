package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Table
@Entity(name = "Document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "iddocument")
    private long iddocument;

    @Column(length = 500)
    private String name;

    @Column(length = 2500, columnDefinition = "varchar(2500) default 'atributo no obligatorio'")
    private String note;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idannouncement", referencedColumnName = "idannouncement")
    private Announcement announcement;
}
