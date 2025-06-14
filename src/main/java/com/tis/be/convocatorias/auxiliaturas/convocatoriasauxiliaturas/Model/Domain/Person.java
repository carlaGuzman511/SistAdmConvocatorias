package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    @Column(name="idperson")
    private long id;
    @Column(name="ci")
    private String ci;
    @Column
    private String name;
    @Column(name = "surname")
    private String lastName;
    @Column
    private String address;
    @Column(name = "phone")
    private int phoneNumber;
    @Column
    private String email;
}
