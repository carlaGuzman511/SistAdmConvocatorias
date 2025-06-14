package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "User")
@EqualsAndHashCode(exclude = {
        "announcements",
        "auxiliarys",
        "thematics"
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="iduser")
    private long iduser;

    @Column(name = "identityCard", length = 100)
    private String identity;

    @Column(name = "password", length = 500, nullable = false)
    private String password;

    @Column(name = "enable", columnDefinition = "boolean default false")
    private boolean enable;

    @Column
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH:mm")
    private Date startHour;

    @Column
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH:mm")
    private Date endHour;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idrole", referencedColumnName = "idrole")
    private Role role;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idperson", referencedColumnName = "idperson")
    private Person person;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"users", "auxiliarys", "postulants", "hibernateLazyInitializer", "handler"})
    private Set<Announcement> announcements = new HashSet<>();;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"users", "thematics", "announcements", "hibernateLazyInitializer", "handler"})
    private Set<Auxiliary> auxiliarys = new HashSet<>();;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"users", "auxiliarys", "hibernateLazyInitializer", "handler"})
    private Set<Thematic> thematics = new HashSet<>();

}
