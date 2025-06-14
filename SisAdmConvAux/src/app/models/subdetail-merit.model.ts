import { DetailMerit } from "./detail-merit.model";

export interface SubDetailMerit {
  idsubdetailmerit: number;
  description: string;
  percentage: number;
  detailmerit: DetailMerit;
}
