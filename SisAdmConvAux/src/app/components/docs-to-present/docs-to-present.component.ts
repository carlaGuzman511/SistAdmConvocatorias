import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';
import { ShowAlertMessage } from 'src/app/showAlertMessage';
import { ApiService } from 'src/app/api-service/api.service';
import { HttpErrorResponse } from '@angular/common/http';
import { DocsToPresentA } from 'src/app/models/docs.model';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-docs-to-present',
  templateUrl: './docs-to-present.component.html',
  styleUrls: ['./docs-to-present.component.css']
})
export class DocsToPresentComponent implements OnInit {
  public form: FormGroup;
  public requests: any[];
  public idAnnouncemnt: number;
  public notes: Array<any> = [];
  public showAlertMessage = new ShowAlertMessage();
  public docs: DocsToPresentA = {
    name: '',
    docs: [],
    note: '',
    announcement: 0
  };
  constructor(private fb: FormBuilder, private apiService: ApiService, private router: Router, private route: ActivatedRoute) {}
  ngOnInit() {
    this.idAnnouncemnt = +this.route.snapshot.paramMap.get('id');
    this.form = this.fb.group({
      notes: this.fb.array([this.initY()]),
      docs: this.fb.array([this.initX()])
    });
  }

  public initX(): any {
    return this.fb.group({
      doc: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(2000)]]
    });
  }

  public addX(): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray> this.form.controls['docs'];
    control.push(this.initX());
  }

  public  removeX(index: number): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray> this.form.controls['docs'];
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
      this.docs.note = this.notes[0].note;
    }
    this.requests = this.form.controls.notes.value;
    this.docs.docs = this.form.controls.docs.value;
    this.docs.announcement = this.idAnnouncemnt;
    // tslint:disable-next-line:forin
    this.apiService.post('/document/create', this.docs).subscribe(
      (response: number) => {
        this.showAlertMessage.showSuccessAlert ('Documentos a presentar registrados exitosamente');
        this.router.navigate(['/admin/fechaLimite',  this.idAnnouncemnt]);
    },
      (error: HttpErrorResponse) => {
        this.showAlertMessage.showErrorAlert('Hubo un error, vuelva a intentarlo');
    });
  }

  public cancel(): void {
    this.showAlertMessage.showCancelAlert('¿Seguro que quiere cancelar el llenado de éste formulario?',
    'No se podrán recuperar los datos ingresados');
  }
}
