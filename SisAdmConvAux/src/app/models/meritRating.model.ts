/*export interface MeritTable {
  idmerit: number;
  description: string;
  baseScore: number;
  percentage: number;
  idAnnouncement: number;
}*/

export interface MeritTable {
  description: string;
  note: string;
  baseScore: number;
  finalScore: number;
  idannouncement: number;
  merits: any[];
}

export interface Merit {
  id: number;
  name: string;
  description: string;
  percentage: number;
  idmeritTable: number;
}

export interface Detail {
  id: number;
  description: string;
  percentage: number;
  idMerit: number;
}

export interface Subdetail {
  id: number;
  description: string;
  percentage: number;
  idDetail: number;
}

export interface PointDetail {
  id: number;
  description: string;
  points: number;
  idSubdetail: number;
}
