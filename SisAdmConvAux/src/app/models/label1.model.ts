import { Postulant } from "./postulant.model";
import { Announcement } from "./announcement.model";
import { Auxiliary } from "./auxiliary.model";

export interface Label1 {
  idlabel: number;
  postulantes: Postulant;
  auxiliary: Auxiliary;
  announcement: Announcement;
}
