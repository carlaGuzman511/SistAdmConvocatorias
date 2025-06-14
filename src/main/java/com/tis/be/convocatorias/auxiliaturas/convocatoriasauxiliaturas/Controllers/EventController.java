package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.EventInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.ScheduleCreateInput.EventCreateInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Event;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Schedule;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.EventRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ScheduleRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @RequestMapping("/event")
    public List<Event> getAllEvent() {
        return eventService.getAllEvent();
    }

    @RequestMapping("/event/{id}")
    public Event getEvent(@PathVariable long id) {
        return eventService.getEvent(id);
    }

    @PostMapping("/event")
    public ResponseEntity<Long> addEvent(@RequestBody EventInput eventInput) {
        Schedule schedule = scheduleRepository.getById(eventInput.getSchedule());
        if (!eventRepository.existsEventByNameAndPlaceAndDateEventAndTimeEventAndSchedule_Idschedule(
                eventInput.getName(), eventInput.getPlace(), eventInput.getDateEvent(), eventInput.getTimeEvent(), eventInput.getSchedule())) {
            Event event = new Event();
            event.setIdevent(999999);
            event.setName(eventInput.getName());
            event.setPlace(eventInput.getPlace());
            event.setTimeEvent(eventInput.getTimeEvent());
            event.setDateEvent(eventInput.getDateEvent());
            event.setSchedule(schedule);


            return ResponseEntity.ok().body(eventService.addEvent(event));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    public ResponseEntity<Long> addEventCreate(@RequestBody EventCreateInput eventCreateInput, long id) {
        Schedule schedule = scheduleRepository.getById(id);
        if (!eventRepository.existsEventByNameAndPlaceAndDateEventAndTimeEventAndSchedule_Idschedule(
                eventCreateInput.getEventName(), eventCreateInput.getPlace(), eventCreateInput.getDate(), eventCreateInput.getTime(), id)) {
            Event event = new Event();
            event.setIdevent(999999);
            event.setName(eventCreateInput.getEventName());
            event.setPlace(eventCreateInput.getPlace());
            event.setDateEvent(eventCreateInput.getDate());
            event.setTimeEvent(eventCreateInput.getTime());
            event.setSchedule(schedule);

            return ResponseEntity.ok().body(eventService.addEvent(event));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @DeleteMapping("/event/{id}")
    public void deleteEvent(@PathVariable long id) {
        eventService.deleteEvent(id);
    }

    @RequestMapping("/event/schedule/{id}")
    public List<Event> ListByKnowledge(@PathVariable("id") long id) {
        return eventService.ListBySchedule(id);
    }

}
