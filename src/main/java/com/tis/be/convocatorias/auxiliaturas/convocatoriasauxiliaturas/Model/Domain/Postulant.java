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
@Table(name = "Postulant")
@EqualsAndHashCode(exclude = "announcements")
public class Postulant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idpostulant")
    private long idpostulant;
    @Column
    private boolean status;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idperson", referencedColumnName = "idperson")
    private Person person;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idcareer", referencedColumnName = "idcareer")
    private Career career;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "postulants")
    @JsonIgnoreProperties({"postulants", "auxiliarys", "users", "hibernateLazyInitializer", "handler"})
    private Set<Announcement> announcements = new HashSet<>();

}
