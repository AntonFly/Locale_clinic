export interface Client {
  id: number;
  person: Person,  
  email: string;
  comment: string;
  implants: Implant[];
  modifications: Modification[];

}

export interface Person {
  id: number,
  name: string,
  surname: string,
  patronymic: string,
  dateOfBirth: string,
  passports: any[]
}

export interface Implant {
  id: number,
  name: string,
  description: string,
  number: number,
  implantation_date: string
}

export interface Modification {
  id: number,
  name: string,
  price: number,
  currency: string, 
  risk: string,
  chance: number,
  accompaniment: string
}

// TODO not full
  