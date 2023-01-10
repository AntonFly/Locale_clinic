import { Injectable, Inject } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import { Client, Implant } from '../../_models/Client';
import { Order } from 'src/app/_models/Order';

@Injectable()
export class ReportingService {
  
  baseUrl:string;

  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) { 
    this.baseUrl = baseUrl;                 
  }
  updateimplants(id:number, impl:Implant[])
  {
    var data = {"client_id":id, "implants":impl};
    return this.http.post(this.baseUrl+"medic/update_implants", data)
  }
     
  getAllClients(){
    return this.http.get<Client[]>(this.baseUrl+"medic/get_all_clients");
  }
  getSupportBySpecializId(spec: string){
    let body = "?name="+spec;
    return this.http.get<Order[]>(this.baseUrl+"medic/get_scenarios_by_spec/"+body);
  }

  getOrdersByPassport(id: number) {
    let body = "?passport="+id;
    return this.http.get<Order[]>(this.baseUrl+"medic/get_orders_by_client_passport/"+body);
  }
}