import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { UserService } from "src/app/api-service/user.service";

@Component({
  selector: "app-home-admin",
  templateUrl: "./home-admin.component.html",
  styleUrls: ["./home-admin.component.css"],
})
export class HomeAdminComponent implements OnInit {
  constructor(private router: Router, private userService: UserService) {}

  ngOnInit() {}

  logOut() {
    this.userService.removeCurrentUser();
    this.router.navigate(["/"]);
  }
}
