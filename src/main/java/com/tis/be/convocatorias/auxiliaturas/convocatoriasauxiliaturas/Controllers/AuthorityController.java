package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.AnnouncementCreateInput.AuthoritiesCreateInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.AuthorityInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Announcement;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Authority;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AnnouncementRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AuthorityRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AuthorityController {
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;



    @RequestMapping("/authority")
    public List<Authority> getAllAuthority() {
        return authorityService.getAllAuthority();
    }

    @RequestMapping("/authority/{id}")
    public Authority getAuthority(@PathVariable long id) {
        return authorityService.getAuthority(id);
    }

    @PostMapping("/authority")
    public ResponseEntity<Long> addAuthority(@RequestBody AuthorityInput authorityInput) {
        Announcement announcement = announcementRepository.getById(authorityInput.getAnnouncement());
        if (!authorityRepository.existsAuthorityByAnnouncement_IdannouncementAndNameAndPosition(announcement.getIdannouncement(), authorityInput.getName(), authorityInput.getPosition())) {
            Authority authority = new Authority();
            authority.setIdauthority(999999);
            authority.setName(authorityInput.getName());
            authority.setPosition(authorityInput.getPosition());
            authority.setAnnouncement(announcement);

            return ResponseEntity.ok().body(authorityService.addAuthority(authority));
        }else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    public ResponseEntity<Long> addAuthorityCreate(@RequestBody AuthoritiesCreateInput authoritiesCreateInput, long id) {
        Announcement announcement = announcementRepository.getById(id);
        if (!authorityRepository.existsAuthorityByAnnouncement_IdannouncementAndNameAndPosition(announcement.getIdannouncement(), authoritiesCreateInput.getName(), authoritiesCreateInput.getPosition())) {
        Authority authority = new Authority();
        authority.setIdauthority(999999);
        authority.setName(authoritiesCreateInput.getName());
        authority.setPosition(authoritiesCreateInput.getPosition());
        authority.setAnnouncement(announcement);

        return ResponseEntity.ok().body(authorityService.addAuthority(authority));
        }else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @DeleteMapping("/authority/{id}")
    public void deleteAuthority(@PathVariable long id) {
        authorityService.deleteAuthority(id);
    }

    @RequestMapping("/authority/announcement/{id}")
    public List<Authority> ListByAnnouncement(@PathVariable("id") long id){
        return authorityService.ListByAnnouncement(id);
    }

}
