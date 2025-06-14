import { Auxiliary } from "./auxiliary.model";
import { Postulant } from "./postulant.model";
export interface AuxiliaryPostulant {
  idauxpostulant: number;
  idauxiliary: Auxiliary;
  idPostulant: Postulant;
}
