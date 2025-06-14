package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Deadline")
public class Deadline {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "iddeadline")
    private long iddeadline;

    @Column
    private String deliveryPlace;

    @Column(length = 2500)
    private String description;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;

    @Column
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH:mm", timezone = "America/La_Paz")
    private Date deliveryTime;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idannouncement", referencedColumnName = "idannouncement")
    private Announcement announcement;



}
