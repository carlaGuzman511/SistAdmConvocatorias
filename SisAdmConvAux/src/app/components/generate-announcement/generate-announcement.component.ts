import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { ShowAlertMessage } from 'src/app/showAlertMessage';
import { ApiService } from 'src/app/api-service/api.service';
import { HttpErrorResponse } from '@angular/common/http';
import { AnnouncementB } from 'src/app/models/announcement.model';
import { Area } from 'src/app/models/area.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-generate-announcement',
  templateUrl: './generate-announcement.component.html',
  styleUrls: ['./generate-announcement.component.css']
})
export class GenerateAnnouncementComponent implements OnInit {
  public areas: Area[];
  public area: Area;
  public today = new Date();
  public academicUnits: [];
  public falculties: [];
  public requests: any[];
  public showAlertMessage = new ShowAlertMessage();
  public announcement: AnnouncementB = {
    title: '',
    description: '',
    pack: false,
    appointment: '',
    area: 0,
    management: 0,
    academicUnit: 0,
    faculty: 0,
    state: true
  };
  public form: FormGroup;

  constructor(private fb: FormBuilder, private apiService: ApiService, private router: Router) {}
  ngOnInit() {
    this.getAreas();
    this.getAcademicUnits();
    this.getFaculties();
    this.form = this.fb.group({
      year: new FormControl('', [
        Validators.minLength(4),
        Validators.maxLength(4),
        Validators.pattern('^[0-9]*$'),
        Validators.required
      ]),
      area: ['', Validators.required],
      academicUnit: ['', Validators.required],
      faculty: ['', Validators.required],
      title: new FormControl('', [
        Validators.minLength(10),
        Validators.maxLength(255),
        Validators.required
      ]),
      description: new FormControl('', [
        Validators.minLength(10),
        Validators.maxLength(1000),
        Validators.required
      ]),
      pack: new FormControl(''),
      ofAppointment: new FormControl('', [
        Validators.minLength(10),
        Validators.maxLength(1000),
        Validators.required
      ])
    });
  }

  public getAreas(): void {
    this.apiService.getAll('/area').subscribe(
      (response: any) => {
        this.areas = response;
      });
  }

  public getAcademicUnits(): void {
    this.apiService.getAll('/academicunit').subscribe(
      (response: any) => {
        this.academicUnits = response;
      });
  }

  public getFaculties(): void {
    this.apiService.getAll('/faculty').subscribe(
      (response: any) => {
        this.falculties = response;
      });
  }
  public save(): void {
    console.log(this.form.value);
    this.announcement.title = this.form.controls.title.value;
    this.announcement.description = this.form.controls.description.value;
    this.announcement.appointment = this.form.controls.ofAppointment.value;
    this.announcement.management = this.form.controls.year.value;
    this.announcement.area = +this.form.controls.area.value;
    this.announcement.academicUnit = +this.form.controls.academicUnit.value;
    this.announcement.faculty = +this.form.controls.faculty.value;
    if (this.form.controls.pack.value !== true) {
      this.announcement.pack = false;
    } else {
      this.announcement.pack = this.form.controls.pack.value;
    }
    this.apiService.post('/announcement/create/single', this.announcement).subscribe(
      (response: string) => {
        this.showAlertMessage.showSuccessAlert ('Datos registrados exitosamente, puede continuar!');
        if (this.announcement.area === 1) {
          this.router.navigate(['/admin/registrarRequerimientosDeLaboratorio', response]);
        } else {
          this.router.navigate(['/admin/registrarRequerimientosDeDocencia', response]);
        }
      },
      (error: HttpErrorResponse) => {
        this.showAlertMessage.showErrorAlert(error.error.message_error);
      }
    );
  }

  public cancel(): void {
    this.showAlertMessage.showCancelAlert('¿Seguro que quiere cancelar el llenado de éste formulario?',
    'No se podrán recuperar los datos ingresados');
  }
}
