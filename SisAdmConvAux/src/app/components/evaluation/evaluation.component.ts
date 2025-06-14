import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Observable } from "rxjs";
import { ApiService } from "../../api-service/api.service";
import { User } from "../../models/user.model";
import { Auxiliary } from "src/app/models/auxiliary.model";
import { UserService } from "src/app/api-service/user.service";
import { LogBook } from "src/app/models/logbook.model";
import { FormGroup, Validators, FormBuilder, FormArray } from "@angular/forms";
import { HttpErrorResponse } from "@angular/common/http";
import { ResultWrittenTeachingEvaluation } from "src/app/models/result-written-teaching-evaluation.model";
import { Knowledge } from "src/app/models/knowledge.model";
import { KnowledgeEvaluation } from "src/app/models/knowledge-evaluation.model";
import { Schedule } from "src/app/models/schedule.model";
import { Event } from "src/app/models/event.model";
import { ShowAlertMessage } from "src/app/showAlertMessage";

@Component({
  selector: "app-evaluation",
  templateUrl: "./evaluation.component.html",
  styleUrls: ["./evaluation.component.css"],
})
/**
  This class contains the written teaching evaluation component
  @class EvaluationComponent
*/
export class EvaluationComponent implements OnInit {
  private user: User;
  private logBook$: Observable<LogBook[]>;
  private logBooks: LogBook[];
  private knowledgeEvaluation: KnowledgeEvaluation;

  private auxiliary: Auxiliary;
  private knowledge: Knowledge;
  private knowledgeEvaluations: KnowledgeEvaluation[];
  private evaluationForm: FormGroup;
  private evaluations: [];
  private showAlertMessage = new ShowAlertMessage();
  private enabled = {
    idannouncement: 0,
    id: 0,
    iduser: 0,
  };
  private checkScores = {
    idauxiliary: 0,
    idknowledgeevaluation: 0,
  };
  private schedule: Schedule;
  private events: Event[];
  private enabledPublication: string;
  private publicationOfResults: string;

  constructor(
    private apiService: ApiService,
    private fb: FormBuilder,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.user = this.userService.getCurrentUser();
    this.auxiliary = this.user.auxiliarys[0];
    this.createEvaluationForm();
    this.datesValidator(this.user.announcements[0].idannouncement);
  }
  /**
   This function creates the evaluation form
    @method createEvaluationForm()
  */
  private createEvaluationForm(): void {
    this.evaluationForm = this.fb.group({
      description: [""],
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
            //Validators.pattern('^[0-9]+([,][0-9]+)?$'),
            Validators.pattern("[0-9]+(.[0-9][0-9]?)?"),
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
    this.enabled.id = this.user.auxiliarys[0].idauxiliary;
    this.enabled.iduser = this.user.iduser;

    this.apiService.post("/logbook/list/UAW", this.enabled).subscribe(
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
        this.showAlertMessage.showError(
          "No se encontraron postulantes habilitados para esta evaluacion"
        );
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
          this.searchKnowledgeEvaluations();
        },
        (error: HttpErrorResponse) => {
          this.showAlertMessage.showError(
            "No se encontro la evaluacion de conocimiento correspondiente a esta convocatoria"
          );
        }
      );
  }
  /**
    This function searches the announcement knowledgeEvaluations
    @method searchKnowledgeEvaluations()
  */
  private searchKnowledgeEvaluations(): void {
    this.apiService
      .getById("knowledgeevaluation/knowledge", this.knowledge.idknowledge)
      .subscribe(
        (response: KnowledgeEvaluation[]) => {
          this.knowledgeEvaluations = response;
          for (const knowledgeEvaluation of this.knowledgeEvaluations) {
            if (knowledgeEvaluation.description === "EVALUACION ESCRITA") {
              this.knowledgeEvaluation = knowledgeEvaluation;
            }
          }
        },
        (error: HttpErrorResponse) => {
          this.showAlertMessage.showError(
            "No se encontro la evaluacion de conocimientos escrita para la convocatoria"
          );
        }
      );
  }
  /**
   This function save the evaluations that were recorded in the form
    @method save()
  */
  private save(): void {
    this.checkScores.idauxiliary = this.auxiliary.idauxiliary;
    this.checkScores.idknowledgeevaluation = this.knowledgeEvaluation.idknowledgeevaluation;
    this.apiService
      .post("/resultwrittenteachingevaluation/auxiliary", this.checkScores)
      .subscribe(
        (response: boolean) => {
          if (!response) {
            let resultEvaluation: ResultWrittenTeachingEvaluation;
            this.evaluations = this.evaluationForm.controls.evaluations.value;
            for (const i in this.evaluations) {
              resultEvaluation = {
                idresultwrittenteachingevaluation: 99999,
                score: this.evaluations[i]["score"],
                knowledgeEvaluation: this.knowledgeEvaluation
                  .idknowledgeevaluation,
                postulant: this.logBooks[i].label.postulantes.idpostulant,
                auxiliary: this.auxiliary.idauxiliary,
                iduser: this.user.iduser,
              };
              this.saveEvaluations(resultEvaluation, +i);
            }
          } else {
            this.showAlertMessage.showInfoAlert(
              "Las calificaciones ya fueron registradas anteriormente"
            );
          }
        },
        (error: HttpErrorResponse) => {
          this.showAlertMessage.showError(error.error.message_error);
        }
      );
  }
  /**
   This function save the evaluations that were recorded in the form
    @method saveEvaluations()
  */
  private saveEvaluations(
    resultEvaluation: ResultWrittenTeachingEvaluation,
    indice: number
  ) {
    this.apiService
      .post("/resultwrittenteachingevaluation", resultEvaluation)
      .subscribe(
        (response: number) => {
          resultEvaluation.idresultwrittenteachingevaluation = response;

          if (this.evaluations.length === indice + 1) {
            this.showAlertMessage.showSuccessAlert(
              "Las calificaciones de la evaluacion escrita fueron registradas exitosamente"
            );
            this.clean();
            this.router.navigate(["conocimiento"]);
          }
        },
        (error: HttpErrorResponse) => {
          this.showAlertMessage.showError(
            "Las calificaciones no fueron registradas exitosamente"
          );
          this.clean();
        }
      );
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
