export interface Person {
  id: number;
  ci: number;
  name: string;
  lastName: string;
  address: string;
  phoneNumber: number;
  email: string;
}

export interface Person1 {
  ci: number;
  name: string;
  lastName: string;
  address: string;
  phoneNumber: number;
  email: string;
}

export interface TeachingLabels {
  person: Person;
  career: number;
  announcement: number;
  auxiliary: any[];
}
