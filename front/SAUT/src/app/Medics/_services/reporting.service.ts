import { Injectable, Inject } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import { Client, Implant } from '../../_models/Client';
import { BodyChange, Order } from 'src/app/_models/Order';

@Injectable()
export class ReportingService {
  
  baseUrl:string;

  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) { 
    this.baseUrl = baseUrl;                 
  }
  updateImplants(id:number, impl:Implant[])
  {
    var data = {"client_id":id, "implants":impl};
    return this.http.post(this.baseUrl+"medic/update_implants", data)
  }

  createChange(body:BodyChange, orderId){
    // nullify id if there is
    body["id"] = null;
    return this.http.put(this.baseUrl+'medic/update_change/?orderId='+orderId, body);
  }

  updateChange(body:BodyChange, orderId){
    return this.http.put(this.baseUrl+'medic/update_change/?orderId='+orderId, body);
  }

  getBodyChanges(orderId: number){
    return this.http.get(this.baseUrl+'medic/get_body_changes/?orderId='+orderId);
  }

  deleteChange(id)
  {
    return this.http.request('delete', this.baseUrl+'medic/drop_change', {body:id})
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