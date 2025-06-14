import { Announcement } from "./announcement.model";

export interface Schedule {
  idschedule: number;
  description: string;
  note: string;
  announcement: Announcement;
}
