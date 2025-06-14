import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, Validators } from '@angular/forms';
import { ApiService } from 'src/app/api-service/api.service';
import { ShowAlertMessage } from 'src/app/showAlertMessage';
import { RequestAux } from 'src/app/models/requirements.model';
import { HttpErrorResponse } from '@angular/common/http';
import { Aux } from 'src/app/models/auxiliary.model';
import { Requirement1 } from 'src/app/models/requirement';
import { Router, ActivatedRoute } from '@angular/router';
import { AnnouncementC } from 'src/app/models/announcement.model';
import { AcademicUnit } from 'src/app/models/academic-unit.model';
import { Area } from 'src/app/models/area.model';

@Component({
  selector: 'app-register-requirements',
  templateUrl: './register-requirements.component.html',
  styleUrls: ['./register-requirements.component.css']
})
export class RegisterRequirementsComponent implements OnInit {
  public registerRequisitosForm: FormGroup;
  public requirementsList: [];
  public requests: any [];
  public selectedAux: Aux;
  public idAuxs: Array<any> = [];
  public codeAuxs: Array<any> = [];
  public dropdownList = [];
  public areaAnnouncement: Area;
  public academicUnit: AcademicUnit;
  public announcement: AnnouncementC = {
    title: '',
    description: '',
    pack: false,
    courtsDescription: '',
    appointment: '',
    area: this.areaAnnouncement ,
    management: 0,
    academicUnit: this.academicUnit,
    faculty: 0

  };
  public dropdownSettings = {};
  public codItems: Aux [];
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
    idarea: 1,
    idacademicunit: 1
  };

  public form: FormGroup;

  constructor(private fb: FormBuilder, private apiService: ApiService, private router: Router, private route: ActivatedRoute) {}
  ngOnInit() {
    this.auxCall();
    this.idAnnouncemnt = +this.route.snapshot.paramMap.get('id');
    this.getAnnouncement(this.idAnnouncemnt);
    this.getAuxiliaries();
    this.form = this.fb.group({
      requirements: this.fb.array([this.initX()])
    });
  }

  public initX(): any {
    return this.fb.group({
      auxiliary: ['', Validators.required],
      academicHours: ['', [Validators.required, Validators.maxLength(11), Validators.minLength(9)]],
      itemsQuantity: ['', [Validators.required, Validators.maxLength(2), Validators.pattern('^[0-9]*$')]],
      code: ['', [Validators.required, Validators.maxLength(50), Validators.minLength(7)]]
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

  public getAuxiliaries(): void {
    this.apiService.post('/auxiliary/AAU', this.request).subscribe(
      (response: any) => {
        this.requirementsList = response;
      });
  }

  public auxCall(): void {
    this.apiService.getAll('/auxiliary').subscribe(
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

  public getAnnouncement(id: number): void {
    this.apiService.getById('/announcement', id).subscribe(
      (response: any) => {
        this.announcement = response;
      });
  }

  public save(): void {
    let repetidos = false;
    let codeRep = false;
    this.idAuxs = [];
    this.codeAuxs = [];
    this.requests = this.form.controls.requirements.value;
    // tslint:disable-next-line:forin
    for (const x in this.requests) {
      this.idAuxs.push(this.requests[x].auxiliary);
    }
    repetidos = this.thereIsRep(this.idAuxs);
    // tslint:disable-next-line:forin
    for (const y in this.requests) {
      this.codeAuxs.push(this.requests[y].code);
    }
    codeRep = this.thereIsRep(this.codeAuxs);
    if (repetidos !== true && codeRep !== true) {
      // tslint:disable-next-line:forin
    for (const i in this.requests) {
      this.requirement.itemsQuantity = +this.requests[i].itemsQuantity;
      this.requirement.auxiliary = +this.requests[i].auxiliary;
      this.requirement.announcement = this.idAnnouncemnt;
      this.apiService.post('/requirement', this.requirement).subscribe(
        (response: number) => {
          if (this.requests.length === ( +i + 1)) {
            this.showAlertMessage.showSuccessAlert ('Requerimientos registrados exitosamente');
            this.router.navigate(['/admin/registrarRequisitos', this.idAnnouncemnt]);
          }
        },
        (error: HttpErrorResponse) => {
          this.showAlertMessage.showErrorAlert(error.error.message_error);
        });
    }
    } else {
      this.showAlertMessage.showErrorAlert('Eligió una auxiliatura o código de auxiliatura más de una vez.');
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
