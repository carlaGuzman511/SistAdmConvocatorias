import { Postulant } from "./postulant.model";
import { Request } from "./request.model";

export interface RequestPostulant {
  idrequestpostulant: number;
  status: boolean;
  requestdetail: Request;
  postulant: Postulant;
  observation: string;
}
