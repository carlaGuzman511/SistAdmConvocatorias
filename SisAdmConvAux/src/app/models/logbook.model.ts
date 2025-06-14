import { Label1 } from "./label1.model";

export interface LogBook {
  idlogbook: number;
  deliveryHour: string;
  deliveryDate: string;
  document_quantity: number;
  label: Label1;
}
