import { KnowledgeEvaluation } from "./knowledge-evaluation.model";
import { Postulant } from "./postulant.model";
import { Auxiliary } from "./auxiliary.model";

export interface ResultOralTeachingEvaluation {
  score: number;
  knowledgeEvaluation: number;
  postulant: number;
  auxiliary: number;
  iduser: number;
}

export interface ResultOralTeachingEvaluation1 {
  idresultoralteachingevaluation: number;
  score: number;
  knowledgeEvaluation: KnowledgeEvaluation;
  postulant: Postulant;
  auxiliary: Auxiliary;
}
