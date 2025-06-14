import { Component } from "@angular/core";
import { ShowAlert } from "../../models/show-alert";
import {
  FormGroup,
  FormBuilder,
  FormControl,
  Validators,
} from "@angular/forms";
import { Observable } from "rxjs";
import { Area } from "../../models/area.model";
import { Management } from "../../models/management.model";
import { AcademicUnit } from "../../models/academic-unit.model";
import { ApiService } from "../../api-service/api.service";
import { Faculty } from "../../models/faculty.model";
import { HttpErrorResponse } from "@angular/common/http";
import { Router } from "@angular/router";
import { User } from "../../models/user.model";
import { UserService } from "../../api-service/user.service";
import { Announcement } from "src/app/models/announcement.model";

@Component({
  selector: "app-list-announcement-admi",
  templateUrl: "./list-announcement-admi.component.html",
  styleUrls: ["./list-announcement-admi.component.css"],
})
/**
  This class contains the component that show the announcement to public admin 
  @class ListAnnouncementAdmiComponent
*/

export class ListAnnouncementAdmiComponent {
  public user: User;
  public showMessage = new ShowAlert();
  public filterForm: FormGroup;
  public managementList: Observable<Management[]>;
  public areaList: Observable<Area[]>; // Observable<Area[]>;
  public academicUnitList: Observable<AcademicUnit[]>;
  public facultyList: Observable<Faculty[]>;
  public announcementList: any[];
  public paramsAU = {
    idfaculty: 0,
    idmanagement: 0,
  };
  public show = false;

  public paramsAnnouncement = {
    idmanagement: 0,
    idarea: 0,
    idacademicunit: 0,
    idfaculty: 0,
  };
  constructor(public fb: FormBuilder, private apiService: ApiService, private userService: UserService, private router: Router) {
    this.user = this.userService.getCurrentUser();
    this.createForm();
  }
   /**
  This function is to obtain the existing management
    @method createForm()
  */
  private createForm(): void {
    this.searchManagement();
    this.searchArea();
    this.searchFaculty();
    this.filterForm = new FormGroup(
      {
        year: new FormControl('Gestion',
          Validators.required,
        ),
        faculty: new FormControl('Facultad',
          Validators.required
        ),
        academicUnit: new FormControl('Unidad Academica',
          Validators.required
        ),
        area: new FormControl('Area',
          Validators.required
        ),
      },
    );
  }
   /**
  This function is to obtain the existing management
    @method searchManagement()
  */
  public searchManagement(): void {
    this.apiService.getAll('/management').subscribe((res: Observable<Management[]>) => {
      this.managementList = res;
    });
  }
  /**
  This function is to get the existing areas
    @method searchArea()
  */
  public searchArea(): void {
    this.apiService.getAll('/area').subscribe((res: Observable<Area[]>) => {
      this.areaList = res;
    });
  }
  /**
  This function is to get the existing faculties
    @method searchFaculty()
  */
  public searchFaculty(): void {
    this.apiService.getAll('/faculty').subscribe((res: Observable<Faculty[]>) => {
      this.facultyList = res;
    });
  }
     /**
   This function is to get the existing academic unit
    @method searchAcademiUnit()
  */
  public searchAcademiUnit() {
    this.apiService.post('/academicunit/list/MF', this.paramsAU)
      .subscribe((res: Observable<AcademicUnit[]>) => {
        this.academicUnitList = res;
      });
  }
   /**
  This function is to save the value of the filters: management, faculty, to fill the academicUnit select
    @method saveDataAU()
  */
  public saveDataAU(): void {
    this.paramsAU.idmanagement = +this.filterForm.controls.year.value;
    this.paramsAU.idfaculty = +this.filterForm.controls.faculty.value;
    this.searchAcademiUnit();
  }
     /**
  This function is to save the value of the filters
    @method saveData()
  */
  public saveData(): void {
    this.paramsAnnouncement.idmanagement = +this.filterForm.controls.year.value;
    this.paramsAnnouncement.idarea = +this.filterForm.controls.area.value;
    this.paramsAnnouncement.idfaculty = +this.filterForm.controls.faculty.value;
    this.paramsAnnouncement.idacademicunit = +this.filterForm.controls.academicUnit.value;
  }
   /**
  This function send parameters necessary for the search of results
    @method onSubmit()
  */
  public onSubmit() {
    this.show = true;
    this.saveData();
    this.apiService.post('/announcement/list/MAAUF', this.paramsAnnouncement)
      .subscribe((res: any[]) => {
        this.announcementList = res;
        if ((this.announcementList).length === 0) {
          this.showMessage.showMessageError('No se encontraron los resultados');
        }
      },
        (error: HttpErrorResponse) => {
          this.showMessage.showMessageError('Ocurrio un error, vuelva a intentarlo');
        }
      );
  }
   /**
  This function redirect to see the announcement
    @method viewAnnouncement(announcement)
  */
  public viewAnnouncement(announcementB: Announcement): void {
    var areaA = announcementB.area.name;
    if (areaA === 'Docencia') {
      this.router.navigate(['/mostrar-convocatoria-docencia', announcementB.idannouncement]);
    } else {
      this.router.navigate(['/mostrar-convocatoria-laboratorio', announcementB.idannouncement]);
    }
  }


}
