import { Component } from "@angular/core";
import { Location } from "@angular/common";
import { ApiService } from "../../api-service/api.service";
import { Router, ActivatedRoute } from "@angular/router";
import { HttpErrorResponse } from "@angular/common/http";
import { ShowAlertMessage } from "../../showAlertMessage";
import * as jsPDF from "jspdf";
import "jspdf-autotable";
@Component({
  selector: "app-knowldge-details-lab",
  templateUrl: "./knowldge-details-lab.component.html",
  styleUrls: ["./knowldge-details-lab.component.css"],
})
export class KnowldgeDetailsLabComponent {
  public idKnowEvaluation: any;
  public knowTeachingEvaluation: any;

  private ShowAlertMessage = new ShowAlertMessage();
  public labEvaluation: any[];
  public idPos: number;
  constructor(
    private apiService: ApiService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private location: Location
  ) {
    this.activatedRoute.params.subscribe((params) => {
      this.idPos = +params["id"];
    });
    this.getData();
  }

  private goBack(): void {
    this.location.back();
  }
  public getData() {
    this.apiService
      .getAll(`/resultlaboratoryevaluation/postulant/${this.idPos}`)
      .subscribe(
        (res) => {
          this.labEvaluation = res;
        },
        (error: HttpErrorResponse) => {
          this.ShowAlertMessage.showErrorAlert(error.error.message_error);
        }
      );
  }

  public tableToExcel(filename: string, sheetname: string): void {
    tableToExcel(filename, sheetname);
  }
  public pdfTable(): void {
    const doc = new jsPDF("p", "pt");
    const res = doc.autoTableHtmlToJson(document.getElementById("basic-table"));
    const postulant =
      "Postulante: " +
      this.labEvaluation[0]["postulantes"]["person"].name +
      " " +
      this.labEvaluation[0]["postulantes"]["person"].lastName;
    const area =
      "Area: " +
      this.labEvaluation[0]["laboratoryEvaluation"]["auxiliary"].area.name;
    const aux =
      "Auxiliatura: " +
      this.labEvaluation[0]["laboratoryEvaluation"]["auxiliary"].name;

    const header = function (data) {
      doc.setFontSize(12);
      doc.setTextColor(15);
      doc.setFontStyle("normal");
      doc.text(
        "Detalles Resultados de la Evaluacion de Conocimiento",
        data.settings.margin.left,
        50
      );
      doc.text(postulant, data.settings.margin.left, 90);
      doc.text(area, data.settings.margin.left, 110);
      doc.text(aux, data.settings.margin.left, 130);
    };
    const options = {
      beforePageContent: header,
      margin: {
        top: 170,
      },
      startY: doc.autoTableEndPosY() + 170,
    };
    doc.autoTable(res.columns, res.data, options);
    doc.save("resultadosLaboratorioConocimientos.pdf");
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
