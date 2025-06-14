package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Log")
@EqualsAndHashCode(exclude = "user")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "idlog")
    private long idlog;

    @Column(length = 30000)
    private String NewField;

    @Column(length = 30000)
    private String OldField;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(timezone = "America/La_Paz")
    @CreatedDate
    private Date DateLog;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "iduser", referencedColumnName = "iduser")
    @JsonIgnoreProperties({"announcements", "auxiliarys", "thematics"})
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ideventlog", referencedColumnName = "ideventlog")
    private EventLog eventLog;

}
