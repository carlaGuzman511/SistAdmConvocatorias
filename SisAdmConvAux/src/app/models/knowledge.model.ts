import { Announcement } from "./announcement.model";

export interface Knowledge {
  idknowledge: number;
  description: string;
  baseScore: number;
  finalScore: number;
  announcement: Announcement;
}
