import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators, FormArray } from '@angular/forms';
import { ShowAlertMessage } from 'src/app/showAlertMessage';
import { ApiService } from 'src/app/api-service/api.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Deadline1 } from 'src/app/models/deadline.model';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-deadline',
  templateUrl: './deadline.component.html',
  styleUrls: ['./deadline.component.css']
})
export class DeadlineComponent implements OnInit {
  public form: FormGroup;
  public showAlertMessage = new ShowAlertMessage();
  public idAnnouncemnt: number;
  public descriptions: Array<any> = [];
  public today = new Date();
  public deadline: Deadline1 = {
    description: '',
    deliveryPlace: '',
    deliveryDate: '',
    deliveryTime: '',
    announcement: 0
  };
  constructor(private fb: FormBuilder, private apiService: ApiService, private router: Router, private route: ActivatedRoute) {}
  ngOnInit() {
    this.idAnnouncemnt = +this.route.snapshot.paramMap.get('id');
    this.form = this.fb.group({
      descriptions: this.fb.array([this.initY()]),
      deliveryplace: new FormControl('', [
        Validators.minLength(5),
        Validators.maxLength(255),
        Validators.required
      ]),
      deliverydate: new FormControl('',
        Validators.required),
      deliverytime: new FormControl('',
        Validators.required),
    });
  }

  public initY(): any {
    return this.fb.group({
      description: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(2000)]]
    });
  }

  public addY(): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray> this.form.controls['descriptions'];
    control.push(this.initY());
  }

  public removeY(index: number): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray> this.form.controls['descriptions'];
    control.removeAt(index);
  }

  public save(): void {
    this.descriptions = this.form.controls.descriptions.value;
    if (this.descriptions.length > 0) {
      this.deadline.description = this.descriptions[0].description;
    }
    this.deadline.deliveryPlace = this.form.controls.deliveryplace.value;
    this.deadline.deliveryDate = this.form.controls.deliverydate.value;
    this.deadline.deliveryTime = this.form.controls.deliverytime.value;
    this.deadline.announcement = this.idAnnouncemnt;
    // tslint:disable-next-line:forin
    this.apiService.post('/deadline', this.deadline).subscribe(
      (response: number) => {
        this.showAlertMessage.showSuccessAlert ('Fecha límite registrada exitosamente');
        this.router.navigate(['/admin/registrarTablaCalificacionMeritos', this.idAnnouncemnt]);
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
