package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.EventLog;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.EventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class EventLogController {

    @Autowired
    private EventLogService eventLogService;



    @RequestMapping("/eventlog")
    public List<EventLog> getAllEventLog() {
        return eventLogService.getAllEventLog();
    }

    @RequestMapping("/eventlog/{id}")
    public EventLog getEventLog(@PathVariable long id) {
        return eventLogService.getEventLog(id);
    }

    @PostMapping("/eventlog")
    public long addEventLog(@RequestBody EventLog eventLog) {
        eventLogService.addEventLog(eventLog);
        return eventLog.getIdeventlog();

    }


    @DeleteMapping("/eventlog/{id}")
    public void deleteEventLog(@PathVariable long id) {
        eventLogService.deleteEventLog(id);
    }


}
