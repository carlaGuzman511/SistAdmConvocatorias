package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Table
@Entity(name = "DocumentDetail")
public class DocumentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "iddocumentdetail")
    private long iddocumentdetail;

    @Column(length = 2500)
    private String detail;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "iddocument", referencedColumnName = "iddocument")
    private Document document;
}
