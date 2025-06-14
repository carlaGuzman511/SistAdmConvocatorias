import { Component, OnInit } from "@angular/core";
import { User } from "../../models/user.model";
import { Router } from "@angular/router";
import { UserService } from "../../api-service/user.service";

@Component({
  selector: "app-navbar-secretary",
  templateUrl: "./navbar-secretary.component.html",
  styleUrls: ["./navbar-secretary.component.css"],
})
export class NavbarSecretaryComponent {
  public user: User;
  public userName;
  constructor(private router: Router, private userService: UserService) {
    this.user = this.userService.getCurrentUser();

    this.getNameUser();
  }
  getNameUser() {
    this.userName = this.user.person.name + " " + this.user.person.lastName;
  }
  logOut() {
    this.userService.removeCurrentUser();
    this.router.navigate(["/"]);
  }
}
