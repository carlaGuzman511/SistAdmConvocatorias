import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Observable, of } from "rxjs";
import { ApiService } from "./../../api-service/api.service";
import { ShowAlert } from "src/app/models/show-alert";
import * as jsPDF from "jspdf";
import "jspdf-autotable";
import {
  FormControl,
  Validators,
  FormGroup,
  FormBuilder,
} from "@angular/forms";
import { HttpErrorResponse } from "@angular/common/http";
import { Auxiliary } from "../../models/auxiliary.model";
import { AcademicUnit } from "../../models/academic-unit.model";
import { Area } from "../../models/area.model";
import { Management } from "../../models/management.model";
import { FilterAuxiliary } from "../../models/filterAuxiliary.model";
import { FinalResultsParameters } from "../../models/finalResultsParameters.model";
import { User } from "../../models/user.model";
import { UserService } from "../../api-service/user.service";

@Component({
  selector: "app-knowldge-results",
  templateUrl: "./knowldge-results.component.html",
  styleUrls: ["./knowldge-results.component.css"],
})
/**
  This class contains the component that show the results  of knowledge tests
  @class KnowldgeResultsComponent
*/
export class KnowldgeResultsComponent {

  public showMessage = new ShowAlert();
  public results: any[];

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
    idauxiliary: 0
  };
  public auxiliaryParameters: FilterAuxiliary = {
    idmanagement: 0,
    idarea: 0,
    idacademicunit: 0,
  };
  public varResult: string;
  public areaName: string;
  public typeKnow = false; //true-docencia 
  public percentageEv: number;
  public user: User;
  public typeUser: string;
  constructor(private fb: FormBuilder, private apiService: ApiService, private userService: UserService, private router: Router) {
    this.user = this.userService.getCurrentUser();
    if (this.user.role.name === "Comision de Conocimientos Docente") {
      this.typeUser = "conocimiento";
    } else {
      this.typeUser = "admin";
    }
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
   This function is to get the existing academic unit
    @method searchAcademiUnit()
  */
  public searchAcademiUnit(): void {
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
      this.typeKnow = true;
    }
    if (this.areaName === "Laboratorio") {
      this.varResult = "resultfinallaboratory";
      this.typeKnow = false;
    }
  }
   /**
  This function is to save the value of the filters: management, area, academicUnit, to fill the auxiliary select
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
  This function is to obtain the value of the corresponding percentage of the evaluation
    @method getPercentage()
  */
  public getPercentage(id: number): void {
    var knowl;
    this.apiService.getAll(`/knowledge/announcement/${id}`).subscribe(res => {
      knowl = res;
      this.percentageEv = +knowl[0]['finalScore'];
    });
  }
  /**
  This function redirect to the corresponding view for the detail of results
    @method viewDetails(idPostulant)
  */
  public viewDetails(idPos: number): void {
    if (this.areaName === "Docencia") {
      this.router.navigate([`/${this.typeUser}/detallesConocimientoDocencia`, idPos]);
    }
    if (this.areaName === "Laboratorio") {
      this.router.navigate([`/${this.typeUser}/detallesConocimientoLaboratorio`, idPos]);
    }
  }
   /**
  This function send parameters necessary for the search of results
    @method onSubmit()
  */
  public onSubmit(): void {
    this.saveData();
    this.apiService.post(`/${this.varResult}/listadoMAAUA`, this.parameters)
      .subscribe(
        (response: any[]) => {
          this.results = response;
          this.viewResults = true;
          if ((this.results).length === 0) {
            this.showMessage.showMessageError('No se encontraron los resultados');
            this.viewResults = false;
          } else {
            this.getPercentage(+this.results[0].announcement.idannouncement);
            this.viewResults = true;
          }
        },
        (error: HttpErrorResponse) => {
          this.showMessage.showMessageError('Ocurrio un error, vuelva a intentarlo');
        }
      );
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
    var area = 'Area: ' + this.results[0]['announcement'].area.name;
    var uAcademic = 'Unidad Academica: ' + this.results[0]['announcement'].academicUnit.name;
    var year = 'Unidad Academica: ' + this.results[0]['announcement'].management.period;
    var aux = 'Auxiliatura: ' + this.results[0]['auxiliary'].name;

    var header = function (data) {
      doc.setFontSize(12);
      doc.setTextColor(15);
      doc.setFontStyle('normal');
      doc.text("Resultado de las pruebas de Conocimiento", data.settings.margin.left, 50);
      doc.text(year, data.settings.margin.left, 90);
      doc.text(uAcademic, data.settings.margin.left, 110);
      doc.text(area, data.settings.margin.left, 130);
      doc.text(aux, data.settings.margin.left, 150);
    };
    const options = {
      beforePageContent: header,
      margin: {
        top: 170,
      },
      startY: doc.autoTableEndPosY() + 170,
    };
    doc.autoTable(res.columns, res.data, options);
    doc.save('resultadosDeConocimientos.pdf');
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
