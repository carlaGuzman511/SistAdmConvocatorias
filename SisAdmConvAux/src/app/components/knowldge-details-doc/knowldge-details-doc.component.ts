import { Component } from "@angular/core";
import { Location } from "@angular/common";
import { ResultOralTeachingEvaluation1 } from "../../models/result-oral-teaching-evaluation.model";
import { ResultWrittenTeachingEvaluation1 } from "../../models/result-written-teaching-evaluation.model";
import { Router, ActivatedRoute } from "@angular/router";
import { ApiService } from "../../api-service/api.service";

@Component({
  selector: "app-knowldge-details-doc",
  templateUrl: "./knowldge-details-doc.component.html",
  styleUrls: ["./knowldge-details-doc.component.css"],
})
export class KnowldgeDetailsDocComponent {
  public oralEvaluation: ResultOralTeachingEvaluation1;
  public writtenEvaluation: ResultWrittenTeachingEvaluation1;
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
  public getData(): void {
      this.apiService.getAll(`/resultoralteachingevaluation/postulant/${this.idPos}`).subscribe( res => {
        this.oralEvaluation = res[0];
      });
    this.apiService
      .getAll(`/resultwrittenteachingevaluation/postulant/${this.idPos}`)
      .subscribe((res) => {
        this.writtenEvaluation = res[0];
      });
  }
  private goBack(): void {
    this.location.back();
  }
}
