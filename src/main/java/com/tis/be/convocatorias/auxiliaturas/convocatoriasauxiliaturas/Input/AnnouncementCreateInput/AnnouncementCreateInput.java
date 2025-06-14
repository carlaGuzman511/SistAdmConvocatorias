package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.AnnouncementCreateInput;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AnnouncementCreateInput implements Serializable {

    private String title, description, courtsDescription, appointment, management;
    private boolean pack, state;

//    @Temporal(TemporalType.DATE)
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private Date publicationDate, dueDate;
//    @Temporal(TemporalType.TIME)
//    @JsonFormat(pattern = "HH:mm")
//    private Date publicationHour, dueHour;

    private long area, academicUnit, faculty;
    private List<AuthoritiesCreateInput> authorities;
    private List<DeadlineCreateInput> deadline;
    private List<ShapeCreateInput> shape;
    private List<DocsToPresentCreateInput> docsToPresent;
}
