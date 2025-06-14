import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators, FormArray } from '@angular/forms';
import { ShowAlertMessage } from 'src/app/showAlertMessage';
import { ApiService } from 'src/app/api-service/api.service';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Courts } from 'src/app/models/courts.model';

@Component({
  selector: 'app-courts',
  templateUrl: './courts.component.html',
  styleUrls: ['./courts.component.css']
})
export class CourtsComponent implements OnInit {
  public form: FormGroup;
  public showAlertMessage = new ShowAlertMessage();
  public descriptions: Array<any> = [];
  public idAnnouncemnt: number;
  public today = new Date();
  public courts: Courts = {
    description: '',
    meritsCourts: 0,
    studentDelegateMerit: 0,
    knowledgeCourts: 0,
    studentDelegateKnowledge: 0,
    idannouncement: 0
  };
  constructor(private fb: FormBuilder, private apiService: ApiService, private router: Router, private route: ActivatedRoute) {}
  ngOnInit() {
    this.idAnnouncemnt = +this.route.snapshot.paramMap.get('id');
    this.form = this.fb.group({
      descriptions: this.fb.array([this.initX()]),
      teachersMerit: new FormControl('', [
        Validators.maxLength(2),
        Validators.required,
        Validators.pattern('^[0-9]*$')
        ]),
      studentDelegateMerit: new FormControl('', [
        Validators.maxLength(2),
        Validators.required,
        Validators.pattern('^[0-9]*$')
        ]),
      teachersKnoledge: new FormControl('', [
        Validators.maxLength(2),
        Validators.required,
        Validators.pattern('^[0-9]*$')
        ]),
      studentDelegateKnoledge: new FormControl('', [
        Validators.maxLength(2),
        Validators.required,
        Validators.pattern('^[0-9]*$')
        ]),
    });
  }

  public initX(): any {
    return this.fb.group({
      description: ['', [Validators.required, Validators.minLength(10),
            Validators.maxLength(1000)]]
    });
  }

  public addX(): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray> this.form.controls['descriptions'];
    control.push(this.initX());
  }

  public removeX(index: number): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray> this.form.controls['descriptions'];
    control.removeAt(index);
  }

  public save(): void {
    this.descriptions = this.form.controls.descriptions.value;
    if (this.descriptions.length > 0) {
      this.courts.description = this.descriptions[0].description;
    }
    this.courts.idannouncement = this.idAnnouncemnt;
    this.courts.meritsCourts = this.form.controls.teachersMerit.value;
    this.courts.studentDelegateMerit = this.form.controls.studentDelegateMerit.value;
    this.courts.knowledgeCourts = this.form.controls.teachersKnoledge.value;
    this.courts.studentDelegateKnowledge = this.form.controls.studentDelegateKnoledge.value;
    this.apiService.post('/courts', this.courts).subscribe(
      (response: number) => {
        this.showAlertMessage.showSuccessAlert ('Registrado exitosamente');
        this.router.navigate(['/admin/registrarFechas', this.idAnnouncemnt]);
    },
      (error: HttpErrorResponse) => {
        this.showAlertMessage.showErrorAlert('Ocurrió un error, vuelva a intentarlo');
    });
  }

  public cancel(): void {
    this.showAlertMessage.showCancelAlert('¿Seguro que quiere cancelar el llenado de éste formulario?',
    'No se podrán recuperar los datos ingresados');
  }
}
