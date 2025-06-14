import { Component, OnInit } from "@angular/core";
import { User } from "src/app/models/user.model";
import { Observable } from "rxjs";
import { LogBook } from "src/app/models/logbook.model";
import { Auxiliary } from "src/app/models/auxiliary.model";
import { Knowledge } from "src/app/models/knowledge.model";
import { FormGroup, FormBuilder, Validators, FormArray } from "@angular/forms";
import { HttpErrorResponse } from "@angular/common/http";
import { ApiService } from "src/app/api-service/api.service";
import { Router } from "@angular/router";
import { UserService } from "src/app/api-service/user.service";
import { ResultLaboratoryEvaluation } from "src/app/models/result-laboratory-evaluation.model";
import { Thematic } from "src/app/models/thematic.model";
import { LaboratoryEvaluation } from "src/app/models/laboratory-evaluation.model";
import { Schedule } from "src/app/models/schedule.model";
import { Event } from "src/app/models/event.model";
import { ShowAlertMessage } from "src/app/showAlertMessage";

@Component({
  selector: "app-written-laboratory-evaluation",
  templateUrl: "./written-laboratory-evaluation.component.html",
  styleUrls: ["./written-laboratory-evaluation.component.css"],
})
/**
  This class contains the written laboratory evaluation component
  @class WrittenLaboratoryEvaluationComponent
*/
export class WrittenLaboratoryEvaluationComponent implements OnInit {
  private user: User;
  private logBook$: Observable<LogBook[]>;
  private logBooks: LogBook[];
  private answers: boolean[] = [];
  private auxiliary: Auxiliary;
  private thematic: Thematic;
  private knowledge: Knowledge;
  private laboratoryEvaluations: LaboratoryEvaluation[];
  private laboratoryEvaluation: LaboratoryEvaluation;
  private evaluationForm: FormGroup;
  private evaluations: [];
  private showAlertMessage = new ShowAlertMessage();
  private enabled = {
    idannouncement: 0,
    idthematic: 0,
  };
  private schedule: Schedule;
  private events: Event[];
  private enabledPublication: string;
  private publicationOfResults: string;
  private laboEvalPostulants = [];

  constructor(
    private apiService: ApiService,
    private fb: FormBuilder,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.user = this.userService.getCurrentUser();
    this.thematic = this.user.thematics[0];
    this.createEvaluationForm();
    this.datesValidator(this.user.announcements[0].idannouncement);
  }
  /**
   This function creates the evaluation form
    @method createEvaluationForm()
  */
  private createEvaluationForm(): void {
    this.evaluationForm = this.fb.group({
      description: [
        "",
        {
          validators: [
            Validators.required,
            Validators.minLength(5),
            Validators.maxLength(2500),
          ],
        },
      ],
      evaluations: this.fb.array([]),
    });
  }
  /**
   This function obtains the evaluations that were recorded in the form
    @method getEvaluations()
  */
  private get getEvaluations() {
    return this.evaluationForm.get("evaluations") as FormArray;
  }
  /**
   This function add new evaluations in the form
    @method addEvaluation()
  */
  private addEvaluation() {
    const evaluationControler = this.fb.group({
      score: [
        "",
        {
          validators: [
            Validators.pattern("[0-9]+(.[0-9][0-9]?)?"),
            // Validators.pattern('(^(\+|\-)(0|([1-9][0-9]*))(\.[0-9]{1,2})?$)|(^(0{0,1}|([1-9][0-9]*))(\.[0-9]{1,2})?$)'),
            Validators.required,
          ],
        },
      ],
    });
    this.getEvaluations.push(evaluationControler);
  }
  /**
   This function delete the evaluations that were recorded in the form
    @method removeEvaluation()
  */
  private removeEvaluation(index: any) {
    this.getEvaluations.removeAt(index);
  }
  /**
    This function validates the dates for revision
    @method datesValidator()
  */
  private datesValidator(idAnnnouncement: number) {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    let enabledPublication = new Date();
    let publicationOfResults = new Date();
    this.apiService
      .getById("schedule/announcement", idAnnnouncement)
      .subscribe((response: Schedule[]) => {
        this.schedule = response[0];
        this.apiService
          .getById("event/schedule", this.schedule.idschedule)
          .subscribe((events: Event[]) => {
            this.events = events;
            for (const event of this.events) {
              if (event.name === "Publicacion de habilitados") {
                this.enabledPublication = event.dateEvent;
                enabledPublication = new Date(this.enabledPublication);
                enabledPublication.setHours(0, 0, 0, 0);
                enabledPublication.setTime(
                  enabledPublication.getTime() + 1 * 24 * 60 * 60 * 1000
                );
                console.log("public de habilitados", enabledPublication);
              } else {
                if (event.name === "Publicacion de resultados") {
                  this.publicationOfResults = event.dateEvent;
                  publicationOfResults = new Date(this.publicationOfResults);
                  publicationOfResults.setHours(0, 0, 0, 0);
                  publicationOfResults.setTime(
                    publicationOfResults.getTime() + 1 * 24 * 60 * 60 * 1000
                  );
                  console.log("public de res finales", publicationOfResults);
                }
              }
            }
            if (today > enabledPublication) {
              if (today <= publicationOfResults) {
                this.searchEnabledPostulants();
              } else {
                this.showAlertMessage.showError(
                  "La fecha de publicación de resultados ya sucedió, fue el: " +
                    this.publicationOfResults
                );
              }
            } else {
              this.showAlertMessage.showError(
                "La fecha de publicacion de postulantes habilitados sera el: " +
                  this.enabledPublication
              );
            }
          });
      });
  }
  /**
    This function searches the enabled postulants
    @method searchEnabledPostulants()
  */
  private searchEnabledPostulants(): void {
    if (this.user === undefined) {
      return;
    }
    this.enabled.idannouncement = this.user.announcements[0].idannouncement;
    this.enabled.idthematic = this.user.thematics[0].idthematic;

    this.apiService.post("/logbook/list/ATstatusT", this.enabled).subscribe(
      (response: LogBook[]) => {
        this.logBooks = response;
        this.addEvaluation();
        this.searchKnowledge();
        for (const logbook of this.logBooks) {
          this.addEvaluation();
        }
        this.removeEvaluation(this.logBooks.length);
      },
      (error: HttpErrorResponse) => {
        this.showAlertMessage.showError(error.error.message_error);
      }
    );
  }
  /**
    This function searches the announcement knowledge
    @method searchKnowledge()
  */
  private searchKnowledge(): void {
    this.apiService
      .getById(
        "knowledge/announcement",
        this.user.announcements[0].idannouncement
      )
      .subscribe(
        (response: Knowledge[]) => {
          this.knowledge = response[0];
          this.searchLaboratoryEvaluations();
        },
        (error: HttpErrorResponse) => {
          this.showAlertMessage.showError(error.error.message_error);
        }
      );
  }
  /**
    This function searches the announcement laboratoryEvaluations
    @method searchLaboratoryEvaluations()
  */
  private searchLaboratoryEvaluations(): void {
    this.apiService
      .getById(
        "laboratoryevaluation/thematic",
        this.user.thematics[0].idthematic
      )
      .subscribe(
        (response: LaboratoryEvaluation[]) => {
          this.laboratoryEvaluations = response;
          this.searchLaboratoryEvaluationForPostulant();
        },
        (error: HttpErrorResponse) => {
          this.showAlertMessage.showError(error.error.message_error);
        }
      );
  }
  /**
    This function searches the postulant laboratory evaluation
    @method searchLaboratoryEvaluationForPostulant()
  */
  private searchLaboratoryEvaluationForPostulant(): void {
    let prueba = [];
    for (let laboEvaluation of this.laboratoryEvaluations) { 
      for(let logbook of this.logBooks){
        if(laboEvaluation.auxiliary.idauxiliary === logbook.label.auxiliary.idauxiliary){
          this.laboEvalPostulants.push(laboEvaluation);
          prueba.push(logbook.label);
        }
      }
    }
  }
  /**
   This function save the evaluations that were recorded in the form
    @method save()
  */
  private save(): void {
    let resultEvaluation: ResultLaboratoryEvaluation;
    let resultEvaluations = [];
    let laboEvalPostulantRepeat;
    this.evaluations = this.evaluationForm.controls.evaluations.value;
    for (let i in this.evaluations) {
      resultEvaluation = {
        idresultlaboratoryevaluation: 99999,
        score: this.evaluations[i]["score"],
        laboratoryEvaluation: this.laboEvalPostulants[i].idlaboratoryevaluation,
        postulant: this.logBooks[i].label.postulantes.idpostulant,
        iduser: this.user.iduser,
      };
      resultEvaluations.push(resultEvaluation);
    }
    laboEvalPostulantRepeat = {
      idlaboratoryevaluation: resultEvaluations[0].laboratoryEvaluation,
      idpostulant: resultEvaluations[0].postulant,
    };
    this.apiService.post("/resultlaboratoryevaluation/laboratoryevaluation", laboEvalPostulantRepeat).subscribe(
      (response: boolean) => {
        if (!response) {
          this.saveScores(resultEvaluations);
        } else {
          this.showAlertMessage.showInfoAlert("Las calificaciones ya fueron registradas anteriormente");
        }
      },
      (error: HttpErrorResponse) => {
        this.showAlertMessage.showError(error.error.message_error);
      }
      );
  }
  /**
   This function save the evaluations that were recorded in the form
    @method saveScores()
  */
  private saveScores(resultEvaluations): void {
    for (const i in resultEvaluations){
      this.apiService.post("/resultlaboratoryevaluation", resultEvaluations[i]).subscribe(
        (response: number) => {
          resultEvaluations[i].idresultlaboratoryevaluation = response;
          if (this.evaluations.length === +i + 1) {
            this.showAlertMessage.showSuccessAlert("Las calificaciones fueron registradas exitosamente");
            this.clean();
            this.router.navigate(["conocimiento"]);
            //window.location.reload();
          }
        },
        (error: HttpErrorResponse) => {
          this.showAlertMessage.showError(error.error.message_error);
        }
      );
    }
  }
  /**
   This function clean the form fields
    @method clean()
  */
  private clean(): void {
    this.getEvaluations.reset();
  }
  /**
  This function cancel to the function save
    @method cancel()
  */
  private cancel(): void {
    this.showAlertMessage.showCancelAlert(
      "¿Esta seguro que no desea guardar las calificaciones?",
      "No se podrán recuperar los datos ingresados"
    );
  }
  /**
   This function shows the postulant full name
    @method getFullNamePostulant()
  */
  private getFullNamePostulant(i: number): string {
    return (
      this.logBooks[i].label.postulantes.person.name +
      " " +
      this.logBooks[i].label.postulantes.person.lastName
    );
  }
  /**
   This function shows the postulant identity card number
    @method getIdentityCardPostulant()
  */
  private getIdentityCardPostulant(i: number): number {
    return this.logBooks[i].label.postulantes.person.ci;
  }
}
