import * as internal from 'assert'
import {Client} from './Client'

export interface Order {
  id: number;
  comment:string;
  confirmation:string;
  accompanimentScript: Accompaniment;
  genome: string;
  client: Client;
  specialization: Spec;
  modifications: Mod[];
  bodyChanges: BodyChange[];
  scenario: Scenario;
}

export interface Spec {
  id: number, 
  name: string
}

export interface Accompaniment{
  id: number;
  scenarios: string;
}

export interface Mod {
  id: number;
  name: string;
  price: number;
  currency: string;
  risk: string;
  chance: number;
  accompaniment: string;
}

export interface BodyChange{
  id:number;
  change: string;
  description: string;
  symptoms: string;
  actions: string;
}

export interface Scenario{
  id: number;
  modificationScenarios: modScen;
}
export interface modScen{
  modification: Mod;
  priority: number;
}

