import { Person1 } from "./person.model";

export interface User1 {
  ci: number;
  password: string;
  role: number;
  person: Person1;
  announcements: number[];
  thematics: number[];
  auxiliarys: number[];
  startDate: string;
  endDate: string;
  startHour: string;
  endHour: string;
  enable: boolean;
  iduser: number;
}
