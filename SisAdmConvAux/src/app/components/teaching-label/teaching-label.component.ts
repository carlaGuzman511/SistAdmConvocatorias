import { Component } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Person, TeachingLabels } from 'src/app/models/person.model';
import { Postulation } from 'src/app/models/postulation';
import { ShowAlertMessage } from 'src/app/showAlertMessage';
import { ApiService } from 'src/app/api-service/api.service';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Template } from 'src/app/models/template';
import * as jsPDF from 'jspdf';
import { Career } from 'src/app/models/career.model';
import { Area } from 'src/app/models/area.model';
import { AcademicUnit } from 'src/app/models/academic-unit.model';
import { Label } from 'src/app/models/label.model';
import { RequestAux } from 'src/app/models/requirements.model';

@Component({
  selector: 'app-teaching-label',
  templateUrl: './teaching-label.component.html',
  styleUrls: ['./teaching-label.component.css']
})
export class TeachingLabelComponent {
  public auxs: string = '';
  public dropdownList = [];
  public selectedItems = [];
  public dropdownSettings = {};
  public labelForm: FormGroup;
  public codItems: any [];
  public careers: any [];
  public currentItem: {};
  public areaAux: Area;
  public currentCareer: Career;
  public academicUnitAux: AcademicUnit;
  public idAuxs: Array<any> = [];
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
    postulant: 0,
    announcement: 0
  };
  public request: RequestAux = {
    idarea: 2,
    idacademicunit: 1
  };
  public teachingLabels: TeachingLabels = {
    person: this.person,
    career: 0,
    announcement: 0,
    auxiliary: []
  };
  public idAnnouncemnt: number;
  public showAlertMessage = new ShowAlertMessage();
  constructor(public fb: FormBuilder, private apiService: ApiService, private router: Router,  private route: ActivatedRoute) {
    this.createLabelForm();
  }

  private createLabelForm(): void {
    this.idAnnouncemnt = +this.route.snapshot.paramMap.get('id');
    this.getCareers();
    this.auxCall(+this.idAnnouncemnt);
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
        address: new FormControl('', [
        Validators.minLength(10),
        Validators.maxLength(255),
        Validators.required
        ]),
        ci: new FormControl('', [
          Validators.minLength(7),
          Validators.maxLength(8),
          Validators.required,
          Validators.pattern('^[0-9]*$')
          ]),
        phone: new FormControl('', [
        Validators.minLength(7),
        Validators.maxLength(8),
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
        auxiliary: new FormControl('')
      },
    );
  }
  public savePostulation() {
    this.idAuxs = [];
    // tslint:disable-next-line:forin
    for (const x in this.selectedItems) {
      this.idAuxs.push(this.selectedItems[x].idauxiliary);
    }
    this.person.name = this.labelForm.controls.name.value;
    this.person.lastName = this.labelForm.controls.surname.value;
    this.person.address = this.labelForm.controls.address.value;
    this.person.phoneNumber = this.labelForm.controls.phone.value;
    this.person.email = this.labelForm.controls.email.value;
    this.person.ci = this.labelForm.controls.ci.value;
    this.teachingLabels.person = this.person;
    this.teachingLabels.career = this.labelForm.controls.career.value;
    this.teachingLabels.announcement = this.idAnnouncemnt;
    this.teachingLabels.auxiliary = this.idAuxs;
    this.apiService.post('/labelTeaching', this.teachingLabels).subscribe(
      (response: number) => {
        this.getCareer();
        this.printLabel();
        this.showAlertMessage.showSuccessAlert ('Rótulo creado exitosamente.');
        this.router.navigate(['/listarConvocatorias']);
      },
      (error: HttpErrorResponse) => {
      this.showAlertMessage.showErrorAlert(error.error.message_error);
    });
  }

  printLabel(): void {
    const doc = new jsPDF();
    const template = Template.teachingTemplate;
    doc.addImage(template, 'JPEG', 0, 0, 180, 150);
    doc.setFontSize(12);
    doc.text(48, 30, this.labelForm.controls.name.value);
    doc.text(48, 45, this.labelForm.controls.surname.value);
    doc.text(48, 59, this.labelForm.controls.address.value);
    doc.text(48, 73, this.labelForm.controls.phone.value);
    doc.text(48, 89, this.labelForm.controls.email.value);
    doc.text(48, 103, this.currentCareer.name);
    doc.text(48, 119, this.getAuxs());
    doc.save('ROTULO');
  }

  public getItems(): any {
    this.apiService.getAll('/auxiliary').subscribe(
      (response: any) => {
        this.dropdownList = response;
      });
  }

  public getCareers(): any {
    this.apiService.getAll('/career').subscribe(
      (response: any) => {
        this.careers = response;
      });
  }

  auxCall(id: number) {
    let endpoint = '/announcement/listadoauxiliary/';
    endpoint = endpoint.concat(id.toString());
    this.apiService.getAll(endpoint).subscribe(
      (response: any) => {
        this.dropdownList = response;
      });
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'idauxiliary',
      textField: 'name',
      selectAllText: 'Seleccionar todos',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true
    };
  }

  public getCareer(): any {
    for (const i in this.careers) {
      if (this.careers[i].idcareer === (+this.labelForm.controls.career.value)) {
        this.currentCareer = this.careers[i];
      }
    }
  }
  getAuxs(): string {
    // tslint:disable-next-line:forin
    for (const i in this.selectedItems) {
      this.auxs = this.auxs.concat(this.selectedItems[i].name.toString());
      this.auxs = this.auxs.concat('\n'.toString());
    }
    return this.auxs;
  }
}
