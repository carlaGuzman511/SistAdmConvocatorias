import { ComponentFixture, TestBed, async } from "@angular/core/testing";
import { BrowserModule } from "@angular/platform-browser";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { Router } from "@angular/router";
import { of, throwError, Observable } from "rxjs";
import { TypeAlertEnum } from "src/app/models/type-alert-enum";
import { EnabledAndNoEnabledPostulantsComponent } from "./enabled-and-no-enabled-postulants.component";
import { AcademicUnit } from "src/app/models/academic-unit.model";
import { Area } from "src/app/models/area.model";
import { Management } from "src/app/models/management.model";
import { Person } from "src/app/models/person.model";
import { Auxiliary } from "src/app/models/auxiliary.model";
import { Postulant } from "src/app/models/postulant.model";
import { ApiService } from "src/app/api-service/api.service";
import { Announcement } from "src/app/models/announcement.model";
import { Career } from "src/app/models/career.model";
import { LogBook } from "src/app/models/logbook.model";

describe("EnabledPostulantsComponent", () => {
  let enabledPostulantsComponent: EnabledAndNoEnabledPostulantsComponent;
  let fixture: ComponentFixture<EnabledAndNoEnabledPostulantsComponent>;
  let router;
  let navigateSpy;
  let apiServiceMock;
  let apiServiceMockAux;

  const person1: Person = {
    id: 1,
    ci: 8798743,
    name: "Maria Elena",
    lastName: "Viza Ustariz",
    address: "Av. America",
    phoneNumber: 65246186,
    email: "maria.viza@gmail.com",
  };
  const person2: Person = {
    id: 2,
    ci: 9754010,
    name: "Pablo",
    lastName: "Villarroel Apaza",
    address: "Av. Petrolera",
    phoneNumber: 76426186,
    email: "pablo.villarroel@gmail.com",
  };
  const person3: Person = {
    id: 3,
    ci: 8548774,
    name: "Jose Maria",
    lastName: "Lopez Ayala",
    address: "Av. Ayacucho",
    phoneNumber: 98547854,
    email: "jose.lopez@gmail.com",
  };
  const person4: Person = {
    id: 4,
    ci: 8454987,
    name: "Ivan",
    lastName: "Morales Quevedo",
    address: "Av. Panamericana",
    phoneNumber: 65787954,
    email: "ivan.morales@gmail.com",
  };
  const person5: Person = {
    id: 5,
    ci: 9054878,
    name: "Gustavo",
    lastName: "Flores Salvatierra",
    address: "Av. Oquendo",
    phoneNumber: 87541245,
    email: "gustavo.flores@gmail.com",
  };
  const person6: Person = {
    id: 6,
    ci: 8985231,
    name: "Drayko",
    lastName: "Jimenez Gonzales",
    address: "Av. Papa Paulo",
    phoneNumber: 98754211,
    email: "drayko.jimenez@gmail.com",
  };
  const person7: Person = {
    id: 7,
    ci: 7598751,
    name: "Gilda",
    lastName: "Rodriguez Velasco",
    address: "Av. Santa Cruz",
    phoneNumber: 98754877,
    email: "gilda.rodriguez@gmail.com",
  };
  const person8: Person = {
    id: 8,
    ci: 8798513,
    name: "Rosi",
    lastName: "Garnica Morales",
    address: "Av. Ecuador",
    phoneNumber: 68795664,
    email: "rosi.garnica@gmail.com",
  };
  const person9: Person = {
    id: 9,
    ci: 8954512,
    name: "Luis Fernando",
    lastName: "Guzman Meneses",
    address: "Av. Petrolera",
    phoneNumber: 68654541,
    email: "luis.guzman@gmail.com",
  };
  const person10: Person = {
    id: 10,
    ci: 7879521,
    name: "Juan Marcelo",
    lastName: "Herbas Troncoso",
    address: "Av. Cala Cala",
    phoneNumber: 78987544,
    email: "juan.herbas@gmail.com",
  };
  const person11: Person = {
    id: 11,
    ci: 8987125,
    name: "Ernesto",
    lastName: "Perez Quispe",
    address: "Av. Santa Barbara",
    phoneNumber: 65785465,
    email: "ernesto.perez@gmail.com",
  };
  const person12: Person = {
    id: 12,
    ci: 8975111,
    name: "Jorge",
    lastName: "Mamani Sanchez",
    address: "Av. Sucre",
    phoneNumber: 98545898,
    email: "jorge.mamani@gmail.com",
  };

  const academicUnit: AcademicUnit = {
    idacademicunit: 1,
    name: "Departamento de Informatica Sistemas",
  };
  const area: Area = { idarea: 1, name: "Laboratorio" };
  const management: Management = { idmanagement: 1, period: "2020" };

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
    area: area,
    management: management,
    academicUnit: academicUnit,
  };

  const career1: Career = {
    idcareer: 1,
    codCareer: 411702,
    name: "Ingenieria en Sistemas",
  };
  const career2: Career = {
    idcareer: 2,
    codCareer: 134111,
    name: "Ingenieria Informatica",
  };

  const auxiliary1: Auxiliary = {
    idauxiliary: 5,
    name: "Administrador de Laboratorio de Cómputo",
    code: "LCO-ADM",
    academicHours: "80 Hrs/mes",
    announcement: announcement,
  };
  const auxiliary2: Auxiliary = {
    idauxiliary: 6,
    name: "Administrador de Laboratorio de Desarrollo",
    code: "LDS-ADM",
    academicHours: "80 Hrs/mes",
    announcement: announcement,
  };
  const auxiliary3: Auxiliary = {
    idauxiliary: 7,
    name: "Auxiliar de Laboratorio de Desarrollo",
    code: "LDS-AUX",
    academicHours: "56 Hrs/mes",
    announcement: announcement,
  };
  const auxiliary4: Auxiliary = {
    idauxiliary: 8,
    name: "Administrador de Laboratorio de Mantenimiento de Software",
    code: "LM-ADM-SW",
    academicHours: "80 Hrs/mes",
    announcement: announcement,
  };
  const auxiliary5: Auxiliary = {
    idauxiliary: 9,
    name: "Auxiliar de Laboratorio de Mantenimiento de Software",
    code: "LM-AUX-SW",
    academicHours: "32 Hrs/mes",
    announcement: announcement,
  };
  const auxiliary6: Auxiliary = {
    idauxiliary: 10,
    name: "Administrador de Laboratorio de Mantenimiento de Hardware",
    code: "LM-ADM-HW",
    academicHours: "80 Hrs/mes",
    announcement: announcement,
  };
  const auxiliary7: Auxiliary = {
    idauxiliary: 11,
    name: "Auxiliar de Laboratorio de Mantenimiento de Hardware",
    code: "LM-AUX-HW",
    academicHours: "32 Hrs/mes",
    announcement: announcement,
  };
  const auxiliary8: Auxiliary = {
    idauxiliary: 12,
    name: "Auxiliar de Terminal de Laboratorio de Cómputo",
    code: "LDS-ATL",
    academicHours: "80 Hrs/mes",
    announcement: announcement,
  };

  const postulant1: Postulant = {
    idpostulant: 1,
    person: person1,
    career: career1,
    status: true,
  };
  const postulant2: Postulant = {
    idpostulant: 1,
    person: person2,
    career: career2,
    status: false,
  };
  const postulant3: Postulant = {
    idpostulant: 1,
    person: person3,
    career: career1,
    status: true,
  };
  const postulant4: Postulant = {
    idpostulant: 1,
    person: person4,
    career: career2,
    status: true,
  };
  const postulant5: Postulant = {
    idpostulant: 1,
    person: person5,
    career: career1,
    status: true,
  };
  const postulant6: Postulant = {
    idpostulant: 1,
    person: person6,
    career: career2,
    status: false,
  };
  const postulant7: Postulant = {
    idpostulant: 1,
    person: person7,
    career: career1,
    status: true,
  };
  const postulant8: Postulant = {
    idpostulant: 1,
    person: person8,
    career: career1,
    status: false,
  };
  const postulant9: Postulant = {
    idpostulant: 1,
    person: person9,
    career: career2,
    status: true,
  };
  const postulant10: Postulant = {
    idpostulant: 1,
    person: person10,
    career: career1,
    status: true,
  };
  const postulant11: Postulant = {
    idpostulant: 1,
    person: person11,
    career: career1,
    status: true,
  };
  const postulant12: Postulant = {
    idpostulant: 1,
    person: person12,
    career: career2,
    status: false,
  };

  const loogbook1: LogBook = {
    idlogbook: 1,
    deliveryHour: "2020-02-01 11:41:00",
    deliveryDate: "2020-02-01 11:41:00",
    document_quantity: 25,
    postulantes: postulant1,
    auxiliary: auxiliary1,
    announcement: announcement,
  };
  const loogbook2: LogBook = {
    idlogbook: 2,
    deliveryHour: "2020-02-01 15:31:00",
    deliveryDate: "2020-02-01 15:31:00",
    document_quantity: 20,
    postulantes: postulant2,
    auxiliary: auxiliary2,
    announcement: announcement,
  };
  const loogbook3: LogBook = {
    idlogbook: 3,
    deliveryHour: "2020-02-01 16:51:00",
    deliveryDate: "2020-02-01 16:51:00",
    document_quantity: 35,
    postulantes: postulant3,
    auxiliary: auxiliary3,
    announcement: announcement,
  };
  const loogbook4: LogBook = {
    idlogbook: 4,
    deliveryHour: "2020-02-01 08:21:00",
    deliveryDate: "2020-02-01 08:21:00",
    document_quantity: 42,
    postulantes: postulant4,
    auxiliary: auxiliary4,
    announcement: announcement,
  };
  const loogbook5: LogBook = {
    idlogbook: 5,
    deliveryHour: "2020-02-01 09:11:00",
    deliveryDate: "2020-02-01 09:11:00",
    document_quantity: 15,
    postulantes: postulant5,
    auxiliary: auxiliary5,
    announcement: announcement,
  };
  const loogbook6: LogBook = {
    idlogbook: 6,
    deliveryHour: "2020-02-01 10:15:00",
    deliveryDate: "2020-02-01 10:15:00",
    document_quantity: 27,
    postulantes: postulant6,
    auxiliary: auxiliary6,
    announcement: announcement,
  };
  const loogbook7: LogBook = {
    idlogbook: 7,
    deliveryHour: "2020-02-01 09:27:00",
    deliveryDate: "2020-02-01 09:27:00",
    document_quantity: 38,
    postulantes: postulant7,
    auxiliary: auxiliary7,
    announcement: announcement,
  };
  const loogbook8: LogBook = {
    idlogbook: 8,
    deliveryHour: "2020-02-01 14:57:00",
    deliveryDate: "2020-02-01 14:57:00",
    document_quantity: 31,
    postulantes: postulant8,
    auxiliary: auxiliary8,
    announcement: announcement,
  };
  const loogbook9: LogBook = {
    idlogbook: 9,
    deliveryHour: "2020-02-01 11:01:00",
    deliveryDate: "2020-02-01 11:01:00",
    document_quantity: 28,
    postulantes: postulant9,
    auxiliary: auxiliary1,
    announcement: announcement,
  };
  const loogbook10: LogBook = {
    idlogbook: 10,
    deliveryHour: "2020-02-01 15:01:00",
    deliveryDate: "2020-02-01 15:01:00",
    document_quantity: 39,
    postulantes: postulant10,
    auxiliary: auxiliary2,
    announcement: announcement,
  };
  const loogbook11: LogBook = {
    idlogbook: 11,
    deliveryHour: "2020-02-01 16:01:00",
    deliveryDate: "2020-02-01 16:01:00",
    document_quantity: 47,
    postulantes: postulant11,
    auxiliary: auxiliary3,
    announcement: announcement,
  };
  const loogbook12: LogBook = {
    idlogbook: 12,
    deliveryHour: "2020-02-01 15:51:00",
    deliveryDate: "2020-02-01 15:51:00",
    document_quantity: 29,
    postulantes: postulant12,
    auxiliary: auxiliary4,
    announcement: announcement,
  };

  const logbooks = [
    loogbook1,
    loogbook2,
    loogbook3,
    loogbook4,
    loogbook5,
    loogbook6,
    loogbook7,
    loogbook8,
    loogbook9,
    loogbook10,
    loogbook11,
    loogbook12,
  ];

  const auxiliarie$: Observable<Auxiliary[]> = of([
    auxiliary1,
    auxiliary2,
    auxiliary3,
    auxiliary4,
    auxiliary5,
    auxiliary6,
    auxiliary7,
    auxiliary8,
  ]);

  beforeEach(async(() => {
    apiServiceMock = jasmine.createSpyObj("ApiService", ["getById"]);
    apiServiceMock.getById.and.returnValue(of(logbooks));

    TestBed.configureTestingModule({
      declarations: [EnabledAndNoEnabledPostulantsComponent],
      imports: [BrowserModule, HttpClientTestingModule, RouterTestingModule],
      providers: [{ provide: ApiService, useValue: apiServiceMock }],
    })
      .compileComponents()
      .then(() => {
        fixture = TestBed.createComponent(
          EnabledAndNoEnabledPostulantsComponent
        );
        enabledPostulantsComponent = fixture.componentInstance;

        router = TestBed.get(Router);
        navigateSpy = spyOn(router, "navigate");
      });
  }));

  it("#constructor, should create a postulant list component", () => {
    expect(EnabledAndNoEnabledPostulantsComponent).toBeTruthy();
  });

  it("#searchEnabledPostulants, should get enabled postulants correctly", () => {
    enabledPostulantsComponent.logBook$ = of(logbooks);
    enabledPostulantsComponent.searchEnabledPostulants();
    enabledPostulantsComponent.logBook$.subscribe((x) =>
      expect(x).toEqual(logbooks)
    );
  });

  it("#searchAuxiliaries, should get auxiliaries correctly", () => {
    apiServiceMockAux.getById.and.returnValue(of(auxiliarie$));
    enabledPostulantsComponent.auxiliarie$ = auxiliarie$;
    enabledPostulantsComponent.searchAuxiliaries();
    enabledPostulantsComponent.auxiliarie$.subscribe((x) =>
      expect(x).toEqual([
        auxiliary1,
        auxiliary2,
        auxiliary3,
        auxiliary4,
        auxiliary5,
        auxiliary6,
        auxiliary7,
        auxiliary8,
      ])
    );
  });

  it("#searchEnabledPostulants, should get enabled postulants incorrectly", () => {
    apiServiceMock.getById.and.returnValue(
      throwError("There are no enabled postulants for this announcement")
    );
    enabledPostulantsComponent.searchEnabledPostulants();
    const actualResult = enabledPostulantsComponent.showMessage.type;
    const expectedResult: TypeAlertEnum = TypeAlertEnum.error;
    expect(actualResult).toBe(expectedResult);
  });

  it("#searchAuxiliaries, should get auxiliaries incorrectly", () => {
    apiServiceMock.getById.and.returnValue(
      throwError("There are no auxiliaries registered for this announcement")
    );
    enabledPostulantsComponent.searchAuxiliaries();
    const actualResult = enabledPostulantsComponent.showMessage.type;
    const expectedResult: TypeAlertEnum = TypeAlertEnum.error;
    expect(actualResult).toBe(expectedResult);
  });

  it("#reviewMerits, should move to the reviewMerits view", () => {
    enabledPostulantsComponent.review(loogbook1);
    expect(navigateSpy).toHaveBeenCalledWith(["merits"]);
  });
});
