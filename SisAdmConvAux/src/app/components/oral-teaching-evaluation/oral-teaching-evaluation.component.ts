import { Component, OnInit } from "@angular/core";
import { User } from "src/app/models/user.model";
import { Observable } from "rxjs";
import { LogBook } from "src/app/models/logbook.model";
import { Auxiliary } from "src/app/models/auxiliary.model";
import { Knowledge } from "src/app/models/knowledge.model";
import { KnowledgeEvaluation } from "src/app/models/knowledge-evaluation.model";
import { FormGroup, FormBuilder, Validators, FormArray } from "@angular/forms";
import { ApiService } from "src/app/api-service/api.service";
import { Router } from "@angular/router";
import { UserService } from "src/app/api-service/user.service";
import { HttpErrorResponse } from "@angular/common/http";
import { ResultOralTeachingEvaluation } from "src/app/models/result-oral-teaching-evaluation.model";
import { Schedule } from "src/app/models/schedule.model";
import { Event } from "src/app/models/event.model";
import { ShowAlertMessage } from "src/app/showAlertMessage";

@Component({
  selector: "app-oral-teaching-evaluation",
  templateUrl: "./oral-teaching-evaluation.component.html",
  styleUrls: ["./oral-teaching-evaluation.component.css"],
})
/**
  This class contains the oral teaching evaluation component
  @class OralTeachingEvaluationComponent
*/
export class OralTeachingEvaluationComponent implements OnInit {
  private user: User;
  private logBook$: Observable<LogBook[]>;
  private logBooks: LogBook[];
  private auxiliary: Auxiliary;
  private knowledge: Knowledge;
  private knowledgeEvaluation: KnowledgeEvaluation;
  private knowledgeEvaluations: KnowledgeEvaluation[];
  private evaluationForm: FormGroup;
  private evaluations: [];
  private showAlertMessage = new ShowAlertMessage();
  private enabled = {
    idauxiliary: 0,
    idannouncement: 0,
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
    this.enabled.idauxiliary = this.auxiliary.idauxiliary;
    this.enabled.idannouncement = this.user.announcements[0].idannouncement;

    this.apiService.post("/logbook/list/UAAproved", this.enabled).subscribe(
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
            "No se encontro la evaluacion de conocimiento de esta convocatoria"
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
            if (knowledgeEvaluation.description === "EVALUACION ORAL") {
              this.knowledgeEvaluation = knowledgeEvaluation;
            }
          }
        },
        (error: HttpErrorResponse) => {
          this.showAlertMessage.showError(
            "No se encontro la evaluacion oral de esta convocatoria"
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
      .post("/resultoralteachingevaluation/auxiliary", this.checkScores)
      .subscribe(
        (response: boolean) => {
          if (!response) {
            let resultEvaluation: ResultOralTeachingEvaluation;
            this.evaluations = this.evaluationForm.controls.evaluations.value;
            for (const i in this.evaluations) {
              resultEvaluation = {
                score: this.evaluations[i]["score"],
                knowledgeEvaluation: this.knowledgeEvaluation
                  .idknowledgeevaluation,
                postulant: this.logBooks[i].label.postulantes.idpostulant,
                auxiliary: this.auxiliary.idauxiliary,
                iduser: this.user.iduser,
              };
              this.saveEvaluation(resultEvaluation, +i);
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
    @method saveEvaluation()
  */
  private saveEvaluation(
    resultEvaluation: ResultOralTeachingEvaluation,
    indice: number
  ): void {
    this.apiService
      .post("/resultoralteachingevaluation", resultEvaluation)
      .subscribe(
        (response: number) => {
          if (this.evaluations.length === indice + 1) {
            this.showAlertMessage.showSuccessAlert(
              "Las calificaciones orales fueron registradas exitosamente"
            );
            this.clean();
            this.router.navigate(["conocimiento"]);
          }
        },
        (error: HttpErrorResponse) => {
          this.showAlertMessage.showInfoAlert(
            "Las calificaciones ya fueron registradas anteriormente"
          );
          this.clean();
          this.router.navigate(["conocimiento"]);
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
