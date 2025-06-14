package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.LogInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.EventLogRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.UserRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class LogController {

    @Autowired
    private LogService logService;
    @Autowired
    private EventLogRepository eventLogRepository;
    @Autowired
    private UserRepository userRepository;


    @RequestMapping("/log")
    public List<Log> getAllLog() {
        return logService.getAllLog();
    }

    @RequestMapping("/log/{id}")
    public Log getLog(@PathVariable long id) {
        return logService.getLog(id);
    }

    @PostMapping("/log")
    public long addLog(@RequestBody LogInput logInput) {
        EventLog eventLog = eventLogRepository.getById(logInput.getEventLog());
        User user = userRepository.getById(logInput.getUser());
        Log log = new Log();
        log.setIdlog(999999);
        log.setNewField(logInput.getNewField());
        log.setOldField(logInput.getOldField());
        log.setEventLog(eventLog);
        log.setUser(user);

        return logService.addLog(log);

    }

    @DeleteMapping("/log/{id}")
    public void deleteLog(@PathVariable long id) {
        logService.deleteLog(id);
    }

}
