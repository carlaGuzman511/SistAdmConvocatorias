import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators, FormArray } from '@angular/forms';
import { ShowAlertMessage } from 'src/app/showAlertMessage';
import { MeritTable } from 'src/app/models/meritRating.model';
import { ApiService } from 'src/app/api-service/api.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { AnnouncementC } from 'src/app/models/announcement.model';
import { Area } from 'src/app/models/area.model';
import { AcademicUnit } from 'src/app/models/academic-unit.model';

@Component({
  selector: 'app-register-merit-rating-table',
  templateUrl: './register-merit-rating-table.component.html',
  styleUrls: ['./register-merit-rating-table.component.css']
})
export class RegisterMeritRatingTableComponent implements OnInit {
  form: FormGroup;
  public test;
  public isCorrectTotal: boolean;
  public idAnnouncemnt: number;
  public meritList: Array<any> = [];
  public detailPercents: Array<any> = [];
  public detailList: Array<any> = [];
  public addResult: Array<any> = [];
  public badDetaeilTotal: boolean;
  public addResultSubdetails: Array<any> = [];
  public isWrongSubDetaeilTotal: boolean;
  public subdetailList: Array<any> = [];
  public notes: Array<any> = [];
  public score = 0;
  public showAlertMessage = new ShowAlertMessage();
  public meritTable: MeritTable = {
    description: '',
    note: '',
    baseScore: 0,
    finalScore: 0,
    idannouncement: 0,
    merits: [],
  };
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

  constructor(private fb: FormBuilder, private apiService: ApiService, private router: Router, private route: ActivatedRoute) {}
  ngOnInit() {
    this.idAnnouncemnt = +this.route.snapshot.paramMap.get('id');
    this.getAnnouncement(this.idAnnouncemnt);
    this.form = this.fb.group({
      description: new FormControl('', [
        Validators.minLength(10),
        Validators.maxLength(1000),
        Validators.required
      ]),
      baseScore: new FormControl('', [
        Validators.maxLength(3),
        Validators.required,
        Validators.pattern('^[0-9]*$')
        ]),
      percentage: new FormControl('', [
        Validators.maxLength(3),
        Validators.required,
        Validators.pattern('^[0-9]*$')
        ]),
        notes: this.fb.array([this.initA()]),
      merits: this.fb.array([this.initX()])
    });
  }

  public initA(): any {
    return this.fb.group({
      note: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(2000)]]
    });
  }

  public addA(): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray> this.form.controls['notes'];
    control.push(this.initA());
  }

  public removeA(index: number): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray> this.form.controls['notes'];
    control.removeAt(index);
  }

  public initX(): any {
    return this.fb.group({
      //  ---------------------forms fields on x level ------------------------
      note: this.fb.array([this.initNote()]),
      merit: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(150)]],
      percentage: ['', [Validators.required, Validators.maxLength(2), Validators.pattern('^[0-9]*$')]],
      // ---------------------------------------------------------------------
      details: this.fb.array([this.initY()])
    });
  }
  public initNote(): any {
    return this.fb.group({
      //  ---------------------forms fields on y level ------------------------
      note: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(150)]]
      // ----------------------------------------------------------------------
    });
  }

  public initY(): any {
    return this.fb.group({
      //  ---------------------forms fields on y level ------------------------
      detail: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(150)]],
      percent: ['', [Validators.required, Validators.maxLength(2), Validators.pattern('^[0-9]*$')]],
      // ---------------------------------------------------------------------
      subdetails: this.fb.array([
        this.initZ()
      ])
    });
  }

  public initZ(): any {
    return this.fb.group({
      //  ---------------------forms fields on z level ------------------------
      subdetail: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(150)]],
      percent: ['', [Validators.required, Validators.maxLength(2), Validators.pattern('^[0-9]*$')]],
      pointDetail: this.fb.array([this.initPointDetail()])
      // ---------------------------------------------------------------------
    });
  }

  public initPointDetail(): any {
    return this.fb.group({
      //  ---------------------forms fields on z level ------------------------
      description: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(150)]],
      points: ['', [Validators.required, Validators.maxLength(3), Validators.pattern('[0-9]+(\.[0-9][0-9]?)?')]]
      // ---------------------------------------------------------------------
    });
  }

  public addX(): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray> this.form.controls['merits'];
    control.push(this.initX());
  }

  public removeX(index: number): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray> this.form.controls['merits'];
    control.removeAt(index);
  }

  public removeNote(index: number, i: number): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = (<FormArray> this.form.controls['merits']).at(index).get('note') as FormArray;
    control.removeAt(i);
  }

  public addY(ix: number): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = (<FormArray> this.form.controls['merits']).at(ix).get('details') as FormArray;
    control.push(this.initY());
  }

  public removeY(index: number, iy: number): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = (<FormArray> this.form.controls['merits']).at(index).get('details') as FormArray;
    control.removeAt(iy);
  }

  public addZ(ix: number, iy: number): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = ((<FormArray> this.form.controls['merits']).at(ix).get('details') as FormArray).at(iy).get('subdetails') as FormArray;
    control.push(this.initZ());
  }

  public removeZ(ix: number, iy: number, iz: number): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = ((<FormArray> this.form.controls['merits']).at(ix).get('details') as FormArray).at(iy).get('subdetails') as FormArray;
    control.removeAt(iz);
  }

  public addPointDetail(ix: number, iy: number, iz: number): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    // tslint:disable-next-line:max-line-length
    const control = (((<FormArray> this.form.controls['merits']).at(ix).get('details') as FormArray).at(iy).get('subdetails') as FormArray).at(iz).get('pointDetail') as FormArray;
    control.push(this.initPointDetail());
  }

  public removePointDetail(ix: number, iy: number, iz: number, iPointDetail: number): void {
    const control = (((<FormArray> this.form.controls['merits']).at(ix).get('details') as FormArray).at(iy).get('subdetails') as FormArray).at(iz).get('pointDetail') as FormArray;
    control.removeAt(iPointDetail);
  }

  public getAnnouncement(id: number): void {
    this.apiService.getById('/announcement', id).subscribe(
      (response: any) => {
        this.announcement = response;
      });
  }

  public save(): void {
    let sco = 0;
    this.meritList = this.form.controls.merits.value;
    this.listDetails();
    this.listSUBDetails();
    this.notes = this.form.controls.notes.value;
    if (this.notes.length > 0) {
      this.meritTable.note = this.notes[0].note;
    }
    this.meritTable.description = this.form.controls.description.value;
    this.meritTable.baseScore = this.form.controls.baseScore.value;
    this.meritTable.finalScore = this.form.controls.percentage.value;
    this.meritTable.merits = this.form.controls.merits.value;
    this.meritTable.idannouncement = this.idAnnouncemnt;
    // tslint:disable-next-line: forin
    for (const i in this.meritTable.merits) {
      sco = sco + (+this.meritTable.merits[i].percentage);
    }
    if (+this.meritTable.baseScore === sco) {
      if (this.isCorrectTotal !== true) {
        if (this.isWrongSubDetaeilTotal !== true) {
          this.apiService.post('/merit/complex', this.meritTable).subscribe(
            (response: number) => {
              this.showAlertMessage.showSuccessAlert ('Tabla de méritos registrada exitosamente');
              if (this.announcement.area.idarea === 1) {
                this.router.navigate(['/admin/laboratorio-tabla', this.idAnnouncemnt]);
              } else {
                this.router.navigate(['/admin/docencia-tabla', this.idAnnouncemnt]);
              }
            },
            (error: HttpErrorResponse) => {
            this.showAlertMessage.showErrorAlert(error.error.message_error);
          }
          );
        } else {
          this.showAlertMessage.showErrorAlert('La suma de los puntos de algunos subdetalles no conincide con el total del detalle');
        }
      } else {
        this.showAlertMessage.showErrorAlert('La suma del porcentaje de algunos detalles no conincide con el total del mérito');
      }
    } else {
      this.showAlertMessage.showErrorAlert('El puntaje base no coincide con la suma del porcentaje de los méritos');
    }
  }

  public listDetails(): void {
    this.detailList = [];
    this.addResult = [];
    // tslint:disable-next-line:forin
    for (const q in this.meritList) {
      this.detailList.push(this.meritList[q].details);
      this.addResult.push(this.addDetails(this.detailList[q], this.meritList[q].percentage));
    }
    this.isCorrectTotal = this.addResult.includes(false);
  }

  public addDetails(list: Array<any>, total: any): boolean {
    let addition = 0;
    let res = false;
    // tslint:disable-next-line:forin
    for (const y in list) {
      addition = addition + (+list[y].percent);
    }
    if (addition === +total) {
      res = true;
    }
    return res;
  }

  public listSUBDetails(): void {
    this.subdetailList = [];
    this.addResultSubdetails = [];
    this.detailPercents = [];
    // tslint:disable-next-line:forin
    for (const x in this.detailList) {
      // tslint:disable-next-line:forin
      for (const q in this.detailList[x]) {
        this.subdetailList.push(this.detailList[x][q].subdetails);
        this.detailPercents.push(this.detailList[x][q].percent);
        }
    }
    // tslint:disable-next-line:forin
    for (const j in this.subdetailList) {
      this.addResultSubdetails.push(this.addSUBDetails(this.subdetailList[j], this.detailPercents[j]));
    }
    this.isWrongSubDetaeilTotal = this.addResultSubdetails.includes(false);
  }

  public addSUBDetails(list: Array<any>, total: any): boolean {
    let addition = 0;
    let res = false;
    if (list.length === 0) {
      addition = +total;
    } else {
      // tslint:disable-next-line:forin
      for (const y in list) {
        addition = addition + (+list[y].percent);
      }
    }
    if (addition === +total) {
      res = true;
    }
    return res;
  }
  public cancel(): void {
    this.showAlertMessage.showCancelAlert('¿Seguro que quiere cancelar el llenado de éste formulario?',
    'No se podrán recuperar los datos ingresados');
  }
}
