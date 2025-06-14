package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Courts")
@EqualsAndHashCode(exclude = "announcement")
public class Courts {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idcourts")
    private long idcourts;
    @Column(length = 1500)
    private String description;
    @Column(columnDefinition = "int default 0")
    private int meritsCourts;
    @Column(columnDefinition = "int default 0")
    private int knowledgeCourts;
    @Column(columnDefinition = "int default 0")
    private int studentDelegateMerit;
    @Column(columnDefinition = "int default 0")
    private int studentDelegateKnowledge;
    @Column(columnDefinition = "int default 0")
    private int numberMeritCourts;
    @Column(columnDefinition = "int default 0")
    private int numberKnowledgeCourts;
    @Column(columnDefinition = "int default 0")
    private int numberKnowledgeStudentDelegate;
    @Column(columnDefinition = "int default 0")
    private int numberMeritStudentDelegate;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idannouncement", referencedColumnName = "idannouncement")
    private Announcement announcement;


}
