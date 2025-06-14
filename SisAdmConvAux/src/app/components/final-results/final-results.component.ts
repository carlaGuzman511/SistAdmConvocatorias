import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Observable, of } from "rxjs";
import { ApiService } from "./../../api-service/api.service";
import { ShowAlert } from "src/app/models/show-alert";
import * as jsPDF from "jspdf";
import "jspdf-autotable";
import { ResultsItem } from "../../models/resultsItems.model";
import {
  FormControl,
  Validators,
  FormGroup,
  FormBuilder,
} from "@angular/forms";
import { HttpErrorResponse } from "@angular/common/http";
import { Requirements } from "../../models/requirements.model";
import { Auxiliary } from "../../models/auxiliary.model";
import { AcademicUnit } from "../../models/academic-unit.model";
import { Area } from "../../models/area.model";
import { Management } from "../../models/management.model";
import { FilterAuxiliary } from "../../models/filterAuxiliary.model";
import { FinalResultsParameters } from "../../models/finalResultsParameters.model";
import { UserService } from "../../api-service/user.service";
import { User } from "../../models/user.model";
import { Schedule } from "../../models/schedule.model";
import { ShowAlertMessage } from "../../showAlertMessage";
import { LogBook } from "../../models/logbook.model";
import { Event } from "src/app/models/event.model";

@Component({
  selector: "app-final-results",
  templateUrl: "./final-results.component.html",
  styleUrls: ["./final-results.component.css"],
})
/**
  This class contains the component that show the final results  of knowledge tests
  @class FinalResultsComponent
*/
export class FinalResultsComponent implements OnInit {
  private user: User;
  public showMessage = new ShowAlert();
  public finalResult: any[];
  public requirements: Requirements;
  public quantityItems = 0;
  public nitems = 0;
  public assigned = 0;
  public satisfied = false;
  public viewResults = false;

  public filterForm: FormGroup;
  public managementList: Observable<Management[]>;
  public areaList: Observable<Area[]>; // Observable<Area[]>;
  public academicUnitList: Observable<AcademicUnit[]>;
  public auxiliaryList: Auxiliary[];
  public parameters: FinalResultsParameters = {
    idmanagement: 0,
    idarea: 0,
    idacademicunit: 0,
    idauxiliary: 0,
  };
  public auxiliaryParameters: FilterAuxiliary = {
    idmanagement: 0,
    idarea: 0,
    idacademicunit: 0,
  };
  public cItems: number;
  public varResult: string;
  public areaName: string;

  private schedule: Schedule;
  private events: Event[];
  private publicationOfResults: string;
  private searchtext: any = {
    text: '',
    idannouncement: '',
  };
  private documentsDelivery: string;
  private assign = true;
  private logBook$: Observable<LogBook[]>;
  public showAlertMessage = new ShowAlertMessage();

  constructor(private fb: FormBuilder, private apiService: ApiService, private router: Router, private userService: UserService,) {
    this.user = this.userService.getCurrentUser();
    this.createForm();
  }
/**
  This function create the form to search for results through filters
    @method createForm()
  */
  private createForm(): void {
    this.searchManagement();
    this.searchArea();
    this.searchAcademiUnit();
    this.filterForm = this.fb.group(
      {
        year: new FormControl(' ',
          Validators.required,
        ),
        area: [' ', Validators.required],
        academicUnit: [' ', Validators.required],
        auxiliary: [' ', Validators.required],
      },
    );
  }
   /**
    This function shows the postulants observations
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
 This function is to get the existing academic unit
   @method searchAcademiUnit()
 */
  public searchAcademiUnit() {
    this.apiService.getAll('/academicunit').subscribe((res: Observable<AcademicUnit[]>) => {
      this.academicUnitList = res;
    });
  }
 /**
 This function is to get the existing auxiliary
   @method searchAuxiliary()
 */
  public searchAuxiliary(): void {
    this.apiService.post('/announcement/auxiliarys/MAAU', this.auxiliaryParameters)
      .subscribe((res: Auxiliary[]) => {
        this.auxiliaryList = res;
      });
  }
  /**
 This function is to save the value of the route depending on the type of area
   @method searchTypeResult()
 */
  public searchTypeResult(i: number): void {
    this.areaName = this.areaList[i - 1].name;
    if (this.areaName === "Docencia") {
      this.varResult = "resultfinalteaching";
    }
    if (this.areaName === "Laboratorio") {
      this.varResult = "resultfinallaboratory";
    }
  }
   /**
  This function is to save the value of the filters to obtain auxiliary select
    @method saveDataAuxiliary()
  */
  public saveDataAuxiliary(): void {
    this.auxiliaryParameters.idmanagement = +this.filterForm.controls.year.value;
    this.auxiliaryParameters.idarea = +this.filterForm.controls.area.value;
    this.auxiliaryParameters.idacademicunit = +this.filterForm.controls.academicUnit.value;
    this.searchTypeResult(+this.filterForm.controls.area.value);
    this.searchAuxiliary();
  }
  /**
 This function is to save the value of the filters
   @method saveData()
 */
  public saveData(): void {
    this.parameters.idmanagement = +this.filterForm.controls.year.value;
    this.parameters.idarea = +this.filterForm.controls.area.value;
    this.parameters.idacademicunit = +this.filterForm.controls.academicUnit.value;
    this.parameters.idauxiliary = +this.filterForm.controls.auxiliary.value;
  }
  /**
 This function send parameters necessary for the search of results
   @method onSubmit()
 */
  public onSubmit(): void {
    this.saveData();
    this.apiService.getAll(`/requirement/auxiliary/${this.parameters.idauxiliary}`).subscribe((res: Requirements) => {
      this.requirements = res;
      this.quantityItems = +this.requirements[0]['itemsQuantity'];
      this.assigned = +this.requirements[0]['assignedItems'];
      this.nitems = this.quantityItems;
      if (this.assigned !== 0) {
        this.quantityItems = this.quantityItems - this.assigned;
      }
    });
    this.apiService.post(`/${this.varResult}/listadoMAAUA`, this.parameters)
      .subscribe(
        (response: any[]) => {
          this.finalResult = response;
          this.viewResults = true;
          if ((this.finalResult).length === 0) {
            this.showMessage.showMessageError('No se encontraron los resultados');
            this.viewResults = false;
          } else {
            this.viewResults = true;
            this.datesValidator(this.finalResult[0]['announcement']['idannouncement']);
          }
        },
        (error: HttpErrorResponse) => {
          this.showMessage.showMessageError(
            "Ocurrio un error, vuelva a intentarlo"
          );
        }
      );

  }

   /**
  This function controls the assignment of items
    @method asignItem()
  */
  public asignItem(result: any): void {
    if (result.status) {
      if (this.quantityItems >= 0 && this.assigned <= this.nitems) {
        this.quantityItems = this.quantityItems + 1;
        this.assigned = this.assigned - 1;
        result.status = false;
        this.satisfied = false;
      }
    } else {
      if (this.quantityItems > 0 && this.assigned < this.nitems) {
        this.quantityItems = this.quantityItems - 1;
        this.assigned = this.assigned + 1;
        result.status = true;
        this.satisfied = false;
      }
    }
    if (this.quantityItems === 0 && this.assigned === this.nitems) {
      this.satisfied = true;
    } else {
      this.satisfied = false;
    }
    if (this.satisfied) {
      this.showMessage.showMessageError("Ya lleno el cupo de items para ser asignados");
    }
  }
  /**
 This function save assigned items
   @method saveAssign()
 */
  private saveAssign(): void {
    var res: ResultsItem;
    var updateAssign = {
      idRequirement: this.requirements[0]['idrequirement'],
      assignedItems: this.assigned
    };
    for (let i in this.finalResult) {
      var idRes;
      if (this.areaName === "Docencia") {
        idRes = this.finalResult[i].idresultfinalteaching;
      }
      if (this.areaName === "Laboratorio") {
        idRes = this.finalResult[i].idresultfinallaboratory;
      }
      res = {
        idResult: idRes,
        status: this.finalResult[i].status,
        iduser: this.user.iduser
      }
      this.apiService.updateList(`/${this.varResult}/update/single/status`, res).subscribe(response => {
        this.showMessage.showMessageSuccess('La asignacion de items se guardo exitosamente');
        window.location.reload();
      }, (error: HttpErrorResponse) => {
        this.showMessage.showMessageError(error.error.message_error);
      });

    }
    this.apiService.updateList('/requirement/update/assigneditems', updateAssign).subscribe();
  }
  private cancel(): void {
    this.showAlertMessage.showCancelAlert('¿Seguro que quiere cancelar la asignacion de items?',
      'No se podrán recuperar los datos ingresados');
  }
    /**
   This function converts the table to document excel
    @method tableToExcel()
  */
  public tableToExcel(filename: string, sheetname: string): void {
    tableToExcel(filename, sheetname);
  }
  /**
   This function converts the table to document pdf
    @method pdfTable()
  */
  public pdfTable(): void {
    var doc = new jsPDF('p', 'pt');
    var res = doc.autoTableHtmlToJson(document.getElementById("basic-table"));
    var area = 'Area: ' + this.finalResult[0]['announcement'].area.name;
    var uAcademic = 'Unidad Academica: ' + this.finalResult[0]['announcement'].academicUnit.name;
    var year = 'Unidad Academica: ' + this.finalResult[0]['announcement'].management.period;
    var aux = 'Auxiliatura: ' + this.finalResult[0]['auxiliary'].name;
    var header = function (data) {
      doc.setFontSize(12);
      doc.setTextColor(40);
      doc.setFontStyle('normal');
      doc.text("Resultados Finales", data.settings.margin.left * 5.5, 70);
      doc.text(year, data.settings.margin.left, 90);
      doc.text(uAcademic, data.settings.margin.left, 110);
      doc.text(area, data.settings.margin.left, 130);
      doc.text(aux, data.settings.margin.left, 150);
    };
    var options = {
      beforePageContent: header,
      margin: {
        top: 80
      },
      startY: doc.autoTableEndPosY() + 170
    };
    doc.autoTable(res.columns, res.data, options);
    doc.save('resultadosFinales.pdf');
  }

   /**
   This function validate dates for item assignment
    @method datesValidator()
  */
  private datesValidator(idAnnnouncement: number): void {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    let documentsDelivery = new Date();
    let publicationOfResults = new Date();
    this.apiService.getById('schedule/announcement', idAnnnouncement).subscribe(
      (response: Schedule[]) => {
        this.schedule = response[0];
        this.apiService.getById('event/schedule', this.schedule.idschedule).subscribe(
          (events: Event[]) => {
            this.events = events;
            for (const event of this.events) {
              if (event.name === 'Presentacion de documentos') {
                this.documentsDelivery = event.dateEvent;
                documentsDelivery = new Date(this.documentsDelivery);
                documentsDelivery.setHours(0, 0, 0, 0);
                documentsDelivery.setTime(documentsDelivery.getTime() + 1 * 24 * 60 * 60 * 1000);

              } else {
                if (event.name === 'Publicacion de resultados') {
                  this.publicationOfResults = event.dateEvent;
                  publicationOfResults = new Date(this.publicationOfResults);
                  publicationOfResults.setHours(0, 0, 0, 0);
                }
              }
            }
            if (today > documentsDelivery) {
              if (today <= publicationOfResults) {
                this.apiService.post('/logbook/searchByText', this.searchtext).subscribe(
                  (labels: any) => {
                    this.logBook$ = labels;
                  },
                  (error: HttpErrorResponse) => {
                    alert(error.error.message_error);
                  });
              } else {
                this.assign = false;
                this.showMessage.showMessageError('La fecha de publicación de resultados ya sucedió, fue el: ' + this.publicationOfResults);
              }
            } else {
              this.assign = false;
              this.showMessage.showMessageError('La fecha límite de la entrega de documentos vence recién el: ' + this.documentsDelivery);
            }
          });
      });
  }

  ngOnInit() {
  }
}

var tableToExcel = (function () {
  var uri = 'data:application/vnd.ms-excel;base64,'
    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--><meta http-equiv="content-type" content="text/plain; charset=UTF-8"/></head><body><table>{table}</table></body></html>'
    , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
    , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
  return function (table, name) {
    if (!table.nodeType) table = document.getElementById(table)
    var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }
    window.location.href = uri + base64(format(template, ctx))
  }
})()

var tableToExcel = (function () {
  const uri = "data:application/vnd.ms-excel;base64,",
    template =
      '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--><meta http-equiv="content-type" content="text/plain; charset=UTF-8"/></head><body><table>{table}</table></body></html>',
    base64 = function (s) {
      return window.btoa(unescape(encodeURIComponent(s)));
    },
    format = function (s, c) {
      return s.replace(/{(\w+)}/g, function (m, p) {
        return c[p];
      });
    };
  return function (table, name) {
    if (!table.nodeType) table = document.getElementById(table);
    const ctx = { worksheet: name || "Worksheet", table: table.innerHTML };
    window.location.href = uri + base64(format(template, ctx));
  };
})();
