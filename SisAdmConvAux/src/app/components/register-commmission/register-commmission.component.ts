import { Component, OnInit } from "@angular/core";
import { Observable } from "rxjs";
import { Auxiliary } from "src/app/models/auxiliary.model";
import { Thematic } from "src/app/models/thematic.model";
import { Announcement } from "src/app/models/announcement.model";
import { Area } from "src/app/models/area.model";
import { Role } from "src/app/models/role.model";
import { AcademicUnit } from "src/app/models/academic-unit.model";
import { ApiService } from "src/app/api-service/api.service";
import { Router, ActivatedRoute } from "@angular/router";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Management } from "src/app/models/management.model";
import { UserService } from "src/app/api-service/user.service";
import { User } from "src/app/models/user.model";
import { User1 } from "../../models/user1.model";
import { Person1 } from "src/app/models/person.model";
import { HttpErrorResponse } from "@angular/common/http";
import { HttpClient } from "@angular/common/http";
import { Schedule } from "src/app/models/schedule.model";
import { Event } from "src/app/models/event.model";
import { Courts1 } from "../../models/courts.model";
import { ShowAlertMessage } from "src/app/showAlertMessage";

@Component({
  selector: "app-register-commmission",
  templateUrl: "./register-commmission.component.html",
  styleUrls: ["./register-commmission.component.css"],
})
/**
  This class contains the component register commission member
  @class RegisterCommmissionComponent
*/
export class RegisterCommmissionComponent implements OnInit {
  private showMessage = new ShowAlertMessage();
  private userForm: FormGroup;
  private thematic$: Observable<Thematic[]>;
  private auxiliarie$: Observable<Auxiliary[]>;
  private role$: Observable<Role[]>;
  private roles: Role[];
  private thematics: Thematic[];
  private auxiliaries: Auxiliary[];
  private schedule: Schedule;
  private event$: Observable<Event[]>;
  private events: Event[];
  private person: Person1 = {
    ci: 0,
    name: "",
    lastName: "",
    address: "",
    phoneNumber: 0,
    email: "",
  };
  private courts: Courts1;
  private user: User1 = {
    ci: 0,
    password: "",
    role: 0,
    person: this.person,
    announcements: [],
    thematics: [],
    auxiliarys: [],
    startDate: "",
    endDate: "",
    startHour: "",
    endHour: "",
    enable: false,
    iduser: 0,
  };
  private idAuxiliary: number;
  private idArea: number;
  private idThematic: number;
  private admin: User;
  private validateUser = {
    ci: 0,
  };
  private searchPostAnnouncements = {
    idarea: 0,
    idacademicunit: 0,
    idmanagement: 0,
  };
  private area: string;
  private credetencials = {
    email: "",
    password: "",
  };
  private areas: Area[];
  private managements: Management[];
  private management$: Observable<Management[]>;
  private area$: Observable<Area[]>;
  private academicUnit$: Observable<AcademicUnit[]>;
  private academicUnits: AcademicUnit[];

  private announcements: Announcement[];
  private announcement$: Observable<Announcement[]>;
  private announcement: Announcement;
  private idAnnouncement: number;

  constructor(
    private apiService: ApiService,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    private http: HttpClient
  ) {}

  ngOnInit() {
    this.admin = this.userService.getCurrentUser();
    this.createRegisterCommissionMemberForm();
  }
  /**
   This function creates the register commission member form
    @method createRegisterCommissionMemberForm()
  */
  private createRegisterCommissionMemberForm(): void {
    this.userForm = new FormGroup({
      academicUnit: new FormControl("", [Validators.required]),
      area: new FormControl("", [Validators.required]),
      management: new FormControl("", [Validators.required]),
      announcement: new FormControl("", [Validators.required]),
      role: new FormControl("", [Validators.required]),

      auxiliary: new FormControl(""),
      thematic: new FormControl(""),

      names: new FormControl("", [
        Validators.minLength(3),
        Validators.maxLength(50),
        Validators.required,
      ]),
      lastNames: new FormControl("", [
        Validators.minLength(3),
        Validators.maxLength(50),
        Validators.required,
      ]),
      identityCard: new FormControl("", [
        Validators.minLength(6),
        Validators.maxLength(10),
        Validators.pattern("^[0-9]*$"),
        Validators.required,
      ]),
      email: new FormControl("", [
        Validators.minLength(10),
        Validators.maxLength(70),
        Validators.required,
        Validators.email,
      ]),
      phoneNumber: new FormControl("", [
        Validators.minLength(8),
        Validators.maxLength(11),
        Validators.required,
        Validators.pattern("^[0-9]*$"),
      ]),
      address: new FormControl("", [
        Validators.minLength(10),
        Validators.maxLength(70),
        Validators.required,
      ]),
    });
    this.searchAcademicUnit();
    this.searchAreas();
    this.searchManagements();
    this.searchRoles();
  }
  /**
   This function searches the academic units
    @method searchAcademicUnit()
  */
  private searchAcademicUnit(): void {
    this.academicUnit$ = this.apiService.getAll("/academicunit");
    this.academicUnit$.subscribe((response: AcademicUnit[]) => {
      this.academicUnits = response;
    });
    if (!this.academicUnit$) {
      this.showMessage.showError("No se encontraron unidades academicas");
    }
  }
  /**
   This function searches the areas
   @method searchAreas()
  */
  private searchAreas(): void {
    this.area$ = this.apiService.getAll("/area");
    this.area$.subscribe((response: Area[]) => {
      this.areas = response;
    });
    if (!this.area$) {
      this.showMessage.showError("No se encontraron areas");
    }
  }
  /**
   This function searches the managements
   @method searchManagements()
  */
  private searchManagements(): void {
    this.management$ = this.apiService.getAll("/management");
    this.management$.subscribe((response: Management[]) => {
      this.managements = response;
    });
    if (!this.management$) {
      this.showMessage.showError("No se encontraron gestiones");
    }
  }
  /**
   This function searches the announcements by academic unit, area and management
    @method searchAnnouncements()
  */
  private searchAnnouncements(): void {
    this.searchPostAnnouncements.idacademicunit = this.userForm.controls.academicUnit.value;
    this.searchPostAnnouncements.idarea = this.userForm.controls.area.value;
    this.searchPostAnnouncements.idmanagement = this.userForm.controls.management.value;

    this.announcement$ = this.apiService.post(
      "/announcement/list/MAAU",
      this.searchPostAnnouncements
    );
    this.announcement$.subscribe((response: Announcement[]) => {
      this.announcements = response;
      this.announcement = this.announcements[0];
      this.idAnnouncement = this.announcement.idannouncement;
      this.searchShedule();
      this.searchCourts();
    });
    if (!this.announcement$) {
      this.showMessage.showError(
        "No se encontraron convocatorias relacionadas al area unidad academica y gestion dados"
      );
    }
  }
  /**
   This function searches the roles
    @method searchRoles()
  */
  private searchRoles(): void {
    this.role$ = this.apiService.getAll("/role");
    this.role$.subscribe((response: Role[]) => {
      this.roles = response.slice(2, 6);
    });
    if (!this.role$) {
      this.showMessage.showError("No se encontraron roles");
    }
  }
  /**
   This function searches the announcement's courts 
    @method searchCourts()
  */
  private searchCourts(): void {
    this.apiService
      .getById("courts/description", this.idAnnouncement)
      .subscribe(
        (response: Courts1) => {
          this.courts = response;
        },
        (error: HttpErrorResponse) => {
          this.showMessage.showError(
            "No se encontraron los tribunales para la convocatoria"
          );
        }
      );
  }
  /**
   This function searches the announcement's schedule
    @method searchShedule()
  */
  private searchShedule(): void {
    this.apiService
      .getById("schedule/announcement", this.idAnnouncement)
      .subscribe(
        (response: Schedule[]) => {
          this.schedule = response[0];
          this.searchEvents();
        },
        (error: HttpErrorResponse) => {
          this.showMessage.showError(
            "No se encontro un cronograma para la convocatoria"
          );
        }
      );
  }
  /**
   This function searches the announcement's events
    @method searchEvents()
  */
  private searchEvents(): void {
    this.event$ = this.apiService.getById(
      "event/schedule",
      this.schedule.idschedule
    );
    this.event$.subscribe(
      (response: Event[]) => {
        this.events = response;
      },
      (error: HttpErrorResponse) => {
        this.showMessage.showError(
          "No se encontraron eventos para el cronograma"
        );
      }
    );
  }
  /**
   This function searches the auxiliaries thematics
    @method searchThematics()
  */
  public searchThematics(): void {
    if (this.searchPostAnnouncements.idarea == 1) {
      this.thematic$ = this.apiService.getById(
        "/thematic/announcement",
        this.idAnnouncement
      );
      this.thematic$.subscribe((response: Thematic[]) => {
        this.thematics = response;
      });
      if (!this.thematic$) {
        this.showMessage.showError(
          "No se encontraron tematicas relacionadas a esta convocatoria"
        );
      }
    } else {
      if (this.searchPostAnnouncements.idarea != 1) {
        this.auxiliarie$ = this.apiService.getById(
          "/announcement/listadoauxiliary",
          this.idAnnouncement
        );
        this.auxiliarie$.subscribe((response: Auxiliary[]) => {
          this.auxiliaries = response;
        });
        if (!this.auxiliarie$) {
          this.showMessage.showError(
            "No se encontraron tematicas relacionadas a esta convocatoria"
          );
        }
      }
    }
  }
  /**
   This function shows the actual quantity courts of the announcement 
    @method showMessageQuantity()
  */
  private showMessageQuantity(): void {
    if (this.userForm.controls.role.value == 3) {
      this.showMessage.showInfoAlert(
        "La cantidad actual de miembros de la comision de conocimientos docentes es: " +
          (+this.courts.numberKnowledgeCourts + 1)
      );
    }
    if (this.userForm.controls.role.value == 4) {
      this.showMessage.showInfoAlert(
        "La cantidad actual de miembros de la comision de meritos docentes es: " +
          (+this.courts.numberMeritCourts + 1)
      );
    }
    if (this.userForm.controls.role.value == 5) {
      this.showMessage.showInfoAlert(
        "La cantidad actual de miembros de la comision de conocimientos estudiantes es: " +
          (+this.courts.numberKnowledgeStudentDelegate + 1)
      );
    }
    if (this.userForm.controls.role.value == 6) {
      this.showMessage.showInfoAlert(
        "La cantidad actual de miembros de la comision de meritos estudiantes es: " +
          (+this.courts.numberMeritStudentDelegate + 1)
      );
    }
  }
  /**
   This function verify the quantity courts of the announcement 
    @method verifyQuantity()
  */
  private verifyQuantity(): boolean {
    if (this.userForm.controls.role.value == 3) {
      if (this.courts.numberKnowledgeCourts < this.courts.knowledgeCourts) {
        return true;
      } else {
        this.showMessage.showWarningAlert(
          "La cantidad de miembros de la comision de conocimientos docentes ya esta satisfecha"
        );
        return false;
      }
    }
    if (this.userForm.controls.role.value == 4) {
      if (this.courts.numberMeritCourts < this.courts.meritsCourts) {
        return true;
      } else {
        this.showMessage.showWarningAlert(
          "La cantidad de miembros de la comision de meritos docentes ya esta satisfecha"
        );
        return false;
      }
    }
    if (this.userForm.controls.role.value == 5) {
      if (
        this.courts.numberKnowledgeStudentDelegate <
        this.courts.studentDelegateKnowledge
      ) {
        return true;
      } else {
        this.showMessage.showWarningAlert(
          "La cantidad de miembros de la comision de conocimientos estudiantes ya esta satisfecha"
        );
        return false;
      }
    }
    if (this.userForm.controls.role.value == 6) {
      if (
        this.courts.numberMeritStudentDelegate <
        this.courts.studentDelegateMerit
      ) {
        return true;
      } else {
        this.showMessage.showWarningAlert(
          "La cantidad de miembros de la comision de meritos estudiantes ya esta satisfecha"
        );
        return false;
      }
    }
  }
  /**
   This function creates the register commission member
    @method saveUser()
  */
  private saveUser(): void {
    this.showMessageQuantity();
    this.validateUser.ci = this.userForm.controls.identityCard.value;
    this.apiService
      .post("/user/verification", this.validateUser)
      .subscribe((response: User) => {
        if (
          this.courts.numberKnowledgeCourts == this.courts.knowledgeCourts &&
          this.courts.numberMeritCourts == this.courts.meritsCourts &&
          this.courts.numberKnowledgeStudentDelegate ==
            this.courts.studentDelegateKnowledge &&
          this.courts.numberMeritStudentDelegate ==
            this.courts.studentDelegateMerit
        ) {
          this.showMessage.showInfoAlert(
            "Los miembros de las distintas comisiones fueron registrados exitosamente"
          );
          this.router.navigate(["/admin"]);
        } else {
          if (this.verifyQuantity()) {
            if (!response) {
              this.saveData();
              this.apiService.post("/user", this.user).subscribe(
                (response: number) => {
                  this.showMessage.showSuccessAlert(
                    "El usuario fue registrado exitosamente..."
                  );
                  //this.sendPassword(this.user.person.email, this.user.password);
                  this.clean();
                  window.location.reload();
                },
                (error: HttpErrorResponse) => {
                  this.showMessage.showError("Ocurrio un problema..");
                  this.clean();
                }
              );
            } else {
              this.showMessage.showWarningAlert(
                "Un usuario con ese carnet de identidad ya fue registrado"
              );
            }
          }
        }
      });
  }
  /**
   This function save the register commission member data that were recorded in the form
    @method saveData()
  */
  private saveData(): void {
    this.person.name = this.userForm.controls.names.value;
    this.person.lastName = this.userForm.controls.lastNames.value;
    this.person.email = this.userForm.controls.email.value;
    this.person.ci = this.userForm.controls.identityCard.value;
    this.person.phoneNumber = this.userForm.controls.phoneNumber.value;
    this.person.address = this.userForm.controls.address.value;
    this.user.announcements = [+this.idAnnouncement];
    this.user.ci = this.userForm.controls.identityCard.value;
    this.user.role = this.userForm.controls.role.value;
    this.user.person = this.person;
    this.user.enable = true;
    this.user.startDate = this.events[0].dateEvent;
    this.user.startHour = this.events[0].timeEvent;
    this.user.endDate = this.events[this.events.length - 1].dateEvent;
    this.user.endHour = this.events[this.events.length - 1].timeEvent;
    this.user.password = this.generatePassword();
    this.user.iduser = this.admin.iduser;
    if (
      this.userForm.controls.role.value == 3 ||
      this.userForm.controls.role.value == 5
    ) {
      if (this.announcement.area.name === "Laboratorio") {
        this.user.thematics = [+this.userForm.controls.thematic.value];
      } else {
        if (this.announcement.area.name === "Docencia") {
          this.user.auxiliarys = [+this.userForm.controls.auxiliary.value];
        }
      }
    }
  }
  /**
    This function generate the password
    @method generatePassword()
  */
  private generatePassword(): string {
    const longitud = (
      this.userForm.controls.names.value +
      this.userForm.controls.lastNames.value
    ).length;
    const caracteres = "abcdefghijkmnpqrtuvwxyzABCDEFGHIJKLMNPQRTUVWXYZ2346789";
    let contraseña = "";
    for (let i = 0; i < longitud; i++) {
      contraseña += caracteres.charAt(
        Math.floor(Math.random() * caracteres.length)
      );
    }
    return contraseña;
  }
  /**
    This function cancel to the function save
    @method cancel()
  */
  private cancel(): void {
    this.showMessage.showCancelAlert(
      "¿Esta seguro que no desea registrar miembros de la comision?",
      ""
    );
  }
  /**
    This function send the password via email
    @method sendPassword()
  */
  private sendPassword(email: string, password: string): void {
    this.credetencials.email = email;
    this.credetencials.password = password;
    this.apiService
      .sendEmail("http://localhost:3000/sendmail", this.credetencials)
      .subscribe(
        (response) => {
          const res: any = response;
          this.showMessage.showSuccessAlert(
            "Se envio la contraseña al email del miembro de la comision"
          );
        },
        (err) => {
          this.showMessage.showError(
            "No se pudo enviar un email al miembro de la comision"
          );
        }
      );
  }
  /**
   This function clean the form fields
    @method clean()
  */
  private clean(): void {
    this.userForm.patchValue({
      role: "",
      area: "",
      auxiliary: "",
      thematic: "",
      names: "",
      lastNames: "",
      identityCard: "",
      email: "",
      phoneNumber: "",
      address: "",
    });
  }
}
