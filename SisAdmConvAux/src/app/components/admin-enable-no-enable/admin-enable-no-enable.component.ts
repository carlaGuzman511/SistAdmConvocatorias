import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { ApiService } from "../../api-service/api.service";
import { User } from "../../models/user.model";
import { ShowAlertMessage } from "src/app/showAlertMessage";
import { UserService } from "src/app/api-service/user.service";
import { LogBook } from "src/app/models/logbook.model";
import "jspdf-autotable";
import * as jsPDF from "jspdf";
import { FormGroup, FormControl } from "@angular/forms";
import { Announcement } from "src/app/models/announcement.model";
import { HttpErrorResponse } from "@angular/common/http";

@Component({
  selector: "app-admin-enable-no-enable",
  templateUrl: "./admin-enable-no-enable.component.html",
  styleUrls: ["./admin-enable-no-enable.component.css"],
})

/**
  This class contains the component that show the enabled and no enabled postulants
  @class AdminEnableNoEnableComponent
*/
export class AdminEnableNoEnableComponent implements OnInit {
  public showMessage = new ShowAlertMessage();
  public user: User;
  public logBooks: LogBook[];
  private searchForm: FormGroup;
  private announcements: Announcement[];
  private searchAnnouncementForm: FormGroup;
  private searchtext: any = {
    text: "",
    idannouncement: "",
  };
  private announcement: Announcement;

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
    This function searches the enabled and no enabled postulants
    @method searchEnabledAndNoEnabledPostulants()
  */
  private searchEnabledAndNoEnabledPostulants(indice: number): void {
    this.searchtext.idannouncement = this.announcements[indice].idannouncement;
    this.announcement = this.announcements[indice];
    this.apiService.post("/logbook/searchByText", this.searchtext).subscribe(
      (response: any) => {
        this.logBooks = response;
      },
      (error: HttpErrorResponse) => {
        this.showMessage.showError(
          "No se encontraron postulantes habilitados y no habilitados para esta convocatoria"
        );
      }
    );
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
   This function shows the annnouncement state
    @method showStatus()
  */
  private showStatus(logbook: LogBook): string {
    if (logbook.label.postulantes.status) {
      return "Habilitado";
    } else {
      return "No Habilitado";
    }
  }
  /**
   This function searches a word specific
    @method searchFuntion()
  */
  private searchFuntion(indice: number): void {
    this.searchtext.idannouncement = this.announcements[indice].idannouncement;
    this.searchtext.text = this.searchForm.controls.searchtext.value.toLowerCase();
    this.searchEnabledAndNoEnabledPostulants(indice);
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
    @method printTable()
  */
  public printTable(): void {
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
      doc.text(
        "Postulantes habilitados e inhabilitados",
        data.settings.margin.right * 5,
        90
      );
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
    doc.save("habilitados_inhabilitados.pdf");
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
