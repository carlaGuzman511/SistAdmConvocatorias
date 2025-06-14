import { Component, OnInit } from "@angular/core";
import { ApiService } from "src/app/api-service/api.service";
import { Router, ActivatedRoute } from "@angular/router";
import { HttpErrorResponse } from "@angular/common/http";
import { Observable } from "rxjs";
import { RequestPostulant } from "../../models/requestPostulant.model";
import * as jsPDF from "jspdf";
import "jspdf-autotable";
import { Postulant } from "../../models/postulant.model";
import {
  FormGroup,
  FormControl,
  Validators,
  FormBuilder,
  FormArray,
} from "@angular/forms";
import { ShowAlertMessage } from "src/app/showAlertMessage";
import { RequestValues } from "../../models/requestValues.model";
import { Area } from "../../models/area.model";
import { UserService } from "../../api-service/user.service";
import { User } from "../../models/user.model";
import { Label1 } from "../../models/label1.model";
import { RequestDetail } from "src/app/models/request-detail.model";

@Component({
  selector: "app-verify-request",
  templateUrl: "./verify-request.component.html",
  styleUrls: ["./verify-request.component.css"],
})
export class VerifyRequestComponent implements OnInit {
  private user: User;
  requestPostulant$: Observable<RequestPostulant[]>;
  requestPostulant: RequestPostulant[];
  idLabelPostulant: number;
  labelA: Label1;
  idAnnouncement: number;
  idPost: number;
  requestList: RequestDetail[];
  idArea: number;
  postulant: Postulant;
  area: Area;

  formStatus: FormGroup;
  requestValues: [];
  request: RequestValues[];
  private showAlertMessage = new ShowAlertMessage();

  constructor(
    private apiService: ApiService,
    private userService: UserService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.user = this.userService.getCurrentUser();
    this.activatedRoute.params.subscribe((params) => {
      this.idLabelPostulant = params["id"];
    });
    this.createRequestForm();
    this.getLabel();
  }
  private createRequestForm(): void {
    this.formStatus = this.fb.group({
      requestValues: this.fb.array([]),
    });
  }

  public get getRequestValues() {
    return this.formStatus.get('requestValues') as FormArray;
  }

  public addRequestValues(st: boolean, obs: string) {
    const statusController = this.fb.group({
      state: st,
      observation: [
        obs,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(1000),
        ],
      ],
    });
    this.getRequestValues.push(statusController);
  }

  public search(): void {
    this.apiService.getAll(`/postulant/${this.idPost}`).subscribe((response: Postulant) => {
      this.postulant = response;
    });
  }

  public getLabel(): void {
    this.apiService.getAll(`/label/${this.idLabelPostulant}`)
    .subscribe((response: Label1) => {
      this.labelA = response;
      this.idAnnouncement = +this.labelA.announcement.idannouncement;
      this.idPost = this.labelA.postulantes.idpostulant;
      this.search();
      this.searchRequestPostulant();
    },
      (error: HttpErrorResponse) => {
        this.showAlertMessage.showErrorAlert(error.error.message_error);
      }
    );
  }
  public searchRequestPostulant(): void {
     this.apiService.getAll(`/requestPostulant/postulant/${this.idPost}`)
      .subscribe((response: RequestPostulant[]) => {
        this.requestPostulant = response;
        if (this.requestPostulant.length === 0) {
          this.apiService.getAll(`/requestdetail/announcement/${this.idAnnouncement}`)
          .subscribe(res => {
            this.requestList = res;
            for (let i in this.requestList) {
              this.addRequestValues(false, "");
            }      
          });
         } else {

          this.showAlertMessage.showError(
            'ya  se realizo la verificacion de requisitos para este postulante');
          this.router.navigate(['merito/postulantes']);
        }
      });
  }
  public getFullNamePostulant(): string {
    return (this.postulant.person.name + ' ' + this.postulant.person.lastName);
  }

  public getRequestDescription(i: number): string {
    return this.requestList[i].description;
  }


  public save(): void {
    var enable = true;
    var requestForm: RequestValues;
    this.requestValues = this.formStatus.controls.requestValues.value;
    for (const i in this.requestValues) {
      requestForm = {
        status: this.requestValues[i]["state"],
        observation: this.requestValues[i]["observation"],
        idrequestdetail: this.requestList[i].idrequestdetail,
        idpostulant: this.idPost,
        iduser: this.user.iduser,
      };
      if (!requestForm.status) {
        enable = false;
      }
      this.apiService.post("/requestPostulant", requestForm).subscribe(
        (response) => {
          const postulantEnabled = {
            idPostulant: this.postulant.idpostulant,
            status: enable,
          };
          this.apiService
            .updateList("postulant/update/status", postulantEnabled)
            .subscribe((res) => {
              if (enable) {
                this.showAlertMessage.showSuccess(
                  "Se registro la calificacion. POSTULANTE HABILITADO "
                );
              } else {
                this.showAlertMessage.showError(
                  "se registro las calificaciones. POSTULANTE INHABILITADO"
                );
              }
            });
        },
        (error: HttpErrorResponse) => {
          this.showAlertMessage.showErrorAlert(error.error.message_error);
        }
      );
    }
    this.router.navigate(['merito/postulantes']);
  }
  public cancel(): void {
    this.router.navigate(['merito/postulantes']);
  }
}
