import { Component, OnInit } from "@angular/core";
import { ShowAlert } from "../../models/show-alert";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Observable } from "rxjs";
import { Management } from "../../models/management.model";
import { Area } from "../../models/area.model";
import { AcademicUnit } from "../../models/academic-unit.model";
import { Auxiliary } from "../../models/auxiliary.model";
import { FinalResultsParameters } from "../../models/finalResultsParameters.model";
import { FilterAuxiliary } from "../../models/filterAuxiliary.model";
import { Router } from "@angular/router";
import { ApiService } from "../../api-service/api.service";
import { HttpErrorResponse } from "@angular/common/http";
import * as jsPDF from "jspdf";
import { ResultFinalLaboratory } from "../../models/resultFinalLaboratory.model";
import { ResultFinalTeaching } from "../../models/resultFinalTeaching.model";

@Component({
  selector: "app-report-final-results",
  templateUrl: "./report-final-results.component.html",
  styleUrls: ["./report-final-results.component.css"],
})
export class ReportFinalResultsComponent {
  public showMessage = new ShowAlert();
  public results: any[];
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
  public parametersWithStatus: any;
  public paramAnnouncementStatus: any;
  public auxiliaryParameters: FilterAuxiliary = {
    idmanagement: 0,
    idarea: 0,
    idacademicunit: 0,
  };
  public varResult: string;
  public viewResults = false;
  public auxS = false;
  constructor(private apiService: ApiService, private router: Router) {
    this.createForm();
  }

  private createForm(): void {
    this.searchManagement();
    this.searchArea();
    this.searchAcademiUnit();
    this.filterForm = new FormGroup({
      year: new FormControl(" ", Validators.required),
      area: new FormControl(" ", Validators.required),
      academicUnit: new FormControl(" ", Validators.required),
      auxiliary: new FormControl(" ", Validators.required),
      statusAssigned: new FormControl(" "),
    });
  }
  searchManagement() {
    this.apiService
      .getAll("/management")
      .subscribe((res: Observable<Management[]>) => {
        this.managementList = res;
      });
  }
  searchArea() {
    this.apiService.getAll("/area").subscribe((res: Observable<Area[]>) => {
      this.areaList = res;
    });
  }
  searchAcademiUnit() {
    this.apiService
      .getAll("/academicunit")
      .subscribe((res: Observable<AcademicUnit[]>) => {
        this.academicUnitList = res;
      });
  }
  searchAuxiliary() {
    this.apiService
      .post("/announcement/auxiliarys/MAAU", this.auxiliaryParameters)
      .subscribe((res: Auxiliary[]) => {
        this.auxiliaryList = res;
      });
  }
  public searchTypeResult(i: number) {
    const areaName = this.areaList[i - 1].name;

    if (areaName === "Docencia") {
      this.varResult = "resultfinalteaching";
    }
    if (areaName === "Laboratorio") {
      this.varResult = "resultfinallaboratory";
    }
  }
  public saveDataAuxiliary(): void {
    this.auxiliaryParameters.idmanagement = +this.filterForm.controls.year
      .value;
    this.auxiliaryParameters.idarea = +this.filterForm.controls.area.value;
    this.auxiliaryParameters.idacademicunit = +this.filterForm.controls
      .academicUnit.value;
    this.searchTypeResult(+this.filterForm.controls.area.value);
    this.searchAuxiliary();
  }
  public saveDataForAnnouncement(): void {
    this.paramAnnouncementStatus = {
      idmanagement: +this.filterForm.controls.year.value,
      idarea: +this.filterForm.controls.area.value,
      idacademicunit: +this.filterForm.controls.academicUnit.value,
      status: this.filterForm.controls.statusAssigned.value,
    };
  }
  public saveData(): void {
    this.parameters.idmanagement = +this.filterForm.controls.year.value;
    this.parameters.idarea = +this.filterForm.controls.area.value;
    this.parameters.idacademicunit = +this.filterForm.controls.academicUnit
      .value;
    this.parameters.idauxiliary = +this.filterForm.controls.auxiliary.value;
  }
  public saveDataWithStatus(): void {
    this.parametersWithStatus = {
      idmanagement: +this.filterForm.controls.year.value,
      idarea: +this.filterForm.controls.area.value,
      idacademicunit: +this.filterForm.controls.academicUnit.value,
      idauxiliary: +this.filterForm.controls.auxiliary.value,
      status: this.filterForm.controls.statusAssigned.value,
    };
  }

  onSubmit() {
    if (
      this.filterForm.controls.auxiliary.value === " " ||
      this.filterForm.controls.auxiliary.value === "Todos"
    ) {
      if (
        this.filterForm.controls.statusAssigned.value === " " ||
        this.filterForm.controls.statusAssigned.value === "Todos"
      ) {
        this.apiService
          .post(`/${this.varResult}/list/MAAU`, this.auxiliaryParameters)
          .subscribe(
            (response: any[]) => {
              this.results = response;
              if (this.results.length === 0) {
                this.showMessage.showMessageError(
                  "No se encontraron los resultados"
                );
                this.viewResults = false;
              } else {
                this.viewResults = true;
              }
            },
            (error: HttpErrorResponse) => {
              this.showMessage.showMessageError(
                "Ocurrio un error, vuelva a intentarlo"
              );
            }
          );
      } else {
        this.saveDataForAnnouncement();
        this.apiService
          .post(
            `/${this.varResult}/list/MAAUstatus`,
            this.paramAnnouncementStatus
          )
          .subscribe(
            (response: any[]) => {
              this.results = response;
              if (this.results.length === 0) {
                this.showMessage.showMessageError(
                  "No se encontraron los resultados"
                );
                this.viewResults = false;
              } else {
                this.viewResults = true;
              }
            },
            (error: HttpErrorResponse) => {
              this.showMessage.showMessageError(
                "Ocurrio un error, vuelva a intentarlo"
              );
            }
          );
      }
    } else {
      this.auxS = true;
      if (
        this.filterForm.controls.statusAssigned.value === " " ||
        this.filterForm.controls.statusAssigned.value === "Todos"
      ) {
        this.saveData();
        this.apiService
          .post(`/${this.varResult}/listadoMAAUA`, this.parameters)
          .subscribe(
            (response: any[]) => {
              this.results = response;
              if (this.results.length === 0) {
                this.showMessage.showMessageError(
                  "No se encontraron los resultados"
                );
                this.viewResults = false;
              } else {
                this.viewResults = true;
              }
            },
            (error: HttpErrorResponse) => {
              this.showMessage.showMessageError(
                "Ocurrio un error, vuelva a intentarlo"
              );
            }
          );
      } else {
        this.saveDataWithStatus();
        this.apiService
          .post(
            `/${this.varResult}/list/MAAUAstatus`,
            this.parametersWithStatus
          )
          .subscribe(
            (response: any[]) => {
              this.results = response;
              if (this.results.length === 0) {
                this.showMessage.showMessageError(
                  "No se encontraron los resultados"
                );
                this.viewResults = false;
              } else {
                this.viewResults = true;
              }
            },
            (error: HttpErrorResponse) => {
              this.showMessage.showMessageError(
                "Ocurrio un error, vuelva a intentarlo"
              );
            }
          );
      }
    }
  }
  private seeDetails(id: number): void {
    this.router.navigate(["/admin/detalles-reporte/", id]);
  }

  public tableToExcel(filename: string, sheetname: string): void {
    tableToExcel(filename, sheetname);
  }
  public pdfTable(): void {
    const doc = new jsPDF("p", "pt");
    const res = doc.autoTableHtmlToJson(document.getElementById("basic-table"));
    const area = "Area: " + this.results[0]["announcement"].area.name;
    const uAcademic =
      "Unidad Academica: " + this.results[0]["announcement"].academicUnit.name;
    const year =
      "Gestion: " + this.results[0]["announcement"].management.period;
    let aux = "";
    if (this.auxS) {
      aux = "Auxiliatura: " + this.results[0]["auxiliary"].name;
    }

    const header = function (data) {
      doc.setFontSize(12);
      doc.setTextColor(40);
      doc.setFontStyle("normal");
      doc.text(
        "Reporte de Resultados Finales",
        data.settings.margin.left * 5.5,
        70
      );
      doc.text(year, data.settings.margin.left, 90);
      doc.text(uAcademic, data.settings.margin.left, 110);
      doc.text(area, data.settings.margin.left, 130);
      doc.text(aux, data.settings.margin.left, 150);
    };
    const options = {
      beforePageContent: header,

      margin: {
        top: 80,
      },

      startY: doc.autoTableEndPosY() + 170,
    };
    doc.autoTable(res.columns, res.data, options);
    doc.save("reporteFinal.pdf");
  }
}
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
