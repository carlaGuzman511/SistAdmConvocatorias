package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.ScheduleCreateInput.ScheduleCreateInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.ScheduleInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Announcement;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Schedule;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AnnouncementRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ScheduleRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private EventController eventController;


    @RequestMapping("/schedule")
    public List<Schedule> getAllSchedule() {
        return scheduleService.getAllSchedule();
    }

    @RequestMapping("/schedule/{id}")
    public Schedule getSchedule(@PathVariable long id) {
        return scheduleService.getSchedule(id);
    }

    @PostMapping("/schedule")
    public ResponseEntity<Long> addSchedule(@RequestBody ScheduleInput scheduleInput) {
        Announcement announcement = announcementRepository.getById(scheduleInput.getAnnouncement());
        if (!scheduleRepository.existsScheduleByAnnouncement_IdannouncementAndDescriptionAndNote(scheduleInput.getAnnouncement(), scheduleInput.getDescription(), scheduleInput.getNote())) {
            Schedule schedule = new Schedule();
            schedule.setIdschedule(999999);
            schedule.setDescription(scheduleInput.getDescription());
            schedule.setNote(scheduleInput.getNote());
            schedule.setAnnouncement(announcement);

            return ResponseEntity.ok().body(scheduleService.addSchedule(schedule));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }

    }

    @PostMapping("/schedule/create")
    public ResponseEntity<Long> addScheduleCreate(@RequestBody ScheduleCreateInput scheduleCreateInput) {
        Announcement announcement = announcementRepository.getById(scheduleCreateInput.getIdAnnouncement());
        if (!scheduleRepository.existsScheduleByAnnouncement_IdannouncementAndDescriptionAndNote(scheduleCreateInput.getIdAnnouncement(), scheduleCreateInput.getDescription(), scheduleCreateInput.getNote())) {
        Schedule schedule = new Schedule();
        schedule.setIdschedule(999999);
        schedule.setDescription(scheduleCreateInput.getDescription());
        schedule.setNote(scheduleCreateInput.getNote());
        schedule.setAnnouncement(announcement);
        scheduleService.addSchedule(schedule);
        for (int i = 0; i < scheduleCreateInput.getEvents().size(); i++) {
            eventController.addEventCreate(
                    scheduleCreateInput.getEvents().get(i),
                    scheduleService.getByLastId()
            );
        }
        return ResponseEntity.ok().body(scheduleService.getByLastId());

        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @DeleteMapping("/schedule/{id}")
    public void deleteSchedule(@PathVariable long id) {
        scheduleService.deleteSchedule(id);
    }

    @RequestMapping("/schedule/announcement/{id}")
    public List<Schedule> ListByAnnouncement(@PathVariable("id") long id) {
        return scheduleService.ListByAnnouncement(id);
    }

}
