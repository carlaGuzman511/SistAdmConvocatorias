import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { UserService } from "../api-service/user.service";

@Injectable({
  providedIn: "root",
})
export class SecretaryGuard implements CanActivate {
  constructor(private userService: UserService, private router: Router) {}

  public canActivate() {
    if (this.userService.getCurrentRol() !== "Secretario") {
      //Obtenemos en nuestro servicio el rol y nos fijamos si es igual o no al de 'Admin
      console.log("Usted no posee permisos para acceder a esta ruta");
      this.userService.removeCurrentUser();
      this.router.navigate(["/"]); //Lo enviamos a la página que queramos
      return false;
    }
    return true;
  }
}
