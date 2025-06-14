package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Document;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.EventLog;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.DocumentRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.EventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventLogService {

    @Autowired
    private EventLogRepository eventLogRepository;

    public List<EventLog> getAllEventLog() {

        return eventLogRepository.findAll();
    }

    public EventLog getEventLog(long id) {
        return eventLogRepository.FindById(id)
                .orElse(null);

    }

    public long addEventLog(EventLog eventLog) {
        eventLogRepository.save(eventLog);
        return eventLogRepository.selectByIdEventLog();
    }

    public void deleteEventLog(long id) {
        eventLogRepository.DeleteByCi(id);
    }

    public long getByLastId(){
        return eventLogRepository.selectByIdEventLog();
    }

}
