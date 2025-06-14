import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { ShowAlertMessage } from 'src/app/showAlertMessage';
import { ApiService } from 'src/app/api-service/api.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Requirement1 } from 'src/app/models/requirement';
import { RequestAux } from 'src/app/models/requirements.model';
import { Aux } from 'src/app/models/auxiliary.model';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-register-teaching-requirements',
  templateUrl: './register-teaching-requirements.component.html',
  styleUrls: ['./register-teaching-requirements.component.css']
})
export class RegisterTeachingRequirementsComponent implements OnInit {

  public registerRequisitosForm: FormGroup;
  public requirementsList: [];
  public requests: any[];
  public selectedAux: Aux;
  public dropdownList = [];
  public dropdownSettings = {};
  public codItems: Aux [];
  public idAuxs: Array<any> = [];
  public idAnnouncemnt: number;
  public showAlertMessage = new ShowAlertMessage();
  public currentItem: Aux = {
    idauxiliary: 0,
    name: '',
    code: '',
    academicHours: ''
  };
  public requirement: Requirement1 = {
    itemsQuantity: 0,
    announcement: 0,
    auxiliary: 0
  };

  public request: RequestAux = {
    idarea: 2,
    idacademicunit: 1
  };

  public form: FormGroup;

  constructor(private fb: FormBuilder, private apiService: ApiService, private router: Router, private route: ActivatedRoute) {
    this.auxCall();
  }
  ngOnInit() {
    this.idAnnouncemnt = +this.route.snapshot.paramMap.get('id');
    this.getAuxiliaries();
    this.form = this.fb.group({
      requirements: this.fb.array([this.initX()])
    });
  }

  public initX(): any {
    return this.fb.group({
      auxiliary: ['', Validators.required],
      academicHours: ['', [Validators.required, Validators.maxLength(11), Validators.minLength(9)]],
      itemsQuantity: ['', [Validators.required, Validators.maxLength(2), Validators.pattern('^[0-9]*$')]]
    });
  }

  public addX(): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray> this.form.controls['requirements'];
    control.push(this.initX());
  }

  public removeX(index: number): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray> this.form.controls['requirements'];
    control.removeAt(index);
  }

  public getAuxiliaries(): any {
    this.apiService.post('/auxiliary/AAU', this.request).subscribe(
      (response: any) => {
        this.requirementsList = response;
      });
  }

  public auxCall(): any {
    this.apiService.post('/announcement/auxiliarys/AAU', this.request).subscribe(
      (response: any) => {
        this.dropdownList = response;
      });
    this.dropdownSettings = {
      singleSelection: true,
      idField: 'idauxiliary',
      textField: 'name',
      selectAllText: 'Seleccionar todos',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true
    };
  }

  public save(): void {
    let repetidos = false;
    this.idAuxs = [];
    this.requests = this.form.controls.requirements.value;
    // tslint:disable-next-line:forin
    for (const x in this.requests) {
      this.idAuxs.push(this.requests[x].auxiliary);
    }
    repetidos = this.thereIsRep(this.idAuxs);
    if (repetidos !== true) {
      // tslint:disable-next-line:forin
      for (const i in this.requests) {
        this.requirement.itemsQuantity = +this.requests[i].itemsQuantity;
        this.requirement.auxiliary = +this.requests[i].auxiliary;
        this.requirement.announcement = this.idAnnouncemnt;
        this.apiService.post('/requirement', this.requirement).subscribe(
          (response: number) => {
            if (this.requests.length === ( +i + 1)) {
              this.router.navigate(['/admin/registrarRequisitos', this.idAnnouncemnt]);
              this.showAlertMessage.showSuccessAlert ('Requerimientos registrados exitosamente');
            }
          },
          (error: HttpErrorResponse) => {
            this.showAlertMessage.showErrorAlert(error.error.message_error);
          });
      }
    } else {
      this.showAlertMessage.showErrorAlert('Eligió una auxiliatura más de una vez.');
    }
  }

  public thereIsRep(list: any): boolean {
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

  public cancel(): void {
    this.showAlertMessage.showCancelAlert('¿Seguro que quiere cancelar el llenado de éste formulario?',
    'No se podrán recuperar los datos ingresados');
  }
}
