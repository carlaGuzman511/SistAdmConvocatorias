import { TestBed } from "@angular/core/testing";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { UserService } from "./user.service";
import { User } from "../models/user.model";
import { AcademicUnit } from "../models/academic-unit.model";
import { Area } from "../models/area.model";
import { Management } from "../models/management.model";
import { Person } from "../models/person.model";
import { Announcement } from "../models/announcement.model";

describe("UserService", () => {
  let userService: UserService;
  const academicUnit: AcademicUnit = {
    idacademicunit: 1,
    name: "Departamento de Informatica Sistemas",
  };
  const area: Area = { idarea: 1, name: "Laboratorio" };
  const management: Management = { idmanagement: 1, period: "2020" };
  const person: Person = {
    id: 1,
    ci: 8779124,
    name: "Maria Elena",
    lastName: "Viza Ustariz",
    address: "Av. America",
    phoneNumber: 65246186,
    email: "maria.viza@gmail.com",
  };
  const announcement: Announcement = {
    idannouncement: 1,
    title:
      "CONVOCATORIA A CONCURSO DE MÉRITOS Y PRUEBAS DE CONOCIMIENTOS PARA OPTAR A AUXILIATURAS EN LABORATORIO DE COMPUTACIÓN, DE MANTENIMIENTO Y DESARROLLO",
    description:
      "El Departamento de Informática y Sistemas junto a las Carreras de Ing. Informática e Ing. de Sistemas, de la Facultad de Ciencias y Tecnología, convoca al concurso de méritos y examen de competencia para la provisión de Auxiliares del Departamento, tomando como base los requerimientos que se tienen programados para la gestión 2020.",
    deadline: "07/02/2020 11:00",
    courtsDescription:
      "Los Honorables Consejos de Carrera de Informática y Sistemas designarán respectivamente; para la calificación de méritos 1 docente y 1 delegado estudiante, de la misma manera para la comisión de conocimientos cada consejo designará 1 docente y un estudiante veedor por cada temática.",
    appointment:
      "Una vez concluido el proceso, la jefatura  decidirá qué auxiliares serán seleccionados para cada ítem, considerando los resultados finales y  las necesidades propias de cada laboratorio. Los nombramientos de auxiliar universitario titular recaerán sobre aquellos postulantes que hubieran aprobado y obtenido mayor calificación. Caso contrario se procederá con el nombramiento de aquel que tenga la calificación mayor como auxiliar invitado. Cabe resaltar que un auxiliar invitado solo tendrá nombramiento por los periodos académicos del semestre I y II de 2020.",
    academicUnit: academicUnit,
    area: area,
    management: management,
  };
  const user: User = {
    id_user: 1,
    id_role: 3,
    identity_card: 7317943,
    password: "",
    person: person,
    announcement: announcement,
  };

  beforeEach(() =>
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService],
    })
      .compileComponents()
      .then(() => {
        userService = TestBed.get(UserService);
      })
  );

  it("#constructor, should be created", () => {
    const userServiceC: UserService = TestBed.get(UserService);
    expect(userServiceC).toBeTruthy();
  });

  it("#getCurrentUser, should be exists a user", () => {
    userService.setCurrentUser(user);
    expect(userService.getCurrentUser()).not.toBeNull();
  });

  it("#getCurrentUser, should be null the user", () => {
    userService.removeCurrentUser();
    expect(userService.getCurrentUser()).toBeUndefined();
  });
});
