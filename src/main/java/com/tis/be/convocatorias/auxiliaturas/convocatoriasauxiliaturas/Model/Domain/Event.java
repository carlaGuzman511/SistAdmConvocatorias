package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Table
@Entity(name = "Event")
public class Event {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "idevent")
    private long idevent;
    @Column
    private String name;

    @Column(length = 2500)
    private String place;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateEvent;

    @Column
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH:mm", timezone = "America/La_Paz")
    private Date timeEvent;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idschedule", referencedColumnName = "idschedule")
    private Schedule schedule;
}
