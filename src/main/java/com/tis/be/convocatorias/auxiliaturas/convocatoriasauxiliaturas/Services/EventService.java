package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Event;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.LaboratoryEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;


    public List<Event> getAllEvent() {

        return eventRepository.findAll();
    }


    public Event getEvent(long id) {
        return eventRepository.FindById(id)
                .orElse(null);

    }

    public long addEvent(Event event) {
        eventRepository.save(event);
        return eventRepository.selectByIdEvent();
    }

    public void deleteEvent(long id) {
        eventRepository.DeleteByCi(id);
    }

    public List<Event> ListBySchedule(long id){
        return eventRepository.findEventsBySchedule_Idschedule(id);
    }


}
