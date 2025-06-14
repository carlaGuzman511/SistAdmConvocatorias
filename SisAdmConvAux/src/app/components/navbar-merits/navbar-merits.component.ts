import { MenuButton } from "../../models/menu-button.model";
import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { Observable, of } from "rxjs";
import { UserService } from "src/app/api-service/user.service";
import { User } from "../../models/user.model";

@Component({
  selector: "app-navbar-merits",
  styleUrls: ["navbar-merits.component.css"],
  templateUrl: "./navbar-merits.component.html",
})
export class NavbarMeritsComponent {
  public user: User;
  public userName;
  listOfButtons$: Observable<MenuButton[]> = of([
    { route: "", name: "Salir", icon: "" },
  ]);
  constructor(private router: Router, private userService: UserService) {
    this.user = this.userService.getCurrentUser();
    if (this.user.role.name === "Comision de Meritos Docente") {
    }

    this.getNameUser();
  }
  public getNameUser(): void {
    this.userName = this.user.person.name +' '+ this.user.person.lastName;
  }
  public logOut(): void {
    this.userService.removeCurrentUser();
    this.router.navigate(["/"]);
  }
}
