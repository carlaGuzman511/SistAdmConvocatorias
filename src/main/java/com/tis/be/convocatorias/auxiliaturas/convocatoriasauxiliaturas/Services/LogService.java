package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Document;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Log;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.DocumentRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public List<Log> getAllLog() {

        return logRepository.findAll();
    }

    public Log getLog(long id) {
        return logRepository.FindById(id)
                .orElse(null);

    }

    public long addLog(Log log) {
        logRepository.save(log);
        return logRepository.selectByIdLog();
    }

    public void deleteLog(long id) {
        logRepository.DeleteByCi(id);
    }

    public long getByLastId(){
        return logRepository.selectByIdLog();
    }

}
