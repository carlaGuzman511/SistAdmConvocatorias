package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Authority;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.LaboratoryEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;


    public List<Authority> getAllAuthority() {

        return authorityRepository.findAll();
    }

    public Authority getAuthority(long id) {
        return authorityRepository.FindById(id)
                .orElse(null);

    }

    public long addAuthority(Authority authority) {
        authorityRepository.save(authority);
        return authorityRepository.selectByIdAuthority();
    }

    public void deleteAuthority(long id) {
        authorityRepository.DeleteByCi(id);
    }

    public List<Authority> ListByAnnouncement(long id){
        return authorityRepository.findAuthoritiesByAnnouncement_Idannouncement(id);
    }

}
