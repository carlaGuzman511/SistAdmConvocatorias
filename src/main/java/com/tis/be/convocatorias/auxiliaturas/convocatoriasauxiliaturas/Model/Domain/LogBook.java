package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "LogBook")
public class LogBook {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idlogbook")
    private long idlogbook;

    @Column
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH:mm", timezone = "America/La_Paz")
    private Date deliveryHour;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;

    @Column
    private int document_quantity;

    @Column(length = 2500)
    private String observation;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idlabel", referencedColumnName = "idlabel")
    private Label label;


}
