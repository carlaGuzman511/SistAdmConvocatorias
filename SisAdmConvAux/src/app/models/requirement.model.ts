import { Announcement } from "./announcement.model";
import { Auxiliary } from "./auxiliary.model";

export interface Requirement {
  idrequirement: number;
  itemsQuantity: number;
  announcement: Announcement;
  auxiliary: Auxiliary;
  assigned_items: number;
}
