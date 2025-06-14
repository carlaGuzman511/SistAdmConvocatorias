import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { ApiService } from 'src/app/api-service/api.service';
import { Router, ActivatedRoute } from '@angular/router';
import * as jsPDF from 'jspdf';
import { Postulation } from 'src/app/models/postulation';
import { ShowAlertMessage } from 'src/app/showAlertMessage';
import { HttpErrorResponse } from '@angular/common/http';
import { Template } from 'src/app/models/template';
import { Person } from 'src/app/models/person.model';
import { Auxiliary } from 'src/app/models/auxiliary.model';
import { Area } from 'src/app/models/area.model';
import { AcademicUnit } from 'src/app/models/academic-unit.model';
import { Career } from 'src/app/models/career.model';
import { Label } from 'src/app/models/label.model';
import { Announcement } from 'src/app/models/announcement.model';
import { RequestAux } from 'src/app/models/requirements.model';

@Component({
  selector: 'app-rotulo',
  templateUrl: './rotulo.component.html',
  styleUrls: ['./rotulo.component.css']
})
export class RotuloComponent {

  public labelForm: FormGroup;
  public codItems: Auxiliary [];
  public careers: any [];
  public areaAux: Area;
  public currentCareer: Career;
  public academicUnitAux: AcademicUnit;
  public idAnnouncemnt: number;
  private announvement: Announcement;
  public currentItem: Auxiliary = {
    idauxiliary: 0,
    name: '',
    code: '',
    academicHours: '',
    announcement: this.announvement,
  };
  private person: Person = {
    id: 0,
    ci: 0,
    name: '',
    lastName: '',
    address: '',
    phoneNumber: 0,
    email: ''
  };
  private postulation: Postulation = {
    person: 0,
    career: 0,
    status: false
  };
  private label: Label = {
    auxiliary: 0,
    announcement: 0,
    postulant: 0
  };
  public request: RequestAux = {
    idarea: 1,
    idacademicunit: 1
  };
  public showAlertMessage = new ShowAlertMessage();
  constructor(public fb: FormBuilder, private apiService: ApiService, private router: Router,  private route: ActivatedRoute) {
    this.createLabelForm();
  }

  private createLabelForm(): void {
    this.idAnnouncemnt = +this.route.snapshot.paramMap.get('id');
    this.getAuxiliaries(+this.idAnnouncemnt);
    this.getCareers();
    this.labelForm = new FormGroup(
      {
        name: new FormControl('', [
        Validators.minLength(3),
        Validators.maxLength(50),
        Validators.required,
        Validators.pattern('^[a-zA-Z\ áéíóúÁÉÍÓÚñÑ\s]*$')
        ]),
        surname: new FormControl('', [
        Validators.minLength(3),
        Validators.maxLength(50),
        Validators.required,
        Validators.pattern('^[a-zA-Z\ áéíóúÁÉÍÓÚñÑ\s]*$')
        ]),
        ci: new FormControl('', [
        Validators.minLength(7),
        Validators.maxLength(8),
        Validators.required,
        Validators.pattern('^[0-9]*$')
        ]),
        address: new FormControl('', [
        Validators.minLength(10),
        Validators.maxLength(255),
        Validators.required
        ]),
        phone: new FormControl('', [
        Validators.minLength(7),
        Validators.maxLength(13),
        Validators.required,
        Validators.pattern('^[0-9]*$')
        ]),
        email: new FormControl('', [
        Validators.minLength(6),
        Validators.maxLength(70),
        Validators.required,
        Validators.email
        ]),
        career: new FormControl('', Validators.required),
        codItem: new FormControl('', Validators.required),
        auxiliary: new FormControl('')
      },
    );
  }

  public savePostulation(): void {
    this.saveData();
    this.apiService.post('/person', this.person).subscribe(
      (idPerson: number) => {
        this.postulation.person = idPerson;
        this.apiService.post('/postulant', this.postulation).subscribe(
          (idPostulation: number) => {
            this.label.postulant = idPostulation;
            this.apiService.post('/label', this.label).subscribe(
              (idLabel: number) => {
                this.showAlertMessage.showSuccessAlert ('Rótulo creado exitosamente');
                this.printLabel();
                this.router.navigate(['/listarConvocatorias']);
              },
                (error: HttpErrorResponse) => {
                this.showAlertMessage.showErrorAlert(error.error.message_error);
              }
            );
          });
        });
    }

  public saveData(): void {
    this.selectedCareer(+this.labelForm.controls.career.value);
    this.person.name = this.labelForm.controls.name.value;
    this.person.lastName = this.labelForm.controls.surname.value;
    this.person.address = this.labelForm.controls.address.value;
    this.person.ci = this.labelForm.controls.ci.value;
    this.person.phoneNumber = this.labelForm.controls.phone.value;
    this.person.email = this.labelForm.controls.email.value;
    this.postulation.career = +this.currentCareer.idcareer;
    this.label.auxiliary = +this.currentItem.idauxiliary;
    this.label.announcement = this.idAnnouncemnt;
  }
  public printLabel(): void {
    const doc = new jsPDF();
    const template = Template.labelTemplate;
    doc.addImage(template, 'JPEG', 0, 0, 180, 150);
    doc.setFontSize(12);
    doc.text(48, 31, this.labelForm.controls.name.value);
    doc.text(48, 46, this.labelForm.controls.surname.value);
    doc.text(48, 61, this.labelForm.controls.address.value);
    doc.text(48, 76, this.labelForm.controls.phone.value);
    doc.text(48, 91, this.labelForm.controls.email.value);
    doc.text(48, 106, this.currentCareer.name);
    doc.text(48, 121, this.currentItem.code);
    doc.text(48, 137, this.currentItem.name);
    doc.save('ROTULO');
  }

  public getCareers(): any {
    this.apiService.getAll('/career').subscribe(
      (response: any) => {
        this.careers = response;
      });
  }

  public selectedItem(id: any): void {
    this.currentItem = this.codItems.find(item => item.idauxiliary == id);
  }

  public selectedCareer(id: any): Career {
    this.currentCareer = this.careers.find(item => item.idcareer === id);
    return this.currentCareer;
  }

  public getAuxiliaries(id: number): any {
    let endpoint = '/announcement/listadoauxiliary/';
    endpoint = endpoint.concat(id.toString());
    this.apiService.getAll(endpoint).subscribe(
      (response: any) => {
        this.codItems = response;
      });
  }
}
