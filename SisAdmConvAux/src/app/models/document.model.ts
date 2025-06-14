import { Announcement } from "./announcement.model";

export interface Document {
  iddocument: number;
  name: string;
  announcement: Announcement;
  note: string;
}
