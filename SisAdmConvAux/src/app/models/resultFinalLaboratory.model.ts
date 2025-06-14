import { Auxiliary } from "./auxiliary.model";
import { Announcement } from "./announcement.model";
import { Postulant } from "./postulant.model";
export interface ResultFinalLaboratory {
  idresultfinallaboratory: number;
  scoreMerits: number;
  scoreLaboratory: number;
  scoreTotal: number;
  status: boolean;
  idannouncement: Announcement;
  idauxiliary: Auxiliary;
  idpostulant: Postulant;
}
