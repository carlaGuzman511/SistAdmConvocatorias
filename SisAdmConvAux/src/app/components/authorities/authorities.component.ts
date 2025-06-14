import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators, FormArray } from "@angular/forms";
import { ShowAlertMessage } from "src/app/showAlertMessage";
import { ApiService } from "src/app/api-service/api.service";
import { Router, ActivatedRoute } from "@angular/router";
import { HttpErrorResponse } from "@angular/common/http";
import { AuthorityModel } from "src/app/models/authority.model";

@Component({
  selector: "app-authorities",
  templateUrl: "./authorities.component.html",
  styleUrls: ["./authorities.component.css"],
})
export class AuthoritiesComponent implements OnInit {
  public form: FormGroup;
  public authoritiesList: any[];
  public idAnnouncemnt: number;
  public showAlertMessage = new ShowAlertMessage();
  public authority: AuthorityModel = {
    name: "",
    position: "",
    announcement: 0,
  };
  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private router: Router,
    private route: ActivatedRoute
  ) {}
  ngOnInit() {
    this.idAnnouncemnt = +this.route.snapshot.paramMap.get("id");
    this.form = this.fb.group({
      authorities: this.fb.array([this.initX()]),
    });
  }

  public initX(): any {
    return this.fb.group({
      name: [
        "",
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(50),
        ],
      ],
      position: [
        "",
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(150),
        ],
      ],
    });
  }

  public addX(): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray>this.form.controls["authorities"];
    control.push(this.initX());
  }

  public removeX(index: number): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray>this.form.controls["authorities"];
    control.removeAt(index);
  }

  public save(): void {
    this.authoritiesList = this.form.controls.authorities.value;
    // tslint:disable-next-line:forin
    for (const i in this.authoritiesList) {
      this.authority.name = this.authoritiesList[i].name;
      this.authority.position = this.authoritiesList[i].position;
      this.authority.announcement = this.idAnnouncemnt;
      this.apiService.post("/authority", this.authority).subscribe(
        (response: number) => {
          if (this.authoritiesList.length === +i + 1) {
            this.showAlertMessage.showSuccessAlert(
              "CONVOCATORIA CREADA EXITOSAMENTE!"
            );
            this.router.navigate([
              "/admin/registrar-miembro-comision",
              this.idAnnouncemnt,
            ]);
          }
        },
        (error: HttpErrorResponse) => {
          this.showAlertMessage.showErrorAlert(
            "Ocurrió un error, vuelve a intentarlo"
          );
        }
      );
    }
  }
}
