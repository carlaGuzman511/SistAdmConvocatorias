import { Person } from "./person.model";
import { Career } from "./career.model";

export interface Postulant {
  idpostulant: number;
  person: Person;
  career: Career;
  status: boolean;
}
