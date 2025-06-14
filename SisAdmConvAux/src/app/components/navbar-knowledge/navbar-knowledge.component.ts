import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { UserService } from "src/app/api-service/user.service";
import { User } from "../../models/user.model";

@Component({
  selector: "app-navbar-knowledge",
  templateUrl: "./navbar-knowledge.component.html",
  styleUrls: ["./navbar-knowledge.component.css"],
})
export class NavbarKnowledgeComponent {
  public user: User;
  public userName;
  public areaName: string;
  public typeKnowD = false;
  public typeKnowL = false;
  constructor(private router: Router, private userService: UserService) {
    this.user = this.userService.getCurrentUser();
    this.getNameUser();
    this.searchTypeResult();
  }
  public getNameUser(): void {
    this.userName = this.user.person.name + ' '+ this.user.person.lastName;
  }
  public logOut(): void {
    this.userService.removeCurrentUser();
    this.router.navigate(['/']);
    }
  public searchTypeResult(): void {
    for (let i in this.user.announcements) {
      this.areaName = this.user.announcements[i].area.name;
      if (this.areaName === "Docencia") {
        this.typeKnowD = true;
      }
      if (this.areaName === "Laboratorio") {
        this.typeKnowL = true;
      }
    }
  }
}
