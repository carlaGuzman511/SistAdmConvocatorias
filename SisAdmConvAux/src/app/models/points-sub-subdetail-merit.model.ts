import { SubSubDetailMerit } from "./sub-subdetail-merit.model";

export interface PointsSubSubDetailMerit {
  idpointssubsubdetailmerit: number;
  description: string;
  score: number;
  subsubdetailmerit: SubSubDetailMerit;
}
