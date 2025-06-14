package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.LaboratoryEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Schedule;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;


    public List<Schedule> getAllSchedule() {
        return scheduleRepository.findAll();
    }


    public Schedule getSchedule(long id) {
        return scheduleRepository.FindById(id)
                .orElse(null);

    }

    public long addSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
        return scheduleRepository.selectByIdSchedule();
    }


    public void deleteSchedule(long id) {
        scheduleRepository.DeleteByCi(id);
    }

    public List<Schedule> ListByAnnouncement(long id){
        return scheduleRepository.findSchedulesByAnnouncement_Idannouncement(id);
    }

    public long getByLastId(){
        return scheduleRepository.selectByIdSchedule();
    }

}
