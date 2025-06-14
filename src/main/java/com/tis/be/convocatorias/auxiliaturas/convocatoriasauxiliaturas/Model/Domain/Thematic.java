package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;//package com.tis.convocatorias.postulant.service.Career;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Thematic")
@EqualsAndHashCode(exclude = {"auxiliarys", "users"})
public class Thematic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idthematic")
    private long idthematic;

    @Column
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "thematics")
    @JsonIgnoreProperties({"thematics", "announcements", "users", "hibernateLazyInitializer", "handler"})
    private Set<Auxiliary> auxiliarys = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "thematics")
    @JsonIgnoreProperties({"thematics", "auxiliarys", "announcements", "hibernateLazyInitializer", "handler"})
    private Set<User> users = new HashSet<>();


}
