import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { User } from "src/app/models/user.model";
import { ShowAlertMessage } from "src/app/showAlertMessage";
import { ApiService } from "src/app/api-service/api.service";
import { Router } from "@angular/router";
import { UserService } from "src/app/api-service/user.service";
import { HttpErrorResponse } from "@angular/common/http";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
})
/**
  This class contains the login component
  @class LoginComponent
*/
export class LoginComponent implements OnInit {
  private loginForm: FormGroup;
  private showMessage = new ShowAlertMessage();
  private user: User;

  constructor(
    private apiService: ApiService,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.createLoginForm();
  }
  /**
   This function creates the login form
    @method createLoginForm()
  */
  private createLoginForm(): void {
    this.loginForm = new FormGroup({
      ci: new FormControl("", [
        Validators.minLength(6),
        Validators.maxLength(10),
        Validators.pattern("^[0-9]*$"),
        Validators.required,
      ]),
      password: new FormControl("", [
        Validators.minLength(5),
        Validators.required,
      ]),
    });
  }
  /**
   This function verify the user credentials
    @method login()
  */
  private login(): void {
    this.apiService.post("/user/login", this.loginForm.value).subscribe(
      (response: User) => {
        this.user = response;
        this.userService.setCurrentUser(this.user);
        this.showMessage.showSuccessAlert("Se inicio sesion exitosamente..!");
        this.openHome();
      },
      (error: HttpErrorResponse) => {
        this.showMessage.showError("Usuario no registrado");
      }
    );
  }
  /**
   this function allows to enter the user's home according to their role
    @method openHome()
  */
  private openHome(): void {
    if (
      this.user.role.name === "Comision de Meritos Docente" ||
      this.user.role.name === "Comision de Meritos Estudiante"
    ) {
      this.router.navigate(["merito"]);
    } else {
      if (
        this.user.role.name === "Comision de Conocimientos Docente" ||
        this.user.role.name === "Comision de Conocimientos Estudiante"
      ) {
        this.router.navigate(["conocimiento"]);
      } else {
        if (this.user.role.name === "Jefe de Departamento") {
          this.router.navigate(["admin"]);
        } else {
          if (this.user.role.name === "Secretario") {
            this.router.navigate(["secretario"]);
          } else {
            this.router.navigate(["/"]);
          }
        }
      }
    }
  }
}
