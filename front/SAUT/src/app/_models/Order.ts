import * as internal from 'assert'
import {Client} from './Client'

export interface Order {
  id: number,
  comment:string,
  client: Client,
  specialization: Spec,
  modifications: Mod[]
}

export interface Spec {
  id: number, 
  name: string
}

export interface Mod {
name: string,
price: number,
currency: string
}

