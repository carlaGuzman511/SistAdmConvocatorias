import { Component, OnInit } from "@angular/core";
import {
  Validators,
  FormBuilder,
  FormGroup,
  FormControl,
  FormArray,
} from "@angular/forms";
import { ApiService } from "../../api-service/api.service";
import { ShowAlertMessage } from "../../showAlertMessage";
import { HttpErrorResponse } from "@angular/common/http";
import { ScheduleEvent } from "../../models/scheduleEvent.model";
import { Router, ActivatedRoute } from "@angular/router";
import { User } from "../../models/user.model";
import { UserService } from "../../api-service/user.service";
import { Deadline } from "src/app/models/deadline.model";

@Component({
  selector: "app-schedule",
  templateUrl: "./schedule.component.html",
  styleUrls: ["./schedule.component.css"],
})
export class ScheduleComponent implements OnInit {
  private user: User;
  public scheduleEvent: ScheduleEvent = {
    description: "",
    note: "",
    idAnnouncement: 0,
    events: [],
    iduser: 0,
  };
  public showAlertMessage = new ShowAlertMessage();
  public form: FormGroup;
  public idAnnouncemnt: number;
  public minDate = new Date();
  public desc: Array<any> = [];
  public notes: Array<any> = [];
  public eventSchedule: Array<any> = [];
  public listEvents: any[];
  public events: string[] = [
    "Publicacion de la convocatoria",
    "Publicacion de habilitados",
    "Presentacion de documentos",
    "Reclamos",
    "Rol de pruebas",
    "Publicacion de resultados",
  ];
  public deadline: any;
  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.user = this.userService.getCurrentUser();
  }
  ngOnInit() {
    this.idAnnouncemnt = +this.route.snapshot.paramMap.get("id");
    this.form = this.fb.group({
      description: this.fb.array([this.initD()]),
      note: this.fb.array([this.initN()]),
      schedule: this.fb.array([this.initSchedule()]),
    });
    this.searchDeadline();
  }
  public initN() {
    return this.fb.group({
      note: [" ", [Validators.minLength(10), Validators.maxLength(1000)]],
    });
  }
  public initD() {
    return this.fb.group({
      description: [
        " ",
        [Validators.minLength(10), Validators.maxLength(1000)],
      ],
    });
  }
  public initSchedule() {
    return this.fb.group({
      eventName: [
        "",
        [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(150),
        ],
      ],
      date: ["", Validators.required],
      time: ["", Validators.required],
      place: [
        "",
        [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(150),
        ],
      ],
    });
  }
  public addEvent(): void {
    const control = <FormArray>this.form.controls['schedule'];
    control.push(this.initSchedule());
  }
  public removeEvent(index): void {
    const control = <FormArray>this.form.controls['schedule'];
    control.removeAt(index);
  }
  public removeDesc(index): void {
    const control = <FormArray>this.form.controls['description'];
    control.removeAt(index);
  }
  public removeNote(index): void {
    const control = <FormArray>this.form.controls['note'];
    control.removeAt(index);
  }

  public thereIsRep(list: any) {
    let rep = false;
    for (let i = 0; i < list.length; i++) {
      for (let j = 0; j < list.length; j++) {
        if (list[i] === list[j] && i !== j) {
          rep = true;
        }
      }
    }
    return rep;
  }
  public existDateDeadline(list: any): boolean {
    let res= false;
    for (let i = 0; i < list.length; i++) {
      if (list[i]["eventName"] === "Presentacion de documentos") {
        if (list[i]["date"] !== this.deadline["deliveryDate"]) {
          res = true;
        }
      }
    }
    return res;
  }

  private saveEvents(): void {
    let repetidos = false;
    this.eventSchedule = [];

    this.listEvents = this.form.value.schedule;
    console.log("date", this.deadline["deliveryDate"]);
    for (const x in this.listEvents) {
      this.eventSchedule.push(this.listEvents[x].eventName);
    }
    repetidos = this.thereIsRep(this.eventSchedule);
    if (this.listEvents.length < this.events.length) {
      this.showAlertMessage.showErrorAlert(
        "Hay eventos que no se registraron."
      );
    } else {
      if (repetidos !== true) {
        if (this.existDateDeadline(this.listEvents)) {
          this.showAlertMessage.showErrorAlert(
            "La fecha del evento: Presentacion de documentos, no coincide con el que registro anteriormente."
          );
        } else {
          this.desc = this.form.controls.description.value;
          if (this.desc.length > 0) {
            this.scheduleEvent.description = this.desc[0].description;
          }
          this.notes = this.form.controls.note.value;
          if (this.notes.length > 0) {
            this.scheduleEvent.note = this.notes[0].note;
          }
          this.scheduleEvent.idAnnouncement = this.idAnnouncemnt;
          this.scheduleEvent.events = this.form.controls.schedule.value;
          this.scheduleEvent.iduser = this.user.iduser;
          this.apiService.post('/schedule/create', this.scheduleEvent).subscribe(
            (response: number) => {
              this.showAlertMessage.showSuccessAlert ('Cronograma registrado exitosamente!');
              this.router.navigate(['/admin/registrar-miembro-comision', this.idAnnouncemnt]);
            },
            (error: HttpErrorResponse) => {
              this.showAlertMessage.showErrorAlert(error.error.message_error);
            }
          );
        }
      } else {
        this.showAlertMessage.showErrorAlert(
          "Hay eventos repetidos. Los eventos tienen que ser diferentes."
        );
      }
    }
  }
  private searchDeadline(): void {
    this.apiService
      .getById("deadline/announcement", this.idAnnouncemnt)
      .subscribe((response) => {
        this.deadline = response[0];
      });
  }

  public cancel(): void {
    this.showAlertMessage.showCancelAlert(
      '¿Seguro que quiere cancelar el llenado de éste formulario?', 'No se podrán recuperar los datos ingresados');
  }
}
