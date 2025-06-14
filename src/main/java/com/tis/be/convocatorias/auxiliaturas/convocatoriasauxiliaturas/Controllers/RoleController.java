package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;//package com.tis.convocatorias.postulant.service.Career;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Role;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.RoleRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping("/role")
    public List<Role> getAllRole() {
        return roleService.getAllRole();
    }

    @RequestMapping("/role/{id}")
    public Role getRole(@PathVariable long id) {
        return roleService.getRole(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/role")
    public ResponseEntity<Long> addRole(@RequestBody Role role) {
        if (!roleRepository.existsRoleByName(role.getName())) {
            roleService.addRole(role);
            return ResponseEntity.ok().body(role.getIdrole());

        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }
}
