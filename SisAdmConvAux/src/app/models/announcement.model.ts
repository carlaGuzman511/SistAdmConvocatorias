import { AcademicUnit } from "./academic-unit.model";
import { Area } from "./area.model";
import { Management } from "./management.model";

export interface Announcement {
  idannouncement: number;
  title: string;
  description: string;
  state: boolean;
  appointment: string;
  area: Area;
  management: Management;
  pack: boolean;
  academicUnit: AcademicUnit;
}

export interface AnnouncementA {
  title: string;
  description: string;
  pack: boolean;
  courtsDescription: string;
  appointment: string;
  area: number;
  management: number;
  academicUnit: number;
  authorities: any[];
}

export interface AnnouncementB {
  title: string;
  description: string;
  pack: boolean;
  appointment: string;
  area: number;
  management: number;
  academicUnit: number;
  faculty: number;
  state: boolean;
}
export interface AnnouncementC {
  title: string;
  description: string;
  pack: boolean;
  courtsDescription: string;
  appointment: string;
  area: Area;
  management: number;
  academicUnit: AcademicUnit;
  faculty: number;
}
