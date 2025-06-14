package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;//package com.tis.convocatorias.postulant.service.Career;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.AdittionalLabelInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.AdittionalLabel;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Label;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AdittionalLabelRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.LabelRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.AdittionalLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AdditionalLabelController {

    @Autowired
    private AdittionalLabelService adittionalLabelService;
    @Autowired
    private AdittionalLabelRepository adittionalLabelRepository;
    @Autowired
    private LabelRepository labelRepository;

    @RequestMapping("/adittionallabel")
    public List<AdittionalLabel> getAllAdittionalLabel() {
        return adittionalLabelService.getAllAdittionalLabel();
    }

    @RequestMapping("/adittionallabel/{id}")
    public AdittionalLabel getAdittionalLabel(@PathVariable long id) {
        return adittionalLabelService.getAdittionalLabel(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/adittionallabel")
    public ResponseEntity<Long> addAdittionalLabel(@RequestBody AdittionalLabelInput adittionalLabelInput) {
        Label label = labelRepository.getById(adittionalLabelInput.getLabel());
        if (!adittionalLabelRepository.existsAdittionalLabelByLabel_IdlabelAndField(adittionalLabelInput.getLabel(), adittionalLabelInput.getField())) {
            AdittionalLabel adittionalLabel = new AdittionalLabel();
            adittionalLabel.setIdadittionallabel(999999);
            adittionalLabel.setField(adittionalLabelInput.getField());
            adittionalLabel.setLabel(label);

            return ResponseEntity.ok().body(adittionalLabelService.addAdittionalLabel(adittionalLabel));
        } else {
            long idaux = -2;
            return ResponseEntity.badRequest().body(idaux);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/adittionallabel/{id}")
    public void deleteAdittionalLabel(@PathVariable int id) {
        adittionalLabelService.deleteAdittionalLabel(id);
    }
}
