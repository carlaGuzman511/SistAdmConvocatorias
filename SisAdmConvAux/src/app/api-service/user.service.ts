import { User } from "../models/user.model";
import { isNullOrUndefined } from "util";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})

/**
 * This is the UserService class.
 */
export class UserService {
  constructor() {}

  /**
   * This method sets a current user
   */
  public setCurrentUser(user: User): void {
    const CURRENT_USER = JSON.stringify(user);
    sessionStorage.setItem("currentUser", CURRENT_USER);
  }

  /**
   * This method gets a current user
   */
  public getCurrentUser(): User {
    const CURRENT_USER = sessionStorage.getItem("currentUser");
    if (!isNullOrUndefined(CURRENT_USER)) {
      const USER = JSON.parse(CURRENT_USER);
      return USER;
    }
    return undefined;
  }

  public getCurrentRol(): string {
    const CURRENT_USER = sessionStorage.getItem("currentUser");
    if (!isNullOrUndefined(CURRENT_USER)) {
      const USER = JSON.parse(CURRENT_USER);
      return USER.role.name;
    }
    return undefined;
  }

  /**
   * This method removes a current user
   */
  public removeCurrentUser(): void {
    sessionStorage.removeItem("currentUser");
  }
}
