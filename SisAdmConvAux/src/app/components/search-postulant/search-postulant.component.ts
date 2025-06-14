import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ApiService } from 'src/app/api-service/api.service';
import { Search } from 'src/app/models/search.model';
import { HttpErrorResponse } from '@angular/common/http';
import { Person } from 'src/app/models/person.model';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/api-service/user.service';
import { ShowAlertMessage } from 'src/app/showAlertMessage';
import { Observable } from 'rxjs';
import { Announcement } from 'src/app/models/announcement.model';
import { Deadline1 } from 'src/app/models/deadline.model';

@Component({
  selector: 'app-search-postulant',
  templateUrl: './search-postulant.component.html',
  styleUrls: ['./search-postulant.component.css']
})
export class SearchPostulantComponent implements OnInit {
  public form: FormGroup;
  public deadline: Deadline1 = {
    description: '',
    deliveryDate: '',
    deliveryTime: '',
    deliveryPlace: '',
    announcement: 0
  };
  public postulants: any[];
  public auxialiaries: Array<any> = [];
  public idPersons: Array<any> = [];
  public postulantsFinded: Array<any> = [];
  public test: Array<any> = [];
  public finalList: Array<any> = [];
  public auxs: string = '';
  public search: Search = {
    text: '',
    idacademicunit: '',
    announcement: null,
  };
  public showAlertMessage = new ShowAlertMessage();
  public user: User;
  public paramsAU = {
    idacademicunit: '',
  };
  public announcementList: Observable<Announcement[]>;

  constructor(private apiService: ApiService, private userService: UserService) {
    this.user = this.userService.getCurrentUser();
    this.search.idacademicunit = this.user.announcements[0].academicUnit.idacademicunit.toString();
    this.paramsAU.idacademicunit = this.search.idacademicunit;
  }

  ngOnInit() {
    this.searchManagement();
    this.form = new FormGroup({
      searchbox: new FormControl('', Validators.required),
      announcement: new FormControl('', Validators.required)
    });
  }

  public getItems(): void {
    const date = this.deadline.deliveryDate;
    const validateDate = this.dateValidator(date);
    if (validateDate === true) {
      this.postulants = [];
      this.apiService.post('/label/searchByText', this.search).subscribe(
      (response: any) => {
        this.postulants = response;
        this.filterRepeated();
      }, (error: HttpErrorResponse) => {
        alert(error.error.message_error);
      }
    );
    } else {
      this.showAlertMessage.showError('La fecha límite para entregar documentos fue hasta: ' + date);
      window.location.reload();
    }
  }

  public dateValidator( date: string): boolean {
    let res: boolean;
    const dateToValidate = new Date(date);
    const today = new Date();
    dateToValidate.setHours(0, 0, 0, 0);
    today.setHours(0, 0, 0, 0);
    dateToValidate.setTime( dateToValidate.getTime() + 1 * 24 * 60 * 60 * 1000);
    if (dateToValidate >= today) {
      res = true;
    } else {
      res = false;
    }
    return res;
  }

  public filterRepeated(): void {
    this.idPersons = [];
    this.postulantsFinded = [];
    this.test = [];
    // tslint:disable-next-line:forin
    for (const i in this.postulants) {
      this.postulantsFinded.push(this.postulants[i].postulantes.idpostulant);
      this.idPersons.push(this.postulants[i].postulantes.person.id);
    }
    this.postulantsFinded = this.postulantsFinded.filter((value, index, array) => array.indexOf(value) === index);
    this.idPersons = this.idPersons.filter((value, index, array) => array.indexOf(value) === index);
    // tslint:disable-next-line:forin
    for (const x in this.postulantsFinded) {
      for (const y in this.postulants) {
        if (this.postulants[y].postulantes.idpostulant === this.postulantsFinded[x]) {
          this.test.push(this.postulants[y]);
        }
      }
    }
    if (this.test.length === 0) {
      this.showAlertMessage.showErrorAlert('No se encontrarón postulantes con el dato ingresado');
    }
    this.stick();
  }

  public stick(): void {
    this.auxialiaries = [];
    // tslint:disable-next-line:forin
    for (const k in this.idPersons) {
      for (const j in this.test) {
        if (this.idPersons[k] === this.test[j].postulantes.person.id) {
          this.auxs = this.auxs.concat('  -  '.toString());
          this.auxs = this.auxs.concat(this.test[j].auxiliary.name.toString());
        }
      }
      this.auxialiaries.push(this.auxs);
      this.auxs = '';
    }
    this.match();
  }

  public match() {
    this.finalList = [];
    // tslint:disable-next-line:forin
    for (const k in this.idPersons) {
      // tslint:disable-next-line:forin
      let quantity = 0;
      for (const d in this.postulants) {
        if (quantity === 0 && this.postulants[d].postulantes.person.id === (+this.idPersons[k])) {
          this.finalList.push(this.postulants[d].postulantes);
          quantity++;
        }
      }
    }
  }

  public searchFuntion(): void {
    this.search.text = this.form.controls.searchbox.value.toLowerCase();
    this.searchDeadline();
  }

  private searchDeadline(): void {
    const idAnnnouncement = +this.form.controls.announcement.value;
    this.apiService.getById('deadline/announcement', idAnnnouncement).subscribe(
      (response: Deadline1[]) => {
        this.deadline = response[0];
        this.postulants = [];
        this.getItems();
      },
      (error: HttpErrorResponse) => {
        this.showAlertMessage.showError('No se encontro una fecha limite para la convocatoria');
      }
    );
  }

  public searchManagement(): void {
    this.apiService.post('/announcement/list/MAU', this.paramsAU).subscribe((res: Observable<Announcement[]>) => {
      this.announcementList = res;
    });
  }
  public saveDataAU(): void {
    this.search.announcement = this.form.controls.announcement.value;
  }
}
