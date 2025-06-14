import { KnowledgeEvaluation } from "./knowledge-evaluation.model";
import { Postulant } from "./postulant.model";
import { Auxiliary } from "./auxiliary.model";

export interface ResultWrittenTeachingEvaluation {
  idresultwrittenteachingevaluation: number;
  score: number;
  knowledgeEvaluation: number;
  postulant: number;
  auxiliary: number;
  iduser: number;
}

export interface ResultWrittenTeachingEvaluation1 {
  idresultwrittenteachingevaluation: number;
  score: number;
  knowledgeEvaluation: KnowledgeEvaluation;
  postulant: Postulant;
  auxiliary: Auxiliary;
}
