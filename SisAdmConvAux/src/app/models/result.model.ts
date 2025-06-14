import { Auxiliary } from "./auxiliary.model";
import { Announcement } from "./announcement.model";
import { Postulant } from "./postulant.model";
export interface Result {
  idresults: number;
  score: number;
  status: boolean;
  idannouncement: Announcement;
  idauxiliary: Auxiliary;
  idpostulant: Postulant;
}
