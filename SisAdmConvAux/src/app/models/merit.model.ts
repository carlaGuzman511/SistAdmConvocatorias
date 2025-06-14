import { Announcement } from "./announcement.model";

export interface Merit {
  idmerit: number;
  description: string;
  base_score: number;
  final_score: number;
  announcement: Announcement;
  note: string;
}

export interface MeritA {
  idmerit: number;
  description: string;
  baseScore: number;
  finalScore: number;
  announcement: Announcement;
  note: string;
}
