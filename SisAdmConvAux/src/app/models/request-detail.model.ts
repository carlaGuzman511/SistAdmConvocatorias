import { Request } from "./request.model";

export interface RequestDetail {
  idrequestdetail: number;
  description: string;
  note: string;
  request: Request;
}
