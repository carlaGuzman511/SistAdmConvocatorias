import { Knowledge } from "./knowledge.model";
import { Auxiliary } from "./auxiliary.model";
import { Thematic } from "./thematic.model";

export interface LaboratoryEvaluation {
  idlaboratoryevaluation: number;
  percentage: number;
  thematic: Thematic;
  auxiliary: Auxiliary;
  knowledge: Knowledge;
}
