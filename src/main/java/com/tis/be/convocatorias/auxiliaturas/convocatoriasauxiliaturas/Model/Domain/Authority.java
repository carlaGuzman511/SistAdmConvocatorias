package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Authority")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "idauthority")
    private long idauthority;

    @Column
    private String name;

    @Column
    private String position;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idannouncement", referencedColumnName = "idannouncement")
    private Announcement announcement;
}
