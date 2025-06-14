import { Component, OnInit } from "@angular/core";
import {
  FormGroup,
  FormControl,
  Validators,
  FormBuilder,
  FormArray,
} from "@angular/forms";
import { Router, ActivatedRoute } from "@angular/router";
import { HttpErrorResponse } from "@angular/common/http";
import { Person } from "src/app/models/person.model";
import { ShowAlertMessage } from "src/app/showAlertMessage";
import { ApiService } from "src/app/api-service/api.service";
import { LogBook1 } from "src/app/models/logbook1.model";
import { VerifyRequest } from "src/app/models/verify-request.model";
import { User } from "src/app/models/user.model";
import { UserService } from "src/app/api-service/user.service";

@Component({
  selector: "app-register-postulant-logbook",
  templateUrl: "./register-postulant-logbook.component.html",
  styleUrls: ["./register-postulant-logbook.component.css"],
})
export class RegisterPostulantLogbookComponent implements OnInit {
  public registerForm: FormGroup;
  private user: User;
  public idPerson: number;
  public today = new Date();
  public repeated: number;
  public titleAnnouncement: string;
  public auxsPerson: Array<any> = [];
  public LabelsPerson: Array<any> = [];
  public careerPerson: string;
  public currentPerson: Person = {
    id: 0,
    ci: 0,
    name: "",
    lastName: "",
    address: "",
    phoneNumber: 0,
    email: "",
  };
  public observation: Array<any> = [];
  public logBook: LogBook1 = {
    deliveryHour: "",
    deliveryDate: "",
    documentQuantity: 0,
    observation: "",
    label: 0,
    // iduser: 0
  };
  public verifyExistence: VerifyRequest = {
    idlabel: 0,
  };
  public showAlertMessage = new ShowAlertMessage();
  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private router: Router,
    private apiService: ApiService,
    private fb: FormBuilder
  ) {
    this.user = this.userService.getCurrentUser();
    this.valregisterForm();
  }

  ngOnInit() {
    this.idPerson = +this.route.snapshot.paramMap.get("id");
    this.getAuxiliaries(this.idPerson);
    this.getPerson();
    this.getLabels(this.idPerson);
  }

  public onSubmit() {
    this.repeated = 0;
    this.verifyExistence.idlabel = this.LabelsPerson[0].idlabel;
    this.apiService
      .post("/logbook/verification/label", this.verifyExistence)
      .subscribe((responseRep: any) => {
        this.repeated = responseRep;
        this.observation = this.registerForm.controls.notes.value;
        if (this.observation.length > 0) {
          this.logBook.observation = this.observation[0].note;
        }
        this.logBook.documentQuantity = +this.registerForm.controls.number_docs
          .value;
        // this.logBook.iduser = this.user.iduser;
        this.logBook.deliveryDate = this.registerForm.controls.deliveryDate.value;
        this.logBook.deliveryHour = this.registerForm.controls.deliveryTime.value;
        if (this.repeated === 2) {
          // tslint:disable-next-line:forin
          for (const i in this.auxsPerson) {
            // tslint:disable-next-line:forin
            for (const j in this.LabelsPerson) {
              if (
                this.auxsPerson[i].idauxiliary ===
                this.LabelsPerson[j].auxiliary.idauxiliary
              ) {
                this.logBook.label = +this.LabelsPerson[j].idlabel;
                this.apiService.post("/logbook", this.logBook).subscribe(
                  (response: number) => {
                    if (this.auxsPerson.length === +i + 1) {
                      this.showAlertMessage.showSuccessAlert(
                        "Postulación registrada exitosamente"
                      );
                      this.router.navigate(["/secretario/buscarPostulante"]);
                    }
                  },
                  (error: HttpErrorResponse) => {
                    this.showAlertMessage.showErrorAlert(
                      "Ocurrió un error, vuelve a intentarlo"
                    );
                  }
                );
              }
            }
          }
        } else {
          this.router.navigate(["/secretario/buscarPostulante"]);
          this.showAlertMessage.showErrorAlert(
            "El postulante ya fue registrado anteriormente."
          );
        }
      });
  }

  private valregisterForm(): void {
    this.registerForm = this.fb.group({
      notes: this.fb.array([this.initY()]),
      number_docs: new FormControl("", [
        Validators.maxLength(3),
        Validators.required,
        Validators.pattern("^[0-9]+"),
      ]),
      deliveryTime: new FormControl("", [Validators.required]),
      deliveryDate: new FormControl("", [Validators.required]),
    });
  }

  public initY(): any {
    return this.fb.group({
      note: [
        "",
        [
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(2000),
        ],
      ],
    });
  }
  public removeY(index: number): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray>this.registerForm.controls["notes"];
    control.removeAt(index);
  }

  public getAuxiliaries(id: number): any {
    let endpoint = "/auxiliary/person/";
    endpoint = endpoint.concat(id.toString());
    this.apiService.getAll(endpoint).subscribe((response: any) => {
      this.auxsPerson = response;
    });
  }
  public getLabels(id: number): any {
    let endpoint = "/label/person/";
    endpoint = endpoint.concat(id.toString());
    this.apiService.getAll(endpoint).subscribe((response: any) => {
      this.LabelsPerson = response;
      this.titleAnnouncement = this.LabelsPerson[0].announcement.title;
      this.careerPerson = this.LabelsPerson[0].postulantes.career.name;
    });
  }

  public getPerson(): any {
    this.apiService
      .getById("/person", this.idPerson)
      .subscribe((response: any) => {
        this.currentPerson = response;
      });
  }
}
