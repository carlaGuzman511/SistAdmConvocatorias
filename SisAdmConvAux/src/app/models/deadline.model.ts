import { Announcement } from "./announcement.model";

export interface Deadline {
  iddeadline: number;
  delivery_place: string;
  delivery_date: string;
  delivery_time: string;
  description: string;
  announcement: Announcement;
}
export interface Deadline1 {
  description: string;
  deliveryPlace: string;
  deliveryDate: string;
  deliveryTime: string;
  announcement: number;
}
