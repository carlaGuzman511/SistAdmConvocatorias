package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;//package com.tis.convocatorias.postulant.service.Career;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "RequestPostulant")
public class RequestPostulant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="idrequestpostulant")
    private long idrequestpostulant;

    @Column
    private boolean status;
    @Column(length = 2500)
    private String observation;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idrequestdetail", referencedColumnName = "idrequestdetail")
    private RequestDetail requestdetail;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idpostulant", referencedColumnName = "idpostulant")
    private Postulant postulant;


}
