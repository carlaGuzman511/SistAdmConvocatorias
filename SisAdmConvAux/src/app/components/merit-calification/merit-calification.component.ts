import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators, FormArray } from "@angular/forms";
import { ApiService } from "src/app/api-service/api.service";
import { Router, ActivatedRoute } from "@angular/router";
import { MeritA } from "src/app/models/merit.model";
import { AnnouncementC } from "src/app/models/announcement.model";
import { DetailMerit } from "src/app/models/detail-merit.model";
import { SubDetailMerit } from "src/app/models/subdetail-merit.model";
import { SubSubDetailMerit } from "src/app/models/sub-subdetail-merit.model";
import { Person } from "src/app/models/person.model";
import { ShowAlertMessage } from "src/app/showAlertMessage";
import { MeritScore } from "src/app/models/merits_score.model";
import { VerifyRequest } from "src/app/models/verify-request.model";

@Component({
  selector: "app-merit-calification",
  templateUrl: "./merit-calification.component.html",
  styleUrls: ["./merit-calification.component.css"],
})
export class MeritCalificationComponent implements OnInit {
  public form: FormGroup;
  public merits: Array<any> = [];
  public test: Array<any> = [];
  public repeated: number;
  public subdetails: Array<any> = [];
  public isCorrect: Array<any> = [];
  public pointDetails: Array<any> = [];
  public idAnnouncemnt: number;
  public merit: MeritA;
  private detailMerits: DetailMerit[];
  private subDetailMerits: SubDetailMerit[][];
  private subSubDetailMerits: SubSubDetailMerit[][];
  public subDetails: SubDetailMerit[];
  public announcement: AnnouncementC;
  public auxsPerson: Array<any> = [];
  public LabelsPerson: Array<any> = [];
  public postulantPerson: any;
  public currentPerson: Person;
  public idPerson: number;
  public posAnnouncements: Array<any> = [];
  public scoresList: Array<any> = [];
  public obsList: Array<any> = [];
  public meritScores: MeritScore = {
    label: 0,
    score: 0,
    percentage: 0,
  };
  public verifyExistence: VerifyRequest = {
    idlabel: 0,
  };
  public showAlertMessage = new ShowAlertMessage();
  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.idPerson = +this.route.snapshot.paramMap.get("id");
    this.form = this.fb.group({
      scores: this.fb.array([this.initX()]),
    });
    this.getPerson();
  }

  initX() {
    return this.fb.group({
      score: [
        "",
        [
          Validators.required,
          Validators.maxLength(5),
          Validators.pattern("[0-9]+(.[0-9][0-9]?)?"),
        ],
      ],
      observation: ["", [Validators.minLength(5), Validators.maxLength(255)]],
      subsubscores: this.fb.array([this.initY()]),
    });
  }

  initY() {
    return this.fb.group({
      //  ---------------------forms fields on y level ------------------------
      score: [
        "",
        [
          Validators.required,
          Validators.maxLength(5),
          Validators.pattern("[0-9]+(.[0-9][0-9]?)?"),
        ],
      ],
      observation: ["", [Validators.minLength(5), Validators.maxLength(255)]],
      // ---------------------------------------------------------------------
    });
  }

  addX() {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray>this.form.controls["scores"];
    control.push(this.initX());
  }

  addY(ix) {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = (<FormArray>this.form.controls["scores"])
      .at(ix)
      .get("subsubscores") as FormArray;
    control.push(this.initY());
  }

  removeX(index) {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray>this.form.controls["scores"];
    control.removeAt(index);
  }

  private getMeritRatingTable(): void {
    this.apiService
      .getById("merit/announcement", this.idAnnouncemnt)
      .subscribe((response: MeritA[]) => {
        this.merit = response[0];
        this.getMerits();
      });
  }

  private getMerits(): void {
    this.apiService
      .getById("detailmerit/merit", this.merit.idmerit)
      .subscribe((response: DetailMerit[]) => {
        this.detailMerits = response;
        this.getDetails();
      });
  }

  private getDetails(): void {
    const subDetailMerits = [];
    const answerSubDetailLists = [];
    for (const detailMerit of this.detailMerits) {
      this.apiService
        .getById("subdetailmerit/detailmerit", detailMerit.iddetailmerit)
        .subscribe((response: SubDetailMerit[]) => {
          subDetailMerits.push(response);
          for (const subdetail of response) {
            answerSubDetailLists.push(subdetail);
            this.addX();
            this.getSubdetails(subdetail);
          }
        });
    }
    this.subDetails = answerSubDetailLists;
    this.removeX(this.subDetails.length);
  }

  private getSubdetails(subDetail: SubDetailMerit): void {
    const subSubDetailMerits = [];
    const answerSubSubDetailLists = [];
    this.apiService
      .getById("subsubdetailmerit/subdetailmerit", subDetail.idsubdetailmerit)
      .subscribe((response: SubSubDetailMerit[]) => {
        if (response.length > 0) {
          subSubDetailMerits.push(response);
        }
        for (const subSubdetail of response) {
          answerSubSubDetailLists.push(subSubdetail);
        }
      });
    this.subdetails.push(answerSubSubDetailLists);
  }

  public getAnnouncement(id: number) {
    this.apiService.getById("/announcement", id).subscribe((response: any) => {
      this.announcement = response;
      this.getMeritRatingTable();
    });
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
    });
  }

  public getPostulation(id: number): any {
    let endpoint = "/postulant/person/";
    endpoint = endpoint.concat(id.toString());
    this.apiService.getAll(endpoint).subscribe((response: any) => {
      this.postulantPerson = response[0];
      this.posAnnouncements = this.postulantPerson.announcements;
      this.idAnnouncemnt = this.posAnnouncements[0].idannouncement;
      this.getAnnouncement(this.idAnnouncemnt);
    });
  }

  public getPerson(): any {
    this.apiService
      .getById("/person", this.idPerson)
      .subscribe((response: any) => {
        this.currentPerson = response;
        this.getAuxiliaries(this.idPerson);
        this.getPostulation(this.idPerson);
        this.getLabels(this.idPerson);
      });
  }

  public save() {
    this.repeated = 0;
    this.verifyExistence.idlabel = this.LabelsPerson[0].idlabel;
    this.apiService
      .post("/resultmeritevaluation/verification/label", this.verifyExistence)
      .subscribe((responseRep: any) => {
        this.repeated = responseRep;
        if (this.repeated === 2) {
          let sco = 0;
          this.scoresList = this.form.controls.scores.value;
          // tslint:disable-next-line:forin
          for (const i in this.scoresList) {
            sco = sco + +this.scoresList[i].score;
          }
          this.isCorrectAdd();
          if (this.isCorrect.includes("BadAddition")) {
            this.showAlertMessage.showErrorAlert(
              "Algún puntaje supera el máximo que se puede ingresar."
            );
          } else {
            if (sco <= +this.merit.baseScore) {
              this.meritScores.score = sco;
              this.meritScores.percentage = this.merit.finalScore;
              // tslint:disable-next-line:forin
              for (const i in this.LabelsPerson) {
                this.meritScores.label = this.LabelsPerson[i].idlabel;
                this.apiService
                  .post("/resultmeritevaluation", this.meritScores)
                  .subscribe((response: number) => {
                    if (this.LabelsPerson.length === +i + 1) {
                      this.showAlertMessage.showSuccessAlert(
                        "Calificación de méritos registrada exitosamente"
                      );
                      this.router.navigate(["/merito/habilitados"]);
                    }
                  });
              }
            } else {
              this.showAlertMessage.showErrorAlert(
                "La suma de los puntos supera el puntaje base"
              );
            }
          }
        } else {
          this.router.navigate(["/merito/habilitados"]);
          this.showAlertMessage.showErrorAlert(
            "La calificación de méritos del postulante ya fue registrado anteriormente."
          );
        }
      });
  }

  public isCorrectAdd() {
    this.isCorrect = [];
    // tslint:disable-next-line:forin
    for (const y in this.scoresList) {
      if (+this.scoresList[y].score > +this.subDetails[y].percentage) {
        this.isCorrect.push("BadAddition");
      } else {
        this.isCorrect.push("Well");
      }
    }
  }
}
