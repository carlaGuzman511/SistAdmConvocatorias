import { Component } from "@angular/core";
import { FormGroup, FormBuilder, FormControl, Validators,} from "@angular/forms";
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
import { Deadline1 } from "src/app/models/deadline.model";
import { ShowAlertMessage } from "src/app/showAlertMessage";
import { Courts1 } from "src/app/models/courts.model";

@Component({
  selector: "app-list-announcement",
  templateUrl: "./list-announcement.component.html",
  styleUrls: ["./list-announcement.component.css"],
})
/**
  This class contains the component that show the announcement to public user 
  @class ListAnnouncementComponent
*/
export class ListAnnouncementComponent {
  public user: User;
  public showMessage = new ShowAlertMessage();
  public filterForm: FormGroup;
  private courts: Courts1;
  public managementList: Observable<Management[]>;
  public areaList: Observable<Area[]>; // Observable<Area[]>;
  public academicUnitList: Observable<AcademicUnit[]>;
  public facultyList: Observable<Faculty[]>;
  public announcementListAll: Array<Announcement> = [];
  public announcementList: Array<Announcement> = [];
  public paramsAU = {
    idfaculty: 0,
    idmanagement: 0,
  };
  public show = false;
  public deadline: any;
  public paramsAnnouncement = {
    idmanagement: 0,
    idarea: 0,
    idacademicunit: 0,
    idfaculty: 0,
  };

  constructor(
    public fb: FormBuilder,
    private apiService: ApiService,
    private userService: UserService,
    private router: Router
  ) {
    this.createForm();
  }
 /**
  This function create the form to search for results through filters
    @method createForm()
  */
  private createForm(): void {
    this.searchManagement();
    this.searchArea();
    this.searchFaculty();
    this.filterForm = new FormGroup({
      year: new FormControl("Gestion", Validators.required),
      faculty: new FormControl("Facultad", Validators.required),
      academicUnit: new FormControl("Unidad Academica", Validators.required),
      area: new FormControl("Area", Validators.required),
    });
  }
   /**
  This function is to obtain the existing management
    @method searchManagement()
  */ 
  public searchManagement(): void {
    this.apiService
      .getAll("/management")
      .subscribe((res: Observable<Management[]>) => {
        this.managementList = res;
      });
  }
  /**
 This function is to get the existing areas
   @method searchArea()
 */
  public searchArea(): void {
    this.apiService.getAll("/area").subscribe((res: Observable<Area[]>) => {
      this.areaList = res;
    });
  }
  public searchFaculty(): void {
    this.apiService
      .getAll("/faculty")
      .subscribe((res: Observable<Faculty[]>) => {
        this.facultyList = res;
      });
  }
  /**
  This function is to get the existing academic unit
   @method searchAcademiUnit()
 */
  public searchAcademiUnit(): void {
    this.apiService
      .post("/academicunit/list/MF", this.paramsAU)
      .subscribe((res: Observable<AcademicUnit[]>) => {
        this.academicUnitList = res;
      });
  }
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
    this.paramsAnnouncement.idacademicunit = +this.filterForm.controls
      .academicUnit.value;
  }
  public onSubmit1(): void {
    this.show = true;
    this.saveData();
    this.apiService
      .post("/announcement/list/MAAUF", this.paramsAnnouncement)
      .subscribe(
        (res: any[]) => {
          this.announcementList = res;
          if (this.announcementList.length === 0) {
            this.showMessage.showErrorAlert("No se encontraron los resultados");
          }
        },
        (error: HttpErrorResponse) => {
          this.showMessage.showErrorAlert(
            "Ocurrio un error, vuelva a intentarlo"
          );
        }
      );
  }
   /**
  This function send parameters necessary for the search of annuncements
    @method onSubmit()
  */
  public onSubmit(): void {
    this.announcementListAll = [];
    this.announcementList = [];
    this.show = true;
    this.saveData();
    this.apiService
      .post("/announcement/list/MAAUF", this.paramsAnnouncement)
      .subscribe(
        (res: any[]) => {
          this.announcementListAll = res;
          for (const x in this.announcementListAll) {
            let comisions = 0;
            let shouldBe = 0;
            this.apiService
              .getById(
                "courts/description",
                this.announcementListAll[x].idannouncement
              )
              .subscribe((cC: any) => {
                comisions =
                  +cC.knowledgeCourts +
                  +cC.meritsCourts +
                  +cC.studentDelegateKnowledge +
                  +cC.studentDelegateMerit;
                shouldBe =
                  +cC.numberKnowledgeCourts +
                  +cC.numberKnowledgeStudentDelegate +
                  +cC.numberMeritCourts +
                  +cC.numberMeritStudentDelegate;
                if (comisions === shouldBe) {
                  this.announcementList.push(this.announcementListAll[x]);
                }
              });
          }
        },
        (error: HttpErrorResponse) => {
          this.showMessage.showErrorAlert(
            "Ocurrio un error, vuelva a intentarlo"
          );
        }
      );
  }  
 /**
  This function redirect to see the announcement
    @method  viewAnnouncement()
  */
  public viewAnnouncement(announcementA: Announcement): void {
    var areaA = announcementA.area.name;
    if (areaA === "Docencia") {
      this.router.navigate([
        "/mostrar-convocatoria-docencia",
        announcementA.idannouncement,
      ]);
    } else {
      this.router.navigate([
        "/mostrar-convocatoria-laboratorio",
        announcementA.idannouncement,
      ]);
    }
  }
   /**
  This function redirect to apply to announcement
    @method apply()
  */
  private apply(id: number, idAnnnouncement: number): void {
    const pack = this.announcementList[id]["pack"];
    this.apiService
      .getById("deadline/announcement", idAnnnouncement)
      .subscribe((response: Deadline1[]) => {
        this.deadline = response[0];
        if (this.dateValidator(this.deadline.deliveryDate)) {
          if (pack) {
            this.router.navigate(["/rotulo-auxiliaturas", idAnnnouncement]);
          } else {
            this.router.navigate(["/rotulo", idAnnnouncement]);
          }
        } else {
          this.showMessage.showErrorTeaching(
            "No es posible postularse a ésta convocatoria ",
            "La fecha máxima para postularse fue el: " +
              this.deadline.deliveryDate
          );
        }
      });
  }

  public dateValidator(date: string): boolean {
    let res = false;
    const dateToValidate = new Date(date);
    const today = new Date();
    dateToValidate.setHours(0, 0, 0, 0);
    today.setHours(0, 0, 0, 0);
    dateToValidate.setTime(dateToValidate.getTime() + 1 * 24 * 60 * 60 * 1000);
    if (dateToValidate >= today) {
      res = true;
    }
    return res;
  }
}
