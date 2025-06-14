import { Component, EventEmitter, Output } from "@angular/core";
import { ApiService } from "../../api-service/api.service";
import { Router, ActivatedRoute } from "@angular/router";

@Component({
  selector: "app-report-final-results-view",
  templateUrl: "./report-final-results-view.component.html",
  styleUrls: ["./report-final-results-view.component.css"],
})
export class ReportFinalResultsViewComponent {
  public idPos: number;
  public typeDoc = false;
  public typeLab = false;
  @Output() idP: EventEmitter<number>;
  constructor(
    private apiService: ApiService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.idP = new EventEmitter();
    this.activatedRoute.params.subscribe((params) => {
      this.idPos = params["id"];
    });
    this.idP.emit(this.idPos);
    this.getPost();
  }

  public getPost() {
    let area;
    this.apiService.getAll(`/postulant/${this.idPos}`).subscribe((res) => {
      area = res["announcements"][0]["area"]["name"];
      if (area === "Docencia") {
        this.typeDoc = true;
      }
      if (area === "Laboratorio") {
        this.typeLab = true;
      }
    });
  }
}
