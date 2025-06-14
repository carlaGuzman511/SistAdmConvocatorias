package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;//package com.tis.convocatorias.postulant.service.Career;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Requirement")
public class Requirement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idrequirement")
    private long idrequirement;

    @Column
    private int itemsQuantity;

    @Column(columnDefinition = "integer default '0'")
    private int assignedItems;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idannouncement", referencedColumnName = "idannouncement")
    private Announcement announcement;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idauxiliary", referencedColumnName = "idauxiliary")
    private Auxiliary auxiliary;


}
