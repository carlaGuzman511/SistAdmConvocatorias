import { Announcement } from "./announcement.model";

export interface Authority {
  idauthority: number;
  name: string;
  position: string;
  announcement: Announcement;
}
export interface AuthorityModel {
  name: string;
  position: string;
  announcement: number;
}
