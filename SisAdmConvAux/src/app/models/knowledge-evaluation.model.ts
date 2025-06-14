import { Knowledge } from "./knowledge.model";

export interface KnowledgeEvaluation {
  idknowledgeevaluation: number;
  description: string;
  percentage: number;
  knowledge: Knowledge;
}
