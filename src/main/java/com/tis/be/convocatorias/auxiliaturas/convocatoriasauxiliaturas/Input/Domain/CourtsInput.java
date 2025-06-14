package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class CourtsInput implements Serializable {

    private String description;
    private int meritsCourts, knowledgeCourts, studentDelegateMerit, studentDelegateKnowledge,
                numberMeritCourts, numberKnowledgeCourts, numberKnowledgeStudentDelegate, numberMeritStudentDelegate;
    private long idannouncement;
}
