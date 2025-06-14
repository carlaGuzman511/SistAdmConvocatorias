package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Deadline;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.DeadlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeadlineService {

    @Autowired
    private DeadlineRepository deadlineRepository;


    public List<Deadline> getAllDeadline() {

        return deadlineRepository.findAll();
    }

    public Deadline getDeadline(long id) {
        return deadlineRepository.FindById(id)
                .orElse(null);

    }

    public long addDeadline(Deadline deadline) {
        deadlineRepository.save(deadline);
        return deadlineRepository.selectByIdDeadline();
    }

    public void deleteDeadline(long id) {
        deadlineRepository.DeleteByCi(id);
    }

    public List<Deadline> ListByAnnouncement(long id){
        return deadlineRepository.findDeadlinesByAnnouncement_Idannouncement(id);
    }

}
