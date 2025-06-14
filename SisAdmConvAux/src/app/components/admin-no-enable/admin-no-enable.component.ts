import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Observable } from "rxjs";
import { ApiService } from "../../api-service/api.service";
import { User } from "../../models/user.model";
import { UserService } from "src/app/api-service/user.service";
import { LogBook } from "src/app/models/logbook.model";
import "jspdf-autotable";
import * as jsPDF from "jspdf";
import { Person } from "src/app/models/person.model";
import { FormGroup, FormControl } from "@angular/forms";
import { Announcement } from "src/app/models/announcement.model";
import { HttpErrorResponse } from "@angular/common/http";
import { ShowAlertMessage } from "src/app/showAlertMessage";

@Component({
  selector: "app-admin-no-enable",
  templateUrl: "./admin-no-enable.component.html",
  styleUrls: ["./admin-no-enable.component.css"],
})
/**
  This class contains the component that show the no enabled postulants
  @class AdminNoEnableComponent
*/
export class AdminNoEnableComponent implements OnInit {
  private user: User;
  public showMessage = new ShowAlertMessage();
  private logBook$: Observable<LogBook[]>;
  private logBooks: LogBook[];
  private logBookS: LogBook[];
  private persons: Person[];
  private searchForm: FormGroup;
  private announcements: Announcement[];
  private searchAnnouncementForm: FormGroup;
  private announcement: Announcement;
  private noEnable = {
    idannouncement: 0,
    status: false,
    text: "",
  };

  constructor(
    private apiService: ApiService,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.user = this.userService.getCurrentUser();
    this.announcements = this.user.announcements;
    this.searchForm = new FormGroup({
      searchtext: new FormControl(""),
    });
    this.searchAnnouncementForm = new FormGroup({
      announcement: new FormControl(""),
    });
  }
  /**
    This function shows the postulants observations
    @method reviewObservations()
  */
  private reviewObservations(log: LogBook): void {}
  /**
    This function searches the no enabled postulants
    @method searchNoEnabledPostulants()
  */
  private searchNoEnabledPostulants(indice: number): void {
    this.noEnable.idannouncement = this.announcements[indice].idannouncement;
    this.announcement = this.announcements[indice];
    this.apiService
      .post("/logbook/searchByText/status", this.noEnable)
      .subscribe(
        (response: LogBook[]) => {
          this.logBooks = response;
        },
        (error: HttpErrorResponse) => {
          this.showMessage.showError(
            "No se encontraron postulantes inhabilitados para esta convocatoria"
          );
        }
      );
  }
  /**
   This function searches a word specific
    @method searchFuntion()
  */
  private searchFuntion(indice: number): void {
    this.noEnable.idannouncement = this.announcements[indice].idannouncement;
    this.noEnable.text = this.searchForm.controls.searchtext.value.toLowerCase();
    this.searchNoEnabledPostulants(indice);
  }
  /**
   This function shows the annnouncement title
    @method titleAnnouncement()
  */
  private titleAnnouncement(): string {
    return this.announcement.title;
  }
  /**
   This function shows the annnouncement area
    @method areaAnnouncement()
  */
  private areaAnnouncement(): string {
    return this.announcement.area.name;
  }
  /**
    This function shows the annnouncement management
    @method managementAnnouncement()
  */
  private managementAnnouncement(): string {
    return this.announcement.management.period;
  }
  /**
   This function shows the annnouncement academic unit
    @method academicUnitAnnouncement()
  */
  private academicUnitAnnouncement(): string {
    return this.announcement.academicUnit.name;
  }
  /**
   This function converts the table to document excel
    @method tableToExcel()
  */
  private tableToExcel(filename: string, sheetname: string): void {
    tableToExcel(filename, sheetname);
  }
  /**
   This function converts the table to document pdf
    @method printTable()
  */
  private printTable(): void {
    const doc = new jsPDF("p", "pt");
    const res = doc.autoTableHtmlToJson(document.getElementById("basic-table"));
    const academicUnit = this.user.announcements[0].academicUnit.name;
    const area = "Area: " + this.user.announcements[0].area.name;
    const period = "Gestion: " + this.user.announcements[0].management.period;
    const header = function (data) {
      doc.setFontSize(12);
      doc.setTextColor(40);
      doc.setFontStyle("normal");
      doc.text(academicUnit, data.settings.margin.left * 5, 70);
      doc.text("Postulantes Inhabilitados", data.settings.margin.right * 6, 90);
      doc.text(area, data.settings.margin.left * 6.5, 110);
      doc.text(period, data.settings.margin.left * 6.5, 130);
    };
    const options = {
      beforePageContent: header,
      margin: {
        top: 150,
      },
      startY: doc.autoTableEndPosY() + 150,
    };
    doc.autoTable(res.columns, res.data, options);
    doc.save("inhabilitados.pdf");
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
