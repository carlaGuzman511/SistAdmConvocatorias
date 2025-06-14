package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.AnnouncementCreateInput.AnnouncementCreateInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.AnnouncementInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists.ListByAuxiliaryByManAreaAcaUnitInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists.ListByManagementAcademicunitInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists.ListByAreaAcademicUnitInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists.ListByManagementAreaAcadUnitFacultyInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Update.UpdateAnnouncementInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.AnnouncementService;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.LabelService;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private ManagementRepository managementRepository;
    @Autowired
    private ManagementController managementController;
    @Autowired
    private AcademicUnitRepository academicUnitRepository;
    @Autowired
    private AuxiliaryRepository auxiliaryRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private AuthorityController authorityController;
    @Autowired
    private DeadlineController deadlineController;
    @Autowired
    private ShapeController shapeController;
    @Autowired
    private DocumentController documentController;
    @Autowired
    private PostulantRepository postulantRepository;
    @Autowired
    private LabelService labelService;
    @Autowired
    private UserService userService;


    @RequestMapping("/announcement")
    public List<Announcement> getAllAnnouncement() {
        return announcementService.getAllAnnouncements();
    }

    @RequestMapping("/announcement/{id}")
    public Announcement getAnnouncement(@PathVariable long id) {
        return announcementService.getAnnouncement(id);
    }

    @PostMapping("/announcement")
    public long addAnnouncement(@RequestBody AnnouncementInput announcementInput) {
        Collections.sort(announcementInput.getAuxiliary());
        Area area = areaRepository.getById(announcementInput.getArea());
        Management management = managementRepository.getById(announcementInput.getManagement());
        Faculty faculty = facultyRepository.getById(announcementInput.getFaculty());
        AcademicUnit academicUnit = academicUnitRepository.getById(announcementInput.getAcademicUnit());
        Announcement announcement = new Announcement();
        Set<Auxiliary> auxiliary = new HashSet<>();
        for (int i = 0; i < announcementInput.getAuxiliary().size(); i++) {
            auxiliary.add(auxiliaryRepository.getById(announcementInput.getAuxiliary().get(i)));
        }
        Set<Postulant> postulant = new HashSet<>();
        for (int i = 0; i < announcementInput.getPostulant().size(); i++) {
            postulant.add(postulantRepository.getById(announcementInput.getPostulant().get(i)));
        }

        announcement.setIdannouncement(999999);
        announcement.setTitle(announcementInput.getTitle());
        announcement.setDescription(announcementInput.getDescription());
        announcement.setAppointment(announcementInput.getAppointment());
        announcement.setPack(announcementInput.isPack());
        announcement.setState(announcementInput.isState());
        announcement.setArea(area);
        announcement.setManagement(management);
        announcement.setAcademicUnit(academicUnit);
        announcement.setFaculty(faculty);
        announcement.setAuxiliarys(auxiliary);
        announcement.setPostulants(postulant);

        return announcementService.addAnnouncement(announcement);

    }

    @PostMapping("/announcement/create")
    public long CreateAnnouncement(@RequestBody AnnouncementCreateInput announcementCreateInput){
        Management management = new Management();
        Announcement announcement = new Announcement();
        announcement.setIdannouncement(999999);
        announcement.setTitle(announcementCreateInput.getTitle());
        announcement.setDescription(announcementCreateInput.getDescription());
        announcement.setAppointment(announcementCreateInput.getAppointment());
        announcement.setPack(announcementCreateInput.isPack());
        announcement.setState(announcementCreateInput.isState());
        announcement.setArea(areaRepository.getById(announcementCreateInput.getArea()));
        announcement.setAcademicUnit(academicUnitRepository.getById(announcementCreateInput.getAcademicUnit()));
        announcement.setFaculty(facultyRepository.getById(announcementCreateInput.getFaculty()));

        management.setPeriod(announcementCreateInput.getManagement());
        ResponseEntity<Long> idmanagement  = managementController.addManagement(management);
        announcement.setManagement(managementRepository.getById(idmanagement.getBody()));
        announcementService.addAnnouncement(announcement);
        for(int i = 0; i < announcementCreateInput.getAuthorities().size(); i++){
            authorityController.addAuthorityCreate(
                    announcementCreateInput.getAuthorities().get(i),
                    announcementService.getlastIdAnnouncement());
        }
        for(int j = 0; j < announcementCreateInput.getDeadline().size(); j++){
            deadlineController.addDeadlineCreate(
                    announcementCreateInput.getDeadline().get(j),
                    announcementService.getlastIdAnnouncement()
            );
        }
        for(int k = 0; k < announcementCreateInput.getShape().size(); k++){
            shapeController.addShapeCreate(
                    announcementCreateInput.getShape().get(k),
                    announcementService.getlastIdAnnouncement()
            );
        }
        for(int l = 0; l < announcementCreateInput.getDocsToPresent().size(); l++){
            documentController.addDocumentCreate(
                    announcementCreateInput.getDocsToPresent().get(l),
                    announcementService.getlastIdAnnouncement()
            );
        }

        return announcementService.getlastIdAnnouncement();

    }

    @PostMapping("/announcement/create/single")
    public ResponseEntity<Long> CreateAnnouncementSingle(@RequestBody AnnouncementCreateInput announcementCreateInput){
        Management management = new Management();
        Area area = areaRepository.getById(announcementCreateInput.getArea());
        Faculty faculty = facultyRepository.getById(announcementCreateInput.getFaculty());
        AcademicUnit academicUnit = academicUnitRepository.getById(announcementCreateInput.getAcademicUnit());
        Announcement announcement = new Announcement();
        if(managementRepository.existsByPeriod(announcementCreateInput.getManagement())) {
            management = managementRepository.findManagementByPeriod(announcementCreateInput.getManagement());
            announcement.setManagement(management);
        }else{
            management.setPeriod(announcementCreateInput.getManagement());
            ResponseEntity<Long> idmanagement  = managementController.addManagement(management);
            announcement.setManagement(managementRepository.getById(idmanagement.getBody()));
        }
        if (!announcementRepository.existsAnnouncementByAcademicUnit_IdacademicunitAndArea_IdareaAndFaculty_IdfacultyAndManagement_PeriodAndTitle(
                academicUnit.getIdacademicunit(), area.getIdarea(), faculty.getIdfaculty(), management.getPeriod(), announcementCreateInput.getTitle())) {
            announcement.setIdannouncement(999999);
            announcement.setTitle(announcementCreateInput.getTitle());
            announcement.setDescription(announcementCreateInput.getDescription());
            announcement.setAppointment(announcementCreateInput.getAppointment());
            announcement.setPack(announcementCreateInput.isPack());
            announcement.setState(announcementCreateInput.isState());
            announcement.setArea(area);
            announcement.setAcademicUnit(academicUnit);
            announcement.setFaculty(faculty);


            announcementService.addAnnouncement(announcement);
            userService.updateUserAnnouncements(announcementService.getlastIdAnnouncement());

            return ResponseEntity.ok().body(announcementService.getlastIdAnnouncement());
        }else{
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }

    }

    public void updateAnnouncementPostulants(long idannouncement, Postulant postulant){
        announcementService.updateAnnouncementPostulant(idannouncement, postulant);

    }

    @PutMapping("/announcement/update")
    public Announcement updateAnnouncement(@RequestBody UpdateAnnouncementInput updateAnnouncementInput){
        Area area = areaRepository.getById(updateAnnouncementInput.getArea());
        AcademicUnit academicUnit = academicUnitRepository.getById(updateAnnouncementInput.getAcademicUnit());
        Management management = managementRepository.getById(updateAnnouncementInput.getManagement());
        Faculty faculty = facultyRepository.getById(updateAnnouncementInput.getFaculty());
        List<Auxiliary> auxiliary = new ArrayList<>();
        for (int i = 0; i < updateAnnouncementInput.getAuxiliarys().size(); i++) {
            auxiliary.add(auxiliaryRepository.getById(updateAnnouncementInput.getAuxiliarys().get(i)));
        }
        List<Postulant> postulant = new ArrayList<>();
        for (int j = 0; j < updateAnnouncementInput.getPostulants().size(); j++) {
            postulant.add(postulantRepository.getById(updateAnnouncementInput.getPostulants().get(j)));
        }
        announcementService.UpdateAnnouncement(
                updateAnnouncementInput.getIdannouncement(),
                updateAnnouncementInput.getTitle(),
                updateAnnouncementInput.getAppointment(),
                updateAnnouncementInput.getCourtsDescription(),
                updateAnnouncementInput.isPack(),
                updateAnnouncementInput.isState(),
                area,
                academicUnit,
                management,
                faculty,
                auxiliary,
                postulant
        );

        return announcementService.getAnnouncement(updateAnnouncementInput.getIdannouncement());
    }

    @DeleteMapping("/announcement/{id}")
    public void deleteAnnouncement(@PathVariable long id) {
        announcementService.deleteAnnouncement(id);
    }

    @RequestMapping("/announcement/listadoauxiliary/{id}")
    public List<Auxiliary> listadoJoin(@PathVariable("id") long id) {
        return announcementService.listAuxiliarysByIdAnnouncement(id);
    }

    @PostMapping("/announcement/auxiliarys/MAAU")
    public List<Auxiliary> listAuxiliarysByManagementAreaAcademicUnit(@RequestBody ListByAuxiliaryByManAreaAcaUnitInput listByAuxiliaryByManAreaAcaUnitInput){
        return announcementService.listAuxiliarysByIdManagementAreaAcademicUnit(
                listByAuxiliaryByManAreaAcaUnitInput.getIdmanagement(),
                listByAuxiliaryByManAreaAcaUnitInput.getIdarea(),
                listByAuxiliaryByManAreaAcaUnitInput.getIdacademicunit()
        );
    }

    @PostMapping("/announcement/list/MAAU")
    public List<Announcement> listByManagementAreaAcademicUnit(@RequestBody ListByAuxiliaryByManAreaAcaUnitInput listByAuxiliaryByManAreaAcaUnitInput){
        return announcementService.listByIdManagementAreaAcademicUnit(
                listByAuxiliaryByManAreaAcaUnitInput.getIdmanagement(),
                listByAuxiliaryByManAreaAcaUnitInput.getIdarea(),
                listByAuxiliaryByManAreaAcaUnitInput.getIdacademicunit()
        );
    }

    @PostMapping("/announcement/list/MAU")
    public List<Announcement> listByManagementAreaAcademicUnit(@RequestBody ListByManagementAcademicunitInput listByManagementAcademicunitInput){
        return announcementService.listByManagementAcademicUnit(
            Long. parseLong(labelService.lastManagement()),
            listByManagementAcademicunitInput.getIdacademicunit()
        );
    }

    @PostMapping("/announcement/list/MAAUF")
    public List<Announcement> listByManagementAreaAcademicUnitFaculty(@RequestBody ListByManagementAreaAcadUnitFacultyInput listByManagementAreaAcadUnitFacultyInput){
        return announcementService.listByManagementAreaAcademicUnitFaculty(
            listByManagementAreaAcadUnitFacultyInput.getIdmanagement(),
            listByManagementAreaAcadUnitFacultyInput.getIdarea(),
            listByManagementAreaAcadUnitFacultyInput.getIdacademicunit(),
            listByManagementAreaAcadUnitFacultyInput.getIdfaculty()
        );
    }

    @PostMapping("/announcement/auxiliarys/AAU")
    public List<Auxiliary> listAuxiliarysByAreaAcademicUnit(@RequestBody ListByAreaAcademicUnitInput listByAreaAcademicUnitInput){
        return announcementService.listAuxiliarysByIdAreaAcademicUnit(
                listByAreaAcademicUnitInput.getIdarea(),
                listByAreaAcademicUnitInput.getIdacademicunit()
        );
    }

    @PostMapping("/announcement/area/name")
    public long findAreaNameByIdAnnouncement(@RequestBody long idInput){
        return announcementService.findAreaNameByIdAnnouncement(idInput);
    }


    @RequestMapping("/pruebaToString")
    public String prueba(){
        return announcementService.getAnnouncement(1).toString();
    }

}
