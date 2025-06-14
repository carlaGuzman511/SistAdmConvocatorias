import { Person } from "./person.model";
import { Announcement } from "./announcement.model";
import { Role } from "./role.model";
import { Thematic } from "./thematic.model";
import { Auxiliary } from "./auxiliary.model";

export interface User {
  iduser: number;
  ci: number;
  password: string;
  role: Role;
  person: Person;
  announcements: Announcement[];
  thematics: Thematic[];
  auxiliarys: Auxiliary[];
}
