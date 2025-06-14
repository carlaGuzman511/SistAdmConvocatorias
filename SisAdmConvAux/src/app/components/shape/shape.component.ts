import { Component, OnInit } from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  Validators,
  FormArray,
  FormControl,
} from "@angular/forms";
import { ShowAlertMessage } from "src/app/showAlertMessage";
import { ApiService } from "src/app/api-service/api.service";
import { Router, ActivatedRoute } from "@angular/router";
import { HttpErrorResponse } from "@angular/common/http";
import { ShapeModel } from "src/app/models/shape.model";

@Component({
  selector: "app-shape",
  templateUrl: "./shape.component.html",
  styleUrls: ["./shape.component.css"],
})
export class ShapeComponent implements OnInit {
  public form: FormGroup;
  public requests: any[];
  public idAnnouncemnt: number;
  public showAlertMessage = new ShowAlertMessage();
  public shape: ShapeModel = {
    description: "",
    shapes: [],
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
      description: new FormControl("", [
        Validators.minLength(10),
        Validators.maxLength(500),
        Validators.required,
      ]),
      datas: this.fb.array([this.initX()]),
    });
  }

  public initX(): any {
    return this.fb.group({
      shapex: [
        "",
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(255),
        ],
      ],
    });
  }

  public addX(): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray>this.form.controls["datas"];
    control.push(this.initX());
  }

  public removeX(index: number): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray>this.form.controls["datas"];
    control.removeAt(index);
  }

  public save(): void {
    this.shape.description = this.form.controls.description.value;
    this.shape.shapes = this.form.controls.datas.value;
    this.shape.announcement = this.idAnnouncemnt;
    console.log(this.shape);
    // tslint:disable-next-line:forin
    this.apiService.post("/shape/create", this.shape).subscribe(
      (response: number) => {
        this.showAlertMessage.showSuccessAlert("Registrado exitosamente");
        this.router.navigate(["/admin/fechaLimite", this.idAnnouncemnt]);
      },
      (error: HttpErrorResponse) => {
        this.showAlertMessage.showErrorAlert(
          "Hubo un error, vuelva a intentarlo"
        );
      }
    );
  }
}
