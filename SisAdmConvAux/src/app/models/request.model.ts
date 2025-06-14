import { Announcement } from "./announcement.model";

export interface Request {
  idrequest: number;
  description: string;
  announcement: Announcement;
  note: string;
}
