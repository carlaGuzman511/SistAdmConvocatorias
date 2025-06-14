import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators, FormArray } from "@angular/forms";
import { ShowAlertMessage } from "src/app/showAlertMessage";
import { Knowledge1 } from "src/app/models/knowledge1.model";
import { ApiService } from "src/app/api-service/api.service";
import { HttpErrorResponse } from "@angular/common/http";
import { LaboratoryEvaluation1 } from "src/app/models/laboratory-evaluation1.model";
import { Observable } from "rxjs";
import { Thematic } from "src/app/models/thematic.model";
import { Router, ActivatedRoute } from "@angular/router";
import { UserService } from "src/app/api-service/user.service";
import { User } from "src/app/models/user.model";
import { Requirement } from "src/app/models/requirement.model";
import { MeritA } from "src/app/models/merit.model";

@Component({
  selector: "app-laboratory-evaluation-table",
  templateUrl: "./laboratory-evaluation-table.component.html",
  styleUrls: ["./laboratory-evaluation-table.component.css"],
})
/**
  This class contains the announcement laboratory evaluation table component
  @class LaboratoryEvaluationTableComponent
*/
export class LaboratoryEvaluationTableComponent implements OnInit {
  private evaluationForm: FormGroup;
  private evaluations: [];
  private thematic$: Observable<Thematic[]>;
  private idAnnouncemnt: number;
  private user: User;
  private showAlertMessage = new ShowAlertMessage();
  private merit: MeritA;
  private knowledge: Knowledge1 = {
    idknowledge: 99999,
    description: "",
    baseScore: 0,
    finalScore: 0,
    announcement: 0,
    iduser: 0,
  };
  private idAuxiliary = 0;
  private requirement$: Observable<Requirement[]>;
  private requirements: Requirement[];

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
    this.searchRequirements();
    this.user = this.userService.getCurrentUser();
    this.searchMerit();
  }
  /**
   This function searches the announcements requirements
    @method searchRequirements()
  */
  private searchRequirements(): void {
    this.requirement$ = this.apiService.getById(
      "requirement/announcement",
      this.idAnnouncemnt
    );
    this.requirement$.subscribe(
      (response: Requirement[]) => {
        this.requirements = response;
      },
      (error: HttpErrorResponse) => {
        this.showAlertMessage.showError(
          "No se encontraron requerimientos para la convocatoria"
        );
      }
    );
  }
  /**
   This function searches the auxiliaries thematics
    @method searchThematics()
  */
  public searchThematics(i: number): void {
    this.idAuxiliary = this.evaluationForm.controls.evaluations.value[i][
      "auxiliary"
    ];
    if (this.idAuxiliary === undefined) {
      return;
    }
    this.thematic$ = this.apiService.getById(
      "auxiliary/thematic",
      this.idAuxiliary
    );
    if (!this.thematic$) {
      this.showAlertMessage.showError(
        "No se encontraron tematicas relacionadas a esta auxiliatura"
      );
    }
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
      evaluations: this.fb.array([]),
    });
    this.addEvaluation();
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
      auxiliary: [""],
      thematic: [""],
      percentage: [
        "",
        {
          validators: [
            Validators.minLength(1),
            Validators.maxLength(3),
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
   This function save the evaluations that were recorded in the form
    @method save()
  */
  private save(): void {
    this.evaluations = this.evaluationForm.controls.evaluations.value;
    if (this.verifyPercentageMerits()) {
      if (this.validateAuxiliaryThematics(this.evaluations)) {
        if (this.validateAllAuxiliariesFilled(this.evaluations)) {
          if (this.validateAuxiliaryScores(this.evaluations)) {
            this.knowledge.description = this.evaluationForm.controls.description.value;
            this.knowledge.baseScore = this.evaluationForm.controls.baseScore.value;
            this.knowledge.finalScore = this.evaluationForm.controls.percentageFinal.value;
            this.knowledge.announcement = this.idAnnouncemnt;
            this.knowledge.iduser = this.user.iduser;
            this.apiService.post("/knowledge", this.knowledge).subscribe(
              (response: number) => {
                this.knowledge.idknowledge = response;
                this.saveEvaluations();
                this.clean();
                this.router.navigate(["/admin/tribunales", this.idAnnouncemnt]);
              },
              (error: HttpErrorResponse) => {
                this.showAlertMessage.showError(error.error.message_error);
              }
            );
          } else {
            this.showAlertMessage.showError(
              "Las sumatoria de los porcentajes de las tematicas de una o mas auxiliaturas no es igual a 100"
            );
          }
        }
      }
    }
  }
  /**
   This function save the evaluations that were recorded in the form
    @method saveEvaluations()
  */
  private saveEvaluations(): void {
    let laboratoryEval: LaboratoryEvaluation1;
    for (const i in this.evaluations) {
      laboratoryEval = {
        idlaboratoryevaluation: 99999,
        percentage: this.evaluations[i]["percentage"],
        thematic: this.evaluations[i]["thematic"],
        auxiliary: this.evaluations[i]["auxiliary"],
        knowledge: this.knowledge.idknowledge,
        iduser: this.user.iduser,
      };
      this.apiService.post("/laboratoryevaluation", laboratoryEval).subscribe(
        (response: number) => {
          laboratoryEval.idlaboratoryevaluation = response;
          if (this.evaluations.length === +i + 1) {
            this.showAlertMessage.showSuccessAlert(
              "Las evaluaciones de laboratorio fueron registradas exitosamente"
            );
            this.router.navigate(["/admin/tribunales", this.idAnnouncemnt]);
          }
        },
        (error: HttpErrorResponse) => {
          this.showAlertMessage.showError(
            "Las evaluaciones de laboratorio no fueron guardadas"
          );
        }
      );
    }
  }
  /**
   This function verify that the all thematics was registered for each auxiliary
    @method validateAuxiliaryThematics()
  */
  private validateAuxiliaryThematics(laboratoryEvaluations: any): boolean {
    let answer = false; //resultado final final
    const laboEvaluations: any[] = [];
    const auxiliaries: any[] = [];
    const auxNames: any[] = [];

    for (const requirement of this.requirements) {
      auxiliaries.push(requirement.auxiliary.idauxiliary);
      auxNames.push(requirement.auxiliary.name);
    }

    for (const auxiliary of auxiliaries) {
      const laboEvalAux = [];
      for (const laboratoryEvaluation of laboratoryEvaluations) {
        if (laboratoryEvaluation.auxiliary == auxiliary) {
          laboEvalAux.push(laboratoryEvaluation);
        }
      }
      laboEvaluations.push(laboEvalAux);
    }

    const thematics: any[] = [];
    for (const laboEvaluation of laboEvaluations) {
      //[[{}, {}, {}], [{}, {}], [{}, {}, {}], [{}, {}]]
      const thematicsByAux: any[] = [];
      for (const laboEval of laboEvaluation) {
        //[{}, {}, {}]
        thematicsByAux.push(+laboEval.thematic);
      }
      thematics.push(thematicsByAux);
    }

    const resThematicsRepeat: boolean[] = []; //[false, true, true, false, true] for every auxiliary
    for (const thematic of thematics) {
      //[[1, 2, 1, 3], [3, 5], [7, 4, 1], [2, 2, 5, 2]]
      var repetidos = [];
      var temporal = [];
      thematic.forEach((value, index) => {
        temporal = Object.assign([], thematic);
        temporal.splice(index, 1);
        if (temporal.indexOf(value) != -1 && repetidos.indexOf(value) == -1) {
          repetidos.push(value);
        }
      });
      if (repetidos.length > 0) {
        resThematicsRepeat.push(true);
      } else {
        resThematicsRepeat.push(false);
      }
    }

    let information = "";
    if (!resThematicsRepeat.includes(true)) {
      answer = true;
    } else {
      for (const i in resThematicsRepeat) {
        if (resThematicsRepeat[i]) {
          information = information + " " + auxNames[i] + ",";
        }
      }
      this.showAlertMessage.showError(
        "Seleccionó una o mas temáticas más de una vez para las siguiente(s) auxiliatura(s): " +
          information
      );
    }
    return answer;
  }
  /**
   This function verify that all auxiliaries required was registered
    @method validateAllAuxiliariesFilled()
  */
  private validateAllAuxiliariesFilled(laboratoryEvaluations: any): boolean {
    let answer = false; //resultado final final
    const laboEvaluations: any[] = [];
    const auxiliaries: any[] = [];
    const auxNames: any[] = [];

    for (const requirement of this.requirements) {
      auxiliaries.push(requirement.auxiliary.idauxiliary);
      auxNames.push(requirement.auxiliary.name);
    }

    for (const auxiliary of auxiliaries) {
      const laboEvalAux = [];
      for (const laboratoryEvaluation of laboratoryEvaluations) {
        if (laboratoryEvaluation.auxiliary == auxiliary) {
          laboEvalAux.push(laboratoryEvaluation);
        }
      }
      laboEvaluations.push(laboEvalAux);
    }

    const auxiliariesFilled = [];

    for (const laboEvaluation of laboEvaluations) {
      if (laboEvaluation.length > 0) {
        auxiliariesFilled.push(+laboEvaluation[0].auxiliary);
      }
    }

    const res = [];
    for (const auxiliary of auxiliaries) {
      if (auxiliariesFilled.includes(auxiliary)) {
        res.push(true);
      } else {
        res.push(false);
      }
    }

    if (!res.includes(false)) {
      answer = true;
    } else {
      let message = "";
      for (const i in res) {
        if (!res[i]) {
          message = message + " " + auxNames[i] + ",";
        }
      }
      this.showAlertMessage.showError(
        "Es necesario el registro de la totalidad de las auxiliaturas, la(s) auxiliatura(s) faltante(s) son: " +
          message
      );
    }
    return answer;
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
    This function searches the announcement merit
    @method searchMerit()
  */
  private searchMerit(): void {
    this.apiService.getById("merit/announcement", this.idAnnouncemnt).subscribe(
      (response: MeritA[]) => {
        this.merit = response[0];
      },
      (error: HttpErrorResponse) => {
        this.showAlertMessage.showError(
          "No se encontro el merito correspondiente a la convocatoria"
        );
      }
    );
  }
  /**
    This function validates the auxiliaries scores must add up to 100
    @method validateAuxiliaryScores()
  */
  private validateAuxiliaryScores(evalScores: any[]): boolean {
    const idAuxiliaries: number[] = [];
    const auxiliaryScores: any[] = [];
    const auxiliaryScoreTotal: number[] = [];
    const auxiliaryScoreTotalAnswer: boolean[] = [];

    for (const evalScore of evalScores) {
      const id = evalScore.auxiliary;
      if (!idAuxiliaries.includes(id)) {
        idAuxiliaries.push(id);
      }
    }

    for (const i in idAuxiliaries) {
      const tematicScores: number[] = [];
      for (const evalScore of evalScores) {
        if (evalScore.auxiliary === idAuxiliaries[i]) {
          tematicScores.push(evalScore.percentage);
        }
      }
      auxiliaryScores.push(tematicScores);
    }

    for (const j in idAuxiliaries) {
      let totalPartial = 0;
      console.log(auxiliaryScores[j]);
      for (const auxiliaryScore of auxiliaryScores[j]) {
        totalPartial = totalPartial + +auxiliaryScore;
      }
      console.log(totalPartial);
      auxiliaryScoreTotal.push(totalPartial);
    }

    for (const auxScores of auxiliaryScoreTotal) {
      if (auxScores === 100) {
        auxiliaryScoreTotalAnswer.push(true);
      } else {
        auxiliaryScoreTotalAnswer.push(false);
      }
    }

    if (auxiliaryScoreTotalAnswer.includes(false)) {
      return false;
    } else {
      return true;
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
    });
    this.getEvaluations.reset();
    this.getEvaluations.controls.splice(0, this.evaluations.length);
    this.addEvaluation();
  }
  /**
  This function cancel to the function save
    @method cancel()
  */
  private cancel(): void {
    this.showAlertMessage.showCancelAlert(
      "¿Seguro que quiere cancelar el llenado de éste formulario?",
      "No se podrán recuperar los datos ingresados"
    );
  }
  /**
    This function validates the thematics scores must add up to 100
    @method validateAllThematicsByAuxiliary()
  */
  private validateAllThematicsByAuxiliary(laboratoryEvaluations: any): boolean {
    const answer = false; //resultado final final
    const laboEvaluations: any[] = [];
    const auxiliaries: any[] = [];
    const auxNames: any[] = [];

    for (const requirement of this.requirements) {
      auxiliaries.push(requirement.auxiliary.idauxiliary);
      auxNames.push(requirement.auxiliary.name);
    }

    for (const auxiliary of auxiliaries) {
      const laboEvalAux = [];
      for (const laboratoryEvaluation of laboratoryEvaluations) {
        if (laboratoryEvaluation.auxiliary == auxiliary) {
          laboEvalAux.push(laboratoryEvaluation);
        }
      }
      laboEvaluations.push(laboEvalAux);
    }

    const thematics: any[] = [];
    for (const laboEvaluation of laboEvaluations) {
      //[[{}, {}, {}], [{}, {}], [{}, {}, {}], [{}, {}]]
      const thematicsByAux: any[] = [];
      for (const laboEval of laboEvaluation) {
        //[{}, {}, {}]
        thematicsByAux.push(+laboEval.thematic);
      }
      thematics.push(thematicsByAux);
    }

    return answer;
  }
}
