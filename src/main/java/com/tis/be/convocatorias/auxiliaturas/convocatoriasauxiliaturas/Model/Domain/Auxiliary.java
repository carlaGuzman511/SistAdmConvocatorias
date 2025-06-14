package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Auxiliary")
@EqualsAndHashCode(exclude = {"announcements", "thematics", "users"})
public class Auxiliary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idauxiliary")
    private long idauxiliary;
    @Column
    private String name;
    @Column
    private String code;
    @Column
    private String academicHours;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idarea", referencedColumnName = "idarea")
    private Area area;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idacademicunit", referencedColumnName = "idacademicunit")
    private AcademicUnit academicUnit;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "auxiliarys")
    @JsonIgnoreProperties({"auxiliarys", "postulants", "users", "hibernateLazyInitializer", "handler"})
    private Set<Announcement> announcements = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"auxiliarys", "users, announcements", "hibernateLazyInitializer", "handler"})
    private Set<Thematic> thematics = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "auxiliarys")
    @JsonIgnoreProperties({"auxiliarys", "announcements", "thematics", "hibernateLazyInitializer", "handler"})
    private Set<User> users = new HashSet<>();

}
