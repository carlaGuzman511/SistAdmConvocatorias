import { SubDetailMerit } from "./subdetail-merit.model";

export interface SubSubDetailMerit {
  idsubsubdetailmerit: number;
  description: string;
  percentage: number;
  subdetailmerit: SubDetailMerit;
}
