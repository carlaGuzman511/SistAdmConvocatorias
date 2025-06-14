import { Announcement } from "./announcement.model";

export interface Auxiliary {
  idauxiliary: number;
  name: string;
  code: string;
  academicHours: string;
  announcement: Announcement;
}

export interface Aux {
  idauxiliary: number;
  name: string;
  code: string;
  academicHours: string;
}
