import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, Validators } from '@angular/forms';
import { ApiService } from 'src/app/api-service/api.service';
import { Request } from 'src/app/models/requisito.model';
import { ShowAlertMessage } from 'src/app/showAlertMessage';
import { HttpErrorResponse } from '@angular/common/http/http';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-register-requisitos',
  templateUrl: './register-requisitos.component.html',
  styleUrls: ['./register-requisitos.component.css']
})
export class RegisterRequisitosComponent implements OnInit {
  public registerRequisitosForm: FormGroup;
  public requests: Array<any> = [];
  public notes: Array<any> = [];
  public idAnnouncemnt: number;
  public showAlertMessage = new ShowAlertMessage();
  public requisitosAnnouncement: Request = {
    note: '',
    requests: [],
    description: '',
    announcement: 0
  };
  public form: FormGroup;

  constructor(private fb: FormBuilder, private apiService: ApiService, private router: Router,  private route: ActivatedRoute) {}
  ngOnInit() {
    this.idAnnouncemnt = +this.route.snapshot.paramMap.get('id');
    this.form = this.fb.group({
      notes: this.fb.array([this.initY()]),
      requests: this.fb.array([this.initX()])
    });
  }

  public initX(): any {
    return this.fb.group({
      requisito: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(2000)]]
    });
  }

  public addX(): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray> this.form.controls['requests'];
    control.push(this.initX());
  }

  public removeX(index: number): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray> this.form.controls['requests'];
    control.removeAt(index);
  }

  public initY(): any {
    return this.fb.group({
      note: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(2000)]]
    });
  }
  public addY(): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray> this.form.controls['notes'];
    control.push(this.initY());
  }

  public removeY(index: number): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray> this.form.controls['notes'];
    control.removeAt(index);
  }

  public save(): void {
    this.notes = this.form.controls.notes.value;
    if (this.notes.length > 0) {
      this.requisitosAnnouncement.note = this.notes[0].note;
    }
    this.requests = this.form.controls.requests.value;
    this.requisitosAnnouncement.requests = this.form.controls.requests.value;
    this.requisitosAnnouncement.announcement = this.idAnnouncemnt;
    this.apiService.post('/request/create', this.requisitosAnnouncement).subscribe(
      (response: number) => {
            this.showAlertMessage.showSuccessAlert ('Requisitos registrados exitosamente');
            this.router.navigate(['/admin/documentosAPresentar', this.idAnnouncemnt]);
          },
          (error: HttpErrorResponse) => {
          this.showAlertMessage.showErrorAlert(error.error.message_error);
      });
  }

  public cancel(): void {
    this.showAlertMessage.showCancelAlert('¿Seguro que quiere cancelar el llenado de éste formulario?',
     'No se podrán recuperar los datos ingresados');
  }
}
