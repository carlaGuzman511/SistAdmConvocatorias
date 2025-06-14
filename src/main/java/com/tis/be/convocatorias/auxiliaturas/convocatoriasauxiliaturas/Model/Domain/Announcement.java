package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Announcement")
@ToString(exclude = {"auxiliarys", "postulants", "users"})
@EqualsAndHashCode(exclude = {"auxiliarys", "postulants", "users"})
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "idannouncement")
    private long idannouncement;
    @Column(name = "title", length = 1000, nullable = false)
    private String title;
    @Column(name = "description", length = 500, nullable = false)
    private String description;
    @Column(name = "appointment", length = 800, nullable = false)
    private String appointment;
    @Column(name = "pack", columnDefinition = "boolean default false")
    private boolean pack;
    @Column(name = "state", columnDefinition = "boolean default false")
    private boolean state;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idarea", referencedColumnName = "idarea")
    private Area area;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idmanagement", referencedColumnName = "idmanagement")
    private Management management;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idacademicunit", referencedColumnName = "idacademicunit")
    private AcademicUnit academicUnit;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idfaculty", referencedColumnName = "idfaculty")
    private Faculty faculty;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"announcements", "thematics", "users", "hibernateLazyInitializer", "handler"})
    private Set<Auxiliary> auxiliarys = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"announcements", "hibernateLazyInitializer", "handler"})
    private Set<Postulant> postulants = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "announcements")
    @JsonIgnoreProperties({"announcements", "auxiliarys", "thematics", "hibernateLazyInitializer", "handler"})
    private Set<User> users = new HashSet<>();

}










//    @Column
//    @Temporal(TemporalType.DATE)
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private Date publicationDate;
//    @Column
//    @Temporal(TemporalType.DATE)
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private Date dueDate;
//    @Column
//    @Temporal(TemporalType.TIME)
//    @JsonFormat(pattern = "HH:mm")
//    private Date publicationHour;
//    @Column
//    @Temporal(TemporalType.TIME)
//    @JsonFormat(pattern = "HH:mm")
//    private Date dueHour;

