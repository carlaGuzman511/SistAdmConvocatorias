import { Auxiliary } from "./auxiliary.model";
import { Announcement } from "./announcement.model";
import { Postulant } from "./postulant.model";
export interface ResultFinalTeaching {
  idresultfinalteaching: number;
  scoreMerits: number;
  scoreTeaching: number;
  scoreWrittenTeaching: number;
  scoreOralTeaching: number;
  scoreTotal: number;
  status: boolean;
  idannouncement: Announcement;
  idauxiliary: Auxiliary;
  idpostulant: Postulant;
}
