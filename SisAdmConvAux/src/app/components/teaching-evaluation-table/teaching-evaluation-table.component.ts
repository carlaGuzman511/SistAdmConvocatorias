import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { ApiService } from "src/app/api-service/api.service";
import { ShowAlertMessage } from "src/app/showAlertMessage";
import { Knowledge1 } from "src/app/models/knowledge1.model";
import { KnowledgeEvaluation1 } from "src/app/models/knowledge-evaluation1.model";
import { HttpErrorResponse } from "@angular/common/http";
import { Router, ActivatedRoute } from "@angular/router";
import { UserService } from "src/app/api-service/user.service";
import { User } from "src/app/models/user.model";
import { MeritA } from "src/app/models/merit.model";

@Component({
  selector: "app-teaching-evaluation-table",
  templateUrl: "./teaching-evaluation-table.component.html",
  styleUrls: ["./teaching-evaluation-table.component.css"],
})
/**
  This class contains the announcement teaching evaluation table component
  @class TeachingEvaluationTableComponent
*/
export class TeachingEvaluationTableComponent implements OnInit {
  private evaluationForm: FormGroup;
  private idAnnouncemnt: number;
  private user: User;
  private showAlertMessage = new ShowAlertMessage();
  private knowledge: Knowledge1 = {
    idknowledge: 99999,
    description: "",
    baseScore: 0,
    finalScore: 0,
    announcement: 0,
    iduser: 0,
  };
  private merit: MeritA;

  constructor(
    private apiService: ApiService,
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.createEvaluationForm();
    this.idAnnouncemnt = +this.route.snapshot.paramMap.get("id");
    this.user = this.userService.getCurrentUser();
    this.searchMerit();
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
      baseScore: [
        "",
        {
          validators: [
            Validators.pattern("[0-9]+(.[0-9][0-9]?)?"),
            Validators.required,
          ],
        },
      ],
      percentageFinal: [
        "",
        {
          validators: [
            Validators.pattern("[0-9]+(.[0-9][0-9]?)?"),
            Validators.required,
          ],
        },
      ],
      writtenScore: [
        "",
        {
          validators: [
            Validators.pattern("[0-9]+(.[0-9][0-9]?)?"),
            Validators.required,
          ],
        },
      ],
      oralScore: [
        "",
        {
          validators: [
            Validators.pattern("[0-9]+(.[0-9][0-9]?)?"),
            Validators.required,
          ],
        },
      ],
    });
  }
  /**
   This function save the evaluations that were recorded in the form
    @method save()
  */
  private save(): void {
    if (this.validateScores() && this.verifyPercentageMerits()) {
      this.knowledge.description = this.evaluationForm.controls.description.value;
      this.knowledge.baseScore = this.evaluationForm.controls.baseScore.value;
      this.knowledge.finalScore = this.evaluationForm.controls.percentageFinal.value;
      this.knowledge.announcement = this.idAnnouncemnt;
      this.knowledge.iduser = this.user.iduser;
      this.apiService.post("/knowledge", this.knowledge).subscribe(
        (response: number) => {
          this.knowledge.idknowledge = response;
          this.saveKnowledgeEvaluations();
        },
        (error: HttpErrorResponse) => {
          this.showAlertMessage.showErrorAlert(
            "Ocurrio algun problema al guardar knowledge"
          );
        }
      );
    }
  }
  /**
    This function searches the announcement merit
    @method searchMerit()
  */
  private searchMerit(): void {
    this.apiService.getById("merit/announcement", this.idAnnouncemnt).subscribe(
      (response: MeritA[]) => {
        this.merit = response[0];
      },
      (error: HttpErrorResponse) => {
        this.showAlertMessage.showErrorAlert(
          "No se encontro el merito correspondiente a la convocatoria"
        );
      }
    );
  }
  /**
   This function verifies that the percentage for the knowledges evaluation must agree with the merits evaluation
    @method verifyPercentageMerits()
  */
  private verifyPercentageMerits(): boolean {
    let answer = false;
    const percentageFinalKnowledge = this.evaluationForm.controls
      .percentageFinal.value;
    const percentageFinalMerit = this.merit.finalScore;
    const percentageBaseMerit = this.merit.baseScore;
    const percent = 100 - +percentageFinalMerit;
    if (
      +percentageFinalKnowledge + +percentageFinalMerit ==
      +percentageBaseMerit
    ) {
      answer = true;
    } else {
      this.showAlertMessage.showErrorTeaching(
        "El porcentaje sobre el puntaje final debe ser: " + percent,
        "Debido a que el porcentaje sobre el final registrado en la tabla de méritos fue: " +
          percentageFinalMerit
      );
    }
    return answer;
  }
  /**
   This function save the knowledges evaluations that were recorded in the form
    @method saveKnowledgeEvaluations()
  */
  private saveKnowledgeEvaluations(): void {
    let knowledgeEval1: KnowledgeEvaluation1;
    let knowledgeEval2: KnowledgeEvaluation1;
    knowledgeEval1 = {
      idknowledgeevaluation: 99999,
      description: "EVALUACION ESCRITA",
      percentage: this.evaluationForm.controls.writtenScore.value,
      knowledge: this.knowledge.idknowledge,
      iduser: this.user.iduser,
    };
    knowledgeEval2 = {
      idknowledgeevaluation: 99999,
      description: "EVALUACION ORAL",
      percentage: this.evaluationForm.controls.oralScore.value,
      knowledge: this.knowledge.idknowledge,
      iduser: this.user.iduser,
    };
    this.apiService.post("/knowledgeevaluation", knowledgeEval1).subscribe(
      (response: number) => {
        knowledgeEval1.idknowledgeevaluation = response;
        this.apiService.post("/knowledgeevaluation", knowledgeEval2).subscribe(
          (response: number) => {
            knowledgeEval2.idknowledgeevaluation = response;
            this.showAlertMessage.showSuccessAlert(
              "Evaluaciones registradas exitosamente"
            );
            this.clean();
            this.router.navigate(["/admin/tribunales", this.idAnnouncemnt]);
          },
          (error: HttpErrorResponse) => {
            this.showAlertMessage.showErrorAlert(error.error.message_error);
          }
        );
      },
      (error: HttpErrorResponse) => {
        this.showAlertMessage.showErrorAlert(error.error.message_error);
      }
    );
  }
  /**
    This function validates the evaluations scores must add up to 100
    @method validateScores()
  */
  private validateScores(): boolean {
    const totalScore =
      +this.evaluationForm.controls.writtenScore.value +
      +this.evaluationForm.controls.oralScore.value;
    if (totalScore === 100) {
      return true;
    } else {
      if (totalScore > 100) {
        this.showAlertMessage.showErrorAlert(
          "La sumatoria de los porcentajes de la Evaluación escrita y Evaluación oral es mayor al puntaje base"
        );
        return false;
      } else {
        if (totalScore < 100) {
          this.showAlertMessage.showErrorAlert(
            "La sumatoria de los porcentajes de la Evaluación escrita y Evaluación oral es menor al puntaje base"
          );
          return false;
        }
      }
    }
  }
  /**
   This function clean the form fields
    @method clean()
  */
  private clean(): void {
    this.evaluationForm.patchValue({
      description: "",
      baseScore: "",
      percentageFinal: "",
      writtenScore: "",
      oralScore: "",
    });
  }
  /**
  This function cancel to the function save
    @method cancel()
  */
  public cancel() {
    this.showAlertMessage.showCancelAlert(
      "¿Seguro que quiere cancelar el llenado de éste formulario?",
      "No se podrán recuperar los datos ingresados"
    );
  }
}
